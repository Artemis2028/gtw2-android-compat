package com.invertbit.games.gtw.android;

/**
 * Hand-written launcher. The original created AdMob banners/interstitials here;
 * ads are removed and the game view is the whole content view.
 *   backends.android.Ca = libGDX AndroidApplication (obfuscated libGDX 1.7.0)
 *   backends.android.Ce = AndroidApplicationConfiguration (h/i = useAccelerometer/useCompass)
 *   gtw.Ca              = the game's ApplicationListener
 *   a(listener, config) = initializeForView
 */
public class AndroidLauncher extends com.badlogic.gdx.backends.android.Ca {
    private com.invertbit.games.gtw.Ca game;

    @Override
    protected void onCreate(android.os.Bundle bundle) {
        super.onCreate(bundle);
        com.invertbit.games.gtw.c.AiOff.set(readAiOff(getIntent()));
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        getWindow().clearFlags(2048);
        com.badlogic.gdx.backends.android.Ce config = new com.badlogic.gdx.backends.android.Ce();
        config.h = false;
        config.i = false;
        this.game = new com.invertbit.games.gtw.Ca(new Platform(this));
        setContentView(a(this.game, config));
    }

    /** Freeze an in-progress battle when the activity goes to the background. */
    @Override
    protected void onPause() {
        try {
            if (this.game != null && this.game.b != null && this.game.b.b != null
                    && (this.game.b.b.c instanceof com.invertbit.games.gtw.c.Caj)) {
                ((com.invertbit.games.gtw.c.Caj) this.game.b.b.c).pauseBattle();
            }
        } catch (java.lang.Throwable ignored) {
        }
        super.onPause();
    }

    @Override
    protected void onNewIntent(android.content.Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        com.invertbit.games.gtw.c.AiOff.set(readAiOff(intent));
    }

    /**
     * Test-only AI-off switch. Intent extra {@code ai_off=true} (adb {@code --ez ai_off true}),
     * or the debug system property {@code debug.gtw.ai_off}, or the JVM property {@code gtw.ai_off}.
     * Default off: a normal launch is unchanged.
     */
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
