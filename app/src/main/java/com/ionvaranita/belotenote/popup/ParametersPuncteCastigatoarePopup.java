package com.ionvaranita.belotenote.popup;

import android.view.View;

public class ParametersPuncteCastigatoarePopup {
    private View mainView;
    private int actioCode;
    private boolean isNomeGiocoMostrabile;
    private int lastMatchWinnerpoints;

    public ParametersPuncteCastigatoarePopup() {

    }

    public ParametersPuncteCastigatoarePopup(View mainView, int actioCode, boolean isNomeGiocoMostrabile,int lastMatchWinnerpoints) {
        this.mainView = mainView;
        this.actioCode = actioCode;
        this.isNomeGiocoMostrabile = isNomeGiocoMostrabile;
        this.lastMatchWinnerpoints = lastMatchWinnerpoints;
    }

    public View getMainView() {
        return mainView;
    }

    public void setMainView(View mainView) {
        this.mainView = mainView;
    }

    public int getActioCode() {
        return actioCode;
    }

    public void setActioCode(int actioCode) {
        this.actioCode = actioCode;
    }

    public boolean isNomeGiocoMostrabile() {
        return isNomeGiocoMostrabile;
    }

    public void setNomeGiocoMostrabile(boolean nomeGiocoMostrabile) {
        isNomeGiocoMostrabile = nomeGiocoMostrabile;
    }
    public int getLastMatchWinnerpoints() {
        return lastMatchWinnerpoints;
    }

    public void setLastMatchWinnerpoints(int lastMatchWinnerpoints) {
        this.lastMatchWinnerpoints = lastMatchWinnerpoints;
    }
}
