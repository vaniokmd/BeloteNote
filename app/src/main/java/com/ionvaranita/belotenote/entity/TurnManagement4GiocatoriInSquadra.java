package com.ionvaranita.belotenote.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by ionvaranita on 22/12/2017.
 */
@Entity
public class TurnManagement4GiocatoriInSquadra {
    @PrimaryKey
    private Integer idGioco;

    public Integer getIdGioco() {
        return idGioco;
    }

    public void setIdGioco(Integer idGioco) {
        this.idGioco = idGioco;
    }

    @NonNull
    private String turnoPresente;


    public String getTurnoPresente() {
        return turnoPresente;
    }

    public void setTurnoPresente(String turnoPresente) {
        this.turnoPresente = turnoPresente;
    }
}
