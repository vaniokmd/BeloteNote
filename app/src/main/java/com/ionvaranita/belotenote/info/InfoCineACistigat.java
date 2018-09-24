package com.ionvaranita.belotenote.info;

import android.content.Context;

import com.ionvaranita.belotenote.constanti.StatusGioco4GiocatoriInSquadra;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class InfoCineACistigat {
    public Integer getCineACistigat() {
        return cineACistigat;
    }

    public void setCineACistigat(Integer cineACistigat) {
        this.cineACistigat = cineACistigat;
    }

    private Integer cineACistigat;
    public static final String WINNER_IS_WE = "A CISTIGAT NOI";
    public static final String WINNER_IS_YOU = "A CISTIGAT VOI";
    private Context context;
    private Map<Integer, Integer> mappaIdCampoValore;
    private Boolean abbiamo2OpiuGiocatoriVincitori;
    private Integer puncteCastigatoare;
    private Map<Integer, Integer> mappaVincitori = new HashMap<>();
    private boolean unVincitore;
    private Integer maxValuePunti;

    public boolean isUnVincitore() {
        return unVincitore;
    }

    public void setUnVincitore(boolean unVincitore) {
        this.unVincitore = unVincitore;
    }

    public InfoCineACistigat(Context context, Map<Integer, Integer> mappaIdCampoValore, Integer puncteCastigatoare, boolean unVincitore) {
        this.mappaIdCampoValore = mappaIdCampoValore;
        this.puncteCastigatoare = puncteCastigatoare;
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
            if (valore >= puncteCastigatoare) {
                if(maxValuePunti<valore){
                    maxValuePunti = valore;
                }
                mappaVincitori.put(id, valore);
            }
        }
        abbiamo2OpiuGiocatoriVincitori = mappaVincitori.size() > 1;

    }

    public Integer aflaCineACistigat() {

        if (mappaVincitori.values().size() == 1) {
            cineACistigat = mappaVincitori.keySet().iterator().next();
        } else if (abbiamo2OpiuGiocatoriVincitori) {
            List<Integer> listaVincitoriMax = getIdMaxPunti();
            if (unVincitore&&listaVincitoriMax.size() == 1) {
                cineACistigat = listaVincitoriMax.get(0);
            } else if (unVincitore&&listaVincitoriMax.size() > 1) {
                cineACistigat = StatusGioco4GiocatoriInSquadra.CODICE_OBBLIGATO_CONTINUA_CON_AGGIUNTA_PUNTI;
            }
            else{
                cineACistigat = StatusGioco4GiocatoriInSquadra.CODICE_PARTIDA_NON_FINITA_PROLUNGATA;
            }
        } else {
            cineACistigat = StatusGioco4GiocatoriInSquadra.CODICE_PARTIDA_NON_FINITA;
        }


        return cineACistigat;

    }

    private List<Integer> getIdMaxPunti() {
        List<Integer> idsMaxPunti = new ArrayList<>();
        Iterator<Integer> iterator = mappaVincitori.keySet().iterator();
        Integer maxValue = null;
        while (iterator.hasNext()) {
            Integer idCampo = iterator.next();
            Integer punti = mappaVincitori.get(idCampo);
            if (maxValue == null) {
                maxValue = punti;
            }if (maxValue < punti) {
                idsMaxPunti.clear();
                maxValue = punti;
                idsMaxPunti.add(idCampo);
            } else if (maxValue.equals(punti)) {
                idsMaxPunti.add(idCampo);
            }
        }

        return idsMaxPunti;

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
