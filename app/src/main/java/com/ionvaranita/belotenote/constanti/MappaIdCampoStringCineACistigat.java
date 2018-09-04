package com.ionvaranita.belotenote.constanti;

import com.ionvaranita.belotenote.info.InfoCineACistigat;

import java.util.HashMap;
import java.util.Map;

public class MappaIdCampoStringCineACistigat {
    public Map<Integer,String> getMappaIdsCampoValoreTesto4giocatoriInSquadra(){
        Map<Integer,String> mappaIdsTesto = new HashMap<>();
        mappaIdsTesto.put(IdsCampiStampa.ID_PUNTI_NOI, InfoCineACistigat.WINNER_IS_WE);
        mappaIdsTesto.put(IdsCampiStampa.ID_PUNTI_VOI,InfoCineACistigat.WINNER_IS_YOU);
        return mappaIdsTesto;
    }
}

