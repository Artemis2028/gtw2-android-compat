package com.invertbit.games.gtw.sim;

/**
 * Original impact flash ({@code gtw.b.Cg} + renderer {@code b.d.Ci}).
 *
 * <p>Evidence from the decompiled game: each resolved strike spawns one of these
 * at the target. Age starts at {@link #INITIAL_AGE} of {@link #DURATION}
 * seconds and the XZ scale is {@code MAX_SCALE * (age / duration)}, so the
 * disc is already partly grown and expands linearly until it is removed.
 * The mesh is the default no-arg {@code MeshBuilder} sphere, a flat
 * 32 x 0.1 x 32 ellipsoid (radius {@link #BASE_RADIUS} in XZ); the
 * {@code explosionObject} material is {@code Color.WHITE} with the
 * {@code rocketobject} shader, which writes alpha 0.5. There is no separate
 * alpha tween — the flash disappears when age reaches duration.
 */
public final class Explosion {
    public static final float DURATION = 5.0f;
    public static final float INITIAL_AGE = 2.0f;
    public static final float MAX_SCALE = 3.0f;
    public static final float BASE_RADIUS = 16.0f;
    public static final float ALPHA = 0.5f;
    public static final float WORLD_WIDTH = 2880.0f;

    public final float x;
    public final float z;
    public float age = INITIAL_AGE;

    public Explosion(float x, float z) {
        this.x = x;
        this.z = z;
    }

    /** @return true when the flash has finished and should be removed */
    public boolean advance(float seconds) {
        age += seconds;
        if (age > DURATION) {
            age = DURATION;
        }
        return age >= DURATION;
    }

    public float scale() {
        return MAX_SCALE * (age / DURATION);
    }

    public float radius() {
        return BASE_RADIUS * scale();
    }

    public float alpha() {
        return ALPHA;
    }
}
