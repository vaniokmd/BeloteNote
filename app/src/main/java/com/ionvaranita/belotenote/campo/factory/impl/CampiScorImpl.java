package com.ionvaranita.belotenote.campo.factory.impl;

import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.widget.EdgeEffect;
import android.widget.TableRow;
import android.widget.TextView;

import com.ionvaranita.belotenote.R;
import com.ionvaranita.belotenote.campo.factory.CampiScorFactory;
import com.ionvaranita.belotenote.constanti.ConstantiGlobal;
import com.ionvaranita.belotenote.constanti.IdsCampiScor;
import com.ionvaranita.belotenote.utils.GlobalLayoutParams;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ionvaranita on 24/04/18.
 */

public class CampiScorImpl implements CampiScorFactory {
    Map<Integer, TextView> mappaCampi = new HashMap<>();

    private Context context;
    private TableRow tableRow;

    private void init(TableRow tableRow) {
        this.tableRow = tableRow;
        this.context = tableRow.getContext();
    }

    @Override
    public Map<Integer, TextView> getMappaCampi() {
        return mappaCampi;
    }


    @Override
    public void popolaRigaScor4GiocatoriInSquadra(TableRow tableRow) {
        init(tableRow);
        List<Integer> listaIdCampi = IdsCampiScor.getIdsScor4GiocatoriInSquadra();
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
        int numeroDeiCampi = this.tableRow.getChildCount();
        for (int i = 0; i < numeroDeiCampi; i++) {
            TextView campo = (TextView) this.tableRow.getChildAt(i);
            mappaCampi.put(campo.getId(), campo);
        }
    }

    private void configuraCampo(TextView campo) {
        TableRow.LayoutParams layoutParams = GlobalLayoutParams.layoutParamsCampiInserimento();
        campo.setLayoutParams(layoutParams);
        campo.setMaxLines(1);
        campo.setTextSize(campo.getTextSize()+ ConstantiGlobal.DELTA_TEXT_SIZE_CAMPI_SCOR);
        if(campo.getId()==IdsCampiScor.ID_SCOR_NOI)campo.setTextColor(context.getResources().getColor(R.color.color_noi));
        if(campo.getId()==IdsCampiScor.ID_SCOR_VOI)campo.setTextColor(context.getResources().getColor(R.color.color_voi));
        if (Build.VERSION.SDK_INT > 17) campo.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        campo.setGravity(Gravity.CENTER);
    }
}