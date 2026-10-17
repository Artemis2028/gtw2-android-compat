package com.invertbit.games.b.a;

/* loaded from: classes.dex */
public class Ca {
    static final com.badlogic.gdx.math.Cg d = new com.badlogic.gdx.math.Cg(com.badlogic.gdx.math.Cl.e);
    static final com.badlogic.gdx.math.Cl e;
    public com.badlogic.gdx.graphics.a.Cd a;
    public com.badlogic.gdx.graphics.a.e.Ci b;
    public float c = 3.0f;
    java.util.List<com.invertbit.games.c.a.a.Cb> f = new java.util.ArrayList();

    static float a(com.invertbit.games.b.a.Ca ca) {
        return ca.c;
    }

    static com.badlogic.gdx.math.Cg a() {
        return d;
    }

    static {
        com.badlogic.gdx.math.Cl cl = com.badlogic.gdx.math.Cl.e;
        com.badlogic.gdx.math.Cl cl2 = new com.badlogic.gdx.math.Cl(1.0f, -1.0f, 1.0f);
        e = cl.a(cl.a * cl2.a, cl.b * cl2.b, cl2.c * cl.c);
    }

    public final com.invertbit.games.b.a.Ca a(com.invertbit.games.c.a.a.Cb cb) {
        this.f.add(cb);
        return this;
    }

    public final com.badlogic.gdx.graphics.a.Ce a(java.lang.String str) {
        com.badlogic.gdx.math.Cl cl;
        com.badlogic.gdx.math.Cl cl2;
        this.b.a();
        java.util.Iterator<com.invertbit.games.c.a.a.Cb> it = this.f.iterator();
        while (it.hasNext()) {
            java.util.List<com.invertbit.games.b.a.Ca.Ia> listB = b(it.next());
            int i = 1;
            while (true) {
                int i2 = i;
                if (i2 >= listB.size()) {
                    break;
                }
                com.invertbit.games.b.a.Ca.Ia ia = listB.get(i2);
                if (ia.e.a - 2880.0f == ia.f.a || ia.e.a + 2880.0f == ia.f.a) {
                    i2++;
                } else {
                    com.invertbit.games.b.a.Ca.Ia ia2 = listB.get(i2 - 1);
                    com.invertbit.games.b.a.Ca.Ia ia3 = listB.get(i2);
                    com.badlogic.gdx.math.Ck ck = new com.badlogic.gdx.math.Ck();
                    if (com.badlogic.gdx.math.Cc.a(ia2.a.a, ia2.a.c, ia2.b.a, ia2.b.c, ia3.a.a, ia3.a.c, ia3.b.a, ia3.b.c, ck)) {
                        cl = new com.badlogic.gdx.math.Cl(ck.d, 0.0f, ck.e);
                    } else {
                        cl = null;
                    }
                    com.badlogic.gdx.math.Ck ck2 = new com.badlogic.gdx.math.Ck();
                    if (com.badlogic.gdx.math.Cc.a(ia2.c.a, ia2.c.c, ia2.d.a, ia2.d.c, ia3.c.a, ia3.c.c, ia3.d.a, ia3.d.c, ck2)) {
                        cl2 = new com.badlogic.gdx.math.Cl(ck2.d, 0.0f, ck2.e);
                    } else {
                        cl2 = null;
                    }
                    if (cl != null && cl2 != null) {
                        ia2.b = new com.badlogic.gdx.math.Cl(cl);
                        ia2.c = new com.badlogic.gdx.math.Cl(cl2);
                        ia3.a = new com.badlogic.gdx.math.Cl(cl);
                        ia3.d = new com.badlogic.gdx.math.Cl(cl2);
                    }
                }
                i = i2 + 1;
            }
            com.badlogic.gdx.graphics.a.e.Ch chA = this.b.a(str, 4, 25L, this.a);
            chA.c();
            int i3 = 0;
            while (true) {
                int i4 = i3;
                if (i4 < listB.size()) {
                    com.invertbit.games.b.a.Ca.Ia ia4 = listB.get(i4);
                    if (ia4.e.a - 2880.0f != ia4.f.a && ia4.e.a + 2880.0f != ia4.f.a) {
                        chA.a(ia4.a, ia4.b, ia4.c, ia4.d, e);
                        if (i4 + 1 < listB.size()) {
                            com.invertbit.games.b.a.Ca.Ia ia5 = listB.get(i4 + 1);
                            chA.a(ia4.c, ia4.f, ia5.d);
                            chA.a(ia4.f, ia4.b, ia5.a);
                        }
                    }
                    i3 = i4 + 1;
                } else {
                    break;
                }
            }
        }
        return this.b.b();
    }

