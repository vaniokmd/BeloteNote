package com.ionvaranita.belotenote.constanti;

import android.content.Context;

import com.ionvaranita.belotenote.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ionvaranita on 25/04/18.
 */

public class MappaIdCampoValoreTestoCampiStampa {
    private Context context;
    public MappaIdCampoValoreTestoCampiStampa(Context context){
        this.context = context;
    }

    public Map<Integer,String> getMappaIdsCampoValoreTesto4giocatoriInSquadra(){
        Map<Integer,String> mappaIdsTesto = new HashMap<>();

        String noiStampa = context.getString(R.string.noi);
        String joacaStampa = context.getString(R.string.joaca);
        String voiStampa = context.getString(R.string.voi);


        mappaIdsTesto.put(IdsCampiStampa.ID_NOI,noiStampa);
        mappaIdsTesto.put(IdsCampiStampa.ID_PUNTI_GIOCO,joacaStampa);
        mappaIdsTesto.put(IdsCampiStampa.ID_VOI,voiStampa);
        return mappaIdsTesto;

    }
}
