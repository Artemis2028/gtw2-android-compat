package com.invertbit.games.gtw.sim;

import com.invertbit.games.gtw.nations.Nations;
import com.invertbit.games.gtw.nations.RegionId;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * AI / auto alliance picks and auto arsenal (original {@code b.e.Ca}).
 * Allies while {@code ownedRegions <= defcon} (equivalent to
 * {@code ownedRegions - 1 < defcon}).
 */
public final class AlliancePicker {
    public static AlliancePicker instance;
    private final AllianceTables tables = AllianceTables.get();
    private final List<Integer[]> splits = new ArrayList<Integer[]>();

    public AlliancePicker() {
        splits.add(new Integer[]{2, 4, 4});
    }

    public static AlliancePicker get() {
        if (instance == null) {
            instance = new AlliancePicker();
        }
        return instance;
    }

    public static void reset() {
        instance = null;
    }

    public void assignAllies(int defcon, Player player) {
        int owned = player.regionCount();
        if (owned <= defcon) {
            List<RegionId> pool = tables.offer(player.side, defcon);
            Collections.shuffle(pool);
            Iterator<RegionId> it = pool.iterator();
            while (true) {
                int current = owned;
                if (it.hasNext()) {
                    Region region = WorldRegions.get().get(it.next().name());
                    if ((!player.owns(region)) && current <= defcon && region != null && region.owner == null) {
                        player.add(region);
                        current++;
                    }
                    owned = current;
                } else {
                    return;
                }
            }
        }
    }


    /**
     * Regions that get a pick marker on the alliance map (original {@code c.Cp}):
     * shown when {@code r < defcon} and the region is not already owned by either
     * side and is not an opponent home. {@code r = player.countries - 1}.
     */
    public List<Region> eligible(Match match) {
        List<Region> out = new ArrayList<Region>();
        int r = match.alliesPicked();
        if (r >= match.defcon) {
            return out;
        }
        for (RegionId id : tables.offer(match.player().side, match.defcon)) {
            Region region = WorldRegions.get().get(id.name());
            if (region != null && canShow(match, region, r)) {
                out.add(region);
            }
        }
        return out;
    }

    private static boolean canShow(Match match, Region region, int r) {
        if (r >= match.defcon) {
            return false;
        }
        if (match.player().owns(region) || match.opponent().owns(region)) {
            return false;
        }
        return !Nations.isHome(match.opponent().side, region.name);
    }

    /**
     * Manual pick (original {@code c.Cr}): reject if {@code r > defcon} or the
     * player already owns the region; otherwise add it (which increments {@code r}).
     */
    public boolean tryPick(Match match, Region region) {
        if (region == null) {
            return false;
        }
        int r = match.alliesPicked();
        if (r > match.defcon || match.player().owns(region)) {
            return false;
        }
        match.player().add(region);
        return true;
    }

    private void fillRow(YieldRow row, int mt) {
        if (WeaponKind.CITY.equals(row.kind) || WeaponKind.BOMBER.equals(row.kind)
                || WeaponKind.SUBMARINE.equals(row.kind)) {
            row.add5(mt);
            return;
        }
        Collections.shuffle(splits);
        Integer[] ratio = splits.get(0);
        int m15 = (ratio[2].intValue() * mt) / 10;
        int m5 = (ratio[0].intValue() * mt) / 10;
        int m10 = (ratio[1].intValue() * mt) / 10;
        int rem = mt - ((m15 + m5) + m10);
        if (rem == 5) {
            m10 = rem + m10;
        } else if (rem == 10) {
            m5 = rem + m5;
        } else {
            m15 = rem + m15;
        }
        row.add5(m5);
        row.add10(m10);
        row.add15(m15);
    }

    public void autoDeploy(Player player) {
        Arsenal arsenal = player.arsenal;
        arsenal.autoDeployed = true;
        int leftover = arsenal.maxPoints - (arsenal.populationCount * 5);
        Collections.shuffle(splits);
        Integer[] ratio = splits.get(0);
        int cities = ((ratio[0].intValue() * leftover) / 10) + (arsenal.populationCount * 5);
        int mid = Math.min((ratio[1].intValue() * leftover) / 10, (arsenal.siloCount * 5) - cities);
        int bombers = (mid * 1) / 10;
        int silos = mid - bombers;
        int subsShare = (ratio[2].intValue() * leftover) / 10;
        fillRow(arsenal.city, cities);
        fillRow(arsenal.silo, silos);
        fillRow(arsenal.bomber, bombers);
        fillRow(arsenal.submarine, subsShare + (leftover - (((cities + silos) + bombers) + subsShare)));
    }
}
