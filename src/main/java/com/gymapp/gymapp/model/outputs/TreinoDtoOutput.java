package com.gymapp.gymapp.model.outputs;

import java.io.Serializable;

public record TreinoDtoOutput(Long id, AlunoDtoOutput aluno, ExercicioDtoOutput exercicio, Long repeticao,
                              String duracao) implements Serializable {
}
