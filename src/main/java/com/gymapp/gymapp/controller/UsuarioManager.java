package com.gymapp.gymapp.controller;

import com.gymapp.gymapp.domain.Perfis;
import com.gymapp.gymapp.domain.Usuario;
import com.gymapp.gymapp.exception.BusinessException;
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
import java.util.List;

@RestController
@RequestMapping("/usuarios")
@PreAuthorize("hasRole('ADMINISTRADOR')")
public class UsuarioManager {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PerfisRepository perfisRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    public UsuarioDtoOutput criarUsuario(@RequestBody @Valid UsuarioDtoInput form) {
        Usuario usuario = new Usuario();
        usuario.setUsername(form.username());
        usuario.setSenha(passwordEncoder.encode(form.senha()));

        Perfis perfilFuncionario = perfisRepository.findByNome("ROLE_FUNCIONARIO")
                .orElseThrow(() -> new IllegalArgumentException("Perfil FUNCIONARIO não encontrado"));

        usuario.setPerfis(Collections.singletonList(perfilFuncionario));

        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        return new UsuarioDtoOutput(
                usuarioSalvo.getId(),
                usuarioSalvo.getUsername(),
                usuarioSalvo.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList()
        );
    }

    @GetMapping
    public List<UsuarioDtoOutput> listarUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();

        return usuarios.stream()
                .filter(usuario -> usuario.getAuthorities().stream()
                        .anyMatch(authority -> authority.getAuthority().equals("ROLE_FUNCIONARIO"))
                )
                .map(usuario -> new UsuarioDtoOutput(
                        usuario.getId(),
                        usuario.getUsername(),
                        usuario.getAuthorities().stream()
                                .map(GrantedAuthority::getAuthority)
                                .toList()
                ))
                .toList();
    }

    @DeleteMapping("/{id}")
    public void excluirUsuario(@PathVariable Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Usuário não encontrado"));

        boolean isAdmin = usuario.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMINISTRADOR"));

        if (isAdmin) {
            throw new BusinessException("Usuário ADMINISTRADOR não pode ser excluído");
        }

        usuarioRepository.delete(usuario);
    }




}