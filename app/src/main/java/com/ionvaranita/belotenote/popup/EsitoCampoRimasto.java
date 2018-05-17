package com.ionvaranita.belotenote.popup;

import com.ionvaranita.belotenote.borders.BorderedEditText;

class EsitoCampoRimasto {
    private boolean rimasto1Campo;
    private BorderedEditText campoRimasto;
    private boolean campoFocusedAndEmpty;



    public EsitoCampoRimasto(){

    }

    public EsitoCampoRimasto(boolean isRimasto1Campo, BorderedEditText campoRimasto) {
        this.rimasto1Campo = isRimasto1Campo;
        this.campoRimasto = campoRimasto;
    }

    public boolean isRimasto1Campo() {
        return rimasto1Campo;
    }

    public void setRimasto1Campo(boolean rimasto1Campo) {
        this.rimasto1Campo = rimasto1Campo;
    }

    public BorderedEditText getCampoRimasto() {
        return campoRimasto;
    }

    public void setCampoRimasto(BorderedEditText campoRimasto) {
        this.campoRimasto = campoRimasto;
    }
    public boolean isCampoFocusedAndEmpty() {
        return campoFocusedAndEmpty;
    }

    public void setCampoFocusedAndEmpty(boolean campoFocusedAndEmpty) {
        this.campoFocusedAndEmpty = campoFocusedAndEmpty;
    }
}
