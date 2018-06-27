package com.ionvaranita.belotenote.constanti;

import com.ionvaranita.belotenote.R;
import com.ionvaranita.belotenote.info.InfoCineACistigat;

import java.util.HashMap;
import java.util.Map;

public class MappaIdCampoStringCineACistigat {
    public Map<Integer,String> getMappaIdsCampoValoreTesto4giocatoriInSquadra(){
        Map<Integer,String> mappaIdsTesto = new HashMap<>();
        mappaIdsTesto.put(IdsCampiStampa.ID_PUNTI_NOI_STAMPA, InfoCineACistigat.WINNER_IS_WE);
        mappaIdsTesto.put(IdsCampiStampa.ID_PUNTI_VOI_STAMPA,InfoCineACistigat.WINNER_IS_YOU);
        return mappaIdsTesto;
    }
}

