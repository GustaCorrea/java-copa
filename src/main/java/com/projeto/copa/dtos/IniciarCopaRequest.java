package com.projeto.copa.dtos;

import jakarta.validation.constraints.NotNull;

public class IniciarCopaRequest {

    @NotNull(message = "O ID do time é obrigatório para iniciar a copa")
    private Long idTimeEscolhido;

    public IniciarCopaRequest() {
    }

    public Long getIdTimeEscolhido() {
        return idTimeEscolhido;
    }

    public void setIdTimeEscolhido(Long idTimeEscolhido) {
        this.idTimeEscolhido = idTimeEscolhido;
    }
}