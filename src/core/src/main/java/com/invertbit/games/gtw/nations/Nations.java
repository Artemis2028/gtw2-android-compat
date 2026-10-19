package com.invertbit.games.gtw.nations;

import com.invertbit.games.gtw.a.Ci;
import com.invertbit.games.gtw.b.Cf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Single source of truth for everything that differs between playable sides.
 *
 * <p>The original game hardcoded USA / RUSSIA / CHINA in ~10 switch statements and if-chains.
 * Those sites now ask this class, so a side is fully described by one entry here plus its
 * {@link Ci.Ia} constant. A side is a "home region" of the map ({@link Cf.Ib}) plus colours,
 * a bomber model, a pick-screen preview position and (for the new sides) real city positions.
 *
 * <p>The three sides added on top of the original game (EUROPE, AFRICA, SOUTH_AMERICA) are
 * bloc/region nations built on regions that already exist in {@code world.svg} with real map
 * outlines and unit-placement grids:
 * <ul>
 *   <li>EUROPE        -> region {@code europe}: Cold War European NATO
 *       (UK, Iceland, Norway, Denmark, Benelux, West Germany, France, Italy,
 *       Portugal, Spain, Greece, Turkey). Not Sweden/Finland/Austria/Switzerland/
 *       Ireland/East Germany/Poland/Warsaw Pact — those are {@code easteurope}.</li>
 *   <li>AFRICA        -> region {@code africa} (central and southern Africa + Madagascar)</li>
 *   <li>SOUTH_AMERICA -> regions {@code brazil} + {@code chile} (whole continent:
 *       Brazil's outline plus the rest of South America as chile)</li>
 * </ul>
 * Neighbouring regions ({@code sahara}, {@code egipt}, {@code easteurope}, ...) stay as
 * alliable countries, exactly like Canada/Mexico are for the USA.
 */
public final class Nations {

    private Nations() {
    }

    /** Pick-screen order: two rows of three, matching the preview grid below. */
    public static final Ci.Ia[] PICK_ORDER = {
            Ci.Ia.USA, Ci.Ia.EUROPE, Ci.Ia.CHINA,
            Ci.Ia.AFRICA, Ci.Ia.RUSSIA, Ci.Ia.SOUTH_AMERICA
    };

    /** True for the three sides of the original game. */
    public static boolean isOriginal(Ci.Ia ia) {
        return ia == Ci.Ia.USA || ia == Ci.Ia.RUSSIA || ia == Ci.Ia.CHINA;
    }

    /** The map region a side starts with. */
    public static Cf.Ib home(Ci.Ia ia) {
        switch (ia) {
            case USA:
                return Cf.Ib.usa;
            case RUSSIA:
                return Cf.Ib.russia;
            case CHINA:
                return Cf.Ib.china;
            case EUROPE:
                return Cf.Ib.europe;
            case AFRICA:
                return Cf.Ib.africa;
            case SOUTH_AMERICA:
                return Cf.Ib.brazil;
            default:
                throw new IllegalArgumentException(String.valueOf(ia));
        }
    }

    /** Is {@code region} (a {@link Cf#e} region name) the home region of {@code ia}? */
    public static boolean isHome(Ci.Ia ia, String region) {
        if (ia == null || region == null) {
            return false;
        }
        if (home(ia).name().equalsIgnoreCase(region)) {
            return true;
        }
        // SOUTH_AMERICA is a whole-continent side: brazil + chile.
        return ia == Ci.Ia.SOUTH_AMERICA && "chile".equalsIgnoreCase(region);
    }

    /** Extra starting regions beyond {@link #home} (SOUTH_AMERICA also owns chile). */
    public static Cf.Ib[] extraHomes(Ci.Ia ia) {
        if (ia == Ci.Ia.SOUTH_AMERICA) {
            return new Cf.Ib[]{Cf.Ib.chile};
        }
        return new Cf.Ib[0];
    }

    /** Is {@code region} the home region of any playable side? */
    public static boolean isAnyHome(Cf.Ib region) {
        for (Ci.Ia ia : Ci.Ia.values()) {
            if (isHome(ia, region.name())) {
                return true;
            }
        }
        return false;
    }

    /** Human-readable name for UI text ("SOUTH AMERICA"). Saves keep using {@code name()}. */
    public static String displayName(Ci.Ia ia) {
        return ia.name().replace('_', ' ');
    }

    /**
     * Arsenal/population profile of a region as owned in the current game.
     *
     * <p>The original superpowers all use (silos 30, submarines 26, population 6). When a new side
     * owns its own home region it gets that same superpower profile, so the arsenal is in line with
     * USA/RUSSIA/CHINA. As an ally of someone else the region keeps its original, smaller profile.
     */
    public static Cf.Ia stats(Cf cf) {
        return stats(cf.e, cf.c);
    }

    /** {@link #stats(Cf)} for a region name and its owner in this game (may be null). */
    public static Cf.Ia stats(String region, Ci.Ia owner) {
        // Only the primary home region gets the superpower (30/26/6) profile. Extra homes
        // (chile for SOUTH_AMERICA) keep their ordinary region stats so the total stays in line.
        if (owner != null && !isOriginal(owner) && home(owner).name().equalsIgnoreCase(region)) {
            return com.invertbit.games.gtw.b.e.Cc.a(Cf.Ib.usa);
        }
        return com.invertbit.games.gtw.b.e.Cc.a(Cf.Ib.valueOf(region));
    }

    // ---------------------------------------------------------------- colours / models

    public static com.invertbit.games.gtw.d.Ca.Ia border(Ci.Ia ia) {
        if (ia == null) {
            return com.invertbit.games.gtw.d.Ca.Ia.defaultBorder;
        }
        switch (ia) {
            case USA:
                return com.invertbit.games.gtw.d.Ca.Ia.usaBorder;
            case RUSSIA:
                return com.invertbit.games.gtw.d.Ca.Ia.russiaBorder;
            case CHINA:
                return com.invertbit.games.gtw.d.Ca.Ia.chinaBorder;
            case EUROPE:
                return com.invertbit.games.gtw.d.Ca.Ia.europeBorder;
            case AFRICA:
                return com.invertbit.games.gtw.d.Ca.Ia.africaBorder;
            case SOUTH_AMERICA:
                return com.invertbit.games.gtw.d.Ca.Ia.southAmericaBorder;
            default:
                return com.invertbit.games.gtw.d.Ca.Ia.defaultBorder;
        }
    }

    public static com.invertbit.games.gtw.d.Ca.Ia mapObject(Ci.Ia ia, boolean inactive) {
        if (ia == null) {
            return com.invertbit.games.gtw.d.Ca.Ia.defaultMapObject;
        }
        switch (ia) {
            case USA:
                return inactive ? com.invertbit.games.gtw.d.Ca.Ia.usaInactiveMapObject : com.invertbit.games.gtw.d.Ca.Ia.usaMapObject;
            case RUSSIA:
                return inactive ? com.invertbit.games.gtw.d.Ca.Ia.russiaInactiveMapObject : com.invertbit.games.gtw.d.Ca.Ia.russiaMapObject;
            case CHINA:
                return inactive ? com.invertbit.games.gtw.d.Ca.Ia.chinaInactiveMapObject : com.invertbit.games.gtw.d.Ca.Ia.chinaMapObject;
            case EUROPE:
                return inactive ? com.invertbit.games.gtw.d.Ca.Ia.europeInactiveMapObject : com.invertbit.games.gtw.d.Ca.Ia.europeMapObject;
            case AFRICA:
                return inactive ? com.invertbit.games.gtw.d.Ca.Ia.africaInactiveMapObject : com.invertbit.games.gtw.d.Ca.Ia.africaMapObject;
            case SOUTH_AMERICA:
                return inactive ? com.invertbit.games.gtw.d.Ca.Ia.southAmericaInactiveMapObject : com.invertbit.games.gtw.d.Ca.Ia.southAmericaMapObject;
            default:
                return com.invertbit.games.gtw.d.Ca.Ia.defaultMapObject;
        }
    }

    /** Base RGB of a new side (USA/RUSSIA/CHINA keep their original hand-tuned colours). */
    public static float[] rgb(Ci.Ia ia) {
        switch (ia) {
            case USA:
                return new float[]{0.0f, 0.52f, 1.0f};
            case RUSSIA:
                return new float[]{1.0f, 0.37f, 0.0f};
            case CHINA:
                return new float[]{0.0f, 1.0f, 0.5f};
            case EUROPE:
                return new float[]{0.65f, 0.45f, 1.0f};   // violet
            case AFRICA:
                return new float[]{1.0f, 0.85f, 0.1f};    // yellow
            case SOUTH_AMERICA:
                return new float[]{1.0f, 0.3f, 0.65f};    // pink
            default:
                return new float[]{0.0f, 0.52f, 1.0f};
        }
    }

    /** Translucent HUD tint (the original used alpha 0.3 for its three sides). */
    public static com.badlogic.gdx.graphics.Cb hudColor(Ci.Ia ia) {
        float[] c = rgb(ia);
        return new com.badlogic.gdx.graphics.Cb(c[0], c[1], c[2], 0.3f);
    }

    /** The game only ships three bomber models; new sides reuse one (it is tinted by side colour). */
    public static com.invertbit.games.gtw.b.a.Ca.Ia bomber(Ci.Ia ia) {
        switch (ia) {
            case RUSSIA:
            case AFRICA:
                return com.invertbit.games.gtw.b.a.Ca.Ia.russiabomber;
            case CHINA:
            case SOUTH_AMERICA:
                return com.invertbit.games.gtw.b.a.Ca.Ia.chinabomber;
            case USA:
            case EUROPE:
            default:
                return com.invertbit.games.gtw.b.a.Ca.Ia.usabomber;
        }
    }

    /**
     * Translation (map x, map z) applied to a side's home outline on the Choose player / opponent
     * screens, laying the six outlines out in two rows of three above the two button rows.
     */
    public static float[] previewOffset(Ci.Ia ia) {
        switch (ia) {
            case USA:
                return new float[]{-180.0f, -170.0f};
            case EUROPE:
                return new float[]{-150.0f, 0.0f};
            case CHINA:
                return new float[]{100.0f, -195.0f};
            case AFRICA:
                return new float[]{-1205.0f, -120.0f};
            case RUSSIA:
                return new float[]{-820.0f, 660.0f};
            case SOUTH_AMERICA:
                return new float[]{1370.0f, -125.0f};
            default:
                return new float[]{0.0f, 0.0f};
        }
    }

    // ---------------------------------------------------------------- real cities

    /**
     * Map projection of world.svg, fitted by least squares to the outline extremes of ten regions
     * (USA, Brazil, Europe, Africa, Australia, Japan, China): x = 1440.5 + 8.069 * lon,
     * z = 963.7 - 10.854 * lat. Typical residual is under 10 map units (about 1 degree); the
     * hand-drawn map is not a precise projection, so city positions are APPROXIMATE.
     */
    public static final float PROJ_X0 = 1440.5f;
    public static final float PROJ_KX = 8.069f;
    public static final float PROJ_Z0 = 963.7f;
    public static final float PROJ_KZ = 10.854f;

    public static float[] project(double lat, double lon) {
        return new float[]{(float) (PROJ_X0 + PROJ_KX * lon), (float) (PROJ_Z0 - PROJ_KZ * lat)};
    }

    /** A real city: true coordinates plus where it is drawn on the game map. */
    public static final class City {
        public final String name;
        public final double lat;
        public final double lon;
        /** Map position: the projection of (lat, lon), nudged inland when it fell on the coastline. */
        public final float x;
        public final float z;

        City(String name, double lat, double lon, float x, float z) {
            this.name = name;
            this.lat = lat;
            this.lon = lon;
            this.x = x;
            this.z = z;
        }
    }

    private static final City[] EUROPE_CITIES = {
            // Cold War European NATO capitals / major cities (Berlin/Warsaw dropped).
            new City("London", 51.507, -0.128, 1439, 405),
            new City("Paris", 48.857, 2.352, 1461, 433),
            new City("Madrid", 40.417, -3.704, 1411, 525),
            new City("Lisbon", 38.722, -9.139, 1367, 544),
            new City("Rome", 41.903, 12.496, 1542, 507),
            new City("Bonn", 50.737, 7.098, 1498, 413),
    };

    private static final City[] AFRICA_CITIES = {
            new City("Kinshasa", -4.441, 15.266, 1564, 1012),
            new City("Lubumbashi", -11.665, 27.479, 1662, 1090),
            new City("Nairobi", -1.286, 36.817, 1738, 978),
            new City("Dar es Salaam", -6.792, 39.208, 1753, 1041),
            new City("Johannesburg", -26.204, 28.047, 1667, 1248),
            new City("Cape Town", -33.925, 18.424, 1599, 1325),
    };

    private static final City[] SOUTH_AMERICA_CITIES = {
            // Whole-continent set (brazil + chile). Map x/z from the global lon/lat fit of
            // world.svg; each point lies inside brazil or the new chile outline.
            new City("Sao Paulo", -23.551, -46.633, 1064, 1219),
            new City("Buenos Aires", -34.603, -58.382, 969, 1339),
            new City("Santiago", -33.449, -70.669, 870, 1327),
            new City("Lima", -12.046, -77.043, 819, 1094),
            new City("Bogota", 4.711, -74.072, 843, 913),
            new City("Caracas", 10.480, -66.903, 901, 850),
    };

    /** Real cities of a side's home region; empty for the original three (they keep random placement). */
    public static List<City> cities(Ci.Ia ia) {
        City[] c;
        switch (ia) {
            case EUROPE:
                c = EUROPE_CITIES;
                break;
            case AFRICA:
                c = AFRICA_CITIES;
                break;
            case SOUTH_AMERICA:
                c = SOUTH_AMERICA_CITIES;
                break;
            default:
                return Collections.emptyList();
        }
        List<City> list = new ArrayList<City>(c.length);
        Collections.addAll(list, c);
        return list;
    }

    /** Real cities to place for {@code cf} in this game: only when it is a new side's own home. */
    public static List<City> homeCities(Cf cf) {
        if (cf.c == null || isOriginal(cf.c) || !isHome(cf.c, cf.e)) {
            return Collections.emptyList();
        }
        // Only the primary home region places the named cities (avoids duplicating them on chile).
        if (!home(cf.c).name().equalsIgnoreCase(cf.e)) {
            return Collections.emptyList();
        }
        return cities(cf.c);
    }
}
