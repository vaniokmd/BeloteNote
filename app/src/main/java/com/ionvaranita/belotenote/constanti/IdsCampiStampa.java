package com.ionvaranita.belotenote.constanti;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ionvaranita on 23/04/18.
 */

public class IdsCampiStampa {
    public static final Integer ID_PUNTI_NOI =410;
    public static final Integer ID_PUNTI_GIOCO = 411;
    public static final Integer ID_PUNTI_VOI = 412;

    public static List<Integer> getIdsStampa4GiocatoriInSquadra(){
        List<Integer> listaIds = new ArrayList<>();
        listaIds.add(ID_PUNTI_NOI);
        listaIds.add(ID_PUNTI_GIOCO);
        listaIds.add(ID_PUNTI_VOI);
        return listaIds;
    }
}
