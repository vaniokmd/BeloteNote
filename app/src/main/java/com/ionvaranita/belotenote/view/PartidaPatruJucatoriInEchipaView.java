package com.ionvaranita.belotenote.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * Created by ionvaranita on 16/12/2017.
 */

public class PartidaPatruJucatoriInEchipaView extends View {
    private Integer idPartida;
    private String numePartida;
    private long dataPartida;

    public Integer getIdPartida() {
        return idPartida;
    }

    public void setIdPartida(Integer idPartida) {
        this.idPartida = idPartida;
    }

    public String getNumePartida() {
        return numePartida;
    }

    public void setNumePartida(String numePartida) {
        this.numePartida = numePartida;
    }

    public long getDataPartida() {
        return dataPartida;
    }

    public void setDataPartida(long dataPartida) {
        this.dataPartida = dataPartida;
    }

    public PartidaPatruJucatoriInEchipaView(Context context) {
        super(context);
    }

}
