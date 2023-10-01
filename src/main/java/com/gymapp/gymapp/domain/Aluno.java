package com.gymapp.gymapp.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nome;

    private String email;

    private String endereco;

    private String telefone;

    private String path;

    private LocalDateTime dataCriacao = LocalDateTime.now();
}
