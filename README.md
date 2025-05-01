# Gymapp

Este projeto foi configurado para facilitar o desenvolvimento e os testes em ambientes distintos, utilizando perfis do Spring Boot para alternar entre diferentes configurações de banco de dados e autenticação.

Atualmente, existem dois conjuntos principais de perfis:

- **postgres/auth:** Para execução com **PostgreSQL** e autenticação habilitada via JWT.
- **h2/noauth:** Para desenvolvimento utilizando o banco de dados em memória **H2** com a autenticação desativada.

> **Atenção:**  
> O console do H2 é gerido por um servlet próprio (`org.h2.server.web.JakartaWebServlet`), o que pode causar conflitos com o `DispatcherServlet` do Spring MVC. Portanto, para evitar esses conflitos, **não utilize o perfil auth com o H2**. Use sempre o perfil **h2/noauth**.

---


---

## Versionamento de Banco com Liquibase

Este projeto utiliza o **[Liquibase](https://www.liquibase.org/)** para controle de versionamento do banco de dados. Todas as alterações de estrutura (schemas, constraints, dados iniciais, etc.) são rastreadas por meio de arquivos `.yaml`, garantindo que o estado do banco esteja sempre em conformidade com o código-fonte.

> ⚠️ O Liquibase é executado automaticamente durante o `startup` da aplicação, aplicando os `changeSets` pendentes ao banco configurado.

## 🧪 Classe `SchemaDiffTest` — Validação e Geração de Diff para o Liquibase

### 📌 O que é?

A classe `SchemaDiffTest` foi criada para validar automaticamente se o changelog do Liquibase está em conformidade com o modelo de entidades JPA da aplicação.  
Além disso, ela permite **gerar um diff em YAML** com as alterações que precisam ser adicionadas ao changelog (`.yaml`) do Liquibase.

---

### 🛠 Como funciona

1. Cria dois schemas temporários em um banco **H2 em memória**:
  - `hibernate_<UUID>`: carregado dinamicamente com base nas entidades JPA (via Hibernate)
  - `liquibase_<UUID>`: atualizado com os scripts existentes no arquivo `db.changelog-master.yaml`

2. Compara os dois schemas usando o mecanismo de diff do **Liquibase**


3. Se encontrar diferenças:
  - Gera o diff no console no formato YAML
  - **Faz o teste falhar**, informando que o changelog precisa ser atualizado

---

### 📂 Localização esperada do changelog

```text
src/main/resources/db/changelog/db.changelog-master.yaml
```
## Perfis de Execução

### 1. GymappApplication-Postgres-Auth
- **Perfis Ativos:** `postgres,auth`
- **Objetivo:**
    - Utilizar o PostgreSQL como banco de dados.
    - Habilitar a autenticação JWT.
- **Observações:**
    - Este perfil é ideal para ambientes que simulam a produção.
    - Utilize o arquivo `docker-compose.yml` fornecido para subir um container do PostgreSQL.

### 2. GymappApplication-H2-NoAuth
- **Perfis Ativos:** `h2,noauth`
- **Objetivo:**
    - Utilizar o H2 em memória para desenvolvimento.
    - Desabilitar a autenticação para evitar conflitos com o servlet do H2.
- **Observações:**
    - Este perfil é recomendado para desenvolvimento e testes rápidos.
    - Garante acesso ao console do H2 sem interferência dos filtros de segurança.

---

## Configuração via Variáveis de Ambiente

As configurações de banco de dados e autenticação utilizam variáveis de ambiente com valores default definidos nos arquivos de propriedades.

Exemplo de configuração no `application.properties`:

```properties
# Configuração do DataSource (PostgreSQL por padrão)
spring.datasource.url=${SPRING_DATASOURCE_URL:jdbc:postgresql://localhost:5432/gymapp}
spring.datasource.username=${SPRING_DATASOURCE_USERNAME:postgres}
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD:postgres}
