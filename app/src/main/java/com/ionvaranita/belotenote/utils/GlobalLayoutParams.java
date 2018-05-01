package com.ionvaranita.belotenote.utils;

import android.widget.TableRow;

/**
 * Created by ionvaranita on 19/04/18.
 */

public class GlobalLayoutParams {
    public static TableRow.LayoutParams layoutParamsCampiInserimento(){
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1f);
        return layoutParams;
    }
}
