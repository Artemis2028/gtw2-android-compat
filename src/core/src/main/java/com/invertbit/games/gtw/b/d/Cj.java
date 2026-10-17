package com.invertbit.games.gtw.b.d;

/* loaded from: classes.dex */
public final class Cj {
    static com.invertbit.games.gtw.b.d.Cj a;
    static final java.util.Map<java.lang.String, com.badlogic.gdx.graphics.g2d.Cb> c = new java.util.HashMap();
    java.util.Map<java.lang.Object, com.badlogic.gdx.graphics.g2d.Cb> b = new java.util.HashMap();

    private Cj() {
    }

    public static com.invertbit.games.gtw.b.d.Cj a() {
        boolean z;
        int iB;
        if (a == null) {
            a = new com.invertbit.games.gtw.b.d.Cj();
            com.badlogic.gdx.graphics.g2d.freetype.Ca ca = new com.badlogic.gdx.graphics.g2d.freetype.Ca(com.badlogic.gdx.Cf.e.b("fonts/VT323-Regular.ttf"));
            for (com.invertbit.games.gtw.b.e.Cg cg : com.invertbit.games.gtw.b.e.Cg.values()) {
                com.badlogic.gdx.graphics.g2d.freetype.Ca.Ib ib = new com.badlogic.gdx.graphics.g2d.freetype.Ca.Ib();
                ib.a = java.lang.Integer.parseInt(cg.name().replace("s", ""));
                com.badlogic.gdx.graphics.g2d.freetype.Ca.Ia ia = new com.badlogic.gdx.graphics.g2d.freetype.Ca.Ia();
                java.lang.String str = ib.k;
                int length = str.length();
                boolean z2 = ib.r;
                ca.a(ib.a);
                com.badlogic.gdx.graphics.g2d.freetype.FreeType.SizeMetrics sizeMetrics = new com.badlogic.gdx.graphics.g2d.freetype.FreeType.SizeMetrics(com.badlogic.gdx.graphics.g2d.freetype.FreeType.Size.getMetrics(new com.badlogic.gdx.graphics.g2d.freetype.FreeType.Size(com.badlogic.gdx.graphics.g2d.freetype.FreeType.Face.getSize(ca.c.b)).b));
                ia.c = ib.n;
                ia.j = com.badlogic.gdx.graphics.g2d.freetype.FreeType.a(com.badlogic.gdx.graphics.g2d.freetype.FreeType.SizeMetrics.getAscender(sizeMetrics.b));
                ia.k = com.badlogic.gdx.graphics.g2d.freetype.FreeType.a(com.badlogic.gdx.graphics.g2d.freetype.FreeType.SizeMetrics.getDescender(sizeMetrics.b));
                ia.h = com.badlogic.gdx.graphics.g2d.freetype.FreeType.a(com.badlogic.gdx.graphics.g2d.freetype.FreeType.SizeMetrics.getHeight(sizeMetrics.b));
                float f = ia.j;
                if (ca.e && ia.h == 0.0f) {
                    for (int i = 32; i < com.badlogic.gdx.graphics.g2d.freetype.FreeType.Face.getNumGlyphs(ca.c.b) + 32; i++) {
                        if (ca.c.a(i, com.badlogic.gdx.graphics.g2d.freetype.FreeType.L)) {
                            int iA = com.badlogic.gdx.graphics.g2d.freetype.FreeType.a(com.badlogic.gdx.graphics.g2d.freetype.FreeType.GlyphMetrics.getHeight(ca.c.a().a().b));
                            ia.h = ((float) iA) > ia.h ? iA : ia.h;
                        }
                    }
                }
                if (ca.c.a(32, com.badlogic.gdx.graphics.g2d.freetype.FreeType.L)) {
                    ia.q = com.badlogic.gdx.graphics.g2d.freetype.FreeType.a(com.badlogic.gdx.graphics.g2d.freetype.FreeType.GlyphMetrics.getHoriAdvance(ca.c.a().a().b));
                } else {
                    ia.q = com.badlogic.gdx.graphics.g2d.freetype.FreeType.Face.getMaxAdvanceWidth(ca.c.b);
                }
                char[] cArr = ia.t;
                int length2 = cArr.length;
                int i2 = 0;
                while (true) {
                    if (i2 >= length2) {
                        break;
                    }
                    if (!ca.c.a(cArr[i2], com.badlogic.gdx.graphics.g2d.freetype.FreeType.L)) {
                        i2++;
                    } else {
                        ia.r = com.badlogic.gdx.graphics.g2d.freetype.FreeType.a(com.badlogic.gdx.graphics.g2d.freetype.FreeType.GlyphMetrics.getHeight(ca.c.a().a().b));
                        break;
                    }
                }
                if (ia.r == 0.0f) {
                    throw new com.badlogic.gdx.utils.Cg("No x-height character found in font");
                }
                char[] cArr2 = ia.u;
                int length3 = cArr2.length;
                int i3 = 0;
                while (true) {
                    if (i3 >= length3) {
                        break;
                    }
                    if (!ca.c.a(cArr2[i3], com.badlogic.gdx.graphics.g2d.freetype.FreeType.L)) {
                        i3++;
                    } else {
                        ia.i = com.badlogic.gdx.graphics.g2d.freetype.FreeType.a(com.badlogic.gdx.graphics.g2d.freetype.FreeType.GlyphMetrics.getHeight(ca.c.a().a().b));
                        break;
                    }
                }
                if (!ca.e && ia.i == 1.0f) {
                    throw new com.badlogic.gdx.utils.Cg("No cap character found in font");
                }
                ia.j -= ia.i;
                ia.l = -ia.h;
                if (ib.n) {
                    ia.j = -ia.j;
                    ia.l = -ia.l;
                }
                com.badlogic.gdx.graphics.g2d.Ce ce = ib.m;
                if (ce != null) {
                    z = false;
                } else {
                    if (z2) {
                        iB = com.badlogic.gdx.graphics.g2d.freetype.Ca.a;
                    } else {
                        int iCeil = (int) java.lang.Math.ceil(ia.h);
                        iB = com.badlogic.gdx.math.Cd.b((int) java.lang.Math.sqrt(iCeil * iCeil * length));
                        if (com.badlogic.gdx.graphics.g2d.freetype.Ca.a > 0) {
                            iB = java.lang.Math.min(iB, com.badlogic.gdx.graphics.g2d.freetype.Ca.a);
                        }
                    }
                    ce = new com.badlogic.gdx.graphics.g2d.Ce(iB, iB, com.badlogic.gdx.graphics.Cm.Ib.RGBA8888);
                    z = true;
                }
                com.badlogic.gdx.graphics.g2d.freetype.FreeType.Stroker stroker = null;
                if (ib.c > 0.0f) {
                    long jStrokerNew = com.badlogic.gdx.graphics.g2d.freetype.FreeType.Library.strokerNew(ca.b.b);
                    if (jStrokerNew == 0) {
                        throw new com.badlogic.gdx.utils.Cg("Couldn't create FreeType stroker");
                    }
                    com.badlogic.gdx.graphics.g2d.freetype.FreeType.Stroker stroker2 = new com.badlogic.gdx.graphics.g2d.freetype.FreeType.Stroker(jStrokerNew);
                    com.badlogic.gdx.graphics.g2d.freetype.FreeType.Stroker.set(stroker2.b, (int) (ib.c * 64.0f), ib.e ? com.badlogic.gdx.graphics.g2d.freetype.FreeType.aj : com.badlogic.gdx.graphics.g2d.freetype.FreeType.ak, ib.e ? com.badlogic.gdx.graphics.g2d.freetype.FreeType.aq : com.badlogic.gdx.graphics.g2d.freetype.FreeType.am, 0);
                    stroker = stroker2;
                }
                if (z2) {
                    ia.w = ca;
                    ia.x = ib;
                    ia.y = stroker;
                    ia.z = ce;
                    ia.A = new com.badlogic.gdx.utils.Ca<>(length + 32);
                }
                int i4 = 0;
                while (true) {
                    int i5 = i4;
                    if (i5 >= length) {
                        break;
                    }
                    char cCharAt = str.charAt(i5);
                    com.badlogic.gdx.graphics.g2d.Cb.Ib ibA = ca.a(cCharAt, ia, ib, stroker, f, ce);
                    if (ibA != null) {
                        ia.a(cCharAt, ibA);
                        if (z2) {
                            ia.A.a(ibA);
                        }
                    }
                    i4 = i5 + 1;
                }
                if (stroker != null && !z2) {
                    stroker.b();
                }
                if (ib.l) {
                    for (int i6 = 0; i6 < length; i6++) {
                        char cCharAt2 = str.charAt(i6);
                        com.badlogic.gdx.graphics.g2d.Cb.Ib ibA2 = ia.a(cCharAt2);
                        if (ibA2 != null) {
                            int iA2 = ca.c.a(cCharAt2);
                            for (int i7 = i6; i7 < length; i7++) {
                                char cCharAt3 = str.charAt(i7);
                                com.badlogic.gdx.graphics.g2d.Cb.Ib ibA3 = ia.a(cCharAt3);
                                if (ibA3 != null) {
                                    int iA3 = ca.c.a(cCharAt3);
                                    int iB2 = ca.c.b(iA2, iA3);
                                    if (iB2 != 0) {
                                        ibA2.a(cCharAt3, com.badlogic.gdx.graphics.g2d.freetype.FreeType.a(iB2));
                                    }
                                    int iB3 = ca.c.b(iA3, iA2);
                                    if (iB3 != 0) {
                                        ibA3.a(cCharAt2, com.badlogic.gdx.graphics.g2d.freetype.FreeType.a(iB3));
                                    }
                                }
                            }
                        }
                    }
                }
                if (z) {
                    ia.v = new com.badlogic.gdx.utils.Ca<>();
                    ce.a(ia.v, ib.p, ib.q, ib.o);
                }
                com.badlogic.gdx.graphics.g2d.Cb.Ib ibA4 = ia.a(' ');
                if (ibA4 == null) {
                    ibA4 = new com.badlogic.gdx.graphics.g2d.Cb.Ib();
                    ibA4.l = (int) ia.q;
                    ibA4.a = 32;
                    ia.a(32, ibA4);
                }
                if (ibA4.d == 0) {
                    ibA4.d = (int) (ibA4.l + ia.e);
                }
                if (ia.v == null && ib.m != null) {
                    ia.v = new com.badlogic.gdx.utils.Ca<>();
                    ib.m.a(ia.v, ib.p, ib.q, ib.o);
                }
                com.badlogic.gdx.graphics.g2d.Cb cb = new com.badlogic.gdx.graphics.g2d.Cb(ia, ia.v, false);
                cb.f = ib.m == null;
                cb.c.g.a(cg.h);
                c.put("VT323-Regular.ttf" + cg, cb);
            }
            ca.b();
        }
        return a;
    }

    public static com.badlogic.gdx.graphics.g2d.Cb a(com.invertbit.games.gtw.b.e.Cg cg) {
        if (c.containsKey("VT323-Regular.ttf" + cg)) {
            return c.get("VT323-Regular.ttf" + cg);
        }
        throw new java.lang.IllegalArgumentException("Font not found VT323-Regular.ttf" + cg);
    }

    public final void b() {
        java.util.Iterator<java.util.Map.Entry<java.lang.Object, com.badlogic.gdx.graphics.g2d.Cb>> it = this.b.entrySet().iterator();
        while (it.hasNext()) {
            it.next().getValue().b();
        }
        java.util.Iterator<java.util.Map.Entry<java.lang.String, com.badlogic.gdx.graphics.g2d.Cb>> it2 = c.entrySet().iterator();
        while (it2.hasNext()) {
            it2.next().getValue().b();
        }
        a = null;
    }
}
