package com.projeto.copa.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "TB_TIMES")
public class Time {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NOME", nullable = false, unique = true)
    private String nome;

    @Column(name = "URL_BANDEIRA", nullable = false)
    private String urlBandeira;

    public Time() {
    }

    public Time(Long id, String nome, String urlBandeira) {
        this.id = id;
        this.nome = nome;
        this.urlBandeira = urlBandeira;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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