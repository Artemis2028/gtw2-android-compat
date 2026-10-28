package com.invertbit.games.gtw.c;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Battle pause: the Resume / Quit overlay and the frozen simulation are one state, a new battle
 * always starts running, and Back / Pause button / app background all give the same state.
 */
public class BattlePauseTest {

    private static final long T0 = 1_000_000L;
    private static final long FRAME = 16L;

    /** Battle stand-in: the real DEFCON clock (Ck.clock) plus counters for units/missiles and AI. */
    static final class FakeBattle implements BattlePause.Sim {
        final com.invertbit.games.gtw.a.Ce clock = new com.invertbit.games.gtw.a.Ce();
        long simMs;        // total battle time given to units, missiles, bombers, subs
        int aiTurns;
        int gameOvers;
        boolean lost;

        FakeBattle(float defconSeconds) {
            clock.d = defconSeconds;
            clock.c.add("first message");
            clock.c.add("second message");
        }

        @Override public boolean over() { return lost; }
        @Override public void gameOver() { gameOvers++; }
        @Override public void advance(long ms) {
            simMs += ms;
            com.invertbit.games.gtw.b.d.Ck.clock(clock, ms);
        }
        @Override public boolean countdownRunning() { return clock.a(); }
        @Override public void ai() { aiTurns++; }

        String snapshot() {
            return simMs + "/" + aiTurns + "/" + gameOvers + "/" + clock.d + "/" + clock.e + "/" + clock.c;
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
        p.quit();                                   // Quit to menu while paused
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
        p.pause();                                  // AndroidLauncher.onPause
        now += 10 * 60_000L;                        // ten minutes in the background
        p.step(now, old);                           // first frame back
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
        FakeBattle b = new FakeBattle(1.0f);       // short DEFCON countdown so the AI is acting
        p.start(T0);
        long now = run(p, b, T0, 100);             // 1.6 s
        assertFalse("countdown should be over", b.clock.a());
        assertTrue(b.aiTurns > 0);
        float defconAfterRun = b.clock.d;

        p.pause();
        assertTrue(p.overlayVisible());
        String frozen = b.snapshot();
        now = run(p, b, now, 5_000);               // N ticks while paused
        now += 30 * 60_000L;                       // and a long trip to the background
        p.step(now, b);
        assertEquals("nothing may move while paused", frozen, b.snapshot());

        int aiBefore = b.aiTurns;
        float timerBefore = b.clock.e;
        p.resume(now);
        assertFalse(p.overlayVisible());
        run(p, b, now, 1);
        assertEquals("resume continues with one frame, no catch-up", 1_600 + FRAME, b.simMs);
        assertEquals("AI acts again", aiBefore + 1, b.aiTurns);
        assertEquals("message timer moves one frame", timerBefore + FRAME / 1000f, b.clock.e, 1e-4f);
        assertEquals("DEFCON stays where it ended", defconAfterRun, b.clock.d, 0f);
    }

    @Test
    public void defconCountdownFreezesWhilePaused() {
        BattlePause p = new BattlePause();
        FakeBattle b = new FakeBattle(60f);
        p.start(T0);
        long now = run(p, b, T0, 10);
        float d = b.clock.d;
        assertTrue(d < 60f);
        assertEquals("no AI during the countdown", 0, b.aiTurns);
        p.pause();
        now = run(p, b, now, 1_000);
        assertEquals(d, b.clock.d, 0f);
        p.resume(now);
        run(p, b, now, 1);
        assertEquals(d - FRAME / 1000f, b.clock.d, 1e-4f);
    }

    @Test
    public void backAndPauseButtonGiveTheSameState() throws Exception {
        Caj byButton = battle();
        byButton.pauseBattle();
        Caj byBack = battle();
        byBack.d();                                  // Back key
        Caj byBackground = battle();
        byBackground.pauseBattle();                  // AndroidLauncher.onPause uses the same call
        for (Caj c : new Caj[]{byButton, byBack, byBackground}) {
            assertTrue(c.pause().isPaused());
            assertTrue(c.pause().overlayVisible());
            assertFalse(c.pause().pauseButtonVisible());
            assertTrue("legacy flag mirrors the pause state", c.i);
        }
    }

    @Test
    public void overlayButtonsResumeAndQuit() throws Exception {
        Caj c = battle();
        com.invertbit.games.gtw.c.a.Cc button = (com.invertbit.games.gtw.c.a.Cc) allocate(com.invertbit.games.gtw.c.a.Cc.class);

        // Resume / Quit do nothing while running (they are hidden then anyway).
        assertFalse(new Cak(c, button).a());
        assertFalse(new Cal(c, button).a());
        assertFalse(c.pause().isPaused());

        c.pauseBattle();
        assertTrue(new Cak(c, button).a());          // Resume
        assertFalse(c.pause().isPaused());
        assertFalse(c.i);

        c.pauseBattle();
        assertTrue(new Cal(c, button).a());          // Quit to menu
        assertFalse("quit while paused leaves nothing paused", c.pause().isPaused());
        assertEquals(1, sounds);

        c.pauseBattle();
        c.d();                                       // Back while paused = quit
        assertFalse(c.pause().isPaused());

        c.pause().start(T0);                         // next match
        assertFalse(c.pause().overlayVisible());
    }

    @Test
    public void overlayIsDrawnExactlyWhilePaused() throws Exception {
        Caj c = battle();
        c.t = (com.badlogic.gdx.f.a.Ci) allocate(com.badlogic.gdx.f.a.Ci.class);   // Resume / Quit
        c.u = (com.badlogic.gdx.f.a.Ci) allocate(com.badlogic.gdx.f.a.Ci.class);   // Pause button
        assertTrue("battle start shows the Pause button, not the overlay", c.drawnStage() == c.u);
        c.pauseBattle();
        assertTrue(c.drawnStage() == c.t);
        c.resumeBattle();
        assertTrue(c.drawnStage() == c.u);
        c.d();                                       // Back
        assertTrue(c.drawnStage() == c.t);
        c.pause().start(T0);                         // next match
        assertTrue(c.drawnStage() == c.u);
    }

    // --- headless battle screen: Caj without its GL setup -------------------------------------

    private int sounds;

    private Caj battle() throws Exception {
        Caj c = (Caj) allocate(Caj.class);
        com.invertbit.games.gtw.Ca game = new com.invertbit.games.gtw.Ca(new com.invertbit.games.a.Ca() {
            @Override public void a() { }
            @Override public void b() { }
            @Override public void c() { }
            @Override public void a(int i) { sounds++; }
        });
        game.b = new com.invertbit.games.e.Cb(null);  // no screens registered: menu switch is a no-op
        c.e = game;
        c.pause().start(T0);
        c.i = false;
        return c;
    }

    private static Object allocate(Class<?> type) throws Exception {
        java.lang.reflect.Field f = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        return ((sun.misc.Unsafe) f.get(null)).allocateInstance(type);
    }
}
