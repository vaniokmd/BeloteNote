package com.ionvaranita.belotenote.constanti;

import android.content.Context;

import com.ionvaranita.belotenote.R;

public class StatusGioco4GiocatoriInSquadra {
    private Context context;

    private String partidaFinita;
    private String partidaNonFinita;
    private String partidaNonFinitaProlungata;

    public StatusGioco4GiocatoriInSquadra(Context context){
        this.context = context;
    }

    public String getPartidaFinita() {
        partidaFinita = context.getResources().getString(R.string.match_finished);
        return partidaFinita;
    }

    public void setPartidaFinita(String partidaFinita) {
        this.partidaFinita = partidaFinita;
    }

    public String getPartidaNonFinita() {
        partidaNonFinita = context.getResources().getString(R.string.playing_match);
        return partidaNonFinita;
    }

    public void setPartidaNonFinita(String partidaNonFinita) {

        this.partidaNonFinita = partidaNonFinita;
    }

    public String getPartidaNonFinitaProlungata() {
        partidaNonFinitaProlungata = context.getResources().getString(R.string.playing_prolonged_match);
        return partidaNonFinitaProlungata;
    }

    public void setPartidaNonFinitaProlungata(String partidaNonFinitaProlungata) {
        this.partidaNonFinitaProlungata = partidaNonFinitaProlungata;
    }
}
