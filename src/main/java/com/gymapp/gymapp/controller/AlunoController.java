package com.gymapp.gymapp.controller;

import com.gymapp.gymapp.model.inputs.AlunotDtoInput;
import com.gymapp.gymapp.model.outputs.AlunoDtoOutput;
import com.gymapp.gymapp.model.outputs.AutocompleteOutput;
import com.gymapp.gymapp.service.aluno.AlunoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("v1/api/aluno")
@SecurityRequirement(name = "bearer-key")
public class AlunoController {

    @Autowired
    private AlunoService service;

    @GetMapping
    public Page<AlunoDtoOutput> getAlunos(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "sort", defaultValue = "id") String sort
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return service.findAll(pageable);
    }

    @GetMapping("/{id}")
    public AlunoDtoOutput getAluno(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<Long> cadastra(@RequestBody AlunotDtoInput input) throws IOException {
        var id = service.save(input);
        return ResponseEntity.ok(id);
    }

    @PostMapping(value = "/importar", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> importarAlunos(@RequestParam(value = "file") MultipartFile file) {
        try {
            this.service.saveLote(file);
            return ResponseEntity.ok("Alunos importados com sucesso!");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Falha ao importar arquivo");
        }
    }

    @GetMapping("/autocomplete")
    public Page<AutocompleteOutput> autocomplete(
            @RequestParam String nome,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("nome").ascending());
        return service.autocomplete(nome, pageable);
    }

}
