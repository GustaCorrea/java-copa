package com.projeto.copa.services;

import com.projeto.copa.dtos.TimeResponse;
import com.projeto.copa.entities.Time;
import com.projeto.copa.mappers.TimeMapper;
import com.projeto.copa.repositories.TimeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TimeService {

    private final TimeRepository repository;

    public TimeService(TimeRepository repository) {
        this.repository = repository;
    }

    public List<TimeResponse> findAll() {
        List<Time> times = repository.findAll();
        List<TimeResponse> responses = new ArrayList<>();

        for (Time time : times) {
            responses.add(TimeMapper.toResponse(time));
        }

        return responses;
    }
}