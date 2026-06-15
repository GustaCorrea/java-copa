package com.projeto.copa.dtos;

public class SalvarPlacarRequest {
    private Integer placarCasa;
    private Integer placarFora;

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
}