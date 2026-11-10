package com.invertbit.games.gtw.c;

/**
 * Battle pause: one flag drives both the Resume / Quit overlay and the frozen simulation.
 * Back key, the Pause button and the app going to the background all go through {@link #pause()}.
 *
 * <p>Plain Java (no libGDX), so the rules can be tested on the JVM.
 */
public final class BattlePause {

    /** What one battle frame advances. {@link Caj} adapts the real map, AI and DEFCON clock. */
    public interface Sim {
        /** True once one side has lost. */
        boolean over();

        /** Called each running frame while {@link #over()} is true (switches to the game-over screen). */
        void gameOver();

        /** Advance units, missiles, bombers, subs, explosions and the DEFCON countdown by {@code ms}. */
        void advance(long ms);

        /** True while the DEFCON countdown before launches is still running. */
        boolean countdownRunning();

        /** Let the AI act (only once the DEFCON countdown is over, and never when {@link AiOff} is on). */
        void ai();
    }

    // Written from the Android UI thread (onPause), read on the GL thread.
    private volatile boolean paused;
    private long last;
    private boolean started;
    private int speed = 1;
    private int defaultSpeed = 1;

    /** A new battle: running, overlay hidden, clock restarted (drops any stale pause).
     *  Speed returns to {@link #defaultSpeed()} (1x until settings change it). */
    public void start(long now) {
        paused = false;
        last = now;
        started = true;
        speed = defaultSpeed;
    }

    public int speed() {
        return speed;
    }

    public int defaultSpeed() {
        return defaultSpeed;
    }

    public String speedLabel() {
        return speed + "x";
    }

    /** Accepts 1, 2 or 4. Other values are ignored. */
    public void setSpeed(int s) {
        if (s == 1 || s == 2 || s == 4) {
            speed = s;
        }
    }

    /** Default applied by {@link #start(long)}. Accepts 1, 2 or 4. */
    public void setDefaultSpeed(int s) {
        if (s == 1 || s == 2 || s == 4) {
            defaultSpeed = s;
        }
    }

    /** Cycle 1x → 2x → 4x → 1x. Returns the new multiplier. */
    public int cycleSpeed() {
        if (speed == 1) {
            speed = 2;
        } else if (speed == 2) {
            speed = 4;
        } else {
            speed = 1;
        }
        return speed;
    }

    public void pause() {
        paused = true;
    }

    /** Unfreeze. The clock restarts from {@code now}, so paused time is never caught up. */
    public void resume(long now) {
        paused = false;
        last = now;
    }

    /** Leaving the battle: nothing stays paused for the next one. */
    public void quit() {
        paused = false;
        started = false;
    }

    public boolean isPaused() {
        return paused;
    }

    /** Resume / Quit overlay: drawn and clickable only while paused. */
    public boolean overlayVisible() {
        return paused;
    }

    /** Pause button: drawn and clickable only while running. */
    public boolean pauseButtonVisible() {
        return !paused;
    }

    /** Back key: pauses a running battle, and quits from the paused one. Returns true to quit. */
    public boolean back() {
        if (paused) {
            return true;
        }
        pause();
        return false;
    }

    /** Battle time for this frame: 0 while paused, else wall-clock since the last frame or resume, times {@link #speed()}. */
    public long frame(long now) {
        if (!started) {
            start(now);
        }
        long ms = now - last;
        last = now;
        if (paused || ms < 0) {
            return 0L;
        }
        return ms * (long) speed;
    }

    /**
     * One battle frame. While paused nothing in {@code sim} is called, so units, missiles, AI and
     * the DEFCON countdown stay where they are. When {@link AiOff} is on the world still advances
     * but {@link Sim#ai()} is never called.
     */
    public void step(long now, Sim sim) {
        long ms = frame(now);
        if (paused) {
            return;
        }
        if (sim.over()) {
            sim.gameOver();
        }
        sim.advance(ms);
        if (!sim.countdownRunning() && !AiOff.isOn()) {
            // One AI turn per 1x frame so 2x/4x keep the same decisions per game-second.
            for (int i = 0; i < speed; i++) {
                sim.ai();
            }
        }
    }
}
