package com.gymapp.gymapp.model.outputs;

import java.time.LocalDate;

public record AssinaturaVencimentoOutput(
        String nomeAluno,
        String plano,
        LocalDate dataFim,
        Long diasRestantes
) {
}

