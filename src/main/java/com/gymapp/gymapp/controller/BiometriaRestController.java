package com.gymapp.gymapp.controller;

import com.gymapp.gymapp.model.outputs.BiometriaOutput;
import com.gymapp.gymapp.service.BiometriaWebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/biometria")
public class BiometriaRestController {

    @Autowired
    private BiometriaWebSocketService service;

    @PostMapping("/cadastrar-digital/{idAluno}")
    public ResponseEntity<BiometriaOutput> cadastrarDigital(@PathVariable Long idAluno) {
        return ResponseEntity.ok(new BiometriaOutput("Biometria liberada para registro", service.cadastrarComInterrupcaoModoScan(idAluno)));
    }

    @PostMapping("/liberar-acesso")
    public ResponseEntity<BiometriaOutput> liberarAcesso() {
        service.destravar();
        return ResponseEntity.ok(new BiometriaOutput("Destravar catraca", null));
    }

    @PostMapping("/ativar-scan")
    public ResponseEntity<BiometriaOutput> ativarModoScan() {
        service.ativarModoScan();
        return ResponseEntity.ok(new BiometriaOutput("Modo scan ativado", null));
    }

    @PostMapping("/desativar-scan")
    public ResponseEntity<BiometriaOutput> desativarModoScan() {
        service.desativarModoScan();
        return ResponseEntity.ok(new BiometriaOutput("Modo scan desativado", null));
    }

    public static class CadastroRequest {
        private String idCatraca;

        public String getIdCatraca() {
            return idCatraca;
        }

        public void setIdCatraca(String idCatraca) {
            this.idCatraca = idCatraca;
        }
    }
}
