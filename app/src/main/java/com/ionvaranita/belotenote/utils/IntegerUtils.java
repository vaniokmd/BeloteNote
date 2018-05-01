package com.ionvaranita.belotenote.utils;

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
    public static Integer integerFix(Integer numero){
        if(numero==null){
            return 0;
        }
        return numero;
    }
}
