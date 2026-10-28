package com.invertbit.games.c.a;

/* loaded from: classes.dex */
public final class Ca {
    com.invertbit.games.c.a.Cb a = new com.invertbit.games.c.a.Cb();

    public final com.invertbit.games.c.a.a.Cd a(java.lang.String str) throws java.lang.Exception {
        java.io.ByteArrayInputStream byteArrayInputStream = new java.io.ByteArrayInputStream(com.badlogic.gdx.Cf.e.b(str).f().getBytes("UTF-8"));
        try {
            return parse(byteArrayInputStream, this.a);
        } finally {
            byteArrayInputStream.close();
        }
    }

    /** Parses an SVG with the game's own handler (no libGDX needed, so tests use it on world.svg). */
    public static com.invertbit.games.c.a.a.Cd parse(java.io.InputStream in) throws java.lang.Exception {
        return parse(in, new com.invertbit.games.c.a.Cb());
    }

    private static com.invertbit.games.c.a.a.Cd parse(java.io.InputStream in, com.invertbit.games.c.a.Cb handler) throws java.lang.Exception {
        javax.xml.parsers.SAXParserFactory.newInstance().newSAXParser().parse(in, handler);
        return handler.a;
    }
}
