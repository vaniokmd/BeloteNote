package com.ionvaranita.belotenote.info;

import android.content.Context;

public class InfoGiocoNuovo4GiocatoriInSquadra {
    private Integer idGioco;
    private String nomeGioco;
    private int puncteCastigatoare;
    private Context context;

    public InfoGiocoNuovo4GiocatoriInSquadra(Context context){
        this.context = context;
    }

    public Integer getIdGioco() {
        return idGioco;
    }

    public void setIdGioco(Integer idGioco) {
        this.idGioco = idGioco;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getNomeGioco() {
        return nomeGioco;
    }

    public void setNomeGioco(String nomeGioco) {
        this.nomeGioco = nomeGioco;
    }

    public int getPuncteCastigatoare() {
        return puncteCastigatoare;
    }

    public void setPuncteCastigatoare(int puncteCastigatoare) {
        this.puncteCastigatoare = puncteCastigatoare;
    }
}
