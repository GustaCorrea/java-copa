package com.projeto.copa.controllers;

import com.projeto.copa.dtos.PartidaResponse;
import com.projeto.copa.dtos.SalvarPlacarRequest;
import com.projeto.copa.services.CopaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/partidas")
@CrossOrigin(origins = "*")
public class PartidaController {

    private final CopaService copaService;

    public PartidaController(CopaService copaService) {
        this.copaService = copaService;
    }

    @PutMapping("/{id}")
    public ResponseEntity<PartidaResponse> salvarPlacar(@PathVariable Long id,
            @RequestBody SalvarPlacarRequest request) {
        return ResponseEntity.ok(copaService.salvarPlacar(id, request));
    }
}