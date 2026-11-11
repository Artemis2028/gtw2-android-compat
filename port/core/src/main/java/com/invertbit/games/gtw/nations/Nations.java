package com.invertbit.games.gtw.nations;

import com.invertbit.games.gtw.sim.Region;
import com.invertbit.games.gtw.sim.RegionStats;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Side profile used by the port. Colours, homes, extras, stats and named cities
 * match {@code com.invertbit.games.gtw.nations.Nations} in the decompiled source.
 */
public final class Nations {

    public static final Side[] PICK_ORDER = {
            Side.USA, Side.EUROPE, Side.CHINA,
            Side.AFRICA, Side.RUSSIA, Side.SOUTH_AMERICA
    };

    public static final float PROJ_X0 = 1440.5f;
    public static final float PROJ_KX = 8.069f;
    public static final float PROJ_Z0 = 963.7f;
    public static final float PROJ_KZ = 10.854f;

    private Nations() {}

    public static boolean isOriginal(Side side) {
        return side == Side.USA || side == Side.RUSSIA || side == Side.CHINA;
    }

    public static RegionId home(Side side) {
        switch (side) {
            case USA: return RegionId.usa;
            case RUSSIA: return RegionId.russia;
            case CHINA: return RegionId.china;
            case EUROPE: return RegionId.europe;
            case AFRICA: return RegionId.africa;
            case SOUTH_AMERICA: return RegionId.brazil;
            default: throw new IllegalArgumentException(String.valueOf(side));
        }
    }

    /**
     * Extra starting regions beyond {@link #home}: SOUTH_AMERICA also owns chile
     * (whole continent), USA also owns alaska.
     */
    public static RegionId[] extraHomes(Side side) {
        if (side == Side.SOUTH_AMERICA) {
            return new RegionId[]{RegionId.chile};
        }
        if (side == Side.USA) {
            return new RegionId[]{RegionId.alaska};
        }
        return new RegionId[0];
    }

