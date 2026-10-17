package com.invertbit.games.gtw.b.e;

/* loaded from: classes.dex */
public final class Ck {
    public static com.badlogic.gdx.math.Ck a(com.badlogic.gdx.math.Ck ck, com.badlogic.gdx.math.Ck ck2) {
        float f = ck2.d - ck.d;
        if (f < 0.0f && java.lang.Math.abs(f) > 1440.0f) {
            ck2.d += 2880.0f;
        } else if (f > 1440.0f) {
            ck2.d -= 2880.0f;
        }
        return ck2;
    }
}
