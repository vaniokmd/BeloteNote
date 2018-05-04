package com.ionvaranita.belotenote.campo.factory.impl;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

import com.ionvaranita.belotenote.R;
import com.ionvaranita.belotenote.campo.factory.CampiStampaFactory;
import com.ionvaranita.belotenote.constanti.IdsCampiStampa;
import com.ionvaranita.belotenote.constanti.MappaIdCampoValoreTestoCampiStampa;
import com.ionvaranita.belotenote.utils.GlobalLayoutParams;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ionvaranita.belotenote.constanti.ConstantiGlobal.DELTA_TEXT_SIZE_CAMPI_STAMPA;

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
        campo.setMaxLines(1);
        if(campo.getId()== IdsCampiStampa.ID_PUNTI_NOI_STAMPA)campo.setTextColor(context.getResources().getColor(R.color.color_noi));
        if(campo.getId()==IdsCampiStampa.ID_PUNTI_VOI_STAMPA)campo.setTextColor(context.getResources().getColor(R.color.color_voi));
        campo.setTextSize(campo.getTextSize()+ DELTA_TEXT_SIZE_CAMPI_STAMPA);
        campo.setTypeface(Typeface.DEFAULT_BOLD);
        if (Build.VERSION.SDK_INT > 17) campo.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        campo.setGravity(Gravity.CENTER);
    }
}
