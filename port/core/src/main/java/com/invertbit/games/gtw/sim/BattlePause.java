package com.invertbit.games.gtw.sim;

import com.invertbit.games.gtw.AiOff;

/**
 * Battle pause: one flag drives both the Resume / Quit overlay and the frozen simulation.
 * Back key, the Pause button and the app going to the background all go through {@link #pause()}.
 *
 * <p>Speed is one of 0.25x, 0.5x, 1x, 2x, 4x. It can be changed while running or while
 * paused; changing it never unpauses. Slow speeds carry the fractional part of the scaled
 * frame time and of the AI turn count over to the next frame, so 0.25x is a true quarter
 * of 1x instead of rounding to zero.
 *
 * <p>Plain Java (no libGDX), so the rules can be tested on the JVM.
 */
public final class BattlePause {

    public interface Sim {
        boolean over();
        void gameOver();
        void advance(long ms);
        boolean countdownRunning();
        void ai();
    }

    private volatile boolean paused;
    private long last;
    private boolean started;
    private float speed = 1f;
    private float defaultSpeed = 1f;
    private double msCarry;
    private float aiCarry;

    public void start(long now) {
        paused = false;
        last = now;
        started = true;
        speed = defaultSpeed;
        msCarry = 0;
        aiCarry = 0f;
    }

    public float speed() {
        return speed;
    }

    public float defaultSpeed() {
        return defaultSpeed;
    }

    /** e.g. "0.5x". */
    public String speedLabel() {
        return Speeds.label(speed);
    }

    /** HUD / overlay caption, e.g. "Speed 0.5x". */
    public String speedCaption() {
        return Speeds.hudLabel(speed);
    }

    public void setSpeed(float s) {
        if (Speeds.valid(s)) {
            speed = s;
        }
    }

    public void setDefaultSpeed(float s) {
        if (Speeds.valid(s)) {
            defaultSpeed = s;
        }
    }

    /** 0.25x → 0.5x → 1x → 2x → 4x → 0.25x. Does not change the pause state. */
    public float cycleSpeed() {
        speed = Speeds.next(speed);
        return speed;
    }

    public void pause() {
        paused = true;
    }

    public void resume(long now) {
        paused = false;
        last = now;
    }

    public void quit() {
        paused = false;
        started = false;
    }

    public boolean isPaused() {
        return paused;
    }

    public boolean overlayVisible() {
        return paused;
    }

    public boolean pauseButtonVisible() {
        return !paused;
    }

    /** The speed control is on the running HUD and on the pause overlay. */
    public boolean speedButtonVisible() {
        return true;
    }

    public boolean back() {
        if (paused) {
            return true;
        }
        pause();
        return false;
    }

    public long frame(long now) {
        if (!started) {
            start(now);
        }
        long ms = now - last;
        last = now;
        if (paused || ms < 0) {
            return 0L;
        }
        double scaled = ms * (double) speed + msCarry;
        long whole = (long) Math.floor(scaled);
        msCarry = scaled - whole;
        return whole;
    }

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
            aiCarry += speed;
            while (aiCarry >= 1f) {
                aiCarry -= 1f;
                sim.ai();
            }
        }
    }
}
