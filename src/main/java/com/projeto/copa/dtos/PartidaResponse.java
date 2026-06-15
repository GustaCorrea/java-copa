package com.projeto.copa.dtos;

import com.projeto.copa.enums.FaseCopa;

public class PartidaResponse {

    private Long id;
    private FaseCopa fase;
    private TimeResponse timeCasa;
    private TimeResponse timeFora;
    private Integer placarCasa;
    private Integer placarFora;
    private Boolean concluida;
    private Long vencedorId;

    public PartidaResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FaseCopa getFase() {
        return fase;
    }

    public void setFase(FaseCopa fase) {
        this.fase = fase;
    }

    public TimeResponse getTimeCasa() {
        return timeCasa;
    }

    public void setTimeCasa(TimeResponse timeCasa) {
        this.timeCasa = timeCasa;
    }

    public TimeResponse getTimeFora() {
        return timeFora;
    }

    public void setTimeFora(TimeResponse timeFora) {
        this.timeFora = timeFora;
    }

    public Integer getPlacarCasa() {
        return placarCasa;
    }

    public void setPlacarCasa(Integer placarCasa) {
        this.placarCasa = placarCasa;
    }

    public Integer getPlacarFora() {
        return placarFora;
    }

    public void setPlacarFora(Integer placarFora) {
        this.placarFora = placarFora;
    }

    public Boolean getConcluida() {
        return concluida;
    }

    public void setConcluida(Boolean concluida) {
        this.concluida = concluida;
    }

    public Long getVencedorId() {
        return vencedorId;
    }

    public void setVencedorId(Long vencedorId) {
        this.vencedorId = vencedorId;
    }
}