package com.invertbit.games.gtw.a;

/* loaded from: classes.dex */
public final class Ca extends com.invertbit.games.gtw.a.Ci {
    public java.util.List<com.invertbit.games.gtw.a.Cd> c = new java.util.ArrayList();
    java.util.List<com.invertbit.games.gtw.a.Cd> b = new java.util.ArrayList();
    public java.util.List<com.invertbit.games.gtw.a.Cd> a = new java.util.ArrayList();

    @Override // com.invertbit.games.gtw.a.Ci
    public final void a() {
        super.a();
        this.c = new java.util.ArrayList();
        this.b = new java.util.ArrayList();
        this.a = new java.util.ArrayList();
    }

    public final void a(com.invertbit.games.gtw.a.Cd cd) {
        for (com.invertbit.games.gtw.a.Cd cd2 : this.c) {
            if (cd2.equals(cd)) {
                this.c.remove(cd2);
                if (cd.a.d) {
                    this.a.remove(cd2);
                    return;
                }
                return;
            }
        }
    }

    public final boolean a(com.invertbit.games.gtw.b.Ca ca) {
        java.util.Iterator<com.invertbit.games.gtw.a.Cd> it = this.c.iterator();
        while (it.hasNext()) {
            if (it.next().a == ca) {
                return true;
            }
        }
        return false;
    }

    public final boolean b(com.invertbit.games.gtw.b.Ca ca) {
        java.util.Iterator<com.invertbit.games.gtw.a.Cd> it = this.c.iterator();
        while (it.hasNext()) {
            if (it.next().b == ca) {
                return true;
            }
        }
        return false;
    }
}
