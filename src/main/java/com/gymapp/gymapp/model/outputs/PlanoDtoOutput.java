package com.gymapp.gymapp.model.outputs;

import com.gymapp.gymapp.enumx.Periodicidade;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PlanoDtoOutput(Long id, String nome, Periodicidade periodicidade, BigDecimal preco, LocalDateTime dataCriacao) implements Serializable {
}