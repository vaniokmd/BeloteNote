package com.ionvaranita.belotenote.utils;

import com.ionvaranita.belotenote.constanti.ConstantiGlobal;

/**
 * Created by ionvaranita on 25/03/2018.
 */

public class IntegerUtils {
    public static boolean isInteger(String numero){
        try{
            Integer.parseInt(numero);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
    public static Integer integerFixAndBoltFix(Integer numero){
        if(numero==null||numero.equals(ConstantiGlobal.STRING_BOLT)){
            return 0;
        }
        return numero;
    }
}
