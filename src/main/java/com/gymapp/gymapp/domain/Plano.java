package com.gymapp.gymapp.domain;

import com.gymapp.gymapp.enumx.Periodicidade;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Plano {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Enumerated(EnumType.STRING)
    private Periodicidade periodicidade;

    private BigDecimal preco;

    private LocalDateTime dataCriacao = LocalDateTime.now();
}
