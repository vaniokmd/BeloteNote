package com.ionvaranita.belotenote.utils;

import android.content.Context;
import android.content.res.Configuration;

/**
 * Created by ionvaranita on 29/03/2018.
 */

public class DeviceUtils {
    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
}
