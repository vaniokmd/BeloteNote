package com.ionvaranita.belotenote.utils;

import java.util.HashMap;
import java.util.Map;

public class InfoCineACistigat {

    private Map<Integer,Integer> mappaIdCampoValore = new HashMap<>();

    public InfoCineACistigat(Map<Integer,Integer> mappaIdCampoValore){
        this.mappaIdCampoValore = mappaIdCampoValore;
    }

    public Map<Integer, Integer> getMappaIdCampoValore() {
        return mappaIdCampoValore;
    }

    public void setMappaIdCampoValore(Map<Integer, Integer> mappaIdCampoValore) {
        this.mappaIdCampoValore = mappaIdCampoValore;
    }
}
