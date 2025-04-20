package com.gymapp.gymapp.model.outputs;

import java.io.Serializable;
import java.time.LocalDateTime;

public record AlunoDtoOutput(Long id, String nome, String email, String endereco, String telefone, String img,
                             LocalDateTime dataCriacao) implements Serializable {
}
