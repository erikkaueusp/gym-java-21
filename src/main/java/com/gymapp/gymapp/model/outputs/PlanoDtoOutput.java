package com.gymapp.gymapp.model.outputs;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

public record PlanoDtoOutput(Long id, AlunoDtoOutput aluno, BigDecimal valor, LocalDate vencimento,
                             boolean isExpirado) implements Serializable {
}