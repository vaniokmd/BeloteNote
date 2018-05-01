package com.ionvaranita.belotenote.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


/**
 * Created by ionvaranita on 09/12/2017.
 */
@Entity(indices = {@Index(value = {"numeGioco", "dataGioco"}, unique = true)})
public class Gioco4GiocatoriInSquadra {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private Integer idGioco;//0
    @NonNull
    private String numeGioco;//1
    @NonNull
    private long dataGioco;//2



    public Gioco4GiocatoriInSquadra() {
        dataGioco = new java.util.Date().getTime();


    }

    @NonNull
    public Integer getIdGioco() {
        return idGioco;
    }

    public void setIdGioco(@NonNull Integer idGioco) {
        this.idGioco = idGioco;
    }

    @NonNull
    public String getNumeGioco() {
        return numeGioco;
    }

    public void setNumeGioco(@NonNull String numeGioco) {
        this.numeGioco = numeGioco;
    }

    @NonNull
    public long getDataGioco() {
        return dataGioco;
    }

    public void setDataGioco(@NonNull long dataGioco) {

        this.dataGioco = dataGioco;
    }

}
