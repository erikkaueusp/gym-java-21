package com.gymapp.gymapp.model.outputs;

import java.io.Serializable;

public record ExercicioDtoOutput(Long id, String nome, String descricao) implements Serializable {
}
