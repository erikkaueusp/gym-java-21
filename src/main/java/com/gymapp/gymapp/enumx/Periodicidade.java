package com.gymapp.gymapp.enumx;

import java.time.LocalDate;
import java.time.Period;

public enum Periodicidade {
    DAILY (Period.ofDays(1)),
    MONTHLY(Period.ofMonths(1)),
    ANNUAL (Period.ofYears(1));

    private final Period duration;
    Periodicidade(Period d){ this.duration = d; }

    /** Retorna a data‑fim inclusiva (aluno pode usar até esse dia). */
    public LocalDate calcularFim(LocalDate inicio){
        return inicio.plus(duration).minusDays(1);
    }
}

