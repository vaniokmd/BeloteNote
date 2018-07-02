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
    private InfoCineACistigat infoCineACistigat;

    public boolean isNuovaPartida() {
        return nuovaPartida;
    }

    public void setNuovaPartida(boolean nuovaPartida) {
        this.partidaProlungata = !nuovaPartida;
        this.nuovaPartida = nuovaPartida;
    }

    public boolean isPartidaProlungata() {
        return partidaProlungata;
    }

    public void setPartidaProlungata(boolean partidaProlungata) {
        this.nuovaPartida = !partidaProlungata;
        this.partidaProlungata = partidaProlungata;
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
}
