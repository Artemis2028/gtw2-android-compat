package com.invertbit.games.gtw.sim;

import com.badlogic.gdx.math.Vector3;
import com.invertbit.games.gtw.nations.Nations;
import com.invertbit.games.gtw.nations.RegionId;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Places cities, silos, bombers and submarines (original {@code b.e.Ce}).
 */
public final class Deployer {
    public interface Grid {
        List<Vector3> cells(RegionId region);
    }

    private static final Random RANDOM = new Random();

    private Deployer() {}

    public static void deployLand(Player player, Grid grid, boolean isPlayer) {
        List<Region> owned = player.regions();
        int cityCount = player.arsenal.populationCount;
        int cityMt = player.arsenal.city.m5;
        List<Integer> sizes = new ArrayList<Integer>(cityCount);
        int leftover = cityMt;
        for (int i = 0; i < cityCount; i++) {
            sizes.add(5);
            leftover -= 5;
        }
        while (leftover > 0 && cityCount > 0) {
            int idx = RANDOM.nextInt(cityCount);
            sizes.set(idx, sizes.get(idx) + 5);
            leftover -= 5;
        }
        Iterator<Integer> sizeIt = sizes.iterator();
        List<Iterator<Vector3>> leftovers = new ArrayList<Iterator<Vector3>>(owned.size());
        for (Region region : owned) {
            List<Vector3> cells = grid.cells(RegionId.valueOf(region.name));
            Collections.shuffle(cells);
            int placed = placeNamedCities(region, player, sizeIt, cells);
            Iterator<Vector3> cellIt = cells.iterator();
            placeRemainingCities(region, player, sizeIt, cellIt, placed);
            leftovers.add(cellIt);
        }
        placeSilos(player.arsenal.silo, player, leftovers, isPlayer);
        placeBombers(player.arsenal.bomber, player, leftovers);
    }

    private static int placeNamedCities(Region region, Player player, Iterator<Integer> sizes,
                                        List<Vector3> grid) {
        int want = Nations.stats(region).cities;
        int placed = 0;
        for (Nations.City city : Nations.homeCities(region)) {
            if (placed >= want || !sizes.hasNext()) {
                break;
            }
            City unit = new City(new Vector3(city.x, 0.0f, city.z), sizes.next().intValue());
            unit.owner = player.side;
            player.add(unit);
            placed++;
            Iterator<Vector3> g = grid.iterator();
            while (g.hasNext()) {
                Vector3 p = g.next();
                float dx = p.x - city.x;
                float dz = p.z - city.z;
                if ((dx * dx) + (dz * dz) < 1600.0f) {
                    g.remove();
                }
            }
        }
        return placed;
    }

    private static void placeRemainingCities(Region region, Player player, Iterator<Integer> sizes,
                                             Iterator<Vector3> cells, int alreadyPlaced) {
        int want = Nations.stats(region).cities - alreadyPlaced;
        for (int i = 0; i < want; i++) {
            if (cells.hasNext() && sizes.hasNext()) {
                City city = new City(cells.next(), sizes.next().intValue());
                city.owner = player.side;
                player.add(city);
                cells.remove();
            }
        }
    }

    private static void placeSilos(YieldRow row, Player player, List<Iterator<Vector3>> cells,
                                   boolean isPlayer) {
        placeLand(5, row.m5 / 5, player, cells, true, isPlayer);
        placeLand(10, row.m10 / 10, player, cells, true, isPlayer);
        placeLand(15, row.m15 / 15, player, cells, true, isPlayer);
    }

    private static void placeBombers(YieldRow row, Player player, List<Iterator<Vector3>> cells) {
        placeLand(5, row.m5 / 5, player, cells, false, false);
        placeLand(10, row.m10 / 10, player, cells, false, false);
        placeLand(15, row.m15 / 15, player, cells, false, false);
    }

    private static void placeLand(int yield, int rounds, Player player, List<Iterator<Vector3>> cells,
                                  boolean silo, boolean isPlayer) {
        for (int i = 0; i < rounds; i++) {
            for (Iterator<Vector3> it : cells) {
                if (it.hasNext()) {
                    Launcher launcher = silo
                            ? Launcher.silo(it.next(), yield, isPlayer)
                            : Launcher.bomber(it.next(), yield);
                    launcher.owner = player.side;
                    player.add(launcher);
                }
            }
        }
    }

    public static void placeSubs(int yield, int count, Player player, Iterator<Vector3> sea,
                                 boolean isPlayer) {
        for (int i = 0; i < count; i++) {
            if (sea.hasNext()) {
                Launcher sub = Launcher.submarine(sea.next(), yield, isPlayer);
                sub.owner = player.side;
                player.add(sub);
            }
        }
    }
}
