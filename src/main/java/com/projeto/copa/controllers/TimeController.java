package com.projeto.copa.controllers;

import com.projeto.copa.dtos.TimeResponse;
import com.projeto.copa.services.TimeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/times")
@CrossOrigin(origins = "*")
public class TimeController {

    private final TimeService service;

    public TimeController(TimeService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<TimeResponse>> listarTodos() {
        List<TimeResponse> lista = service.findAll();
        return ResponseEntity.ok(lista);
    }
}