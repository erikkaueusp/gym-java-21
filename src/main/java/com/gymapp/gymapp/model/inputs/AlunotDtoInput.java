package com.gymapp.gymapp.model.inputs;

import java.io.Serializable;

// precisa adicionar a data de vencimento
public record AlunotDtoInput(String nome, String email, String endereco, String telefone, String img) implements Serializable {
}
