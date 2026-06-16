package com.projeto.copa.controllers;

import com.projeto.copa.dtos.CopaResponse;
import com.projeto.copa.dtos.IniciarCopaRequest;
import com.projeto.copa.services.CopaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
        RequestMethod.DELETE })
@RestController
@RequestMapping("/api/copa")
public class CopaController {

    private final CopaService copaService;

    public CopaController(CopaService copaService) {
        this.copaService = copaService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CopaResponse> buscarPorId(@PathVariable Long id) {
        CopaResponse response = copaService.findById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CopaResponse> iniciarCopa(@Valid @RequestBody IniciarCopaRequest request) {
        CopaResponse response = copaService.iniciarCopa(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCopa(@PathVariable Long id) {
        copaService.deleteCopa(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/time/{novoTimeId}")
    public ResponseEntity<CopaResponse> trocarTime(@PathVariable Long id, @PathVariable Long novoTimeId) {
        CopaResponse response = copaService.trocarTime(id, novoTimeId);
        return ResponseEntity.ok(response);
    }
}