package com.gymapp.gymapp.model;

import java.io.Serializable;

public record TreinoDto(Long id, AlunoDto aluno, ExercicioDto exercicio, Long repeticao,
                        String duracao) implements Serializable {
}
