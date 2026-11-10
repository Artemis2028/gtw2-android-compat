package com.invertbit.games.gtw.screens;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.badlogic.gdx.math.Vector3;
import com.invertbit.games.gtw.map.WorldMap;
import com.invertbit.games.gtw.nations.Nations;
import com.invertbit.games.gtw.nations.Side;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

public class NationPreviewLayoutTest {

    @Test
    public void nationFontPrefersHalfButtonHeightButShrinksToFitSouthAmerica() {
        int target = Math.round(NationPreviewLayout.BUTTON_HEIGHT
                * NationPreviewLayout.NATION_FONT_HEIGHT_RATIO);
        assertEquals(77, target);
        assertEquals(target, NationPreviewLayout.nationFontUiSize(
                NationPreviewLayout.BUTTON_WIDTH, NationPreviewLayout.BUTTON_HEIGHT,
                500f, target));
        int shrunk = NationPreviewLayout.nationFontUiSize(
                NationPreviewLayout.BUTTON_WIDTH, NationPreviewLayout.BUTTON_HEIGHT,
                700f, target);
        assertTrue("should shrink when SOUTH AMERICA overflows", shrunk < target);
        assertTrue(shrunk >= 24);
        float maxW = NationPreviewLayout.BUTTON_WIDTH - NationPreviewLayout.NATION_FONT_WIDTH_PAD;
        assertTrue(700f * shrunk / (float) target <= maxW + 0.01f);
    }

    @Test
    public void cellsMatchPickOrderTopRowFirst() {
        float[] cells = NationPreviewLayout.cells(0f, 100f, 1920f, 400f);
        assertEquals(Nations.PICK_ORDER.length * 4, cells.length);
        // USA top-left
        assertEquals(0f, cells[0], 0.01f);
        assertEquals(300f, cells[1], 0.01f);
        assertEquals(640f, cells[2], 0.01f);
        assertEquals(200f, cells[3], 0.01f);
        // SOUTH AMERICA bottom-right
        int sa = 5 * 4;
        assertEquals(1280f, cells[sa], 0.01f);
        assertEquals(100f, cells[sa + 1], 0.01f);
    }

    @Test
    public void fittedCornersStayInsideTheCell() {
        float[] fit = NationPreviewLayout.fit(0f, 0f, 400f, 800f, 10f, 20f, 200f, 160f, 20f);
        float[] xs = {0f, 400f, 0f, 400f};
        float[] zs = {0f, 0f, 800f, 800f};
        for (int i = 0; i < 4; i++) {
            float x = NationPreviewLayout.screenX(xs[i], fit);
            float y = NationPreviewLayout.screenY(zs[i], fit);
            assertTrue("x " + x, x >= 10f - 0.05f && x <= 210f + 0.05f);
            assertTrue("y " + y, y >= 20f - 0.05f && y <= 180f + 0.05f);
        }
    }

    @Test
    public void everyHomeSilhouetteFitsItsPickCell() throws Exception {
        WorldMap map = loadMap();
        float[] cells = NationPreviewLayout.cells(0f, 400f, 1920f, 500f);
        for (int i = 0; i < Nations.PICK_ORDER.length; i++) {
            Side side = Nations.PICK_ORDER[i];
            List<List<Vector3>> rings = NationPreviewLayout.homeRings(map, side);
            assertTrue(side + " has no home rings", !rings.isEmpty());
            float[] box = NationPreviewLayout.bounds(rings);
            assertNotNull(side + " bounds", box);
            float cellX = cells[i * 4];
            float cellY = cells[i * 4 + 1];
            float cellW = cells[i * 4 + 2];
            float cellH = cells[i * 4 + 3];
            float[] fit = NationPreviewLayout.fit(box[0], box[1], box[2], box[3],
                    cellX, cellY, cellW, cellH, NationPreviewLayout.CELL_PAD);
            assertTrue(side + " scale", fit[0] > 0f);
            int points = 0;
            for (int r = 0; r < rings.size(); r++) {
                List<Vector3> ring = rings.get(r);
                for (int p = 0; p < ring.size(); p++) {
                    Vector3 v = ring.get(p);
                    float x = NationPreviewLayout.screenX(v.x, fit);
                    float y = NationPreviewLayout.screenY(v.z, fit);
                    assertTrue(side + " clipped x=" + x + " y=" + y,
                            x >= cellX - 0.5f && x <= cellX + cellW + 0.5f
                                    && y >= cellY - 0.5f && y <= cellY + cellH + 0.5f);
                    points++;
                }
            }
            assertTrue(side + " empty", points > 0);
        }
    }

    private static WorldMap loadMap() throws Exception {
        String[] candidates = {
                "../../src/android/src/main/assets/world.svg",
                "../src/android/src/main/assets/world.svg",
                "src/android/src/main/assets/world.svg",
                "../android/src/main/assets/world.svg"
        };
        File file = null;
        for (int i = 0; i < candidates.length; i++) {
            File f = new File(candidates[i]);
            if (f.isFile()) {
                file = f;
                break;
            }
        }
        if (file == null) {
            throw new AssertionError("world.svg not found");
        }
        FileInputStream in = new FileInputStream(file);
        try {
            return WorldMap.load(in);
        } finally {
            in.close();
        }
    }
}
