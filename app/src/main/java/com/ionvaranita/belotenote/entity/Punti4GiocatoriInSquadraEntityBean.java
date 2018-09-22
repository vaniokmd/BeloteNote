package com.ionvaranita.belotenote.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by ionvaranita on 20/11/17.
 */
@Entity
public class Punti4GiocatoriInSquadraEntityBean {
    @PrimaryKey(autoGenerate = true)
    private Integer id=null;

    private Integer idGioco;

    private Integer turno;

    private Integer puntiNoi;

    private Integer puntiVoi;

    private Integer puntiGioco;

    private Integer idPartida;



    public Integer getPuntiNoi() {
        return puntiNoi;
    }

    public void setPuntiNoi(Integer puntiNoi) {
        this.puntiNoi = puntiNoi;
    }

    public Integer getPuntiVoi() {
        return puntiVoi;
    }

    public void setPuntiVoi(Integer puntiVoi) {
        this.puntiVoi = puntiVoi;
    }

    public Integer getPuntiGioco() {
        return puntiGioco;
    }

    public void setPuntiGioco(Integer puntiGioco) {
        this.puntiGioco = puntiGioco;
    }


    public Integer getTurno() {
        return turno;
    }

    public void setTurno(Integer turno) {
        this.turno = turno;
    }

    public Integer getIdGioco() {
        return idGioco;
    }

    public void setIdGioco(Integer idGioco) {
        this.idGioco = idGioco;
    }

    public Integer getIdPartida() {
        return idPartida;
    }

    public void setIdPartida(Integer idPartida) {
        this.idPartida = idPartida;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
