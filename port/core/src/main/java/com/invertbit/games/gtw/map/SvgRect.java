package com.invertbit.games.gtw.map;

import com.badlogic.gdx.math.Vector3;

/** A {@code <rect>} from world.svg: label (with onmouseover) or a unit-grid cell. */
public final class SvgRect {
    public final Vector3 origin;
    public final float width;
    public final float height;

    public SvgRect(Vector3 origin, float width, float height) {
        this.origin = origin;
        this.width = width;
        this.height = height;
    }
}
