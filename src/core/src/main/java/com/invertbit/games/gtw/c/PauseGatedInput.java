package com.invertbit.games.gtw.c;

/**
 * Passes input to a battle stage only in one pause state: the Pause button stage while running, the
 * Resume / Quit stage while paused. A hidden stage must not swallow taps meant for the map.
 * (com.badlogic.gdx.Ck = InputProcessor.)
 */
final class PauseGatedInput implements com.badlogic.gdx.Ck {
    private final com.badlogic.gdx.Ck target;
    private final com.invertbit.games.gtw.c.Caj battle;
    private final boolean whenPaused;

    PauseGatedInput(com.badlogic.gdx.Ck target, com.invertbit.games.gtw.c.Caj battle, boolean whenPaused) {
        this.target = target;
        this.battle = battle;
        this.whenPaused = whenPaused;
    }

    private boolean open() {
        return battle.pause().isPaused() == whenPaused;
    }

    @Override public final boolean a(int keycode) { return open() && target.a(keycode); }
    @Override public final boolean b(int keycode) { return open() && target.b(keycode); }
    @Override public final boolean a(char c) { return open() && target.a(c); }
    @Override public final boolean a(int x, int y, int pointer, int button) { return open() && target.a(x, y, pointer, button); }
    @Override public final boolean b(int x, int y, int pointer, int button) { return open() && target.b(x, y, pointer, button); }
    @Override public final boolean a(int x, int y, int pointer) { return open() && target.a(x, y, pointer); }
    @Override public final boolean a(int x, int y) { return open() && target.a(x, y); }
    @Override public final boolean c(int amount) { return open() && target.c(amount); }
}
