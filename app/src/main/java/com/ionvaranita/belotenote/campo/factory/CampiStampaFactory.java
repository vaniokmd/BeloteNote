package com.ionvaranita.belotenote.campo.factory;

import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * Created by ionvaranita on 23/04/18.
 */

public interface CampiStampaFactory {
    Map<Integer,TextView> getMappaCampi();
    void popolaRigaStampa4GiocatoriInSquadra(TableRow tableRow);
}
