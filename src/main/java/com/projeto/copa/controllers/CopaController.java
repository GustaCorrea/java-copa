package com.projeto.copa.controllers;

import com.projeto.copa.dtos.CopaResponse;
import com.projeto.copa.dtos.IniciarCopaRequest;
import com.projeto.copa.services.CopaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/copa")
@CrossOrigin(origins = "*")
public class CopaController {

    private final CopaService copaService;

    public CopaController(CopaService copaService) {
        this.copaService = copaService;
    }

    @PostMapping
    public ResponseEntity<CopaResponse> iniciarCopa(@Valid @RequestBody IniciarCopaRequest request) {
        CopaResponse response = copaService.iniciarCopa(request);
        return ResponseEntity.ok(response);
    }
}