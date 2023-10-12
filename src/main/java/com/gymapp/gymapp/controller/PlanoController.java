package com.gymapp.gymapp.controller;


import com.gymapp.gymapp.model.inputs.PlanoDtoInput;
import com.gymapp.gymapp.model.outputs.PlanoDtoOutput;
import com.gymapp.gymapp.service.plano.PlanoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/api/plano")
@SecurityRequirement(name = "bearer-key")
public class PlanoController {

    @Autowired
    private PlanoService service;

    @PostMapping
    public ResponseEntity<Long> cadastra(@RequestBody PlanoDtoInput input) {
        var id = service.save(input);
        return ResponseEntity.ok(id);
    }

    @GetMapping("/{id}")
    public PlanoDtoOutput getPlano(@PathVariable Long id) {
        return this.service.findById(id);
    }

    @GetMapping
    public Page<PlanoDtoOutput> getPlanos(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "sort", defaultValue = "id") String sort
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return service.findAll(pageable);
    }

}
