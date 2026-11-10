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
    /** Bomber sortie: fly here at {@link #BOMBER_SPEED} world-units/sec, then strike. */
    public MapUnit destination;
    /** Bomber has struck and left the map. */
    public boolean spent;
    public final Vector3 moveFrom = new Vector3();
    public final Vector3 moveTo = new Vector3();
    public float moveElapsed;
    public float moveDuration;
    /**
     * Bomber cruise speed in world-units/sec. Original {@code b.Cc.o} is 30, but
     * that is faster than 10 MT missiles (20) and 15 MT missiles (13.3) —
     * missile speed is {@code 200 / yield} from {@code duration = dist * (yield/2) / 100}.
     * Half of the 10 MT missile speed, so bombers stay slower than every yield class.
     */
    public static final float BOMBER_SPEED = 10.0f;

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

    public boolean isMoving() {
        return destination != null;
    }

    public void beginMoveTo(MapUnit target) {
        this.destination = target;
        this.moveElapsed = 0f;
        this.moveFrom.set(position);
        this.moveTo.set(target.position);
        Missile.wrapShortest(moveFrom, moveTo);
        float dist = (float) Math.hypot(moveTo.x - moveFrom.x, moveTo.z - moveFrom.z);
        this.moveDuration = Math.max(0.05f, dist / BOMBER_SPEED);
    }

    /** Missile travel speed implied by the original duration formula, world-units/sec. */
    public static float missileSpeed(int yield) {
        return 100.0f / (yield / 2.0f);
    }

    /**
     * Nose heading in degrees (original {@code b.d.Cd} / {@code b.Cc.l}): 0 = north
     * (-Z), -90 = east (+X), 90 = west, ±180 = south. The objects.svg bomber
     * glyphs already point north, so this is the rotation to apply. Parked
     * bombers keep the native north facing.
     */
    public float headingDegrees() {
        if (!isMoving()) {
            return 0f;
        }
        float dx = moveTo.x - moveFrom.x;
        float dz = moveTo.z - moveFrom.z;
        if (dx == 0f && dz == 0f) {
            return 0f;
        }
        return (float) Math.toDegrees(Math.atan2(-dx, -dz));
    }

    /**
     * Advance a bomber sortie. Returns the target on arrival (caller should
     * resolve the strike), or null while still en route.
     */
    public MapUnit advanceMove(float seconds) {
        if (destination == null) {
            return null;
        }
        moveElapsed += seconds;
        float t = moveDuration <= 0f ? 1f : Math.min(1f, moveElapsed / moveDuration);
        position.x = Missile.wrapX(moveFrom.x + (moveTo.x - moveFrom.x) * t);
        position.y = 0f;
        position.z = moveFrom.z + (moveTo.z - moveFrom.z) * t;
        if (t >= 1f) {
            MapUnit hit = destination;
            destination = null;
            position.x = Missile.wrapX(moveTo.x);
            position.z = moveTo.z;
            return hit;
        }
        return null;
    }
}
