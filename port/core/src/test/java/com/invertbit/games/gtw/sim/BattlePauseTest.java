package com.invertbit.games.gtw.sim;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.invertbit.games.gtw.AiOff;
import org.junit.Test;

public class BattlePauseTest {

    private static final long T0 = 1_000_000L;
    private static final long FRAME = 16L;

    static final class FakeBattle implements BattlePause.Sim {
        final DefconClock clock = new DefconClock();
        long simMs;
        int aiTurns;
        int gameOvers;
        boolean lost;

        FakeBattle(float defconSeconds) {
            clock.countdown = defconSeconds;
            clock.messages.add("first message");
            clock.messages.add("second message");
        }

        @Override public boolean over() { return lost; }
        @Override public void gameOver() { gameOvers++; }
        @Override public void advance(long ms) {
            simMs += ms;
            DefconClock.clock(clock, ms);
        }
        @Override public boolean countdownRunning() { return clock.countdownRunning(); }
        @Override public void ai() { aiTurns++; }

        String snapshot() {
            return simMs + "/" + aiTurns + "/" + gameOvers + "/" + clock.countdown + "/" + clock.messageTimer + "/" + clock.messages;
        }
    }

    private static long run(BattlePause p, FakeBattle b, long now, int frames) {
        for (int k = 0; k < frames; k++) {
            now += FRAME;
            p.step(now, b);
        }
        return now;
    }

    @Test
    public void newBattleStartsRunningWithOverlayHidden() {
        BattlePause p = new BattlePause();
        p.start(T0);
        assertFalse(p.isPaused());
        assertFalse("overlay must be hidden at battle start", p.overlayVisible());
        assertTrue(p.pauseButtonVisible());
    }

    @Test
    public void newBattleDropsAPauseLeftFromTheLastMatch() {
        BattlePause p = new BattlePause();
        p.start(T0);
        p.pause();
        p.start(T0 + 5_000);
        assertFalse("paused last match must not carry over", p.isPaused());
        assertFalse(p.overlayVisible());

        p.pause();
        p.quit();
        p.start(T0 + 10_000);
        assertFalse(p.isPaused());
        assertFalse(p.overlayVisible());
    }

    @Test
    public void newBattleAfterAppBackgroundStartsRunningWithoutCatchUp() {
        BattlePause p = new BattlePause();
        FakeBattle old = new FakeBattle(5f);
        p.start(T0);
        long now = run(p, old, T0, 10);
        p.pause();
        now += 10 * 60_000L;
        p.step(now, old);
        p.quit();

        FakeBattle next = new FakeBattle(5f);
        now += 1_000;
        p.start(now);
        assertFalse(p.isPaused());
        assertFalse(p.overlayVisible());
        run(p, next, now, 1);
        assertEquals("first frame of the new battle is one frame, not the background time", FRAME, next.simMs);
    }

    @Test
    public void pausedTicksChangeNothingAndResumeContinuesWithoutCatchUp() {
        BattlePause p = new BattlePause();
        FakeBattle b = new FakeBattle(1.0f);
        p.start(T0);
        long now = run(p, b, T0, 100);
        assertFalse("countdown should be over", b.clock.countdownRunning());
        assertTrue(b.aiTurns > 0);
        float defconAfterRun = b.clock.countdown;

        p.pause();
        assertTrue(p.overlayVisible());
        String frozen = b.snapshot();
        now = run(p, b, now, 5_000);
        now += 30 * 60_000L;
        p.step(now, b);
        assertEquals("nothing may move while paused", frozen, b.snapshot());

        int aiBefore = b.aiTurns;
        float timerBefore = b.clock.messageTimer;
        p.resume(now);
        assertFalse(p.overlayVisible());
        run(p, b, now, 1);
        assertEquals("resume continues with one frame, no catch-up", 1_600 + FRAME, b.simMs);
        assertEquals("AI acts again", aiBefore + 1, b.aiTurns);
        assertEquals("message timer moves one frame", timerBefore + FRAME / 1000f, b.clock.messageTimer, 1e-4f);
        assertEquals("DEFCON stays where it ended", defconAfterRun, b.clock.countdown, 0f);
    }

