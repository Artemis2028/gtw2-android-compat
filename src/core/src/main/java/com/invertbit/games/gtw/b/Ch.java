package com.invertbit.games.gtw.b;

/* loaded from: classes.dex */
public class Ch extends com.invertbit.games.gtw.b.Ca {
    public com.invertbit.games.gtw.a.Cd e;
    public com.invertbit.games.b.Ca f;

    @Override // com.invertbit.games.gtw.b.Ca
    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        if (!super.equals(obj)) {
            return false;
        }
        com.invertbit.games.gtw.b.Ch ch = (com.invertbit.games.gtw.b.Ch) obj;
        if (this.e == null ? ch.e != null : !this.e.equals(ch.e)) {
            return false;
        }
        if (this.f != null) {
            if (this.f.equals(ch.f)) {
                return true;
            }
        } else if (ch.f == null) {
            return true;
        }
        return false;
    }

    @Override // com.invertbit.games.gtw.b.Ca
    public int hashCode() {
        return (((this.e != null ? this.e.hashCode() : 0) + (super.hashCode() * 31)) * 31) + (this.f != null ? this.f.hashCode() : 0);
    }

    public java.lang.String toString() {
        return "Rocket{attackBean=" + this.e.toString() + '}';
    }

    public Ch(com.invertbit.games.gtw.a.Cd cd, com.invertbit.games.b.Ca ca) {
        super(cd.b.b);
        this.e = cd;
        this.f = ca;
    }
}
