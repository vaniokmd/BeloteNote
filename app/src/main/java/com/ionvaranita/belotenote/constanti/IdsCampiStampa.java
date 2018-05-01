package com.ionvaranita.belotenote.constanti;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ionvaranita on 23/04/18.
 */

public class IdsCampiStampa {
    public static final int ID_PUNTI_NOI_STAMPA =410;
    public static final int ID_PUNTI_VOI_STAMPA = 411;

    public static List<Integer> getIdsStampa4GiocatoriInSquadra(){
        List<Integer> listaIds = new ArrayList<>();
        listaIds.add(ID_PUNTI_NOI_STAMPA);
        listaIds.add(ConstantiGlobal.PUNTI_GIOCO_STAMPA);
        listaIds.add(ID_PUNTI_VOI_STAMPA);
        return listaIds;
    }
}
