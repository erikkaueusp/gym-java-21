package com.gymapp.gymapp.controller;

import com.gymapp.gymapp.domain.Assinatura;
import com.gymapp.gymapp.model.inputs.AssinaturaFilter;
import com.gymapp.gymapp.model.outputs.AssinaturaVencimentoOutput;
import com.gymapp.gymapp.model.outputs.TotaisFinanceirosOutput;
import com.gymapp.gymapp.service.assinatura.AssinaturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/assinaturas")
public class AssinaturaController {

    @Autowired
    private AssinaturaService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Assinatura create(@RequestBody NovaAssinaturaDTO dto) {
        return service.criarAssinatura(dto.alunoId(), dto.planoId());
    }

    @GetMapping("/totais")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public TotaisFinanceirosOutput totais(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim
    ) {
        return service.calcularTotaisFinanceiros(inicio, fim);
    }

    /** Expira manualmente todas as assinaturas vencidas (útil em dev). */
    @PostMapping("/expirar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void expirarManualmente() {
        service.expirarAssinaturas();
    }

    @GetMapping("/filter")
    public Page<Assinatura> filtrar(
            @ModelAttribute AssinaturaFilter filter,
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {

        return service.listarPorFiltro(filter, pageable);
    }

    @GetMapping("/proximos-vencimentos")
    public Page<AssinaturaVencimentoOutput> listarProximosVencimentos(
            @RequestParam(defaultValue = "1") int dias,
            Pageable pageable
    ) {
        return service.listarProximosVencimentos(pageable, dias);
    }

    /* ---------- DTOs internos ---------- */

    /**
     * Corpo esperado em POST /assinaturas
     * {
     *   "alunoId": 42,
     *   "planoId": 3,
     * }
     */
    public record NovaAssinaturaDTO(
            Long alunoId,
            Long planoId) {}
}

