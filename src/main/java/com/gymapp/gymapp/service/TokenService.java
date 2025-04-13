package com.gymapp.gymapp.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.gymapp.gymapp.domain.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

@Service
@ConditionalOnProperty(name = "app.auth.enabled", havingValue = "true", matchIfMissing = true)
public class TokenService {

    @Value("${variavel.jwt.expiration}")
    private Long expiration;

    @Value("${secret.jwt}")
    private String secret;

    public String gerarToken(Authentication authentication) {

        Usuario logado = (Usuario) authentication.getPrincipal();
        LocalDate hoje = LocalDate.now();
        Instant instant = Instant.ofEpochMilli(expiration);
        LocalDate dataExpiracao = instant.atZone(ZoneId.systemDefault()).toLocalDate();

        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("auth0")
                    .withSubject(logado.getUsername())
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception){
            throw exception;
        }
    }

    public String  getSubjectFromToken (String token) {
        DecodedJWT decodedJWT;
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    // specify an specific claim validations
                    .withIssuer("auth0")
                    // reusable verifier instance
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception){
            throw exception;
        }
    }
}
