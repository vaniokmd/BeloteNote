package com.ionvaranita.belotenote.campo.factory;

import android.widget.TableRow;

import com.ionvaranita.belotenote.borders.BorderedEditText;

import java.util.List;
import java.util.Map;

/**
 * Created by ionvaranita on 19/04/18.
 */

public interface CampiInserimentoPuntiFactory {
    Map<Integer,BorderedEditText> getMappaCampi();
    void popolaRigaInserimento4GiocatoriInSquadra(TableRow tableRow);
    List<BorderedEditText> creaCampiInserimento4JucatoriIndividual();
    List<BorderedEditText> creaCampiInserimento3Jucatori();
    List<BorderedEditText> creaCampiInserimento2Jucatori();
}
