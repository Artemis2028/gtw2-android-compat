package com.invertbit.games.gtw.sim;

/**
 * A launcher-to-target pairing (original {@code a.Cd}). Distance wraps at 1440
 * map units across the 2880-wide world.
 */
public final class AttackPlan implements Comparable<AttackPlan> {
    public final MapUnit target;
    public final Launcher source;
    public final float distance;

    public AttackPlan(Launcher source, MapUnit target) {
        this.source = source;
        this.target = target;
        if (source.owner == null) {
            throw new IllegalStateException("playerType is null " + source);
        }
        float d = source.position.dst(target.position);
        if (d > 1440.0f) {
            com.badlogic.gdx.math.Vector3 wrapped = new com.badlogic.gdx.math.Vector3(source.position);
            if (source.position.x < target.position.x) {
                wrapped.x += 2880.0f;
            } else {
                wrapped.x -= 2880.0f;
            }
            d = wrapped.dst(target.position);
        }
        this.distance = d;
    }

    @Override
    public int compareTo(AttackPlan other) {
        return Float.compare(distance, other.distance);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        AttackPlan other = (AttackPlan) obj;
        return target.equals(other.target) && source.equals(other.source);
    }

    @Override
    public int hashCode() {
        return (target.hashCode() * 31) + source.hashCode();
    }
}
