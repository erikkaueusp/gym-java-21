package com.gymapp.gymapp.controller;


import com.gymapp.gymapp.model.inputs.TreinoDtoInput;
import com.gymapp.gymapp.model.outputs.TreinoDtoOutput;
import com.gymapp.gymapp.service.treino.TreinoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/api/treino")
@SecurityRequirement(name = "bearer-key")
public class TreinoController {

    @Autowired
    private TreinoService service;

    @GetMapping
    public Page<TreinoDtoOutput> getTreinos(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "sort", defaultValue = "id") String sort
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return service.findAll(pageable);
    }

    @GetMapping("/{id}")
    public TreinoDtoOutput getAluno(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<Long> cadastra(@RequestBody TreinoDtoInput input) {
        Long id = service.save(input);
        return ResponseEntity.ok(id);
    }

}
