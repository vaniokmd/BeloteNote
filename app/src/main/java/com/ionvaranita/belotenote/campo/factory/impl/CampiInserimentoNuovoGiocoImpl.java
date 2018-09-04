package com.ionvaranita.belotenote.campo.factory.impl;

import android.content.Context;
import android.os.Build;
import android.text.InputFilter;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TableRow;

import com.ionvaranita.belotenote.R;
import com.ionvaranita.belotenote.borders.BorderedEditText;
import com.ionvaranita.belotenote.campo.factory.CampiInsermientoNuovoGiocoFactory;
import com.ionvaranita.belotenote.constanti.ConstantiGlobal;
import com.ionvaranita.belotenote.constanti.IdsCampiInserimentoNuovoGioco;
import com.ionvaranita.belotenote.constanti.LengthOfCharacters;
import com.ionvaranita.belotenote.utils.GlobalLayoutParams;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CampiInserimentoNuovoGiocoImpl implements CampiInsermientoNuovoGiocoFactory {
    private Map<Integer, BorderedEditText> mappaCampi = new HashMap<>();
    private Context context;
    private TableRow tableRow;

    private void init(TableRow riga) {
        tableRow = riga;
        context = riga.getContext();
    }

    @Override
    public void popolaRigaCampiNuovoGioco4giocatoriInSquadra(TableRow riga) {
        init(riga);
        List<Integer> listaIds = IdsCampiInserimentoNuovoGioco.getListaIdsCampiInserimento4GiocatoriInSquadra();
        boolean laRigaEPopolata = this.tableRow.getChildCount() != 0;
        if (!laRigaEPopolata) {
            for (int i = 0; i < listaIds.size(); i++) {
                BorderedEditText campo = new BorderedEditText(context);
                campo.setId(listaIds.get(i));
                configuraCampo(campo);
                tableRow.addView(campo);
            }
        }

    }

    private void configuraCampo(BorderedEditText campo) {
        campo.setLayoutParams(GlobalLayoutParams.layoutParamsCampiInserimento());
        InputFilter[] filters = new InputFilter[1];
        if(campo.getId()== ConstantiGlobal.ID_NOME_GIOCO){
            filters[0] = new InputFilter.LengthFilter(LengthOfCharacters.INSERIMENTO_NOME_GIOCO_MAX_LENGTH);
            campo.setHint(R.string.inserisc_nome_gioco);
            campo.setFilters(filters);
        }
        else{
            filters[0] = new InputFilter.LengthFilter(LengthOfCharacters.INSERIMENTO_PUNTI_MAX_LENGHT);
        }

         //Filter to 10 characters

        campo.setSingleLine();

        campo.setImeOptions(EditorInfo.IME_ACTION_DONE);
        if (Build.VERSION.SDK_INT > 17) campo.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        campo.setGravity(Gravity.CENTER);
    }

    private void popolaMappaCampi() {
        int nrCampi = tableRow.getChildCount();
        for (int i = 0; i < nrCampi; i++) {
            BorderedEditText campo = (BorderedEditText) tableRow.getChildAt(i);
            mappaCampi.put(campo.getId(), campo);
        }
    }

    @Override
    public Map<Integer, BorderedEditText> getMappaCampi() {
        popolaMappaCampi();
        return mappaCampi;
    }
}
