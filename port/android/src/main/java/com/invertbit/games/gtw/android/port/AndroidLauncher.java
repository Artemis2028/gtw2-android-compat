package com.invertbit.games.gtw.android.port;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.invertbit.games.gtw.AiOff;
import com.invertbit.games.gtw.GtwGame;

public class AndroidLauncher extends AndroidApplication {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AiOff.set(readAiOff(getIntent()));
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        getWindow().clearFlags(2048);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useAccelerometer = false;
        config.useCompass = false;
        initialize(new GtwGame(), config);
    }

    @Override
    protected void onNewIntent(android.content.Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        AiOff.set(readAiOff(intent));
    }

    static boolean readAiOff(android.content.Intent intent) {
        if (intent != null && intent.getBooleanExtra("ai_off", false)) {
            return true;
        }
        try {
            Class<?> sp = Class.forName("android.os.SystemProperties");
            String v = (String) sp.getMethod("get", String.class, String.class)
                    .invoke(null, "debug.gtw.ai_off", "");
            if ("1".equals(v) || "true".equalsIgnoreCase(v)) {
                return true;
            }
        } catch (Throwable ignored) {
        }
        String prop = System.getProperty("gtw.ai_off", "");
        return "1".equals(prop) || "true".equalsIgnoreCase(prop);
    }
}
