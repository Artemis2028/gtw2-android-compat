package com.invertbit.games.gtw.sim;

import com.badlogic.gdx.math.Vector3;

/**
 * In-flight warhead. Duration is {@code distance * launcher.speedFactor / 100}
 * seconds, as in {@code b.d.Cm}. The path is the original cubic Bézier from
 * {@code com.invertbit.games.b.Ca}: P0 = start, P3 = wrap-adjusted end, and
 * coincident control points P1 = P2 = (midX, end.z - |dx|/2) (clamped to z=10
 * when that would go negative).
 */
public final class Missile {
    public final Launcher source;
    public final MapUnit target;
    public final Vector3 start = new Vector3();
    public final Vector3 end = new Vector3();
    public final Vector3 control = new Vector3();
    public final Vector3 position = new Vector3();
    public final float duration;
    public float elapsed;
    public boolean arrived;

    public Missile(Launcher source, MapUnit target) {
        this.source = source;
        this.target = target;
        this.start.set(source.position);
        this.end.set(target.position);
        wrapShortest(start, end);
        float dx = end.x - start.x;
        float ctrlZ = end.z - Math.abs(dx) / 2.0f;
        if (ctrlZ < 0.0f) {
            ctrlZ = 10.0f;
        }
        control.set(start.x + dx / 2.0f, 0.0f, ctrlZ);
        float dist = (float) Math.hypot(dx, end.z - start.z);
        this.duration = Math.max(0.05f, (dist * source.speedFactor) / 100.0f);
        bezier(0f, position);
    }

    /** Shift {@code from.x} by ±2880 so the path takes the short way around the map. */
    public static void wrapShortest(Vector3 from, Vector3 to) {
        float dx = to.x - from.x;
        if (dx < 0.0f && Math.abs(dx) > 1440.0f) {
            to.x += 2880.0f;
        } else if (dx > 1440.0f) {
            to.x -= 2880.0f;
        }
    }

    public static void bezier(Vector3 p0, Vector3 p1, Vector3 p3, float t, Vector3 out) {
        float u = 1.0f - t;
        float uu = u * u;
        float uuu = uu * u;
        float tt = t * t;
        float ttt = tt * t;
        // P1 == P2 (original coincident controls): (1-t)^3 P0 + 3(1-t)^2 t P1 + 3(1-t)t^2 P1 + t^3 P3
        out.x = uuu * p0.x + 3.0f * uu * t * p1.x + 3.0f * u * tt * p1.x + ttt * p3.x;
        out.y = 0.0f;
        out.z = uuu * p0.z + 3.0f * uu * t * p1.z + 3.0f * u * tt * p1.z + ttt * p3.z;
    }

    public void bezier(float t, Vector3 out) {
        bezier(start, control, end, t, out);
    }

    public static float wrapX(float x) {
        while (x > 2880.0f) {
            x -= 2880.0f;
        }
        while (x < 0.0f) {
            x += 2880.0f;
        }
        return x;
    }

    public boolean advance(float seconds) {
        if (arrived) {
            return true;
        }
        elapsed += seconds;
        float t = duration <= 0 ? 1f : Math.min(1f, elapsed / duration);
        bezier(t, position);
        if (t >= 1f) {
            arrived = true;
            position.set(end);
        }
        return arrived;
    }
}
