package com.invertbit.games.gtw.map;

import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.List;

/** A {@code <path>} from world.svg. {@link #name} is {@code onclick}, overwritten by {@code <title>}. */
public final class SvgPath {
    public String name;
    private List<Vector3> points;

    public List<Vector3> points() {
        if (points == null) {
            points = new ArrayList<Vector3>();
        }
        return points;
    }
}
