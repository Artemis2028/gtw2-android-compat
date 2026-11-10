package com.invertbit.games.gtw.sim;

import com.badlogic.gdx.math.Vector3;

/** A city (original {@code b.Cd}). {@link #population} is the remaining "mln" / MT-sized pool. */
public final class City extends MapUnit {
    public int population;
    public int lastHit;

    public City(Vector3 position, int population) {
        super(position);
        this.population = population;
    }

    public void hit(int yield) {
        lastHit = Math.min(yield, population);
        population -= yield;
        if (population < 0) {
            population = 0;
        }
        destroyed = population <= 0;
    }
}
