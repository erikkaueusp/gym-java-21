package com.gymapp.gymapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    //TODO Ajustar essa configuração de cors pois o @EnableWebMVC na classe acaba gerando problemas na configuração de retorn de datas
    // https://stackoverflow.com/questions/63682619/localdatetime-is-representing-in-array-format
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }
}
