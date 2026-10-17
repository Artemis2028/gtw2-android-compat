package com.invertbit.games.gtw.b.a;

/* loaded from: classes.dex */
public final class Cb {
    static com.invertbit.games.gtw.b.a.Cb a;

    public static com.invertbit.games.gtw.b.a.Cb a() {
        if (a == null) {
            a = new com.invertbit.games.gtw.b.a.Cb();
        }
        return a;
    }

    public static com.badlogic.gdx.graphics.Co a(int i, int i2, com.badlogic.gdx.graphics.Cb cb) {
        int i3 = 0;
        com.badlogic.gdx.graphics.Cm cm = new com.badlogic.gdx.graphics.Cm(i, i2, com.badlogic.gdx.graphics.Cm.Ib.RGBA8888);
        cm.a(cb);
        com.badlogic.gdx.graphics.g2d.Gdx2DPixmap.fillRect(cm.a.a, 0, 0, i, i2, cm.b);
        cm.a(com.badlogic.gdx.graphics.Cb.f);
        while (true) {
            int i4 = i3;
            if (i4 >= 5) {
                com.badlogic.gdx.graphics.Co co = new com.badlogic.gdx.graphics.Co(cm);
                cm.b();
                return co;
            }
            com.badlogic.gdx.graphics.g2d.Gdx2DPixmap gdx2DPixmap = cm.a;
            int i5 = cm.b;
            com.badlogic.gdx.graphics.g2d.Gdx2DPixmap.drawRect(gdx2DPixmap.a, 10 - i4, 10 - i4, (i - 20) + (i4 * 2), (i2 - 20) + (i4 * 2), i5);
            i3 = i4 + 1;
        }
    }
}
