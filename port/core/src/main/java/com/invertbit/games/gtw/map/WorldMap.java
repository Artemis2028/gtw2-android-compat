package com.invertbit.games.gtw.map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.invertbit.games.gtw.nations.Nations;
import com.invertbit.games.gtw.nations.Side;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Region outlines loaded from {@code world.svg}. Every named path is kept so
 * multi-part regions (NATO islands, Pacific, Russia far-east, etc.) can be hit;
 * {@link #byName} points at the first outline of that name.
 *
 * <p>Touching rings of the same region are dissolved in {@code world.svg}.
 * Separate starting-home regions that touch (brazil and chile) keep their own
 * outlines and are drawn separately.
 */
public final class WorldMap {

    public static final Color UNOWNED = new Color(0.35f, 0.38f, 0.42f, 1f);

    public final float width;
    public final float height;
    public final List<Outline> outlines = new ArrayList<Outline>();
    public final Map<String, Outline> byName = new LinkedHashMap<String, Outline>();

    public WorldMap(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public static WorldMap load(InputStream in) throws Exception {
        SvgDocument doc = SvgParser.parse(in);
        float w = doc.width > 0 ? doc.width : 2880f;
        float h = doc.height > 0 ? doc.height : 1920f;
        WorldMap map = new WorldMap(w, h);
        if (doc.layer == null) {
            return map;
        }
        for (SvgPath path : doc.layer.paths()) {
            if (path.name == null || path.points().size() < 2) {
                continue;
            }
            Outline outline = new Outline(path.name, path.points());
            map.outlines.add(outline);
            if (!map.byName.containsKey(path.name)) {
                map.byName.put(path.name, outline);
            }
        }
        return map;
    }

    /** Outlines to stroke on pick / alliance / battle / map screens. */
    public List<Outline> drawableOutlines() {
        return outlines;
    }

    public static Color colorFor(String regionName) {
        return colorFor(regionName, Nations.ownerOf(regionName));
    }

    public static Color colorFor(String regionName, Side owner) {
        if (owner == null) {
            return UNOWNED;
        }
        float[] rgb = Nations.rgb(owner);
        return new Color(rgb[0], rgb[1], rgb[2], 1f);
    }

    public static final class Outline {
        public final String name;
        public final List<Vector3> points;

        public Outline(String name, List<Vector3> points) {
            this.name = name;
            this.points = points;
        }
    }
}
