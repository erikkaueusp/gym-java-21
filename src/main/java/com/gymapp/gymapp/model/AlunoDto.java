package com.gymapp.gymapp.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public record AlunoDto(Long id, String nome, String email, String endereco, String telefone, String path,
                       LocalDateTime dataCriacao) implements Serializable {
}
