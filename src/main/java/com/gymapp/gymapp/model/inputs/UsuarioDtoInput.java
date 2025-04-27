package com.gymapp.gymapp.model.inputs;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public record UsuarioDtoInput(
        @NotBlank String username,
        @NotBlank String senha
) implements Serializable {
}