    @Test
    public void defconCountdownFreezesWhilePaused() {
        BattlePause p = new BattlePause();
        FakeBattle b = new FakeBattle(60f);
        p.start(T0);
        long now = run(p, b, T0, 10);
        float d = b.clock.countdown;
        assertTrue(d < 60f);
        assertEquals("no AI during the countdown", 0, b.aiTurns);
        p.pause();
        now = run(p, b, now, 1_000);
        assertEquals(d, b.clock.countdown, 0f);
        p.resume(now);
        run(p, b, now, 1);
        assertEquals(d - FRAME / 1000f, b.clock.countdown, 1e-4f);
    }

    @Test
    public void aiOffMeansZeroAiActionsOverManyTicks() {
        AiOff.set(true);
        try {
            BattlePause p = new BattlePause();
            FakeBattle b = new FakeBattle(0.5f);
            p.start(T0);
            run(p, b, T0, 500);
            assertFalse("countdown should be over, so the AI would have acted if on", b.clock.countdownRunning());
            assertEquals("AI-off means zero AI actions", 0, b.aiTurns);
            assertEquals("the world still advances", 500 * FRAME, b.simMs);
        } finally {
            AiOff.reset();
        }
    }

    @Test
    public void defaultLeavesAiUnchanged() {
        assertFalse("AI-off must default to off", AiOff.isOn());
        BattlePause p = new BattlePause();
        FakeBattle b = new FakeBattle(0.5f);
        p.start(T0);
        run(p, b, T0, 100);
        assertFalse(b.clock.countdownRunning());
        assertTrue("default play still lets the AI act", b.aiTurns > 0);
    }

    @Test
    public void speedMultipliesRunningTimeAndIsIgnoredWhilePaused() {
        BattlePause p = new BattlePause();
        FakeBattle b = new FakeBattle(-1f);
        p.start(T0);
        assertEquals(1, p.speed());
        assertEquals("1x", p.speedLabel());
        long now = run(p, b, T0, 10);
        assertEquals(10 * FRAME, b.simMs);
        assertEquals(10, b.aiTurns);

        p.setSpeed(2);
        assertEquals("2x", p.speedLabel());
        now = run(p, b, now, 10);
        assertEquals("2x doubles battle time", 10 * FRAME + 10 * FRAME * 2, b.simMs);
        assertEquals("2x runs two AI turns per frame", 10 + 20, b.aiTurns);

        p.setSpeed(4);
        now = run(p, b, now, 5);
        assertEquals(10 * FRAME + 20 * FRAME + 5 * FRAME * 4, b.simMs);
        assertEquals(10 + 20 + 20, b.aiTurns);

        p.pause();
        String frozen = b.snapshot();
        run(p, b, now, 40);
        assertEquals("speed must not thaw a paused battle", frozen, b.snapshot());
    }

    @Test
    public void cycleSpeedGoesOneTwoFourOneAndRejectsJunk() {
        BattlePause q = new BattlePause();
        assertEquals(1, q.speed());
        assertEquals(2, q.cycleSpeed());
        assertEquals(4, q.cycleSpeed());
        assertEquals(1, q.cycleSpeed());
        assertEquals(2, q.cycleSpeed());
        q.setSpeed(3);
        assertEquals("invalid speed is ignored", 2, q.speed());
        q.setSpeed(0);
        assertEquals(2, q.speed());
    }

    @Test
    public void newBattleRestoresDefaultSpeed() {
        BattlePause p = new BattlePause();
        p.start(T0);
        p.setSpeed(4);
        p.start(T0 + 1_000);
        assertEquals(1, p.speed());
        p.setDefaultSpeed(2);
        p.setSpeed(4);
        p.start(T0 + 2_000);
        assertEquals("start applies defaultSpeed, not the last in-battle setting", 2, p.speed());
    }
}
