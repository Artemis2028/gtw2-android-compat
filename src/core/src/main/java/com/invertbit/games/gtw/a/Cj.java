package com.invertbit.games.gtw.a;

/* loaded from: classes.dex */
public final class Cj {
    public int a = 0;
    public int b = 0;
    public int c = 0;
    public com.invertbit.games.gtw.a.Cj.Ia d;

    public enum Ia {
        CITY,
        SILO,
        BOMBER,
        SUBMARINE
    }

    public final java.lang.Object clone() {
        return a();
    }

    public Cj(com.invertbit.games.gtw.a.Cj.Ia ia) {
        this.d = ia;
    }

    public final com.invertbit.games.gtw.a.Cj a() {
        com.invertbit.games.gtw.a.Cj cj = new com.invertbit.games.gtw.a.Cj(this.d);
        cj.a(this.a);
        cj.b(this.b);
        cj.c(this.c);
        return cj;
    }

    public final int b() {
        return this.a + this.b + this.c;
    }

    public final int c() {
        return (this.a / 5) + (this.b / 10) + (this.c / 15);
    }

    public final void a(int i) {
        this.a += i;
    }

    public final void b(int i) {
        this.b += i;
    }

    public final void c(int i) {
        this.c += i;
    }

    public final java.lang.String toString() {
        return "[m5]=" + this.a + " [m10]=" + this.b + " [m15]=" + this.c;
    }
}
