package com.invertbit.games.c.a;

/* loaded from: classes.dex */
public final class Ca {
    com.invertbit.games.c.a.Cb a = new com.invertbit.games.c.a.Cb();

    public final com.invertbit.games.c.a.a.Cd a(java.lang.String str) throws java.lang.Exception {
        javax.xml.parsers.SAXParser sAXParserNewSAXParser = javax.xml.parsers.SAXParserFactory.newInstance().newSAXParser();
        java.io.ByteArrayInputStream byteArrayInputStream = new java.io.ByteArrayInputStream(com.badlogic.gdx.Cf.e.b(str).f().getBytes("UTF-8"));
        sAXParserNewSAXParser.parse(byteArrayInputStream, this.a);
        byteArrayInputStream.close();
        return this.a.a;
    }
}
