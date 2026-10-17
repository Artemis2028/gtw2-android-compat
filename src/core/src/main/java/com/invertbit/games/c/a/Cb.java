package com.invertbit.games.c.a;

/* loaded from: classes.dex */
public final class Cb extends org.xml.sax.helpers.DefaultHandler {
    com.invertbit.games.c.a.a.Cd a;
    java.lang.String b;

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public final void characters(char[] cArr, int i, int i2) {
        if (!"title".equals(this.b)) {
            return;
        }
        java.util.List<com.invertbit.games.c.a.a.Cb> r0 = this.a.c.a();
        com.invertbit.games.c.a.a.Cb cb = r0.get(r0.size() - 1);
        java.lang.String string = new java.lang.StringBuilder().append(cArr, i, i2).toString();
        if (string.trim().length() <= 0) {
            return;
        }
        cb.a = string;
    }

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public final void startElement(java.lang.String str, java.lang.String str2, java.lang.String str3, org.xml.sax.Attributes attributes) throws java.lang.NumberFormatException {
        java.lang.String value;
        boolean z;
        com.badlogic.gdx.math.Cl cl;
        this.b = str3;
        if (!"path".equals(str3)) {
            if (!"rect".equals(str3)) {
                if (!"svg".equals(str3)) {
                    if (!"g".equals(str3) || this.a.c != null) {
                        return;
                    }
                    this.a.c = new com.invertbit.games.c.a.a.Ca();
                    return;
                }
                this.a = new com.invertbit.games.c.a.a.Cd();
                if (attributes == null) {
                    return;
                }
                java.lang.String value2 = attributes.getValue("width");
                if (value2 != null) {
                    this.a.a = java.lang.Float.parseFloat(value2);
                }
                java.lang.String value3 = attributes.getValue("height");
                if (value3 != null) {
                    return;
                }
                this.a.b = java.lang.Float.parseFloat(value3);
                return;
            }
            com.invertbit.games.c.a.a.Ca ca = this.a.c;
            if (attributes == null) {
                throw new java.lang.IllegalArgumentException(str3);
            }
            java.lang.String value4 = attributes.getValue("onclick");
            if (value4 == null) {
                throw new java.lang.IllegalArgumentException(str3);
            }
            float f = java.lang.Float.parseFloat(attributes.getValue("width"));
            float f2 = java.lang.Float.parseFloat(attributes.getValue("height"));
            com.badlogic.gdx.math.Cl cl2 = new com.badlogic.gdx.math.Cl(java.lang.Float.parseFloat(attributes.getValue("x")), 0.0f, java.lang.Float.parseFloat(attributes.getValue("y")));
            java.lang.String value5 = attributes.getValue("onmouseover");
            if (value5 == null || value5.isEmpty()) {
                com.invertbit.games.c.a.a.Cc cc = new com.invertbit.games.c.a.a.Cc(cl2, f, f2);
                if (ca.a.containsKey(value4)) {
                    ca.a.get(value4).add(cc);
                    return;
                }
                java.util.ArrayList arrayList = new java.util.ArrayList();
                arrayList.add(cc);
                ca.a.put(value4, arrayList);
                return;
            }
            com.invertbit.games.c.a.a.Cc cc2 = new com.invertbit.games.c.a.a.Cc(cl2, f, f2);
            if (ca.b.containsKey(value4)) {
                return;
            }
            ca.b.put(value4, cc2);
            return;
        }
        com.invertbit.games.c.a.a.Ca ca2 = this.a.c;
        if (attributes != null && (value = attributes.getValue("d")) != null) {
            java.lang.String value6 = attributes.getValue("onclick");
            com.invertbit.games.c.a.a.Cb cb = new com.invertbit.games.c.a.a.Cb();
            cb.a = value6;
            com.badlogic.gdx.math.Cl cl3 = new com.badlogic.gdx.math.Cl();
            java.lang.String str4 = null;
            java.lang.String[] strArrSplit = value.split(" ");
            int length = strArrSplit.length;
            int i = 0;
            while (i < length) {
                java.lang.String str5 = strArrSplit[i];
                if ("m".equalsIgnoreCase(str5) || "l".equalsIgnoreCase(str5)) {
                    z = true;
                } else {
                    z = false;
                }
                if (!z) {
                    java.lang.String[] strArrSplit2 = str5.split(",");
                    if ("m".equals(str4) || "l".equals(str4)) {
                        cl = new com.badlogic.gdx.math.Cl(cl3.a + java.lang.Float.parseFloat(strArrSplit2[0]), 0.0f, java.lang.Float.parseFloat(strArrSplit2[1]) + cl3.c);
                    } else if ("M".equals(str4) || "L".equals(str4)) {
                        cl = new com.badlogic.gdx.math.Cl(java.lang.Float.parseFloat(strArrSplit2[0]), 0.0f, java.lang.Float.parseFloat(strArrSplit2[1]));
                    } else {
                        throw new java.lang.IllegalArgumentException("Wrong directive " + str4);
                    }
                    cl3.a = cl.a;
                    cl3.c = cl.c;
                    cb.a().add(cl);
                    str5 = str4;
                }
                i++;
                str4 = str5;
            }
            ca2.a().add(cb);
        }
    }
}