    protected class Ia {
        com.badlogic.gdx.math.Cl a;
        com.badlogic.gdx.math.Cl b;
        com.badlogic.gdx.math.Cl c;
        com.badlogic.gdx.math.Cl d;
        com.badlogic.gdx.math.Cl e;
        com.badlogic.gdx.math.Cl f;

        public Ia(com.badlogic.gdx.math.Cl cl, com.badlogic.gdx.math.Cl cl2) {
            com.badlogic.gdx.math.Cl clC = new com.badlogic.gdx.math.Cl(cl2).c(cl);
            com.badlogic.gdx.math.Cg cgA = com.invertbit.games.b.a.Ca.a();
            com.badlogic.gdx.math.Cg.b.a(cgA);
            com.badlogic.gdx.math.Cg cg = com.badlogic.gdx.math.Cg.b;
            cg.c = -cg.c;
            cg.d = -cg.d;
            cg.e = -cg.e;
            com.badlogic.gdx.math.Cg.b.b(com.badlogic.gdx.math.Cg.a.a(clC.a, clC.b, clC.c, 0.0f)).b(cgA);
            clC.a = com.badlogic.gdx.math.Cg.b.c;
            clC.b = com.badlogic.gdx.math.Cg.b.d;
            clC.c = com.badlogic.gdx.math.Cg.b.e;
            com.badlogic.gdx.math.Cl clB = clC.b(100.0f);
            float fA = com.invertbit.games.b.a.Ca.a(com.invertbit.games.b.a.Ca.this);
            float f = fA * fA;
            float r2 = clB.b();
            if (r2 > f) {
                clB.b((float) java.lang.Math.sqrt(f / r2));
            }
            this.a = new com.badlogic.gdx.math.Cl(cl).b(clB);
            this.b = new com.badlogic.gdx.math.Cl(cl2).b(clB);
            this.c = new com.badlogic.gdx.math.Cl(cl2).c(clB);
            this.d = new com.badlogic.gdx.math.Cl(cl).c(clB);
            this.e = cl;
            this.f = cl2;
        }

        public final java.lang.String toString() {
            return "Line{corner00=" + this.a + ", corner10=" + this.b + ", corner11=" + this.c + ", corner01=" + this.d + ", startPoint=" + this.e + ", endPoint=" + this.f + '}';
        }
    }

    protected java.util.List<com.invertbit.games.b.a.Ca.Ia> b(com.invertbit.games.c.a.a.Cb cb) {
        java.lang.Object[] array = cb.a().toArray();
        java.util.ArrayList arrayList = new java.util.ArrayList(array.length);
        int i = 1;
        while (true) {
            int i2 = i;
            if (i2 >= array.length) {
                return arrayList;
            }
            com.badlogic.gdx.math.Cl clA = ((com.badlogic.gdx.math.Cl) array[i2 - 1]).a();
            com.badlogic.gdx.math.Cl clA2 = ((com.badlogic.gdx.math.Cl) array[i2]).a();
            if (clA.a - 2880.0f != clA2.a) {
                arrayList.add(new com.invertbit.games.b.a.Ca.Ia(clA, clA2));
            }
            i = i2 + 1;
        }
    }
}
