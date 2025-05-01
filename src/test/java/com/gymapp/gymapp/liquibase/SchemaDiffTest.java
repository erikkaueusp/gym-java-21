package com.gymapp.gymapp.liquibase;

import jakarta.persistence.EntityManagerFactory;
import liquibase.*;
import liquibase.changelog.ChangeSet;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.diff.DiffGeneratorFactory;
import liquibase.diff.DiffResult;
import liquibase.diff.compare.CompareControl;
import liquibase.diff.output.DiffOutputControl;
import liquibase.diff.output.changelog.DiffToChangeLog;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.serializer.ChangeLogSerializerFactory;
import org.hibernate.cfg.AvailableSettings;
import org.junit.jupiter.api.Test;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static org.junit.jupiter.api.Assertions.fail;

public class SchemaDiffTest {

    private static final String H2_BASE_URL = "jdbc:h2:mem:refdb;DB_CLOSE_DELAY=-1;MODE=PostgreSQL";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "";
    private static final String DB_CHANGELOG_PATH = "db/changelog/db.changelog-master.yaml";
    private static final String ENTITY_PACKAGE = "com.gymapp.gymapp.domain";
    private static final String YAML_FORMAT = "yaml";

    @Test
    void testSchemaDiffInMemory() throws Exception {
        String schemaHibernate = "hibernate_" + UUID.randomUUID().toString().replace("-", "");
        String schemaLiquibase = "liquibase_" + UUID.randomUUID().toString().replace("-", "");

        Database referenceDatabase = initHibernateSchema(schemaHibernate);
        Database liquibaseTarget = initLiquibaseSchema(schemaLiquibase);

        DiffResult diffResult = DiffGeneratorFactory.getInstance()
                .compare(referenceDatabase, liquibaseTarget, CompareControl.STANDARD);

        List<ChangeSet> changeLog = new DiffToChangeLog(diffResult,
                new DiffOutputControl().setIncludeCatalog(false).setIncludeSchema(false))
                .generateChangeSets();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ChangeLogSerializerFactory.getInstance().getSerializer(YAML_FORMAT).write(changeLog, out);

        String yaml = out.toString(StandardCharsets.UTF_8);
        System.err.println("=== Liquibase Schema Diff (YAML) ===\n" + yaml);

        if (!changeLog.isEmpty()) fail("Esquema desatualizado. Ajuste seu changelog.");
        System.out.println("[OK] Nenhuma diferença entre os esquemas.");
    }

    private Database initHibernateSchema(String schema) throws Exception {
        String url = buildH2Url(schema);
        Map<String, Object> props = Map.of(
                AvailableSettings.JAKARTA_HBM2DDL_DATABASE_ACTION, "create",
                AvailableSettings.URL, url,
                AvailableSettings.USER, DB_USER,
                AvailableSettings.PASS, DB_PASSWORD,
                AvailableSettings.DIALECT, "org.hibernate.dialect.PostgreSQLDialect",
                AvailableSettings.PHYSICAL_NAMING_STRATEGY, "org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy",
                AvailableSettings.IMPLICIT_NAMING_STRATEGY, "org.hibernate.boot.model.naming.ImplicitNamingStrategyLegacyJpaImpl"
        );



        var factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setPackagesToScan(ENTITY_PACKAGE);
        factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factoryBean.setJpaPropertyMap(props);
        factoryBean.afterPropertiesSet();
        Optional.ofNullable(factoryBean.getObject()).ifPresent(EntityManagerFactory::close);

        return DatabaseFactory.getInstance().openDatabase(url, DB_USER, DB_PASSWORD, null, new ClassLoaderResourceAccessor());
    }

    private Database initLiquibaseSchema(String schema) throws Exception {
        String url = buildH2Url(schema);
        Database db = DatabaseFactory.getInstance().openDatabase(url, DB_USER, DB_PASSWORD, null, new ClassLoaderResourceAccessor());
        db.setDefaultSchemaName(schema);

        new Liquibase(DB_CHANGELOG_PATH, new ClassLoaderResourceAccessor(), db)
                .update(new Contexts(), new LabelExpression());

        return db;
    }

    private String buildH2Url(String schema) {
        return H2_BASE_URL + ";INIT=CREATE SCHEMA IF NOT EXISTS " + schema + "\\;SET SCHEMA " + schema;
    }
}
