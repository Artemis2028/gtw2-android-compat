package com.invertbit.games.gtw.sim;

import com.badlogic.gdx.math.Vector3;
import com.invertbit.games.gtw.AiOff;
import com.invertbit.games.gtw.nations.RegionId;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Deploys a new round when the player presses Start (original {@code b.e.RoundSetup}):
 * AI alliances and arsenal, then every side's cities, silos, bombers and submarines.
 */
public final class RoundSetup {
    private RoundSetup() {}

    public static void deploy(Match match, Deployer.Grid grid) {
        match.messages.clear();
        AlliancePicker picker = AlliancePicker.get();
        if (!AiOff.isOn()) {
            picker.assignAllies(match.defcon, match.opponent());
        }
        picker.autoDeploy(match.opponent());
        if (match.player().arsenal.empty()) {
            picker.autoDeploy(match.player());
            match.messages.add("Your arsenal has been automatically deployed.");
        }
        Region sea = WorldRegions.get().get("submarine");
        if (sea == null || sea.grid == null) {
            throw new IllegalStateException(
                    "world.svg did not load (no submarine grid); see the SVG parse error earlier in the log");
        }
        List<Vector3> seaCells = new ArrayList<Vector3>(sea.grid);
        Collections.shuffle(seaCells);
        Iterator<Vector3> seaIt = seaCells.iterator();
        for (Player player : match.sides) {
            boolean isPlayer = match.player().equals(player);
            Deployer.deployLand(player, grid, isPlayer);
            YieldRow subs = player.arsenal.submarine;
            Deployer.placeSubs(5, subs.m5 / 5, player, seaIt, isPlayer);
            Deployer.placeSubs(10, subs.m10 / 10, player, seaIt, isPlayer);
            Deployer.placeSubs(15, subs.m15 / 15, player, seaIt, isPlayer);
        }
        match.prepareAiTargets();
    }

    public static Deployer.Grid gridFromWorld() {
        return new Deployer.Grid() {
            @Override
            public List<Vector3> cells(RegionId region) {
                Region r = WorldRegions.get().get(region.name());
                if (r == null || r.grid == null || r.grid.isEmpty()) {
                    throw new IllegalArgumentException(region.name());
                }
                return new ArrayList<Vector3>(r.grid);
            }
        };
    }
}
