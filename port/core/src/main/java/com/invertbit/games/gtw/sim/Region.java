package com.invertbit.games.gtw.sim;

import com.badlogic.gdx.math.Vector3;
import com.invertbit.games.gtw.map.SvgRect;

import java.util.ArrayList;
import java.util.List;

/** A named country / sea grid (original {@code b.Cf}). Equality is by name. */
public final class Region extends MapUnit implements Comparable<Region> {
    public final String name;
    public SvgRect label;
    public List<Vector3> grid = new ArrayList<Vector3>();

    public Region(String name) {
        super(null);
        this.name = name;
    }

    @Override
    public float distanceTo(Vector3 other) {
        return Float.MAX_VALUE;
    }

    @Override
    public int compareTo(Region other) {
        return equals(other) ? 1 : -1;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 1;
    }

    @Override
    public boolean equals(Object obj) {
        if (name != null && obj instanceof Region) {
            return name.equals(((Region) obj).name);
        }
        return false;
    }
}
