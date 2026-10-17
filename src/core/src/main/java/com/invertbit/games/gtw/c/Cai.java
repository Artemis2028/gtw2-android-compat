package com.invertbit.games.gtw.c;

/* loaded from: classes.dex */
public final class Cai extends com.invertbit.games.gtw.c.Ca {
    com.invertbit.games.gtw.b.a.Ca j;
    com.badlogic.gdx.graphics.g2d.Ch k;
    com.badlogic.gdx.graphics.g2d.Ch l;
    com.badlogic.gdx.graphics.g2d.Cb m;

    public Cai(com.invertbit.games.gtw.Ca ca) {
        super(ca);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.invertbit.games.gtw.c.Ca
    public final void c() {
        super.c();
        com.invertbit.games.gtw.b.d.Cj.a();
        this.m = com.invertbit.games.gtw.b.d.Cj.a(com.invertbit.games.gtw.b.e.Cg.s37);
        com.badlogic.gdx.graphics.g2d.Ch ch = new com.badlogic.gdx.graphics.g2d.Ch();
        com.badlogic.gdx.math.Matrix4 matrix4 = new com.badlogic.gdx.math.Matrix4();
        new com.badlogic.gdx.math.Cl(1.0f, 0.0f, 0.0f);
        matrix4.a();
        ch.b(matrix4);
        this.k = ch;
        this.l = new com.badlogic.gdx.graphics.g2d.Ch();
    }

    @Override // com.invertbit.games.gtw.c.Ca
    public final void a(java.lang.Object... objArr) {
        super.a(objArr);
    }

    @Override // com.invertbit.games.gtw.c.Ca
    public final void b(float f) {
        com.badlogic.gdx.graphics.Cm cm;
        boolean z;
        this.k.a();
        com.badlogic.gdx.graphics.g2d.Cb cb = this.m;
        com.badlogic.gdx.graphics.g2d.Ch ch = this.k;
        float f2 = a;
        cb.c.a();
        cb.c.a("Loading...", 0.0f, b / 2.0f, "Loading...".length(), f2, 1);
        cb.c.a(ch);
        this.k.c();
        if (this.j == null) {
            this.j = com.invertbit.games.gtw.b.a.Ca.a();
            return;
        }
        com.invertbit.games.gtw.b.d.Cp.a();
        com.invertbit.games.gtw.b.a.Ca ca = this.j;
        ca.b();
        ca.c();
        com.invertbit.games.gtw.b.a.Ca ca2 = this.j;
        com.badlogic.gdx.graphics.a.Cf cf = new com.badlogic.gdx.graphics.a.Cf((byte) 0);
        com.badlogic.gdx.graphics.Ca ca3 = this.f;
        ca2.d = new java.util.HashMap(com.invertbit.games.gtw.b.a.Ca.Ia.values().length);
        com.invertbit.games.gtw.b.a.Ca.Ia[] iaArrValues = com.invertbit.games.gtw.b.a.Ca.Ia.values();
        int length = iaArrValues.length;
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 >= length) {
                com.invertbit.games.gtw.Ca ca4 = this.e;
                ca4.b.a(new com.invertbit.games.e.Cc(com.invertbit.games.e.Ca.menu, com.invertbit.games.e.Ca.exit, new com.invertbit.games.gtw.c.Can(ca4)));
                ca4.b.a(new com.invertbit.games.e.Cc(com.invertbit.games.e.Ca.start, com.invertbit.games.e.Ca.menu, new com.invertbit.games.gtw.c.Caj(ca4)));
                ca4.b.a(new com.invertbit.games.e.Cc(com.invertbit.games.e.Ca.game0, com.invertbit.games.e.Ca.menu, new com.invertbit.games.gtw.c.Cc(ca4)));
                ca4.b.a(new com.invertbit.games.e.Cc(com.invertbit.games.e.Ca.game1, com.invertbit.games.e.Ca.menu, new com.invertbit.games.gtw.c.Cg(ca4)));
                ca4.b.a(new com.invertbit.games.e.Cc(com.invertbit.games.e.Ca.game2, com.invertbit.games.e.Ca.menu, new com.invertbit.games.gtw.c.Ck(ca4)));
                ca4.b.a(new com.invertbit.games.e.Cc(com.invertbit.games.e.Ca.game3, com.invertbit.games.e.Ca.menu, new com.invertbit.games.gtw.c.Cp(ca4)));
                ca4.b.a(new com.invertbit.games.e.Cc(com.invertbit.games.e.Ca.game4, com.invertbit.games.e.Ca.menu, new com.invertbit.games.gtw.c.Cs(ca4)));
                ca4.b.a(new com.invertbit.games.e.Cc(com.invertbit.games.e.Ca.game5, com.invertbit.games.e.Ca.menu, new com.invertbit.games.gtw.c.Cw(ca4)));
                ca4.b.a(new com.invertbit.games.e.Cc(com.invertbit.games.e.Ca.tutorial1, com.invertbit.games.e.Ca.menu, new com.invertbit.games.gtw.c.d.Ca(ca4)));
                ca4.b.a(new com.invertbit.games.e.Cc(com.invertbit.games.e.Ca.gameOver, com.invertbit.games.e.Ca.menu, new com.invertbit.games.gtw.c.Caf(ca4)));
                ca4.b.a(new com.invertbit.games.e.Cc(com.invertbit.games.e.Ca.settings, com.invertbit.games.e.Ca.menu, new com.invertbit.games.gtw.c.Cas(ca4)));
                ca4.b.a(new com.invertbit.games.e.Cc(com.invertbit.games.e.Ca.slotsNew, com.invertbit.games.e.Ca.menu, new com.invertbit.games.gtw.c.SlotScreen(ca4, true)));
                ca4.b.a(new com.invertbit.games.e.Cc(com.invertbit.games.e.Ca.slotsContinue, com.invertbit.games.e.Ca.menu, new com.invertbit.games.gtw.c.SlotScreen(ca4, false)));
                this.e.a(com.invertbit.games.e.Ca.menu, new java.lang.Object[0]);
                return;
            }
            com.invertbit.games.gtw.b.a.Ca.Ia ia = iaArrValues[i2];
            java.util.Map<com.invertbit.games.gtw.b.a.Ca.Ia, com.badlogic.gdx.graphics.g2d.Cg> map = ca2.d;
            new com.badlogic.gdx.graphics.glutils.Cc(com.badlogic.gdx.graphics.Cm.Ib.RGBA8888).c();
            com.badlogic.gdx.Cf.g.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
            com.badlogic.gdx.Cf.g.glClear(16384);
            com.badlogic.gdx.graphics.a.Cg cg = new com.badlogic.gdx.graphics.a.Cg(ca2.a(ia));
            float[] fArrA = com.invertbit.games.gtw.b.e.Ch.a(ia);
            cg.f.a(fArrA[0], 0.0f, fArrA[1]);
            com.badlogic.gdx.math.Matrix4 matrix4 = cg.f;
            float[] fArr = matrix4.a;
            fArr[0] = fArr[0] * 44.0f;
            float[] fArr2 = matrix4.a;
            fArr2[5] = fArr2[5] * 44.0f;
            float[] fArr3 = matrix4.a;
            fArr3[10] = fArr3[10] * 44.0f;
            cf.a(ca3);
            cf.a(cg, com.invertbit.games.gtw.d.Ca.a().a(com.invertbit.games.gtw.d.Ca.Ia.defaultMapObject));
            cf.c();
            com.badlogic.gdx.Cf.g.glPixelStorei(3333, 1);
            com.badlogic.gdx.graphics.Cm cm2 = new com.badlogic.gdx.graphics.Cm(91, 70, com.badlogic.gdx.graphics.Cm.Ib.RGBA8888);
            com.badlogic.gdx.Cf.g.glReadPixels(0, 0, 91, 70, 6408, 5121, cm2.d());
            int iA = com.badlogic.gdx.Cf.b.a();
            int iB = com.badlogic.gdx.Cf.b.b();
            com.badlogic.gdx.Cf.h.glBindFramebuffer(36160, com.badlogic.gdx.graphics.glutils.Cd.b);
            com.badlogic.gdx.Cf.h.glViewport(0, 0, iA, iB);
            com.badlogic.gdx.graphics.g2d.Ck ck = new com.badlogic.gdx.graphics.g2d.Ck(new com.badlogic.gdx.graphics.Co(cm2));
            ck.a(false, true);
            int i3 = cm2.a.b;
            int i4 = cm2.a.c;
            int i5 = cm2.a.b;
            int i6 = cm2.a.c;
            boolean z2 = i3 == cm2.a.b && i4 == cm2.a.c;
            boolean z3 = i5 == cm2.a.b && i6 == cm2.a.c;
            if (cm2.e() == com.badlogic.gdx.graphics.Cm.Ib.RGBA8888 && z2 && z3) {
                z = true;
                cm = cm2;
            } else {
                com.badlogic.gdx.graphics.Cm cm3 = new com.badlogic.gdx.graphics.Cm(i5, i6, com.badlogic.gdx.graphics.Cm.Ib.RGBA8888);
                cm3.a(cm2, 0, 0, i3, i4, 0, 0, i5, i6);
                cm2.b();
                cm = cm3;
                z = false;
            }
            java.nio.ByteBuffer byteBufferD = cm.d();
            if (byteBufferD.limit() != i5 * i6 * 4) {
                throw new java.lang.IllegalArgumentException("inputRGBA must be in RGBA format");
            }
            java.nio.ByteBuffer byteBufferA = com.invertbit.games.gtw.b.e.Cb.a(com.invertbit.games.gtw.b.e.Cb.a(com.invertbit.games.gtw.b.e.Cb.a(byteBufferD), i5, i6));
            com.badlogic.gdx.graphics.Cm cm4 = new com.badlogic.gdx.graphics.Cm(i5, i6, com.badlogic.gdx.graphics.Cm.Ib.RGBA8888);
            java.nio.ByteBuffer byteBufferD2 = cm4.d();
            byteBufferD2.clear();
            byteBufferD2.put(byteBufferA);
            byteBufferD2.flip();
            if (z) {
                cm.b();
            }
            new com.badlogic.gdx.graphics.g2d.Ck(new com.badlogic.gdx.graphics.Co(cm4)).a(false, true);
            map.put(ia, new com.badlogic.gdx.graphics.g2d.Cg(ck));
            i = i2 + 1;
        }
    }

    @Override // com.badlogic.gdx.Co
    public final void b() {
    }

    @Override // com.badlogic.gdx.Co
    public final void a(int i, int i2) {
    }

    @Override // com.invertbit.games.gtw.c.Ca, com.badlogic.gdx.Co
    public final void a() {
    }
}
