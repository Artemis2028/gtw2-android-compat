package com.invertbit.games.gtw.sim;

import java.util.ArrayList;
import java.util.List;

/**
 * In-battle DEFCON countdown and rotating HUD messages (original {@code a.Ce}
 * plus {@code b.d.Ck.clock}).
 */
public final class DefconClock {
    public float countdown = -1f;
    public float messageTimer;
    public final List<String> messages = new ArrayList<String>();

    public void addMessage(String text) {
        messages.add(text);
        if (messages.size() > 5) {
            messages.remove(0);
        }
    }

    public boolean countdownRunning() {
        return countdown >= 0.0f;
    }

    public static void clock(DefconClock clock, long ms) {
        if (clock.countdownRunning()) {
            clock.countdown -= ms / 1000.0f;
        }
        clock.messageTimer += ms / 1000.0f;
        if (clock.messageTimer > 5.0f) {
            clock.messageTimer = 0.0f;
            if (clock.messages.size() > 0) {
                clock.messages.remove(0);
            }
        }
    }
}
