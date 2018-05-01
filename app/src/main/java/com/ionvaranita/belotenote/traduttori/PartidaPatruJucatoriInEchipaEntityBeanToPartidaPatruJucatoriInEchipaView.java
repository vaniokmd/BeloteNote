package com.ionvaranita.belotenote.traduttori;

import android.content.Context;
import android.view.View;

import com.ionvaranita.belotenote.entity.Gioco4GiocatoriInSquadra;
import com.ionvaranita.belotenote.view.PartidaPatruJucatoriInEchipaView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ionvaranita on 16/12/2017.
 */

public class PartidaPatruJucatoriInEchipaEntityBeanToPartidaPatruJucatoriInEchipaView extends View {

    public PartidaPatruJucatoriInEchipaEntityBeanToPartidaPatruJucatoriInEchipaView(Context context) {
        super(context);
    }

    public List<PartidaPatruJucatoriInEchipaView>toPartidaPatruJucatoriInEchipaView(List<Gioco4GiocatoriInSquadra> listaGioco4GiocatoriInSquadra, int idView){

        List<PartidaPatruJucatoriInEchipaView> listaPartidaPatruJucatoriInEchipaViews = new ArrayList<>();

        for (Gioco4GiocatoriInSquadra partidaPatruJucatoriInEchipaEntityBean:
                listaGioco4GiocatoriInSquadra) {

            PartidaPatruJucatoriInEchipaView partidaPatruJucatoriInEchipaView  = new PartidaPatruJucatoriInEchipaView(this.getContext());
            partidaPatruJucatoriInEchipaView.setIdPartida(partidaPatruJucatoriInEchipaEntityBean.getIdGioco());
            partidaPatruJucatoriInEchipaView.setId(idView);
            partidaPatruJucatoriInEchipaView.setDataPartida(partidaPatruJucatoriInEchipaEntityBean.getDataGioco());
            partidaPatruJucatoriInEchipaView.setNumePartida(partidaPatruJucatoriInEchipaEntityBean.getNumeGioco());
            listaPartidaPatruJucatoriInEchipaViews.add(partidaPatruJucatoriInEchipaView);
        }


        return listaPartidaPatruJucatoriInEchipaViews;
    }
}
