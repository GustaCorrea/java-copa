package com.projeto.copa.mappers;

import com.projeto.copa.dtos.CopaResponse;
import com.projeto.copa.dtos.PartidaResponse;
import com.projeto.copa.entities.Copa;
import com.projeto.copa.entities.Partida;

import java.util.ArrayList;
import java.util.List;

public class CopaMapper {

    public static CopaResponse toResponse(Copa entity) {
        if (entity == null)
            return null;

        CopaResponse response = new CopaResponse();
        response.setId(entity.getId());
        response.setFaseAtual(entity.getFaseAtual());
        response.setStatus(entity.getStatus());

        response.setTimeDoJogador(TimeMapper.toResponse(entity.getTimeDoJogador()));

        List<PartidaResponse> listaPartidas = new ArrayList<>();
        if (entity.getPartidas() != null) {
            for (Partida partida : entity.getPartidas()) {
                listaPartidas.add(PartidaMapper.toResponse(partida));
            }
        }
        response.setPartidas(listaPartidas);

        return response;
    }
}