    public static boolean isHome(Side side, String region) {
        if (side == null || region == null) {
            return false;
        }
        if (home(side).name().equalsIgnoreCase(region)) {
            return true;
        }
        for (RegionId extra : extraHomes(side)) {
            if (extra.name().equalsIgnoreCase(region)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isAnyHome(RegionId region) {
        for (Side side : Side.values()) {
            if (isHome(side, region.name())) {
                return true;
            }
        }
        return false;
    }

    /** The playable side that starts owning this region, or {@code null} if none. */
    public static Side ownerOf(String region) {
        for (Side side : Side.values()) {
            if (isHome(side, region)) {
                return side;
            }
        }
        return null;
    }

    /** The game only ships three bomber models; new sides reuse one (tinted by side colour). */
    public static String bomberGlyph(Side side) {
        switch (side) {
            case RUSSIA:
            case AFRICA:
                return "russiabomber";
            case CHINA:
            case SOUTH_AMERICA:
                return "chinabomber";
            default:
                return "usabomber";
        }
    }

    /**
     * Translation (map x, map z) from the original {@code Nations.previewOffset}.
     * The port's choose-player / choose-opponent screens no longer apply this:
     * they scale and center each side's home outlines in a 2x3 cell grid
     * ({@code NationPreviewLayout}) so every silhouette fits above the buttons.
     */
    public static float[] previewOffset(Side side) {
        switch (side) {
            case USA: return new float[]{-180.0f, -170.0f};
            case EUROPE: return new float[]{-150.0f, 0.0f};
            case CHINA: return new float[]{100.0f, -195.0f};
            case AFRICA: return new float[]{-1205.0f, -120.0f};
            case RUSSIA: return new float[]{-820.0f, 660.0f};
            case SOUTH_AMERICA: return new float[]{1370.0f, -125.0f};
            default: return new float[]{0.0f, 0.0f};
        }
    }

    public static String displayName(Side side) {
        return side.name().replace('_', ' ');
    }

    /** User-visible region label. Internal ids stay lowercase enum names. */
    public static String displayName(RegionId id) {
        switch (id) {
            case usa: return "USA";
            case easteurope: return "East Europe";
            case egypt: return "Egypt";
            case middleeast: return "Middle East";
            case pacific: return "Pacific";
            default:
                String n = id.name();
                return Character.toUpperCase(n.charAt(0)) + n.substring(1);
        }
    }

    public static String displayName(String region) {
        if (region == null) {
            return null;
        }
        try {
            return displayName(RegionId.valueOf(region));
        } catch (IllegalArgumentException e) {
            return region;
        }
    }

    /** Base RGB of a side (USA/RUSSIA/CHINA keep their original hand-tuned colours). */
    public static float[] rgb(Side side) {
        switch (side) {
            case USA:
                return new float[]{0.0f, 0.52f, 1.0f};
            case RUSSIA:
                return new float[]{1.0f, 0.37f, 0.0f};
            case CHINA:
                return new float[]{0.0f, 1.0f, 0.5f};
            case EUROPE:
                return new float[]{0.65f, 0.45f, 1.0f};
            case AFRICA:
                return new float[]{1.0f, 0.85f, 0.1f};
            case SOUTH_AMERICA:
                return new float[]{1.0f, 0.3f, 0.65f};
            default:
                return new float[]{0.0f, 0.52f, 1.0f};
        }
    }

    /** Original region stats (silos, submarines, cities). */
    public static RegionStats stats(RegionId id) {
        switch (id) {
            case usa: return new RegionStats(30, 26, 6);
            case russia: return new RegionStats(30, 26, 6);
            case china: return new RegionStats(30, 26, 6);
            case alaska: return new RegionStats(8, 11, 1);
            case canada: return new RegionStats(30, 37, 5);
            case mexico: return new RegionStats(15, 14, 4);
            case greenland: return new RegionStats(0, 27, 0);
            case europe: return new RegionStats(30, 25, 5);
            case easteurope: return new RegionStats(25, 22, 5);
            case brazil: return new RegionStats(20, 24, 5);
            case chile: return new RegionStats(15, 20, 3);
            case sahara: return new RegionStats(20, 24, 3);
            case egypt: return new RegionStats(20, 24, 4);
            case africa: return new RegionStats(30, 40, 3);
            case middleeast: return new RegionStats(10, 13, 3);
            case india: return new RegionStats(30, 29, 5);
            case indonesia: return new RegionStats(15, 14, 3);
            case australia: return new RegionStats(15, 24, 3);
            case cuba: return new RegionStats(2, 1, 1);
            case pacific: return new RegionStats(15, 14, 3);
            default:
                throw new IllegalArgumentException("no countrytype defined");
        }
    }

    /**
     * Arsenal/population profile of a region as owned in the current game.
     * New sides get the USA superpower (30/26/6) profile on their primary home;
     * extra homes and allied regions keep the original table.
     */
    public static RegionStats stats(String region, Side owner) {
        if (owner != null && !isOriginal(owner) && home(owner).name().equalsIgnoreCase(region)) {
            return stats(RegionId.usa);
        }
        return stats(RegionId.valueOf(region));
    }

    public static RegionStats stats(Region region) {
        return stats(region.name, region.owner);
    }

    public static float[] project(double lat, double lon) {
        return new float[]{(float) (PROJ_X0 + PROJ_KX * lon), (float) (PROJ_Z0 - PROJ_KZ * lat)};
    }

    public static final class City {
        public final String name;
        public final double lat;
        public final double lon;
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

    /*
     * Every slot sits on SOUTH_AMERICA's own land (brazil or chile) and clear of
     * mexico's grid. Bogota and Caracas used to fill the last two slots, but they
     * lie in mexico's northern-SA lobe, so an ally holding mexico could place units
     * on them and they were drawn on another side's land. Manaus and Brasilia keep
     * the six-city count and the northern/central spread.
     */
    private static final City[] SOUTH_AMERICA_CITIES = {
            new City("Sao Paulo", -23.551, -46.633, 1064, 1219),
            new City("Buenos Aires", -34.603, -58.382, 969, 1339),
            new City("Santiago", -33.449, -70.669, 870, 1327),
            new City("Lima", -12.046, -77.043, 819, 1094),
            new City("Manaus", -3.119, -60.022, 956, 998),
            new City("Brasilia", -15.794, -47.882, 1054, 1135),
    };

    public static List<City> cities(Side side) {
        City[] c;
        switch (side) {
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

    /** Real cities to place for {@code region} in this game: only when it is a new side's own primary home. */
    public static List<City> homeCities(Region region) {
        if (region.owner == null || isOriginal(region.owner) || !isHome(region.owner, region.name)) {
            return Collections.emptyList();
        }
        if (!home(region.owner).name().equalsIgnoreCase(region.name)) {
            return Collections.emptyList();
        }
        return cities(region.owner);
    }
}
