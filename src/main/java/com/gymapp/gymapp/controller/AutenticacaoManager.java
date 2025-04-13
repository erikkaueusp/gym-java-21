package com.gymapp.gymapp.controller;

import com.gymapp.gymapp.model.LoginForm;
import com.gymapp.gymapp.model.TokenModel;
import com.gymapp.gymapp.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@ConditionalOnProperty(name = "app.auth.enabled", havingValue = "true", matchIfMissing = true)
public class AutenticacaoManager {


    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<TokenModel> autenticar(@RequestBody @Valid LoginForm form) {
        UsernamePasswordAuthenticationToken authenticationToken = form.converter();

        try {
            Authentication authentication = authManager.authenticate(authenticationToken);
            String token = tokenService.gerarToken(authentication);
            return ResponseEntity.ok(new TokenModel(token, "Bearer"));
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().build();
        }

    }
}
