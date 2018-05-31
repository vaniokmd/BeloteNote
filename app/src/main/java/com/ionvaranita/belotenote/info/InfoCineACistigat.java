package com.ionvaranita.belotenote.info;

import android.content.Context;

import com.ionvaranita.belotenote.constanti.ConstantiGlobal;
import com.ionvaranita.belotenote.constanti.MappaIdCampoValoreTestoCampiStampa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class InfoCineACistigat {
    private Context context;
    private Map<Integer,Integer> mappaIdCampoValore;
    private Boolean abbiamo2OpiuGiocatoriVincitori;
    private Integer maxValuePunti;
    private List<Integer> listaIdVincitori = new ArrayList<>();

    public InfoCineACistigat(Context context, Map<Integer,Integer> mappaIdCampoValore, Integer puncteCastigatoare){
        this.mappaIdCampoValore=mappaIdCampoValore;
        this.maxValuePunti = puncteCastigatoare;
        this.context = context;
        inizializza();
    }

    private void inizializza(){
        Set<Integer> ids = mappaIdCampoValore.keySet();
        for(Integer id : ids){
            Integer valore = mappaIdCampoValore.get(id);
            if(valore>maxValuePunti){
                maxValuePunti = valore;
                listaIdVincitori.add(id);
            }
        }
        abbiamo2OpiuGiocatoriVincitori = listaIdVincitori.size()>1;

    }

    public String cineACistigat(){

        if(listaIdVincitori.size()==1){
        Map<Integer,String> mappaIdCampoNomeTesto = new MappaIdCampoValoreTestoCampiStampa(context).getMappaIdsCampoValoreTesto4giocatoriInSquadra();
        return mappaIdCampoNomeTesto.get(listaIdVincitori.get(0));
    }
        else if(abbiamo2OpiuGiocatoriVincitori){
        return ConstantiGlobal.CONTINUA_CON_AGGIUNTA_PUNTI;
    }
        else{
        return ConstantiGlobal.CONTINUA;
    }
    }

    public Map<Integer, Integer> getMappaIdCampoValore() {
        return mappaIdCampoValore;
    }

    public Boolean getAbbiamo2OpiuGiocatoriVincitori() {
        return abbiamo2OpiuGiocatoriVincitori;
    }

    public Integer getMaxValuePunti() {
        return maxValuePunti;
    }

    public List<Integer> getListaIdVincitori() {
        return listaIdVincitori;
    }
}
