package com.ionvaranita.belotenote.campo.factory;

import android.widget.TableRow;

import com.ionvaranita.belotenote.borders.BorderedEditText;

import java.util.Map;

public interface CampiInsermientoNuovoGiocoFactory {
    void popolaRigaCampiNuovoGioco4giocatoriInSquadra(TableRow riga);

    Map<Integer,BorderedEditText> getMappaCampi();
}







