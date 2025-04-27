package com.gymapp.gymapp.controller;

import com.gymapp.gymapp.domain.Perfis;
import com.gymapp.gymapp.domain.Usuario;
import com.gymapp.gymapp.model.inputs.UsuarioDtoInput;
import com.gymapp.gymapp.model.outputs.UsuarioDtoOutput;
import com.gymapp.gymapp.repository.PerfisRepository;
import com.gymapp.gymapp.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;

@RestController
@RequestMapping("/usuarios")
public class UsuarioManager {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PerfisRepository perfisRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public UsuarioDtoOutput criarUsuario(@RequestBody @Valid UsuarioDtoInput form) {
        Usuario usuario = new Usuario();
        usuario.setUsername(form.username());
        usuario.setSenha(passwordEncoder.encode(form.senha()));

        Perfis perfilFuncionario = perfisRepository.findByNome("ROLE_FUNCIONARIO")
                .orElseThrow(() -> new IllegalArgumentException("Perfil FUNCIONARIO não encontrado"));

        usuario.setPerfis(Collections.singletonList(perfilFuncionario));

        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        return new UsuarioDtoOutput(
                usuarioSalvo.getUsername(),
                usuarioSalvo.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList()
        );
    }
}