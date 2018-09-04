package com.ionvaranita.belotenote.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(primaryKeys = {"idGioco","idRiga"})
public class VisualizzazioneBoltEntityBean {
    @NonNull
    private Integer idGioco;
    @NonNull
    private Integer idRiga;
    private Integer idPersona;

    public Integer getIdGioco() {
        return idGioco;
    }

    public void setIdGioco(Integer idGioco) {
        this.idGioco = idGioco;
    }

    public Integer getIdRiga() {
        return idRiga;
    }

    public void setIdRiga(Integer idRiga) {
        this.idRiga = idRiga;
    }

    public Integer getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Integer idPersona) {
        this.idPersona = idPersona;
    }
}
