package com.ionvaranita.belotenote.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by ionvaranita on 02/04/18.
 */
@Entity
public class PuncteCastigatoare4JucatoriInEchipaBean {
    @PrimaryKey
    private Integer idGioco;
    @NonNull
    private Integer idPartida;
    @NonNull
    private Integer puncteCastigatoare;

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
    public Integer getPuncteCastigatoare() {
        return puncteCastigatoare;
    }

    public void setPuncteCastigatoare(@NonNull Integer puncteCastigatoare) {
        this.puncteCastigatoare = puncteCastigatoare;
    }
}
