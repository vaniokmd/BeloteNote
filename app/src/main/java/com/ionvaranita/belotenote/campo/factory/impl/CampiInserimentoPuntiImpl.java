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
import com.ionvaranita.belotenote.campo.factory.CampiInserimentoPuntiFactory;
import com.ionvaranita.belotenote.constanti.ConstantiGlobal;
import com.ionvaranita.belotenote.constanti.IdsCampiInserimento;
import com.ionvaranita.belotenote.constanti.LengthOfCharacters;
import com.ionvaranita.belotenote.utils.GlobalLayoutParams;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ionvaranita on 19/04/18.
 */

public class CampiInserimentoPuntiImpl implements CampiInserimentoPuntiFactory {
    Map<Integer, BorderedEditText> mappaCampi = new HashMap<>();
    private Context context;
    private TableRow tableRow;

    private void init(TableRow tableRow){
        this.tableRow = tableRow;
        this.context = tableRow.getContext();
    }

    @Override
    public void popolaRigaInserimento4GiocatoriInSquadra(TableRow riga) {
        init(riga);
        List<Integer> listaIdCampi = IdsCampiInserimento.getIdsInserimento4GiocatoriInSquadra();
        boolean laRigaEPopolata = this.tableRow.getChildCount() != 0;
        if (!laRigaEPopolata) {
            for (int i = 0; i < listaIdCampi.size(); i++) {
                BorderedEditText campo = new BorderedEditText(context);
                campo.setId(listaIdCampi.get(i));
                configuraCampo(campo);
                this.tableRow.addView(campo);
            }


        }
        initMappaCampi();

    }
    private void initMappaCampi(){
        int numeroDeiCampi = tableRow.getChildCount();
        for (int i = 0; i < numeroDeiCampi; i++) {
            BorderedEditText campo = (BorderedEditText) tableRow.getChildAt(i);
            mappaCampi.put(campo.getId(), campo);
        }
    }

    private void configuraCampo(BorderedEditText campo) {
        TableRow.LayoutParams layoutParams = GlobalLayoutParams.layoutParamsCampiInserimento();
        campo.setLayoutParams(layoutParams);
        InputFilter[] filters = new InputFilter[1];
        filters[0] = new InputFilter.LengthFilter(LengthOfCharacters.INSERIMENTO_PUNTI_MAX_LENGHT); //Filter to 10 characters
        campo.setFilters(filters);
        campo.setMaxLines(1);
        if (Build.VERSION.SDK_INT > 17) campo.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        campo.setGravity(Gravity.CENTER);
    }

    @Override
    public Map<Integer, BorderedEditText> getMappaCampi() {
        return mappaCampi;
    }

    @Override
    public List<BorderedEditText> creaCampiInserimento4JucatoriIndividual() {
        return null;
    }

    @Override
    public List<BorderedEditText> creaCampiInserimento3Jucatori() {
        return null;
    }

    @Override
    public List<BorderedEditText> creaCampiInserimento2Jucatori() {
        return null;
    }
}
