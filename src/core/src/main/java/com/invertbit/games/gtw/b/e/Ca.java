package com.invertbit.games.gtw.b.e;

/* loaded from: classes.dex */
public final class Ca {
    public static com.invertbit.games.gtw.b.e.Ca a;
    public com.invertbit.games.gtw.b.e.Cc b = com.invertbit.games.gtw.b.e.Cc.a();
    java.util.List<java.lang.Integer[]> c = new java.util.ArrayList();

    public Ca() {
        this.c.add(new java.lang.Integer[]{2, 4, 4});
    }

    public final void a(int i, com.invertbit.games.gtw.a.Ci ci) {
        int iF = ci.f();
        if (iF <= i) {
            java.util.List<com.invertbit.games.gtw.b.Cf.Ib> listA = this.b.a(ci.e, i);
            java.util.Collections.shuffle(listA);
            java.util.Iterator<com.invertbit.games.gtw.b.Cf.Ib> it = listA.iterator();
            while (true) {
                int i2 = iF;
                if (it.hasNext()) {
                    com.invertbit.games.gtw.b.Cf cf = com.invertbit.games.gtw.b.a.Ca.a().b().get(it.next().name());
                    if ((!ci.d.contains(cf)) && i2 <= i && cf.c == null) {
                        ci.c(cf);
                        i2++;
                    }
                    iF = i2;
                } else {
                    return;
                }
            }
        }
    }

    private void a(com.invertbit.games.gtw.a.Cj cj, int i) {
        int i2;
        int i3;
        int i4;
        if (com.invertbit.games.gtw.a.Cj.Ia.CITY.equals(cj.d) || com.invertbit.games.gtw.a.Cj.Ia.BOMBER.equals(cj.d) || com.invertbit.games.gtw.a.Cj.Ia.SUBMARINE.equals(cj.d)) {
            cj.a(i);
            return;
        }
        java.util.Collections.shuffle(this.c);
        java.lang.Integer[] numArr = this.c.get(0);
        int iIntValue = (numArr[2].intValue() * i) / 10;
        int iIntValue2 = (numArr[0].intValue() * i) / 10;
        int iIntValue3 = (numArr[1].intValue() * i) / 10;
        int i5 = i - ((iIntValue + iIntValue2) + iIntValue3);
        if (i5 == 5) {
            i4 = iIntValue;
            i2 = i5 + iIntValue3;
            i3 = iIntValue2;
        } else if (i5 == 10) {
            i3 = i5 + iIntValue2;
            i4 = iIntValue;
            i2 = iIntValue3;
        } else {
            int i6 = i5 + iIntValue;
            i2 = iIntValue3;
            i3 = iIntValue2;
            i4 = i6;
        }
        cj.a(i2);
        cj.b(i3);
        cj.c(i4);
    }

    public final void a(com.invertbit.games.gtw.a.Ci ci) {
        com.invertbit.games.gtw.a.Cb cb = ci.i;
        cb.j = true;
        int i = cb.d - (cb.a * 5);
        java.util.Collections.shuffle(this.c);
        java.lang.Integer[] numArr = this.c.get(0);
        int iIntValue = ((numArr[0].intValue() * i) / 10) + (cb.a * 5);
        int iMin = java.lang.Math.min((numArr[1].intValue() * i) / 10, (cb.b * 5) - iIntValue);
        int i2 = (iMin * 1) / 10;
        int i3 = iMin - i2;
        int iIntValue2 = (numArr[2].intValue() * i) / 10;
        a(cb.f, iIntValue);
        a(cb.g, i3);
        a(cb.h, i2);
        a(cb.i, iIntValue2 + (i - (((iIntValue + i3) + i2) + iIntValue2)));
    }
}
