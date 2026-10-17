package com.invertbit.games.gtw.a;

/* loaded from: classes.dex */
public final class Ck implements java.lang.Comparable<com.invertbit.games.gtw.a.Ck> {
    public com.badlogic.gdx.graphics.a.Cg a;
    float b;

    @Override // java.lang.Comparable
    public final int compareTo(com.invertbit.games.gtw.a.Ck ck) {
        return java.lang.Float.valueOf(this.b).compareTo(java.lang.Float.valueOf(ck.b));
    }

    public Ck(com.badlogic.gdx.graphics.a.Cg cg, float f) {
        this.a = cg;
        this.b = f;
    }

    public final java.lang.String toString() {
        return "SelectedObject{modelInstance=" + this.a.g + ", distance=" + this.b + '}';
    }
}
