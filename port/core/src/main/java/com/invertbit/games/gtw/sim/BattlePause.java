package com.invertbit.games.gtw.sim;

import com.invertbit.games.gtw.AiOff;

/**
 * Battle pause: one flag drives both the Resume / Quit overlay and the frozen simulation.
 * Back key, the Pause button and the app going to the background all go through {@link #pause()}.
 *
 * <p>Plain Java (no libGDX), so the rules can be tested on the JVM. Behaviour matches
 * the {@code gtw.c.BattlePause} landed on {@code feat/more-nations}.
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
    private int speed = 1;
    private int defaultSpeed = 1;

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

    public void setSpeed(int s) {
        if (s == 1 || s == 2 || s == 4) {
            speed = s;
        }
    }

    public void setDefaultSpeed(int s) {
        if (s == 1 || s == 2 || s == 4) {
            defaultSpeed = s;
        }
    }

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
        return ms * (long) speed;
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
            for (int i = 0; i < speed; i++) {
                sim.ai();
            }
        }
    }
}
