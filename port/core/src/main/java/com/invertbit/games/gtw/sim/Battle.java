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
    /** Bombers flying to their target (original {@code b.d.Cd}); not ICBMs, so not in {@link #missiles}. */
    public final List<Launcher> sorties = new ArrayList<Launcher>();
    /** Impact blooms (original {@code gtw.b.Cg}); aged with battle time. */
    public final List<Explosion> explosions = new ArrayList<Explosion>();
    public Launcher selected;
    public boolean finished;
    /** Missiles that reached their target; the screen plays the impact sound when it grows. */
    public int impacts;
    /** Silo missiles launched; the screen plays launch_silo when this grows. */
    public int siloLaunches;
    /** Submarine missiles launched; the screen plays launch_sub when this grows. */
    public int subLaunches;
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
        if (com.invertbit.games.gtw.QuickDefeat.isOn() && !clock.countdownRunning()) {
            match.opponent().markDefeated();
        }
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
        Iterator<Explosion> flash = explosions.iterator();
        while (flash.hasNext()) {
            if (flash.next().advance(seconds)) {
                flash.remove();
            }
        }
        List<Missile> landed = new ArrayList<Missile>();
        for (Missile missile : missiles) {
            if (missile.advance(seconds)) {
                landed.add(missile);
            }
        }
        for (Missile missile : landed) {
            // A missile can be dropped by an earlier impact this frame (its silo destroyed).
            if (missiles.remove(missile)) {
                resolve(missile.source, missile.target);
            }
        }
        Iterator<Launcher> sortie = sorties.iterator();
        List<Launcher> arrived = new ArrayList<Launcher>();
        while (sortie.hasNext()) {
            Launcher bomber = sortie.next();
            MapUnit hit = bomber.advanceMove(seconds);
            if (hit != null) {
                arrived.add(bomber);
                sortie.remove();
                resolve(bomber, hit);
            }
        }
        for (Launcher bomber : arrived) {
            // Original b.d.Cd removes the bomber model once it has struck.
            bomber.spent = true;
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
        if (source.kind == WeaponKind.BOMBER) {
            // Original b.d.Cd: the bomber itself flies to the target at 30 units/s.
            source.ammo = 0;
            source.beginMoveTo(target);
            sorties.add(source);
            return;
        }
        if (source.kind == WeaponKind.SUBMARINE) {
            // Original b.d.Cq: firing surfaces the sub, so it becomes visible and targetable.
            source.surfaced = true;
        }
        source.ammo--;
        if (source.ammo < 0) {
            source.ammo = 0;
        }
        missiles.add(new Missile(source, target));
        if (source.kind == WeaponKind.SILO) {
            siloLaunches++;
        } else if (source.kind == WeaponKind.SUBMARINE) {
            subLaunches++;
        }
        Player shooter = ownerOf(source.owner);
        if (shooter != null) {
            shooter.launchesUsed++;
            shooter.inFlightPlans.add(new AttackPlan(source, target));
        }
    }

    private void resolve(Launcher source, MapUnit target) {
        impacts++;
        if (target != null && target.position != null) {
            explosions.add(new Explosion(target.position.x, target.position.z));
        }
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
            if (hit.kind != WeaponKind.BOMBER) {
                // Original b.d.Co.b: a destroyed silo/sub loses the warheads it still has in the air.
                Iterator<Missile> drop = missiles.iterator();
                while (drop.hasNext()) {
                    if (drop.next().source == hit) {
                        drop.remove();
                    }
                }
            }
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
                if (unit.destroyed || unit.position == null || unit instanceof Region || !visible(unit)) {
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

    /**
     * Drawn and pickable: spent bombers are gone; enemy subs show only once surfaced
     * (original {@code b.d.Cq}: {@code j || i}).
     */
    public boolean visible(MapUnit unit) {
        if (unit instanceof Launcher) {
            Launcher launcher = (Launcher) unit;
            if (launcher.spent) {
                return false;
            }
            if (launcher.kind == WeaponKind.SUBMARINE && !launcher.surfaced
                    && launcher.owner != match.player().side) {
                return false;
            }
        }
        return true;
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
