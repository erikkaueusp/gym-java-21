package com.gymapp.gymapp.config;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@ConditionalOnProperty(name = "agendamento.habilitado", havingValue = "true")
public class ConfiguracaoDeAgendamento {

    private static final Logger logger = LoggerFactory.getLogger(ConfiguracaoDeAgendamento.class);

    @PostConstruct
    public void logarStatusAgendamento() {
        logger.info("Agendamento habilitado");
    }

}
