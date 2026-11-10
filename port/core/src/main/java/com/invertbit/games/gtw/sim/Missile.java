package com.invertbit.games.gtw.sim;

import com.badlogic.gdx.math.Vector3;

/**
 * In-flight warhead. Duration is {@code distance * launcher.speedFactor / 100}
 * seconds, as in {@code b.d.Cm} ({@code ck.b(end) * cb.g / 100}).
 */
public final class Missile {
    public final Launcher source;
    public final MapUnit target;
    public final Vector3 start = new Vector3();
    public final Vector3 end = new Vector3();
    public final Vector3 position = new Vector3();
    public final float duration;
    public float elapsed;
    public boolean arrived;

    public Missile(Launcher source, MapUnit target) {
        this.source = source;
        this.target = target;
        this.start.set(source.position);
        this.end.set(target.position);
        float dx = end.x - start.x;
        if (Math.abs(dx) > 1440f) {
            if (start.x < end.x) {
                start.x += 2880f;
            } else {
                start.x -= 2880f;
            }
        }
        float dist = (float) Math.hypot(end.x - start.x, end.z - start.z);
        this.duration = Math.max(0.05f, (dist * source.speedFactor) / 100.0f);
        this.position.set(start);
    }

    public boolean advance(float seconds) {
        if (arrived) {
            return true;
        }
        elapsed += seconds;
        float t = duration <= 0 ? 1f : Math.min(1f, elapsed / duration);
        // Visual stand-in for the original cubic Bézier: a raised lerp. The
        // arrival time itself follows the original duration formula above.
        float arc = 4f * t * (1f - t) * Math.min(400f, duration * 8f);
        position.x = start.x + (end.x - start.x) * t;
        position.y = arc;
        position.z = start.z + (end.z - start.z) * t;
        if (t >= 1f) {
            arrived = true;
            position.set(end);
        }
        return arrived;
    }
}
