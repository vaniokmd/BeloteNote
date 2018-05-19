package com.ionvaranita.belotenote.popup;

import com.ionvaranita.belotenote.borders.BorderedEditText;

public class EsitoCampoRimasto {
    private boolean eRimasto1Campo;
    private BorderedEditText campoRimasto;

    public EsitoCampoRimasto(){

    }

    public EsitoCampoRimasto(boolean eRimasto1Campo, BorderedEditText campoRimasto) {
        this.eRimasto1Campo = eRimasto1Campo;
        this.campoRimasto = campoRimasto;
    }

    public boolean iseRimasto1Campo() {
        return eRimasto1Campo;
    }

    public void seteRimasto1Campo(boolean eRimasto1Campo) {
        this.eRimasto1Campo = eRimasto1Campo;
    }

    public BorderedEditText getCampoRimasto() {
        return campoRimasto;
    }

    public void setCampoRimasto(BorderedEditText campoRimasto) {
        this.campoRimasto = campoRimasto;
    }
}
