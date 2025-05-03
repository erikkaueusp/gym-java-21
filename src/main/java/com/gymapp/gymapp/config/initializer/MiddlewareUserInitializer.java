package com.gymapp.gymapp.config.initializer;

import com.gymapp.gymapp.domain.Perfis;
import com.gymapp.gymapp.domain.Usuario;
import com.gymapp.gymapp.repository.PerfisRepository;
import com.gymapp.gymapp.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class MiddlewareUserInitializer {

    @Autowired
    private  UsuarioRepository usuarioRepository;

    @Autowired
    private  PerfisRepository perfisRepository;

    @Autowired
    private  PasswordEncoder passwordEncoder;

    @Value("${middleware.username}")
    private String username;

    @Value("${middleware.password}")
    private String password;

    @EventListener(ContextRefreshedEvent.class)
    @Order(1)
    public void init() {
        if (usuarioRepository.findByUsername(username).isPresent()) {
            System.out.println("Usuário middleware já existe.");
            return;
        }

        System.out.println("Criando usuário middleware...");
        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        usuario.setSenha(passwordEncoder.encode(password));

        Perfis perfil = perfisRepository.findByNome("ROLE_MIDDLEWARE")
                .orElseThrow(() -> new IllegalArgumentException("Perfil ROLE_MIDDLEWARE não encontrado"));

        usuario.setPerfis(Collections.singletonList(perfil));
        usuarioRepository.save(usuario);
        System.out.println("Usuário middleware criado.");
    }
}

