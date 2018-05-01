package com.ionvaranita.belotenote.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by ionvaranita on 09/12/2017.
 */
@Entity
public class Scor4JucatoriInEchipaEntityBean {
    @PrimaryKey
    private Integer idGioco;
    @NonNull
    private Integer scorNoi;
    @NonNull
    private Integer scorVoi;

    @NonNull
    public Integer getIdGioco() {
        return idGioco;
    }

    public void setIdGioco(Integer idGioco) {
        this.idGioco = idGioco;
    }

    @NonNull
    public Integer getScorNoi() {
        return scorNoi;
    }

    public void setScorNoi(Integer scorNoi) {
        this.scorNoi = scorNoi;
    }

    @NonNull
    public Integer getScorVoi() {
        return scorVoi;
    }

    public void setScorVoi(Integer scorVoi) {
        this.scorVoi = scorVoi;
    }
}
