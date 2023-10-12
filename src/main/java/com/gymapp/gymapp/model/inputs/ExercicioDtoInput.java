package com.gymapp.gymapp.model.inputs;

import java.io.Serializable;

public record ExercicioDtoInput(String nome, String descricao) implements Serializable {
}
