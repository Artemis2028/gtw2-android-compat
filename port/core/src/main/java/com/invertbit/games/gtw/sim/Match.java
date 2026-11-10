package com.invertbit.games.gtw.sim;

import com.invertbit.games.gtw.nations.Nations;
import com.invertbit.games.gtw.nations.RegionId;
import com.invertbit.games.gtw.nations.Side;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/** The two-sided match (original {@code a.Cg}). */
public final class Match {
    public final List<Player> sides = new ArrayList<Player>();
    public int defcon;
    public final List<String> messages = new ArrayList<String>();

    public Player player() {
        return sides.get(0);
    }

    public Player opponent() {
        return sides.get(1);
    }

    public static Match create(Side playerSide, Side opponentSide, int defcon) {
        Match match = new Match();
        match.defcon = defcon;
        match.addSide(playerSide, true);
        match.addSide(opponentSide, false);
        return match;
    }

    public Player addSide(Side side, boolean human) {
        RegionId home = Nations.home(side);
        if (human) {
            releaseMap();
        }
        Player player = new Player(side, human);
        Region homeRegion = new Region(home.name());
        homeRegion.owner = side;
        player.add(homeRegion);
        claimOnMap(side, home);
        for (RegionId extra : Nations.extraHomes(side)) {
            Region more = new Region(extra.name());
            more.owner = side;
            player.add(more);
            claimOnMap(side, extra);
        }
        sides.add(player);
        return player;
    }

    private static void releaseMap() {
        Map<String, Region> map = WorldRegions.get().all();
        if (map != null) {
            for (Region region : map.values()) {
                region.owner = null;
            }
        }
    }

    private static void claimOnMap(Side side, RegionId id) {
        Map<String, Region> map = WorldRegions.get().all();
        Region onMap = map == null ? null : map.get(id.name());
        if (onMap != null) {
            onMap.owner = side;
        }
    }

    /** Original alliance UI predicate: {@code player.countries - 1 < defcon}. */
    public boolean alliancesOpen() {
        return player().regionCount() - 1 < defcon;
    }

    /**
     * Build the AI target lists (original {@code a.Cg.d()}). Called at the end
     * of {@link RoundSetup#deploy}.
     */
    public void prepareAiTargets() {
        for (Player ai : sides) {
            if (ai.human) {
                continue;
            }
            for (Player other : sides) {
                if (ai == other) {
                    continue;
                }
                for (MapUnit unit : ai.units) {
                    if (!(unit instanceof Launcher)) {
                        continue;
                    }
                    Launcher launcher = (Launcher) unit;
                    for (MapUnit target : other.units) {
                        if (target instanceof Region) {
                            continue;
                        }
                        ai.aiTargets.add(new AttackPlan(launcher, target));
                    }
                }
                Collections.sort(ai.aiTargets);
                switch (Difficulty.current()) {
                    case EASY:
                        Collections.shuffle(ai.aiTargets);
                        break;
                    case NORMAL:
                        jitter(ai.aiTargets, 0.35f);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    static void jitter(List<AttackPlan> list, float jitter) {
        final IdentityHashMap<AttackPlan, Float> key = new IdentityHashMap<AttackPlan, Float>();
        Random random = new Random();
        for (AttackPlan plan : list) {
            key.put(plan, plan.distance * (1.0f - jitter + (2.0f * jitter * random.nextFloat())));
        }
        Collections.sort(list, new Comparator<AttackPlan>() {
            @Override
            public int compare(AttackPlan a, AttackPlan b) {
                return Float.compare(key.get(a), key.get(b));
            }
        });
    }
}
