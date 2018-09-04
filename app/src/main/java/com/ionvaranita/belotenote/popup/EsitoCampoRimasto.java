package com.ionvaranita.belotenote.popup;

import com.ionvaranita.belotenote.borders.BorderedEditText;


public class EsitoCampoRimasto {
    private Integer hintDifferenza;

    public Integer getHintDifferenza() {
        return hintDifferenza;
    }

    public void setHintDifferenza(Integer hintDifferenza) {
        this.hintDifferenza = hintDifferenza;
    }

    private boolean rimasto1Campo;
    private BorderedEditText campoRimasto;
    private boolean campoFocusedAndEmpty;
    private boolean eRimasto1Campo;


    public EsitoCampoRimasto() {

    }


    public EsitoCampoRimasto(boolean eRimasto1Campo, BorderedEditText campoRimasto) {
        this.eRimasto1Campo = eRimasto1Campo;
        this.campoRimasto = campoRimasto;
        this.eRimasto1Campo = eRimasto1Campo;
    }


    public boolean iseRimasto1Campo() {
        return eRimasto1Campo;
    }

    public void seteRimasto1Campo(boolean eRimasto1Campo) {
        this.eRimasto1Campo = eRimasto1Campo;
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
