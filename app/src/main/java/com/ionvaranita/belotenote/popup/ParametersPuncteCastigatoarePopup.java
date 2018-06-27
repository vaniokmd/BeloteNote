package com.ionvaranita.belotenote.popup;

import android.content.Context;
import android.view.View;

import com.ionvaranita.belotenote.info.InfoCineACistigat;

public class ParametersPuncteCastigatoarePopup {
    private Context context;
    private Integer idGioco;
    private Integer idPartida;
    private View anchorView;
    private Integer actioCode;
    private boolean isNomeGiocoMostrabile;
    private InfoCineACistigat infoCineACistigat;
    public ParametersPuncteCastigatoarePopup() {

    }

    public View getAnchorView() {
        return anchorView;
    }

    public void setAnchorView(View anchorView) {
        this.anchorView = anchorView;
    }

    public Integer getActioCode() {
        return actioCode;
    }

    public void setActioCode(Integer actioCode) {
        this.actioCode = actioCode;
    }

    public boolean isNomeGiocoMostrabile() {
        return isNomeGiocoMostrabile;
    }

    public void setNomeGiocoMostrabile(boolean nomeGiocoMostrabile) {
        isNomeGiocoMostrabile = nomeGiocoMostrabile;
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
