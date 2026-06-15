package com.projeto.copa.dtos;

import com.projeto.copa.enums.FaseCopa;
import com.projeto.copa.enums.StatusCopa;
import java.util.List;

public class CopaResponse {

    private Long id;
    private TimeResponse timeDoJogador;
    private FaseCopa faseAtual;
    private StatusCopa status;
    private List<PartidaResponse> partidas;

    public CopaResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TimeResponse getTimeDoJogador() {
        return timeDoJogador;
    }

    public void setTimeDoJogador(TimeResponse timeDoJogador) {
        this.timeDoJogador = timeDoJogador;
    }

    public FaseCopa getFaseAtual() {
        return faseAtual;
    }

    public void setFaseAtual(FaseCopa faseAtual) {
        this.faseAtual = faseAtual;
    }

    public StatusCopa getStatus() {
        return status;
    }

    public void setStatus(StatusCopa status) {
        this.status = status;
    }

    public List<PartidaResponse> getPartidas() {
        return partidas;
    }

    public void setPartidas(List<PartidaResponse> partidas) {
        this.partidas = partidas;
    }
}