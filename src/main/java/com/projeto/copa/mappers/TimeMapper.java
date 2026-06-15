package com.projeto.copa.mappers;

import com.projeto.copa.dtos.TimeRequest;
import com.projeto.copa.dtos.TimeResponse;
import com.projeto.copa.entities.Time;

public class TimeMapper {

    // Converte a Entidade do Banco para o DTO de Saída
    public static TimeResponse toResponse(Time entity) {
        TimeResponse response = new TimeResponse();
        response.setId(entity.getId());
        response.setNome(entity.getNome());
        response.setUrlBandeira(entity.getUrlBandeira());
        return response;
    }

    // Converte o DTO de Entrada para Entidade do Banco
    public static Time toEntity(TimeRequest request) {
        Time entity = new Time();
        entity.setNome(request.getNome());
        entity.setUrlBandeira(request.getUrlBandeira());
        return entity;
    }
}