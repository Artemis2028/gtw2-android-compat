package com.invertbit.games.gtw.android.port;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.invertbit.games.gtw.AiOff;
import com.invertbit.games.gtw.GtwGame;
import com.invertbit.games.gtw.QuickDefeat;

public class AndroidLauncher extends AndroidApplication {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AiOff.set(readAiOff(getIntent()));
        QuickDefeat.set(readQuickDefeat(getIntent()));
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
        QuickDefeat.set(readQuickDefeat(intent));
    }

    /** Debug builds only: {@code --ez quick_defeat true} ends each round right after the countdown. */
    boolean readQuickDefeat(android.content.Intent intent) {
        boolean debuggable = (getApplicationInfo().flags & android.content.pm.ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        return debuggable && intent != null && intent.getBooleanExtra("quick_defeat", false);
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
