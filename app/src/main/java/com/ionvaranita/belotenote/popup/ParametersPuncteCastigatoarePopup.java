package com.ionvaranita.belotenote.popup;

import android.content.Context;
import android.view.View;

import com.ionvaranita.belotenote.info.InfoCineACistigat;

public class ParametersPuncteCastigatoarePopup {
    private Context context;
    private Integer idGioco;
    private Integer idPartida;
    private Integer actioCode;
    private boolean nuovaPartida;
    private boolean partidaProlungata;
    private boolean isObligatoProlungare;
    private boolean isNuovoGioco;
    private InfoCineACistigat infoCineACistigat;

    public boolean isNuovaPartida() {
        return nuovaPartida;
    }



    public ParametersPuncteCastigatoarePopup() {

    }
    public Integer getActioCode() {
        return actioCode;
    }

    public void setActioCode(Integer actioCode) {
        this.actioCode = actioCode;
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

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public InfoCineACistigat getInfoCineACistigat() {
        return infoCineACistigat;
    }

    public void setInfoCineACistigat(InfoCineACistigat infoCineACistigat) {
        this.infoCineACistigat = infoCineACistigat;
    }
    public boolean isObligatoProlungare() {
        return isObligatoProlungare;
    }

    public void setObligatoProlungare(boolean obligatoProlungare) {
        this.isNuovoGioco = !obligatoProlungare;
        this.partidaProlungata = !obligatoProlungare;
        this.nuovaPartida = !obligatoProlungare;
        this.isObligatoProlungare=obligatoProlungare;
    }

    public boolean isNuovoGioco() {
        return isNuovoGioco;
    }

    public void setNuovoGioco(boolean nuovoGioco) {
        this.isNuovoGioco = nuovoGioco;
        this.partidaProlungata = !nuovoGioco;
        this.nuovaPartida = !nuovoGioco;
        this.isObligatoProlungare=!nuovoGioco;
    }
    public void setNuovaPartida(boolean nuovaPartida) {
        this.isNuovoGioco = !nuovaPartida;
        this.partidaProlungata = !nuovaPartida;
        this.nuovaPartida = nuovaPartida;
        this.isObligatoProlungare=!nuovaPartida;
    }

    public boolean isPartidaProlungata() {
        return partidaProlungata;
    }

    public void setPartidaProlungata(boolean partidaProlungata) {
        this.nuovaPartida = !partidaProlungata;
        this.isNuovoGioco = !partidaProlungata;
        this.partidaProlungata = partidaProlungata;
        this.isObligatoProlungare = !partidaProlungata;
    }
}
