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
}
