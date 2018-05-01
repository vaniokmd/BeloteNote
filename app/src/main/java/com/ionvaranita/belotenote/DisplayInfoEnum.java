package com.ionvaranita.belotenote;

/**
 * Created by ionvaranita on 23/10/17.
 */

public enum DisplayInfoEnum {
    DISPLAY_WIDTH("Larghezza del Display"),
    DISPLAY_HEIGHT("Altezza del Display");
    private String info;

    DisplayInfoEnum(String info){
        this.info=info;
    }
    public String getInfo(){
        return info;
    }
}
