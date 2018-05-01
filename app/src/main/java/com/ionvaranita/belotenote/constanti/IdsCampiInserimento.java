package com.ionvaranita.belotenote.constanti;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ionvaranita on 17/04/18.
 */

public class IdsCampiInserimento {

    public static final int ID_PUNTI_NOI_INSERIMENTO = 420;
    public static final int ID_PUNTI_VOI_INSERIMENTO = 421;

    public static List<Integer> getIdsInserimento4GiocatoriInSquadra(){
        List<Integer> listaIds = new ArrayList<>();
        listaIds.add(ID_PUNTI_NOI_INSERIMENTO);
        listaIds.add(ConstantiGlobal.PUNTI_GIOCO_INSERIMENTO);
        listaIds.add(ID_PUNTI_VOI_INSERIMENTO);
        return listaIds;

    }
}
