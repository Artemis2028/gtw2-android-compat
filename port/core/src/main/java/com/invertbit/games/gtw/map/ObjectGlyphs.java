package com.invertbit.games.gtw.map;

import com.badlogic.gdx.math.Vector3;
import com.invertbit.games.gtw.nations.Side;
import com.invertbit.games.gtw.sim.WeaponKind;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Unit outlines from the original {@code objects.svg} (original {@code b.a.Ca} model kinds:
 * silo, city, usabomber, russiabomber, chinabomber, submarine). Each kind may have several
 * paths (the silo glyph is three silos). Offsets centre a glyph on its unit, as in
 * {@code b.e.Ch.a(Ia)}.
 */
public final class ObjectGlyphs {

    public enum Kind {
        silo, city, usabomber, russiabomber, chinabomber, submarine
    }

    private final Map<Kind, List<List<Vector3>>> glyphs = new EnumMap<Kind, List<List<Vector3>>>(Kind.class);

    public static ObjectGlyphs load(InputStream in) throws Exception {
        SvgDocument doc = SvgParser.parse(in);
        ObjectGlyphs out = new ObjectGlyphs();
        for (Kind kind : Kind.values()) {
            out.glyphs.put(kind, new ArrayList<List<Vector3>>());
        }
        if (doc.layer == null) {
            return out;
        }
        for (SvgPath path : doc.layer.paths()) {
            if (path.name == null) {
                continue;
            }
            Kind kind;
            try {
                kind = Kind.valueOf(path.name);
            } catch (IllegalArgumentException e) {
                continue;
            }
            List<Vector3> points = path.points();
            if (points.size() >= 2) {
                out.glyphs.get(kind).add(points);
            }
        }
        return out;
    }

    public List<List<Vector3>> paths(Kind kind) {
        List<List<Vector3>> list = glyphs.get(kind);
        return list == null ? new ArrayList<List<Vector3>>() : list;
    }

    /** Original {@code b.e.Ch.a(Ia)}: translation that centres the glyph on its unit. */
    public static float[] offset(Kind kind) {
        switch (kind) {
            case silo: return new float[]{-12.5f, -19.0f};
            case city: return new float[]{-24.0f, -6.936f};
            case usabomber:
            case russiabomber:
            case chinabomber: return new float[]{-15.0f, -19.0f};
            case submarine: return new float[]{-22.51f, -19.0f};
            default: return new float[]{0f, 0f};
        }
    }

    /** Original {@code nations.Nations.bomber}: three bomber models, new sides reuse one. */
    public static Kind bomber(Side side) {
        switch (side) {
            case RUSSIA:
            case AFRICA:
                return Kind.russiabomber;
            case CHINA:
            case SOUTH_AMERICA:
                return Kind.chinabomber;
            default:
                return Kind.usabomber;
        }
    }

    public static Kind forWeapon(WeaponKind weapon, Side side) {
        switch (weapon) {
            case SILO: return Kind.silo;
            case BOMBER: return bomber(side);
            case SUBMARINE: return Kind.submarine;
            default: return Kind.city;
        }
    }
}
