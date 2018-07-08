package com.ionvaranita.belotenote.info;

import android.content.Context;

import com.ionvaranita.belotenote.constanti.ConstantiGlobal;
import com.ionvaranita.belotenote.constanti.MappaIdCampoStringCineACistigat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class InfoCineACistigat {
    public String getCineACistigat() {
        return cineACistigat;
    }

    public void setCineACistigat(String cineACistigat) {
        this.cineACistigat = cineACistigat;
    }

    private String cineACistigat;
    public static final String WINNER_IS_WE = "A CISTIGAT NOI";
    public static final String WINNER_IS_YOU = "A CISTIGAT VOI";
    private Context context;
    private Map<Integer, Integer> mappaIdCampoValore;
    private Boolean abbiamo2OpiuGiocatoriVincitori;
    private Integer maxValuePunti;
    private Map<Integer, Integer> mappaVincitori = new HashMap<>();
    private boolean unVincitore;

    public boolean isUnVincitore() {
        return unVincitore;
    }

    public void setUnVincitore(boolean unVincitore) {
        this.unVincitore = unVincitore;
    }

    public InfoCineACistigat(Context context, Map<Integer, Integer> mappaIdCampoValore, Integer puncteCastigatoare, boolean unVincitore) {
        this.mappaIdCampoValore = mappaIdCampoValore;
        this.maxValuePunti = puncteCastigatoare;
        this.context = context;
        this.unVincitore = unVincitore;
        inizializza();
    }

    public InfoCineACistigat() {

    }

    private void inizializza() {
        Set<Integer> ids = mappaIdCampoValore.keySet();
        for (Integer id : ids) {
            Integer valore = mappaIdCampoValore.get(id);
            if (valore >= maxValuePunti) {
                maxValuePunti = valore;
                mappaVincitori.put(id, valore);
            }
        }
        abbiamo2OpiuGiocatoriVincitori = mappaVincitori.values().size() > 1;

    }

    public String aflaCineACistigat() {

        if (mappaVincitori.values().size() == 1) {
            Map<Integer, String> mappaIdCampoNomeTesto = new MappaIdCampoStringCineACistigat().getMappaIdsCampoValoreTesto4giocatoriInSquadra();
            cineACistigat = mappaIdCampoNomeTesto.get(mappaVincitori.keySet().iterator().next());
        } else if (abbiamo2OpiuGiocatoriVincitori) {
            Map<Integer, String> mappaIdCampoNomeTesto = new MappaIdCampoStringCineACistigat().getMappaIdsCampoValoreTesto4giocatoriInSquadra();
            List<Integer> listaVincitoriMax = getMaxPunti();
            if (unVincitore && listaVincitoriMax.size() == 1) {
                mappaIdCampoNomeTesto.get(listaVincitoriMax.get(0));
            } else if (unVincitore&&listaVincitoriMax.size() > 1) {
                cineACistigat = ConstantiGlobal.OBBLIGATO_CONTINUA_CON_AGGIUNTA_PUNTI;
            }
            else{
                cineACistigat = ConstantiGlobal.CONTINUA_CON_AGGIUNTA_PUNTI;
            }
        } else {
            cineACistigat = ConstantiGlobal.CONTINUA;
        }


        return cineACistigat;

    }

    private List<Integer> getMaxPunti() {
        List<Integer> listaPunti = new ArrayList<>();
        Iterator<Integer> iterator = mappaVincitori.values().iterator();
        Integer maxValue = null;
        while (iterator.hasNext()) {
            Integer punti = iterator.next();
            if (maxValue == null) {
                maxValue = punti;
            } else if (maxValue < punti) {
                listaPunti.clear();
                maxValue = punti;
                listaPunti.add(maxValue);
            } else if (maxValue == punti) {
                listaPunti.add(maxValue);
            }
        }
        return listaPunti;

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

    public Map<Integer,Integer> getMappaVincitori() {
        return mappaVincitori;
    }
}
