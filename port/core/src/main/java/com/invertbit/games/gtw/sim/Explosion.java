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
 *
 * <p>Brightness ({@link #shade}): the shader's last live assignment is
 * {@code c = v_color.rgb * abs(1.0 / 0.9*sin(p.y))} with {@code p = uv * 2 - 1}.
 * GLSL reads that as {@code (1.0 / 0.9) * sin(p.y)}; the {@code 1/sin} glow line
 * above it is overwritten. Seen from above, a sphere point at normalised radius
 * {@code r} has {@code v = asin(r) / PI} (top half; the bottom half mirrors it),
 * so the disc is {@code 0.935} white at the centre falling to black at the rim.
 * The alpha 0.5 never blends: the original 1.7.0 {@code ModelBatch.begin} owns its
 * render context and calls {@code glDisable(GL_BLEND)}, and the custom shader
 * never turns blending back on, so the disc is drawn opaque.
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

    /** Shader output alpha; unused for compositing because the original draws with blending off. */
    public float alpha() {
        return ALPHA;
    }

    /** {@code rocketobject} brightness at normalised radius {@code r} (0 centre, 1 rim) of the disc. */
    public static float shade(float r) {
        if (r >= 1f) {
            return 0f;
        }
        float v = (float) (Math.asin(Math.max(0f, r)) / Math.PI);
        float py = v * 2f - 1f;
        return Math.min(1f, Math.abs((1.0f / 0.9f) * (float) Math.sin(py)));
    }
}
