package com.gymapp.gymapp.config;

import com.gymapp.gymapp.config.interceptors.JwtHandshakeInterceptor;
import com.gymapp.gymapp.service.BiometriaWebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private BiometriaWebSocketService service;

    @Autowired
    private JwtHandshakeInterceptor jwtHandshakeInterceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(service, "/biometria")
                .addInterceptors(jwtHandshakeInterceptor)
                .setAllowedOrigins("*"); // Depois podemos melhorar para apenas suas origens
    }
}
