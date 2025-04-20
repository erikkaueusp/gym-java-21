package com.gymapp.gymapp.model.outputs;

import com.gymapp.gymapp.enumx.Periodicidade;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

public record TotaisFinanceirosOutput(
        LocalDate inicio,
        LocalDate fim,
        Map<Periodicidade, BigDecimal> totais,
        BigDecimal totalGeral
) {}
