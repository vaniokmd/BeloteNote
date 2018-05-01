package com.ionvaranita.belotenote.campo.factory;

import android.widget.TableRow;
import android.widget.TextView;

import java.util.Map;

/**
 * Created by ionvaranita on 24/04/18.
 */

public interface CampiScorFactory {
    Map<Integer,TextView> getMappaCampi();
    void popolaRigaScor4GiocatoriInSquadra(TableRow tableRow);
}
