package com.ionvaranita.belotenote;

import android.app.Activity;
import android.content.res.Resources;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableRow;

/**
 * Created by ionvaranita on 23/10/17.
 */

public class DisplayInfo extends  Activity{
    private int larghezzaDisplay;

    public int getAltezzaDisplay() {
        return altezzaDisplay;
    }



    private  int altezzaDisplay;

    public int getLarghezzaDisplay() {
        return larghezzaDisplay;
    }


    public DisplayInfo(){
        larghezzaDisplay= Resources.getSystem().getDisplayMetrics().widthPixels;
        altezzaDisplay = Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    public static TableRow.LayoutParams getTableRowLayoutParams(float percentoLarghezza, float percentoAltezza){
        DisplayInfo displayInfo = new DisplayInfo();
        int larghezzaDisplay = displayInfo.getLarghezzaDisplay();
        int altezzaDisplay = displayInfo.getAltezzaDisplay();
        int largezzaCella = (int) (percentoLarghezza*larghezzaDisplay);
        int altezzaCella = (int) (percentoAltezza*altezzaDisplay);
        if(percentoAltezza==ViewGroup.LayoutParams.WRAP_CONTENT){
            return new TableRow.LayoutParams(largezzaCella,ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        else{
            return  new TableRow.LayoutParams(largezzaCella,altezzaCella);
        }
    }

    public static LinearLayout.LayoutParams getLayoutParamsForButton(float percentoLarghezza,float percentoAltezza){
        DisplayInfo displayInfo = new DisplayInfo();
        int larghezzaDisplay = displayInfo.getLarghezzaDisplay();
        int altezzaDisplay = displayInfo.getAltezzaDisplay();
        int largezzaButton = (int) (percentoLarghezza*larghezzaDisplay);
        int altezzaButton = (int) (percentoAltezza*altezzaDisplay);
        if(percentoAltezza==ViewGroup.LayoutParams.WRAP_CONTENT){
            return new LinearLayout.LayoutParams(largezzaButton,ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        else{
            return  new LinearLayout.LayoutParams(largezzaButton,altezzaButton);
        }
    }

    public static RelativeLayout.LayoutParams getRelativelayaotLayaoutParams(float percentoLarghezza,float percentoAltezza){
        DisplayInfo displayInfo = new DisplayInfo();
        int larghezzaDisplay = displayInfo.getLarghezzaDisplay();
        int altezzaDisplay = displayInfo.getAltezzaDisplay();
        int largezzaButton = (int) (percentoLarghezza*larghezzaDisplay);
        int altezzaButton = (int) (percentoAltezza*altezzaDisplay);
        if(percentoAltezza==ViewGroup.LayoutParams.WRAP_CONTENT){
            return new RelativeLayout.LayoutParams(largezzaButton,ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        else{
            return  new RelativeLayout.LayoutParams(largezzaButton,altezzaButton);
        }
    }

    public static int getLargezzaAttraversoPercento(float percento){
        DisplayInfo displayInfo = new DisplayInfo();
        int larghezzaDisplay = displayInfo.getLarghezzaDisplay();
        return (int) percento*larghezzaDisplay;
    }

    public static int getAltezzaAttraversoPercento(float percento){
        DisplayInfo displayInfo = new DisplayInfo();
        int altezzaDisplay = displayInfo.getAltezzaDisplay();
        return (int) percento*altezzaDisplay;
    }

}
