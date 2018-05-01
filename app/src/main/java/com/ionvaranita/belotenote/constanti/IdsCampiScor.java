package com.ionvaranita.belotenote.constanti;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ionvaranita on 24/04/18.
 */

public class IdsCampiScor {
    public static final int ID_SCOR_NOI =400;
    public static final int ID_SCOR_VOI =401;

    public static List<Integer> getIdsScor4GiocatoriInSquadra(){
        List<Integer> listaIds = new ArrayList<>();

        listaIds.add(ID_SCOR_NOI);
        listaIds.add(ConstantiGlobal.ID_NOME_GIOCO);
        listaIds.add(ID_SCOR_VOI);
        return listaIds;

    }
}
