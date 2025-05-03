package com.gymapp.gymapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MonitoringController {

    @GetMapping("/monitoring")
    public ResponseEntity<String> monitor() {
        return ResponseEntity.ok("OK");
    }
}
