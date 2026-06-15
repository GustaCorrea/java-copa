package com.projeto.copa.mappers;

import com.projeto.copa.dtos.PartidaResponse;
import com.projeto.copa.entities.Partida;

public class PartidaMapper {

    public static PartidaResponse toResponse(Partida entity) {
        if (entity == null)
            return null;

        PartidaResponse response = new PartidaResponse();
        response.setId(entity.getId());
        response.setFase(entity.getFase());
        response.setPlacarCasa(entity.getPlacarCasa());
        response.setPlacarFora(entity.getPlacarFora());
        response.setConcluida(entity.getConcluida());
        response.setVencedorId(entity.getVencedorId());

        response.setTimeCasa(TimeMapper.toResponse(entity.getTimeCasa()));
        response.setTimeFora(TimeMapper.toResponse(entity.getTimeFora()));

        return response;
    }
}