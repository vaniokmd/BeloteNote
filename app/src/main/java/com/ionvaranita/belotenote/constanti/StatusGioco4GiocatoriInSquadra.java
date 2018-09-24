package com.ionvaranita.belotenote.constanti;

import android.content.Context;

import com.ionvaranita.belotenote.R;

public class StatusGioco4GiocatoriInSquadra {
    private Context context;

    public static final Integer CODICE_PARTIDA_FINITA = 1;
    public static final Integer CODICE_PARTIDA_NON_FINITA = 2;
    public static final Integer CODICE_PARTIDA_NON_FINITA_PROLUNGATA = 3;
    public static final Integer CODICE_OBBLIGATO_CONTINUA_CON_AGGIUNTA_PUNTI = 4;

    public String getStatusByCodiceStatus(Integer status){
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

    private String getPartidaNonFinita() {
        partidaNonFinita = context.getResources().getString(R.string.playing_match);
        return partidaNonFinita;
    }


    private String getPartidaNonFinitaProlungata() {
        partidaNonFinitaProlungata = context.getResources().getString(R.string.playing_prolonged_match);
        return partidaNonFinitaProlungata;
    }


}
