package com.gymapp.gymapp.model;

import java.io.Serializable;

public record ExercicioDto(Long id, String nome, String descricao) implements Serializable {
}
