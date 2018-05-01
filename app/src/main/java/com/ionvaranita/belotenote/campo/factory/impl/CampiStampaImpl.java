package com.ionvaranita.belotenote.campo.factory.impl;

import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;

import com.ionvaranita.belotenote.campo.factory.CampiStampaFactory;
import com.ionvaranita.belotenote.constanti.IdsCampiStampa;
import com.ionvaranita.belotenote.constanti.MappaIdCampoValoreTestoCampiStampa;
import com.ionvaranita.belotenote.utils.GlobalLayoutParams;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ionvaranita on 23/04/18.
 */

public class CampiStampaImpl implements CampiStampaFactory {
    private Map<Integer,TextView> mappaCampi = new HashMap<>();
    private TableRow tableRow;
    private Context context;

    private void init(TableRow tableRow){
        this.tableRow = tableRow;
        this.context=tableRow.getContext();
    }

    @Override
    public Map<Integer, TextView> getMappaCampi() {
        return mappaCampi;
    }


    @Override
     public void popolaRigaStampa4GiocatoriInSquadra(TableRow riga) {
        init(riga);
        List<Integer> listaIdCampi = IdsCampiStampa.getIdsStampa4GiocatoriInSquadra();
        boolean laRigaEPopolata = this.tableRow.getChildCount() != 0;
        if (!laRigaEPopolata) {
            for (int i = 0; i < listaIdCampi.size(); i++) {
                TextView campo = new TextView(context);
                campo.setId(listaIdCampi.get(i));
                configuraCampo(campo);
                this.tableRow.addView(campo);
            }
            initMappaCampi();

        }
    }

    private void initMappaCampi(){
        int numeroDeiCampi = tableRow.getChildCount();
        Map<Integer,String> mappaIdValore = new MappaIdCampoValoreTestoCampiStampa(context).getMappaIdsCampoValoreTesto4giocatoriInSquadra();
        for (int i = 0; i < numeroDeiCampi; i++) {
            TextView campo = (TextView) tableRow.getChildAt(i);
            campo.setText(mappaIdValore.get(campo.getId()));
            this.mappaCampi.put(campo.getId(), campo);
        }
    }
    private void configuraCampo(TextView campo) {
        TableRow.LayoutParams layoutParams = GlobalLayoutParams.layoutParamsCampiInserimento();
        campo.setLayoutParams(layoutParams);

        if (Build.VERSION.SDK_INT > 17) campo.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        campo.setGravity(Gravity.CENTER);
    }
}
