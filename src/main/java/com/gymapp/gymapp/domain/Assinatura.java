package com.gymapp.gymapp.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"aluno_id", "ativa"})
})
@Getter
@Setter
@NoArgsConstructor
public class Assinatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Aluno aluno;

    @ManyToOne(optional = false)
    private Plano plano;

    private LocalDate dataInicio;

    private LocalDate dataFim;

    private boolean ativa;

    public Assinatura(Aluno aluno, Plano plano, LocalDate dataInicio, LocalDate dataFim, boolean ativa) {
        this.aluno = aluno;
        this.plano = plano;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.ativa = ativa;
    }
}
