package com.projeto.copa.dtos;

import jakarta.validation.constraints.NotBlank;

public class TimeRequest {

    @NotBlank(message = "O nome do time é obrigatório")
    private String nome;

    private String urlBandeira;

    public TimeRequest() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUrlBandeira() {
        return urlBandeira;
    }

    public void setUrlBandeira(String urlBandeira) {
        this.urlBandeira = urlBandeira;
    }
}