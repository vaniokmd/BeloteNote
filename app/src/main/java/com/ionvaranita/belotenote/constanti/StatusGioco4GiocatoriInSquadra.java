package com.ionvaranita.belotenote.constanti;

import android.content.Context;

import com.ionvaranita.belotenote.R;

public class StatusGioco4GiocatoriInSquadra {
    private Context context;

    public static final int CODICE_PARTIDA_FINITA = 1;
    public static final int CODICE_PARTIDA_NON_FINITA = 2;
    public static final int CODICE_PARTIDA_NON_FINITA_PROLUNGATA = 3;

    public String getStatusByCodiceStatus(int status){
        if(status==CODICE_PARTIDA_FINITA){
            return getPartidaFinita();
        }
        else if(status==CODICE_PARTIDA_NON_FINITA){
            return getPartidaNonFinita();
        }
        else if(status==CODICE_PARTIDA_NON_FINITA_PROLUNGATA){
            return getPartidaNonFinitaProlungata();
        }
        return null;
    }

    private String partidaFinita;
    private String partidaNonFinita;
    private String partidaNonFinitaProlungata;

    public StatusGioco4GiocatoriInSquadra(Context context){
        this.context = context;
    }

    private String getPartidaFinita() {
        partidaFinita = context.getResources().getString(R.string.match_finished);
        return partidaFinita;
    }

    public void setPartidaFinita(String partidaFinita) {
        this.partidaFinita = partidaFinita;
    }

    private String getPartidaNonFinita() {
        partidaNonFinita = context.getResources().getString(R.string.playing_match);
        return partidaNonFinita;
    }

    public void setPartidaNonFinita(String partidaNonFinita) {

        this.partidaNonFinita = partidaNonFinita;
    }

    private String getPartidaNonFinitaProlungata() {
        partidaNonFinitaProlungata = context.getResources().getString(R.string.playing_prolonged_match);
        return partidaNonFinitaProlungata;
    }

    public void setPartidaNonFinitaProlungata(String partidaNonFinitaProlungata) {
        this.partidaNonFinitaProlungata = partidaNonFinitaProlungata;
    }
}
