package com.ionvaranita.belotenote.info;

import android.support.v4.app.FragmentManager;
import android.view.View;

public class ParametersPopupWindowInserimentoPunti {
    private Integer actionCode;
    private Integer idGioco;
    private View popupLayout;
    private FragmentManager fragmentManager;
    private int width;
    private int height;
    private boolean giocaQualcuno;

    public Integer getActionCode() {
        return actionCode;
    }

    public void setActionCode(Integer actionCode) {
        this.actionCode = actionCode;
    }

    public Integer getIdGioco() {
        return idGioco;
    }

    public void setIdGioco(Integer idGioco) {
        this.idGioco = idGioco;
    }

    public View getPopupLayout() {
        return popupLayout;
    }

    public void setPopupLayout(View popupLayout) {
        this.popupLayout = popupLayout;
    }

    public FragmentManager getFragmentManager() {
        return fragmentManager;
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isGiocaQualcuno() {
        return giocaQualcuno;
    }

    public void setGiocaQualcuno(boolean giocaQualcuno) {
        this.giocaQualcuno = giocaQualcuno;
    }
}
