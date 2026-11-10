package com.invertbit.games.gtw.sim;

import com.badlogic.gdx.math.Vector3;

/**
 * Silo, bomber or submarine (original {@code b.Ci} / {@code b.Cc} / {@code b.Cj}).
 *
 * <p>Ammo matches the factory in {@code b.b.Ca}: silos and subs get {@code e = 10},
 * bombers get {@code e = 1}. {@link #speedFactor} is {@code yield / 2}, used as
 * the missile flight-time scale ({@code distance * speedFactor / 100} seconds).
 */
public final class Launcher extends MapUnit {
    public final WeaponKind kind;
    public int ammo;
    public final int yield;
    public final float speedFactor;
    public final boolean playerOwned;
    /** Subs start submerged ({@code j = false}) and cannot be targeted until surfaced. */
    public boolean surfaced;

    public Launcher(Vector3 position, WeaponKind kind, int yield, int ammo, float speedFactor,
                    boolean playerOwned) {
        super(position);
        this.kind = kind;
        this.yield = yield;
        this.ammo = ammo;
        this.speedFactor = speedFactor;
        this.playerOwned = playerOwned;
        this.surfaced = kind != WeaponKind.SUBMARINE;
    }

    public static Launcher silo(Vector3 at, int yield, boolean playerOwned) {
        return new Launcher(at, WeaponKind.SILO, yield, 10, yield / 2.0f, playerOwned);
    }

    public static Launcher bomber(Vector3 at, int yield) {
        return new Launcher(at, WeaponKind.BOMBER, yield, 1, yield / 2.0f, false);
    }

    public static Launcher submarine(Vector3 at, int yield, boolean playerOwned) {
        return new Launcher(at, WeaponKind.SUBMARINE, yield, 10, yield / 2.0f, playerOwned);
    }

    /**
     * Original {@code b.Cb.a(target)}: cannot fire at the same side, at a bomber,
     * or at a submerged sub, and needs ammo.
     */
    public boolean canFireAt(MapUnit target) {
        if (target == null || owner == null || owner.equals(target.owner) || target instanceof Launcher
                && ((Launcher) target).kind == WeaponKind.BOMBER) {
            return false;
        }
        if (target instanceof Launcher) {
            Launcher other = (Launcher) target;
            if (other.kind == WeaponKind.SUBMARINE && !other.surfaced) {
                return false;
            }
        }
        return ammo > 0;
    }

    /** Bombers ({@code b.Cc.a()}) report ready-to-clear after a single launch. */
    public boolean clearsSelectionAfterLaunch() {
        return kind == WeaponKind.BOMBER;
    }
}
