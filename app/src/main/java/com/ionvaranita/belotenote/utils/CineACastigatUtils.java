package com.ionvaranita.belotenote.utils;

import android.content.Context;

import com.ionvaranita.belotenote.constanti.ConstantiGlobal;
import com.ionvaranita.belotenote.constanti.MappaIdCampoValoreTestoCampiStampa;

import java.util.List;
import java.util.Map;

public class CineACastigatUtils {
    private Context context;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Integer getPuncteCastigatoare() {
        return puncteCastigatoare;
    }

    public void setPuncteCastigatoare(Integer puncteCastigatoare) {
        this.puncteCastigatoare = puncteCastigatoare;
    }

    private Integer puncteCastigatoare;

    public CineACastigatUtils(Context context, Integer puncteCastigatoare){
        this.context = context;
        this.puncteCastigatoare = puncteCastigatoare;
    }

    public String cineACistigat(InfoCineACistigat infoCineACistigat){
        Map<Integer,Integer> mappaIdCampoValore =infoCineACistigat.getMappaIdCampoValore();

        int quantiHannoSuperato=0;

        Integer idCampoCastigator = null;

        for(Integer idCampo:mappaIdCampoValore.keySet()){
            if(mappaIdCampoValore.get(idCampo)>=puncteCastigatoare){
                quantiHannoSuperato++;
                idCampoCastigator = idCampo;
            }
        }
        if(quantiHannoSuperato==1){
            Map<Integer,String> mappaIdCampoNomeTesto = new MappaIdCampoValoreTestoCampiStampa(context).getMappaIdsCampoValoreTesto4giocatoriInSquadra();
            return mappaIdCampoNomeTesto.get(idCampoCastigator);
        }
        else if(quantiHannoSuperato>1){
            return ConstantiGlobal.CONTINUA_CON_AGGIUNTA_PUNTI;
        }
        else{
            return ConstantiGlobal.CONTINUA;
        }

    }
}
