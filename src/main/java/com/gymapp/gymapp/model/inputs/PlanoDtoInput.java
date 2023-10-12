package com.gymapp.gymapp.model.inputs;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

public record PlanoDtoInput(Long alunoIdId, BigDecimal valor, LocalDate vencimento) implements Serializable {
}