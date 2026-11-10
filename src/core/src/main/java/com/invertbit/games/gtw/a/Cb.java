package com.invertbit.games.gtw.a;

/* loaded from: classes.dex */
public final class Cb {
    public int a;
    public int b;
    public int c;
    public int d;
    public int e;
    public com.invertbit.games.gtw.a.Cj f = new com.invertbit.games.gtw.a.Cj(com.invertbit.games.gtw.a.Cj.Ia.CITY);
    public com.invertbit.games.gtw.a.Cj g = new com.invertbit.games.gtw.a.Cj(com.invertbit.games.gtw.a.Cj.Ia.SILO);
    public com.invertbit.games.gtw.a.Cj h = new com.invertbit.games.gtw.a.Cj(com.invertbit.games.gtw.a.Cj.Ia.BOMBER);
    public com.invertbit.games.gtw.a.Cj i = new com.invertbit.games.gtw.a.Cj(com.invertbit.games.gtw.a.Cj.Ia.SUBMARINE);
    public boolean j;

    public final java.lang.Object clone() {
        return e();
    }

    public final boolean a() {
        return this.e <= 0;
    }

    public final boolean b() {
        return this.f.b() <= 0;
    }

    public final boolean c() {
        return this.g.b() <= 0 && this.h.b() <= 0 && this.i.b() <= 0;
    }

    public final boolean d() {
        if (a()) {
            return true;
        }
        return (b() || c()) ? false : true;
    }

    public final void a(int i, com.invertbit.games.gtw.a.Cj cj) {
        if (this.e + i <= this.d) {
            if (!(com.invertbit.games.gtw.a.Cj.Ia.SUBMARINE.equals(cj.d) && cj.c() + 1 > this.c)) {
                if (!(com.invertbit.games.gtw.a.Cj.Ia.SILO.equals(cj.d) && com.invertbit.games.gtw.a.Cj.Ia.BOMBER.equals(cj.d) && cj.c() + 1 > this.b)) {
                    this.e += i;
                    if (i == 5) {
                        cj.a(i);
                    } else if (i == 10) {
                        cj.b(i);
                    } else if (i == 15) {
                        cj.c(i);
                    }
                }
            }
        }
    }

    public final com.invertbit.games.gtw.a.Cb e() {
        com.invertbit.games.gtw.a.Cb cb = new com.invertbit.games.gtw.a.Cb();
        cb.h = this.h.a();
        cb.f = this.f.a();
        cb.g = this.g.a();
        cb.i = this.i.a();
        cb.a = this.a;
        cb.b = this.b;
        cb.c = this.c;
        cb.d = this.d;
        cb.e = this.e;
        return cb;
    }

    public final void a(java.lang.String str, com.invertbit.games.gtw.Cc cc) {
        java.lang.String str2 = str + ".AB";
        com.invertbit.games.gtw.a.Cj[] cjArr = {this.f, this.g, this.h, this.i};
        for (int i = 0; i < 4; i++) {
            com.invertbit.games.gtw.a.Cj cj = cjArr[i];
            cc.a(str2 + "." + cj.d.name() + ".m5", cj.a);
            cc.a(str2 + "." + cj.d.name() + ".m10", cj.b);
            cc.a(str2 + "." + cj.d.name() + ".m15", cj.c);
        }
        cc.a(str + ".populationCount", this.a);
        cc.a(str + ".siloCount", this.b);
        cc.a(str + ".submarineCount", this.c);
        cc.a(str + ".maxPoints", this.d);
        cc.a(str + ".points", this.e);
    }

    public final void b(java.lang.String str, com.invertbit.games.gtw.Cc cc) {
        java.lang.String str2 = str + ".AB";
        com.invertbit.games.gtw.a.Cj.Ia[] iaArr = {com.invertbit.games.gtw.a.Cj.Ia.CITY, com.invertbit.games.gtw.a.Cj.Ia.SILO, com.invertbit.games.gtw.a.Cj.Ia.BOMBER, com.invertbit.games.gtw.a.Cj.Ia.SUBMARINE};
        for (int i = 0; i < 4; i++) {
            com.invertbit.games.gtw.a.Cj.Ia ia = iaArr[i];
            switch (com.invertbit.games.gtw.a.Cc.a[ia.ordinal()]) {
                case 1:
                    this.h.a(cc.b(str2 + "." + ia.name() + ".m5"));
                    this.h.b(cc.b(str2 + "." + ia.name() + ".m10"));
                    this.h.c(cc.b(str2 + "." + ia.name() + ".m15"));
                    break;
                case 2:
                    this.f.a(cc.b(str2 + "." + ia.name() + ".m5"));
                    this.f.b(cc.b(str2 + "." + ia.name() + ".m10"));
                    this.f.c(cc.b(str2 + "." + ia.name() + ".m15"));
                    break;
                case 3:
                    this.g.a(cc.b(str2 + "." + ia.name() + ".m5"));
                    this.g.b(cc.b(str2 + "." + ia.name() + ".m10"));
                    this.g.c(cc.b(str2 + "." + ia.name() + ".m15"));
                    break;
                case 4:
                    this.i.a(cc.b(str2 + "." + ia.name() + ".m5"));
                    this.i.b(cc.b(str2 + "." + ia.name() + ".m10"));
                    this.i.c(cc.b(str2 + "." + ia.name() + ".m15"));
                    break;
            }
        }
        this.e = cc.b(str + ".points");
    }

    public final void a(com.invertbit.games.gtw.b.Cf cf) {
        com.invertbit.games.gtw.b.Cf.Ia iaA = com.invertbit.games.gtw.nations.Nations.stats(cf);
        this.a += iaA.c;
        this.f.a(this.a * 5);
        this.b += iaA.a;
        this.c += iaA.b;
        this.d = (iaA.b * 5) + (iaA.a * 5) + this.d;
    }

    public final void b(int i, com.invertbit.games.gtw.a.Cj cj) {
        if (!(com.invertbit.games.gtw.a.Cj.Ia.CITY.equals(cj.d) && cj.c() <= this.a) && cj.c() != 0) {
            if (i != 5 || cj.a <= 0) {
                if (i != 10 || cj.b <= 0) {
                    if (i != 15 || cj.c <= 0) {
                        return;
                    }
                    cj.c -= i;
                    this.e -= i;
                    return;
                }
                cj.b -= i;
                this.e -= i;
                return;
            }
            cj.a -= i;
            this.e -= i;
        }
    }
}
