package com.ionvaranita.belotenote.custom.integers;

public class IntegerBolt {
   Integer valore;

   public IntegerBolt(Integer valore){
       if(valore==null){
           this.valore=0;
       }
       else{
           this.valore=valore;
       }
   }

    public Integer getValore() {
        return valore;
    }

    public void setValore(Integer valore) {
        this.valore = valore;
    }
}
