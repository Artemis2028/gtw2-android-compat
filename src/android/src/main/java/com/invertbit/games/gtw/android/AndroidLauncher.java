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
    @Override
    protected void onCreate(android.os.Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        getWindow().clearFlags(2048);
        com.badlogic.gdx.backends.android.Ce config = new com.badlogic.gdx.backends.android.Ce();
        config.h = false;
        config.i = false;
        setContentView(a(new com.invertbit.games.gtw.Ca(new Platform(this)), config));
    }
}
