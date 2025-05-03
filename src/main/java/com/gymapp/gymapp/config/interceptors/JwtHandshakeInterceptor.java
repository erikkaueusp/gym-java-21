package com.gymapp.gymapp.config.interceptors;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.gymapp.gymapp.repository.UsuarioRepository;
import com.gymapp.gymapp.service.TokenService;
import com.gymapp.gymapp.domain.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.List;
import java.util.Map;

@Component
public class JwtHandshakeInterceptor implements HandshakeInterceptor {

    @Autowired
    private  TokenService tokenService;

    @Autowired
    private  UsuarioRepository usuarioRepository;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) {
        List<String> authHeaders = request.getHeaders().get(HttpHeaders.AUTHORIZATION);
        if (authHeaders != null && !authHeaders.isEmpty()) {
            String token = authHeaders.get(0).replace("Bearer ", "");

            try {
                String subject = tokenService.getSubjectFromToken(token);
                Usuario usuario = usuarioRepository.findByUsername(subject).orElse(null);
                if (usuario != null) {
                    attributes.put("usuario", usuario);
                    return true;
                }
            } catch (JWTVerificationException ex) {
                response.setStatusCode(org.springframework.http.HttpStatus.UNAUTHORIZED);
                return false;
            }
        }

        response.setStatusCode(org.springframework.http.HttpStatus.FORBIDDEN);
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception exception) {
        // nada a fazer por enquanto
    }
}

