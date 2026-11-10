package com.invertbit.games.gtw.sim;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Running battle: missiles, DEFCON countdown, player launches and the AI
 * (original {@code Caj} + {@code b.d.Cm} + {@code b.d.Ca}).
 */
public final class Battle implements BattlePause.Sim {
    public final Match match;
    public final DefconClock clock = new DefconClock();
    public final List<Missile> missiles = new ArrayList<Missile>();
    public Launcher selected;
    public boolean finished;
    /** Missiles that reached their target; the screen plays the impact sound when it grows. */
    public int impacts;
    private int gameOverCalls;

    public Battle(Match match) {
        this.match = match;
        clock.countdown = 5.0f;
        if (match.messages != null) {
            for (String message : match.messages) {
                clock.addMessage(message);
            }
        }
    }

    @Override
    public boolean over() {
        for (Player player : match.sides) {
            if (player.defeated()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void gameOver() {
        finished = true;
        gameOverCalls++;
    }

    public int gameOverCalls() {
        return gameOverCalls;
    }

    @Override
    public void advance(long ms) {
        DefconClock.clock(clock, ms);
        float seconds = ms / 1000.0f;
        Iterator<Missile> it = missiles.iterator();
        while (it.hasNext()) {
            Missile missile = it.next();
            if (missile.advance(seconds)) {
                resolve(missile);
                it.remove();
            }
        }
        recountInFlight();
    }

    @Override
    public boolean countdownRunning() {
        return clock.countdownRunning();
    }

    @Override
    public void ai() {
        if (com.invertbit.games.gtw.AiOff.isOn()) {
            return;
        }
        recountInFlight();
        for (Player player : match.sides) {
            if (player.human || player.budgetExhausted()) {
                continue;
            }
            AttackPlan next = nextPlan(player);
            if (next != null) {
                launch(next.source, next.target);
            }
        }
    }

    private AttackPlan nextPlan(Player player) {
        for (AttackPlan plan : player.aiTargets) {
            if (plan.target == null || plan.target.destroyed || plan.source.destroyed) {
                continue;
            }
            if (alreadyAimed(player, plan)) {
                continue;
            }
            if (!plan.source.canFireAt(plan.target)) {
                continue;
            }
            return plan;
        }
        return null;
    }

    private boolean alreadyAimed(Player player, AttackPlan plan) {
        for (AttackPlan flying : player.inFlightPlans) {
            if (flying.target == plan.target || flying.source == plan.source) {
                return true;
            }
        }
        return false;
    }

    /**
     * Recount in-flight launches so {@link Player#launchesUsed} is "missiles in
     * the air", matching the original AI tick that resets {@code f} then counts
     * live rockets.
     */
    private void recountInFlight() {
        for (Player player : match.sides) {
            player.launchesUsed = 0;
            player.inFlightPlans.clear();
        }
        for (Missile missile : missiles) {
            Player owner = ownerOf(missile.source.owner);
            if (owner != null) {
                owner.launchesUsed++;
                owner.inFlightPlans.add(new AttackPlan(missile.source, missile.target));
            }
        }
    }

    public Player ownerOf(com.invertbit.games.gtw.nations.Side side) {
        for (Player player : match.sides) {
            if (player.side == side) {
                return player;
            }
        }
        return null;
    }

    public String tryLaunch(Launcher source, MapUnit target) {
        if (clock.countdownRunning()) {
            return String.format("%.0f sec to start", Math.max(0f, clock.countdown));
        }
        Player shooter = ownerOf(source.owner);
        if (shooter != null && shooter.budgetExhausted()) {
            return "Too many ICBMs in the air (" + shooter.launchesUsed + ")";
        }
        if (!source.canFireAt(target)) {
            return "Cannot fire at that target";
        }
        launch(source, target);
        return null;
    }

    public void launch(Launcher source, MapUnit target) {
        source.ammo--;
        if (source.ammo < 0) {
            source.ammo = 0;
        }
        missiles.add(new Missile(source, target));
        Player shooter = ownerOf(source.owner);
        if (shooter != null) {
            shooter.launchesUsed++;
            shooter.inFlightPlans.add(new AttackPlan(source, target));
        }
    }

    private void resolve(Missile missile) {
        impacts++;
        MapUnit target = missile.target;
        Launcher source = missile.source;
        Player shooter = ownerOf(source.owner);
        if (shooter != null) {
            shooter.remainingWeapons -= source.yield;
            if (shooter.remainingWeapons < 0) {
                shooter.remainingWeapons = 0;
            }
        }
        if (target instanceof City) {
            City city = (City) target;
            if (!city.destroyed) {
                city.hit(source.yield);
                Player victim = ownerOf(city.owner);
                if (victim != null) {
                    Arsenal.addLoss(victim.losses.city, source.yield, source.yield);
                    victim.population -= city.lastHit;
                    if (victim.population < 0) {
                        victim.population = 0;
                    }
                }
                if (city.destroyed) {
                    clock.addMessage("CITY DESTROYED");
                }
            }
            return;
        }
        if (target instanceof Launcher && !target.destroyed) {
            Launcher hit = (Launcher) target;
            hit.destroyed = true;
            Player victim = ownerOf(hit.owner);
            if (victim != null) {
                int lost = hit.kind == WeaponKind.BOMBER ? hit.yield : hit.yield * Math.max(hit.ammo, 0);
                if (hit.kind == WeaponKind.SUBMARINE) {
                    Arsenal.addLoss(victim.losses.submarine, hit.yield, lost);
                } else if (hit.kind == WeaponKind.BOMBER) {
                    Arsenal.addLoss(victim.losses.bomber, hit.yield, lost);
                } else {
                    Arsenal.addLoss(victim.losses.silo, hit.yield, lost);
                }
                victim.remainingWeapons -= lost;
                if (victim.remainingWeapons < 0) {
                    victim.remainingWeapons = 0;
                }
            }
            clock.addMessage("SILO DESTROYED");
        }
    }

    /** Nearest unit to a map point, or null if nothing is within {@code radius}. */
    public MapUnit pick(float x, float z, float radius) {
        MapUnit best = null;
        float bestD = radius * radius;
        for (Player player : match.sides) {
            for (MapUnit unit : player.units) {
                if (unit.destroyed || unit.position == null || unit instanceof Region) {
                    continue;
                }
                float dx = unit.position.x - x;
                float dz = unit.position.z - z;
                float d = dx * dx + dz * dz;
                if (d <= bestD) {
                    bestD = d;
                    best = unit;
                }
            }
        }
        return best;
    }

    public String handleTap(float x, float z) {
        MapUnit unit = pick(x, z, 48f);
        if (unit == null) {
            return null;
        }
        if (selected == null) {
            if (unit instanceof Launcher && unit.owner == match.player().side && !unit.destroyed) {
                selected = (Launcher) unit;
                return "Selected " + selected.kind + " " + selected.yield + " MT";
            }
            return null;
        }
        if (unit instanceof Launcher && unit.owner == match.player().side) {
            selected = (Launcher) unit;
            return "Selected " + selected.kind + " " + selected.yield + " MT";
        }
        String error = tryLaunch(selected, unit);
        if (error != null) {
            clock.addMessage(error);
            return error;
        }
        if (selected.clearsSelectionAfterLaunch() || selected.ammo <= 0) {
            selected = null;
        }
        return "Launch";
    }
}
