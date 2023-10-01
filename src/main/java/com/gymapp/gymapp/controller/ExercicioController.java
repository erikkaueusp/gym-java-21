package com.gymapp.gymapp.controller;

import com.gymapp.gymapp.model.ExercicioDto;
import com.gymapp.gymapp.service.exercicio.ExercicioService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/api/exercicio")
@SecurityRequirement(name = "bearer-key")
public class ExercicioController {

    @Autowired
    private ExercicioService service;


    @GetMapping
    public Page<ExercicioDto> getExercicios(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "sort", defaultValue = "id") String sort
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return service.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ExercicioDto getExercicio(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<Long> cadastra(@RequestBody ExercicioDto exercicioDto) {
        var id = service.save(exercicioDto);
        return ResponseEntity.ok(id);
    }
}
