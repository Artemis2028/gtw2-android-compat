package com.invertbit.games.gtw.map;

import java.io.File;

/** Lets tests in other packages find objects.svg the same way ObjectGlyphsTest does. */
public final class ObjectGlyphsTestAccess {
    private ObjectGlyphsTestAccess() {}

    public static File objectsSvg() {
        return ObjectGlyphsTest.objectsSvg();
    }
}
