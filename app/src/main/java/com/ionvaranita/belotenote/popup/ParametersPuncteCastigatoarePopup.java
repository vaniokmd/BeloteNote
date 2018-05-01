package com.ionvaranita.belotenote.popup;

import android.view.View;
import android.view.Window;

public class ParametersPuncteCastigatoarePopup {
    private Window mainWindow;
    private int actioCode;
    private boolean isNomeGiocoMostrabile;

    public ParametersPuncteCastigatoarePopup() {

    }

    public ParametersPuncteCastigatoarePopup(Window mainWindow, int actioCode, boolean isNomeGiocoMostrabile) {
        this.mainWindow = mainWindow;
        this.actioCode = actioCode;
        this.isNomeGiocoMostrabile = isNomeGiocoMostrabile;
    }

    public Window getMainWindow() {
        return mainWindow;
    }

    public void setMainWindow(Window mainWindow) {
        this.mainWindow = mainWindow;
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
}
