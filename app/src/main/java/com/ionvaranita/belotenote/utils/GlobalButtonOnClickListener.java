package com.ionvaranita.belotenote.utils;

import android.content.Context;
import android.media.AudioManager;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;

/**
 * Created by ionvaranita on 19/04/18.
 */

public class GlobalButtonOnClickListener {
    private Context context;

    public GlobalButtonOnClickListener(Context context) {
        this.context = context;
    }

    public  void vibrate() {


        AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        Vibrator vibe = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        switch (am.getRingerMode()) {
            case AudioManager.RINGER_MODE_SILENT:
                vibe.vibrate(20);
                Log.i("MyApp", "Silent mode");
                break;
            case AudioManager.RINGER_MODE_VIBRATE:
                vibe.vibrate(20);
                Log.i("MyApp", "Vibrate mode");
                break;
            case AudioManager.RINGER_MODE_NORMAL:
                vibe.vibrate(20);
                Log.i("MyApp", "Normal mode");
                break;
        }

    }
}
