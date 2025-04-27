package com.gymapp.gymapp.model.inputs;

import com.gymapp.gymapp.enumx.Periodicidade;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
public class AssinaturaFilter {

    private String nomeAluno;
    private Periodicidade periodicidade;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dataInicio;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dataFim;

    private Boolean ativa;
}

