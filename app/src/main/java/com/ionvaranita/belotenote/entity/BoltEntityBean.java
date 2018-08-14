package com.ionvaranita.belotenote.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(indices = {@Index(value = {"idGioco", "getIdPartida","idPersona"}, unique = true)})
public class BoltEntityBean {
    @PrimaryKey
    private Integer idGioco;
    private Integer idPartida;
    private Integer idPersona;
    private Integer nrBolt;

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

    public Integer getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Integer idPersona) {
        this.idPersona = idPersona;
    }

    public Integer getNrBolt() {
        return nrBolt;
    }

    public void setNrBolt(Integer nrBolt) {
        this.nrBolt = nrBolt;
    }
}
