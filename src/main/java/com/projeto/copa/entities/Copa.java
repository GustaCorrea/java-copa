package com.projeto.copa.entities;

import com.projeto.copa.enums.FaseCopa;
import com.projeto.copa.enums.StatusCopa;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TB_COPA")
public class Copa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "time_jogador_id")
    private Time timeDoJogador;

    @Enumerated(EnumType.STRING)
    @Column(name = "fase_atual")
    private FaseCopa faseAtual;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusCopa status;

    @OneToMany(mappedBy = "copa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Partida> partidas = new ArrayList<>();

    public Copa() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Time getTimeDoJogador() {
        return timeDoJogador;
    }

    public void setTimeDoJogador(Time timeDoJogador) {
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

    public List<Partida> getPartidas() {
        return partidas;
    }

    public void setPartidas(List<Partida> partidas) {
        this.partidas = partidas;
    }
}