package com.invertbit.games.gtw.screens;

import com.badlogic.gdx.math.Vector3;
import com.invertbit.games.gtw.GtwGame;
import com.invertbit.games.gtw.map.ObjectGlyphs;
import com.invertbit.games.gtw.map.WorldMap;
import com.invertbit.games.gtw.nations.Nations;
import com.invertbit.games.gtw.nations.Side;
import com.invertbit.games.gtw.sim.Region;
import com.invertbit.games.gtw.sim.WorldRegions;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BattleHudLayoutTest {

    private static final float[][] SCREENS = {
            {2400f, 1080f},
            {1920f, 1080f},
            {2560f, 1440f},
    };

    @Test
    public void statusSlotSitsBetweenSpeedAndPauseInTheTopBar() {
        float[] slot = BattleHudLayout.statusSlotUi();
        float expectedLeft = BattleHudLayout.TOP_PAD + BattleHudLayout.SPEED_WIDTH
                + BattleHudLayout.TOP_PAD + BattleHudLayout.STATUS_INSET;
        float expectedRight = GtwGame.UI_WIDTH - (BattleHudLayout.TOP_PAD
                + BattleHudLayout.PAUSE_WIDTH + BattleHudLayout.TOP_PAD
                + BattleHudLayout.STATUS_INSET);
        assertEquals(expectedLeft, slot[0], 0.01f);
        assertEquals(expectedRight - expectedLeft, slot[2], 0.01f);
        assertTrue("status must start after the speed button, not at the left edge",
                slot[0] > BattleHudLayout.SPEED_WIDTH);
        float topFromBottom = slot[1] + slot[3];
        float chromeTop = GtwGame.UI_HEIGHT - BattleHudLayout.TOP_PAD;
        float chromeBottom = chromeTop - BattleHudLayout.SPEED_HEIGHT;
        assertTrue("caption should sit inside the top chrome",
                slot[1] >= chromeBottom - 0.01f && topFromBottom <= chromeTop + 0.01f);
    }

    @Test
    public void statusSlotDoesNotCoverLandForAnyNation() throws Exception {
        WorldMap map = loadMap();
        View view = fittedView(map);
        for (int s = 0; s < SCREENS.length; s++) {
            float screenW = SCREENS[s][0];
            float screenH = SCREENS[s][1];
            float[] status = BattleHudLayout.uiRectToScreen(
                    BattleHudLayout.statusSlotUi(), screenW, screenH);
            for (Side side : Nations.PICK_ORDER) {
                int hits = landHits(map, side, view, screenW, screenH, status);
                assertEquals("status slot overlaps " + side + " land on "
                        + (int) screenW + "x" + (int) screenH + " (" + hits + " points)",
                        0, hits);
            }
        }
    }

    @Test
    public void legacyLeftAlignedRowDidCoverUsaAlaskaOnThePlaytestScreen() throws Exception {
        WorldMap map = loadMap();
        View view = fittedView(map);
        float[] legacy = BattleHudLayout.uiRectToScreen(
                BattleHudLayout.legacyStatusSlotUi(), 2400f, 1080f);
        int hits = landHits(map, Side.USA, view, 2400f, 1080f, legacy);
        assertTrue("sanity: the old second-row status should overlap USA/Alaska, not "
                + hits + " points", hits > 0);
    }

    @Test
    public void speedButtonClearsEveryUnitGlyphWithMargin() throws Exception {
        WorldMap map = loadMap();
        View view = fittedView(map);
        Map<String, Region> regions = loadRegions();
        float[] reach = glyphReach();
        for (int s = 0; s < SCREENS.length; s++) {
            float screenW = SCREENS[s][0];
            float screenH = SCREENS[s][1];
            float[] speed = BattleHudLayout.uiRectToScreen(
                    BattleHudLayout.speedButtonUi(), screenW, screenH);
            float worst = Float.MAX_VALUE;
            String where = "";
            for (Region region : regions.values()) {
                for (com.badlogic.gdx.math.Vector3 cell : region.grid) {
                    // glyph footprint in world units around the unit, y up = -z
                    float[] a = view.project(cell.x + reach[0], -(cell.z + reach[3]), screenW, screenH);
                    float[] b = view.project(cell.x + reach[2], -(cell.z + reach[1]), screenW, screenH);
                    float gap = rectGap(speed, Math.min(a[0], b[0]), Math.min(a[1], b[1]),
                            Math.max(a[0], b[0]), Math.max(a[1], b[1]));
                    if (gap < worst) {
                        worst = gap;
                        where = region.name + " @" + (int) cell.x + "," + (int) cell.z;
                    }
                }
            }
            assertTrue("Speed button is only " + worst + " px from the " + where + " unit on "
                            + (int) screenW + "x" + (int) screenH,
                    worst >= BattleHudLayout.SPEED_UNIT_CLEARANCE_PX);
        }
    }

    @Test
    public void speedButtonStillSitsInTheTopLeftChrome() {
        float[] speed = BattleHudLayout.speedButtonUi();
        assertEquals(BattleHudLayout.TOP_PAD, speed[0], 0.01f);
        assertEquals(GtwGame.UI_HEIGHT - BattleHudLayout.TOP_PAD, speed[1] + speed[3], 0.01f);
        assertTrue("speed button must fit in the top bar", speed[3] <= BattleHudLayout.SPEED_HEIGHT);
        float[] status = BattleHudLayout.statusSlotUi();
        assertTrue("status must start right of the speed button", status[0] > speed[0] + speed[2]);
    }

    /** Pixel distance between two screen rects (0 when they touch or overlap). */
    private static float rectGap(float[] r, float x0, float y0, float x1, float y1) {
        float dx = Math.max(0f, Math.max(r[0] - x1, x0 - (r[0] + r[2])));
        float dy = Math.max(0f, Math.max(r[1] - y1, y0 - (r[1] + r[3])));
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    /** Union footprint of every unit glyph relative to its unit: minX, minZ, maxX, maxZ. */
    private static float[] glyphReach() throws Exception {
        FileInputStream in = new FileInputStream(com.invertbit.games.gtw.map.ObjectGlyphsTestAccess.objectsSvg());
        ObjectGlyphs glyphs;
        try {
            glyphs = ObjectGlyphs.load(in);
        } finally {
            in.close();
        }
        float[] r = {Float.MAX_VALUE, Float.MAX_VALUE, -Float.MAX_VALUE, -Float.MAX_VALUE};
        for (ObjectGlyphs.Kind kind : ObjectGlyphs.Kind.values()) {
            float[] off = ObjectGlyphs.offset(kind);
            for (List<com.badlogic.gdx.math.Vector3> path : glyphs.paths(kind)) {
                for (com.badlogic.gdx.math.Vector3 p : path) {
                    r[0] = Math.min(r[0], p.x + off[0]);
                    r[1] = Math.min(r[1], p.z + off[1]);
                    r[2] = Math.max(r[2], p.x + off[0]);
                    r[3] = Math.max(r[3], p.z + off[1]);
                }
            }
        }
        return r;
    }

    private static Map<String, Region> loadRegions() throws Exception {
        FileInputStream in = new FileInputStream(worldFile());
        try {
            return WorldRegions.load(in);
        } finally {
            in.close();
        }
    }

    private static int landHits(WorldMap map, Side side, View view,
                                float screenW, float screenH, float[] rect) {
        int hits = 0;
        for (WorldMap.Outline outline : map.outlines) {
            if (!Nations.isHome(side, outline.name)) {
                continue;
            }
            for (Vector3 p : outline.points) {
                float[] screen = view.project(p.x, -p.z, screenW, screenH);
                if (inside(rect, screen[0], screen[1])) {
                    hits++;
                }
            }
        }
        return hits;
    }

    private static boolean inside(float[] rect, float x, float y) {
        return x >= rect[0] && x <= rect[0] + rect[2]
                && y >= rect[1] && y <= rect[1] + rect[3];
    }

    /**
     * Same world-fit as {@code BattleScreen.fitCamera}: bounding box of every
     * outline (including the Chukotka scrap) plus 80 world-units of pad.
     */
    private static View fittedView(WorldMap map) {
        float minX = Float.MAX_VALUE, maxX = -Float.MAX_VALUE;
        float minY = Float.MAX_VALUE, maxY = -Float.MAX_VALUE;
        for (WorldMap.Outline outline : map.outlines) {
            for (Vector3 p : outline.points) {
                if (p.x < minX) {
                    minX = p.x;
                }
                if (p.x > maxX) {
                    maxX = p.x;
                }
                float y = -p.z;
                if (y < minY) {
                    minY = y;
                }
                if (y > maxY) {
                    maxY = y;
                }
            }
        }
        float w = Math.max(100f, maxX - minX);
        float h = Math.max(100f, maxY - minY);
        float pad = 80f;
        View view = new View();
        view.worldW = w + pad * 2f;
        view.worldH = h + pad * 2f;
        view.camX = minX + w * 0.5f;
        view.camY = minY + h * 0.5f;
        return view;
    }

    private static final class View {
        float worldW, worldH, camX, camY;

        float[] project(float worldX, float worldY, float screenW, float screenH) {
            float scale = Math.min(screenW / worldW, screenH / worldH);
            float vpW = worldW * scale;
            float vpH = worldH * scale;
            float vx = (screenW - vpW) * 0.5f;
            float vy = (screenH - vpH) * 0.5f;
            float sx = vx + (worldX - (camX - worldW * 0.5f)) * scale;
            float syFromBottom = vy + (worldY - (camY - worldH * 0.5f)) * scale;
            return new float[]{sx, screenH - syFromBottom};
        }
    }

    private static File worldFile() {
        String[] candidates = {
                "../../src/android/src/main/assets/world.svg",
                "../src/android/src/main/assets/world.svg",
                "src/android/src/main/assets/world.svg",
                "../android/src/main/assets/world.svg"
        };
        for (int i = 0; i < candidates.length; i++) {
            File f = new File(candidates[i]);
            if (f.isFile()) {
                return f;
            }
        }
        throw new AssertionError("world.svg not found");
    }

    private static WorldMap loadMap() throws Exception {
        FileInputStream in = new FileInputStream(worldFile());
        try {
            return WorldMap.load(in);
        } finally {
            in.close();
        }
    }
}
