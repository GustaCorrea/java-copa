package com.projeto.copa.entities;

import com.projeto.copa.enums.FaseCopa;
import jakarta.persistence.*;

@Entity
@Table(name = "TB_PARTIDA")
public class Partida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "copa_id")
    private Copa copa;

    @Enumerated(EnumType.STRING)
    @Column(name = "fase")
    private FaseCopa fase;

    @ManyToOne
    @JoinColumn(name = "time_casa_id")
    private Time timeCasa;

    @ManyToOne
    @JoinColumn(name = "time_fora_id")
    private Time timeFora;

    @Column(name = "placar_casa")
    private Integer placarCasa;

    @Column(name = "placar_fora")
    private Integer placarFora;

    @Column(name = "concluida")
    private Boolean concluida;

    @Column(name = "vencedor_id")
    private Long vencedorId;

    public Partida() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Copa getCopa() {
        return copa;
    }

    public void setCopa(Copa copa) {
        this.copa = copa;
    }

    public FaseCopa getFase() {
        return fase;
    }

    public void setFase(FaseCopa fase) {
        this.fase = fase;
    }

    public Time getTimeCasa() {
        return timeCasa;
    }

    public void setTimeCasa(Time timeCasa) {
        this.timeCasa = timeCasa;
    }

    public Time getTimeFora() {
        return timeFora;
    }

    public void setTimeFora(Time timeFora) {
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