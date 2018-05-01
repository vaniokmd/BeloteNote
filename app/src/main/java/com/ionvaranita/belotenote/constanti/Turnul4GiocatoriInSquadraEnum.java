package com.ionvaranita.belotenote.constanti;

/**
 * Created by ionvaranita on 11/11/17.
 */

public enum Turnul4GiocatoriInSquadraEnum {
    TURNUL_NOI("TURN NOI"),
    TURNUL_VOI("TURN VOI"),
    FINE_GIOCO("FINE GIOCO"),
    ID_PARTIDA("ID  PARTIDA"),
    ID_JOC ("ID JOC"),
    NUME_PARTIDA("NUME PARTIDA");



    public String getDescrizione() {
        return descrizione;
    }

    private String descrizione ;

    Turnul4GiocatoriInSquadraEnum(String descrizione){
        this.descrizione=descrizione;
    }
    @Override
    public String toString(){
        return descrizione;
    }




}
