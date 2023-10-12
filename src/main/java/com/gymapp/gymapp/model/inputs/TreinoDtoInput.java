package com.gymapp.gymapp.model.inputs;

import java.io.Serializable;

public record TreinoDtoInput(Long alunoId, Long exercicioId, Long repeticao,
                             String duracao) implements Serializable {
}
