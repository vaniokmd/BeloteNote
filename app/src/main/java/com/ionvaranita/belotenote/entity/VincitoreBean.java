package com.ionvaranita.belotenote.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(primaryKeys = {"idGioco","idPartida"})
public class VincitoreBean {
    @NonNull
    private Integer idGioco;
    @NonNull
    private Integer idPartida;
    @NonNull
    private Integer vincitore;

    public Integer getIdGioco() {
        return idGioco;
    }

    public void setIdGioco(Integer idGioco) {
        this.idGioco = idGioco;
    }

    @NonNull
    public Integer getIdPartida() {
        return idPartida;
    }

    public void setIdPartida(@NonNull Integer idPartida) {
        this.idPartida = idPartida;
    }

    @NonNull
    public Integer getVincitore() {
        return vincitore;
    }

    public void setVincitore(@NonNull Integer vincitore) {
        this.vincitore = vincitore;
    }
}
