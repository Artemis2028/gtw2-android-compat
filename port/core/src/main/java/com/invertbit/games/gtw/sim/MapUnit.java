package com.invertbit.games.gtw.sim;

import com.badlogic.gdx.math.Vector3;
import com.invertbit.games.gtw.nations.Side;

/** Anything that lives on the map (original {@code b.Ca}). */
public abstract class MapUnit {
    public final Vector3 position;
    public Side owner;
    public boolean destroyed;

    protected MapUnit(Vector3 position) {
        this.position = position;
    }

    public float distanceTo(Vector3 other) {
        if (position == null || other == null) {
            return Float.MAX_VALUE;
        }
        return position.dst(other);
    }
}
