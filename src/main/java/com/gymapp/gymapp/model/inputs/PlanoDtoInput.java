package com.gymapp.gymapp.model.inputs;

import com.gymapp.gymapp.enumx.Periodicidade;

import java.io.Serializable;
import java.math.BigDecimal;

public record PlanoDtoInput(String nome, Periodicidade periodicidade, BigDecimal preco) implements Serializable {
}