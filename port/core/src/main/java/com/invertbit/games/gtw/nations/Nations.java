package com.invertbit.games.gtw.nations;

/**
 * Side profile used by the port. Colours, homes and extra homes match
 * {@code com.invertbit.games.gtw.nations.Nations} in the decompiled source.
 */
public final class Nations {

    private Nations() {}

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

    /** The playable side that starts owning this region, or {@code null} if none. */
    public static Side ownerOf(String region) {
        for (Side side : Side.values()) {
            if (isHome(side, region)) {
                return side;
            }
        }
        return null;
    }

    public static String displayName(Side side) {
        return side.name().replace('_', ' ');
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
}
