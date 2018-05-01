package com.ionvaranita.belotenote.utils;

import android.app.Application;
import android.content.Context;

/**
 * Created by ionvaranita on 25/04/18.
 */

public class CustomApplicationContext extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public Context getContext(){
        return mContext;
    }
}