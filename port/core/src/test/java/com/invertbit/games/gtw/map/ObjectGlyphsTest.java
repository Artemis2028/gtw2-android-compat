package com.invertbit.games.gtw.map;

import com.invertbit.games.gtw.nations.Side;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class ObjectGlyphsTest {

    static File objectsSvg() {
        String[] candidates = {
                "../../src/android/src/main/assets/objects.svg",
                "../src/android/src/main/assets/objects.svg",
                "src/android/src/main/assets/objects.svg"
        };
        for (String path : candidates) {
            File f = new File(path);
            if (f.isFile()) {
                return f;
            }
        }
        throw new AssertionError("objects.svg not found");
    }

    @Test
    public void everyOriginalKindHasAnOutline() throws Exception {
        InputStream in = new FileInputStream(objectsSvg());
        ObjectGlyphs glyphs;
        try {
            glyphs = ObjectGlyphs.load(in);
        } finally {
            in.close();
        }
        for (ObjectGlyphs.Kind kind : ObjectGlyphs.Kind.values()) {
            assertFalse(kind + " has no path", glyphs.paths(kind).isEmpty());
        }
        assertEquals("the silo glyph is three silos", 3, glyphs.paths(ObjectGlyphs.Kind.silo).size());
    }

    @Test
    public void bomberModelPerSideMatchesOriginal() {
        assertEquals(ObjectGlyphs.Kind.usabomber, ObjectGlyphs.bomber(Side.USA));
        assertEquals(ObjectGlyphs.Kind.usabomber, ObjectGlyphs.bomber(Side.EUROPE));
        assertEquals(ObjectGlyphs.Kind.russiabomber, ObjectGlyphs.bomber(Side.RUSSIA));
        assertEquals(ObjectGlyphs.Kind.russiabomber, ObjectGlyphs.bomber(Side.AFRICA));
        assertEquals(ObjectGlyphs.Kind.chinabomber, ObjectGlyphs.bomber(Side.CHINA));
        assertEquals(ObjectGlyphs.Kind.chinabomber, ObjectGlyphs.bomber(Side.SOUTH_AMERICA));
    }
}
