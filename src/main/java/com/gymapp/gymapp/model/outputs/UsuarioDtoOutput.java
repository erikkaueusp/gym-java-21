package com.gymapp.gymapp.model.outputs;

import java.io.Serializable;
import java.util.List;

public record UsuarioDtoOutput(
        Long id,
        String username,
        List<String> perfis
) implements Serializable {
}