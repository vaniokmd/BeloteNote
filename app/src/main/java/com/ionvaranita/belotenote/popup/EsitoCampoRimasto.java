package com.ionvaranita.belotenote.popup;

import com.ionvaranita.belotenote.borders.BorderedEditText;

<<<<<<< HEAD
public class EsitoCampoRimasto {
    private boolean eRimasto1Campo;
    private BorderedEditText campoRimasto;
=======
class EsitoCampoRimasto {
    private boolean rimasto1Campo;
    private BorderedEditText campoRimasto;
    private boolean campoFocusedAndEmpty;


>>>>>>> 1430b236ae553fc2b686409012016134ea64af67

    public EsitoCampoRimasto(){

    }

<<<<<<< HEAD
    public EsitoCampoRimasto(boolean eRimasto1Campo, BorderedEditText campoRimasto) {
        this.eRimasto1Campo = eRimasto1Campo;
        this.campoRimasto = campoRimasto;
    }

    public boolean iseRimasto1Campo() {
        return eRimasto1Campo;
    }

    public void seteRimasto1Campo(boolean eRimasto1Campo) {
        this.eRimasto1Campo = eRimasto1Campo;
=======
    public EsitoCampoRimasto(boolean isRimasto1Campo, BorderedEditText campoRimasto) {
        this.rimasto1Campo = isRimasto1Campo;
        this.campoRimasto = campoRimasto;
    }

    public boolean isRimasto1Campo() {
        return rimasto1Campo;
    }

    public void setRimasto1Campo(boolean rimasto1Campo) {
        this.rimasto1Campo = rimasto1Campo;
>>>>>>> 1430b236ae553fc2b686409012016134ea64af67
    }

    public BorderedEditText getCampoRimasto() {
        return campoRimasto;
    }

    public void setCampoRimasto(BorderedEditText campoRimasto) {
        this.campoRimasto = campoRimasto;
    }
<<<<<<< HEAD
=======
    public boolean isCampoFocusedAndEmpty() {
        return campoFocusedAndEmpty;
    }

    public void setCampoFocusedAndEmpty(boolean campoFocusedAndEmpty) {
        this.campoFocusedAndEmpty = campoFocusedAndEmpty;
    }
>>>>>>> 1430b236ae553fc2b686409012016134ea64af67
}
