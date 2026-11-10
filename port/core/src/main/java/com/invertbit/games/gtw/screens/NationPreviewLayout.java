package com.invertbit.games.gtw.screens;

import com.badlogic.gdx.math.Vector3;
import com.invertbit.games.gtw.map.WorldMap;
import com.invertbit.games.gtw.nations.Nations;
import com.invertbit.games.gtw.nations.Side;

import java.util.ArrayList;
import java.util.List;

/**
 * Choose-player / choose-opponent silhouette slots: a 2x3 grid matching the
 * nation buttons, with each side scaled and centered from its home outlines
 * so nothing is clipped by the button row.
 */
public final class NationPreviewLayout {

    public static final float BUTTON_WIDTH = 620f;
    public static final float BUTTON_HEIGHT = 140f;
    public static final float BUTTON_PAD = 8f;
    public static final float BACK_HEIGHT = 90f;
    public static final float TITLE_PAD_TOP = 16f;
    public static final float TITLE_PAD_BOTTOM = 8f;
    public static final float BACK_PAD_TOP = 8f;
    public static final float BACK_PAD_BOTTOM = 16f;
    public static final float CELL_PAD = 22f;
    /** Keep an extra home ring only if the fitted scale stays at least this
     * fraction of the largest-ring scale. Drops dateline scraps (Chukotka)
     * that would shrink the whole silhouette, but keeps Alaska. */
    public static final float MIN_KEEP_SCALE = 0.5f;
    public static final int COLS = 3;
    public static final int ROWS = 2;
    /** Target nation-label size as a fraction of the 140px button height. */
    public static final float NATION_FONT_HEIGHT_RATIO = 0.55f;
    public static final float NATION_FONT_WIDTH_PAD = 48f;

    private NationPreviewLayout() {}

    public static int nationFontUiSize(float buttonWidth, float buttonHeight,
                                      float textWidthAtTarget, int targetPx) {
        float wanted = buttonHeight * NATION_FONT_HEIGHT_RATIO;
        int target = targetPx > 0 ? targetPx : Math.max(24, Math.round(wanted));
        float maxWidth = buttonWidth - NATION_FONT_WIDTH_PAD;
        if (textWidthAtTarget <= maxWidth || textWidthAtTarget <= 0f) {
            return target;
        }
        return Math.max(24, (int) Math.floor(target * (maxWidth / textWidthAtTarget)));
    }

    /**
     * Six cells in PICK_ORDER (row-major, top row first). Each group of four
     * values is {@code x, y, w, h} in the same space as {@code area}.
     */
    public static float[] cells(float areaX, float areaY, float areaW, float areaH) {
        float[] out = new float[PICK_SLOTS() * 4];
        float cw = areaW / COLS;
        float ch = areaH / ROWS;
        for (int i = 0; i < PICK_SLOTS(); i++) {
            int row = i / COLS;
            int col = i % COLS;
            out[i * 4] = areaX + col * cw;
            out[i * 4 + 1] = areaY + (ROWS - 1 - row) * ch;
            out[i * 4 + 2] = cw;
            out[i * 4 + 3] = ch;
        }
        return out;
    }

    public static int PICK_SLOTS() {
        return Nations.PICK_ORDER.length;
    }

    public static List<List<Vector3>> homeRings(WorldMap map, Side side) {
        List<List<Vector3>> rings = new ArrayList<List<Vector3>>();
        if (map == null) {
            return rings;
        }
        for (WorldMap.Outline outline : map.drawableOutlines()) {
            if (outline.points.size() < 2) {
                continue;
            }
            if (!Nations.isHome(side, outline.name)) {
                continue;
            }
            rings.add(outline.points);
        }
        return rings;
    }

    /** {@code minX, minZ, maxX, maxZ} or null if there are no points. */
    public static float[] bounds(List<List<Vector3>> rings) {
        float minX = Float.POSITIVE_INFINITY;
        float minZ = Float.POSITIVE_INFINITY;
        float maxX = Float.NEGATIVE_INFINITY;
        float maxZ = Float.NEGATIVE_INFINITY;
        boolean any = false;
        for (int r = 0; r < rings.size(); r++) {
            List<Vector3> ring = rings.get(r);
            for (int i = 0; i < ring.size(); i++) {
                Vector3 p = ring.get(i);
                if (p.x < minX) {
                    minX = p.x;
                }
                if (p.z < minZ) {
                    minZ = p.z;
                }
                if (p.x > maxX) {
                    maxX = p.x;
                }
                if (p.z > maxZ) {
                    maxZ = p.z;
                }
                any = true;
            }
        }
        return any ? new float[]{minX, minZ, maxX, maxZ} : null;
    }

    /**
     * Uniform scale and centers that map world {@code (x, z)} into the cell
     * via {@link #screenX} / {@link #screenY}.
     * Returned array: {@code scale, worldCx, worldCz, cellCx, cellCy}.
     */
    public static float[] fit(float minX, float minZ, float maxX, float maxZ,
                              float cellX, float cellY, float cellW, float cellH,
                              float pad) {
        float bw = Math.max(1f, maxX - minX);
        float bh = Math.max(1f, maxZ - minZ);
        float innerW = Math.max(1f, cellW - 2f * pad);
        float innerH = Math.max(1f, cellH - 2f * pad);
        float scale = Math.min(innerW / bw, innerH / bh);
        return new float[]{
                scale,
                (minX + maxX) * 0.5f,
                (minZ + maxZ) * 0.5f,
                cellX + cellW * 0.5f,
                cellY + cellH * 0.5f
        };
    }

    public static float screenX(float worldX, float[] fit) {
        return fit[3] + (worldX - fit[1]) * fit[0];
    }

    public static float screenY(float worldZ, float[] fit) {
        return fit[4] + (fit[2] - worldZ) * fit[0];
    }

    public static float ringArea(List<Vector3> ring) {
        float area = 0f;
        int n = ring.size();
        for (int i = 0; i < n; i++) {
            Vector3 p = ring.get(i);
            Vector3 q = ring.get((i + 1) % n);
            area += p.x * q.z - q.x * p.z;
        }
        return Math.abs(area) * 0.5f;
    }

    /**
     * Rings to draw in a pick cell: start from the largest landmass and add
     * extras only while the fitted scale stays above {@link #MIN_KEEP_SCALE}
     * of that primary scale.
     */
    public static List<List<Vector3>> previewRings(List<List<Vector3>> rings,
                                                   float cellW, float cellH, float pad) {
        if (rings == null || rings.size() <= 1) {
            return rings;
        }
        int primary = 0;
        float best = -1f;
        for (int i = 0; i < rings.size(); i++) {
            float area = ringArea(rings.get(i));
            if (area > best) {
                best = area;
                primary = i;
            }
        }
        List<List<Vector3>> included = new ArrayList<List<Vector3>>();
        included.add(rings.get(primary));
        float[] primBox = bounds(included);
        float primScale = fit(primBox[0], primBox[1], primBox[2], primBox[3],
                0f, 0f, cellW, cellH, pad)[0];
        for (int i = 0; i < rings.size(); i++) {
            if (i == primary) {
                continue;
            }
            included.add(rings.get(i));
            float[] box = bounds(included);
            float scale = fit(box[0], box[1], box[2], box[3],
                    0f, 0f, cellW, cellH, pad)[0];
            if (scale < primScale * MIN_KEEP_SCALE) {
                included.remove(included.size() - 1);
            }
        }
        return included;
    }

}
