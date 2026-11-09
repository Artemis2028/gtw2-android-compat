package com.invertbit.games.gtw.settings;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class GameSettingsTest {

    @Before
    public void reset() {
        GameSettings.resetForTest();
    }

    @Test
    public void defaultsAreSoundOnFullVolumeAndOneX() {
        GameSettings gs = GameSettings.get();
        assertTrue(gs.soundOn());
        assertEquals(100, gs.volumePercent());
        assertEquals(1, gs.defaultSpeed());
        assertEquals("Sound on", gs.soundLabel());
        assertEquals("Volume: 100%", gs.volumeLabel());
        assertEquals("Speed: 1x", gs.defaultSpeedLabel());
        assertEquals(0.45f, gs.scaledVolume(GameSettings.BUTTON_BASE), 0.0001f);
        assertEquals(1.0f, gs.scaledVolume(GameSettings.IMPACT_BASE), 0.0001f);
    }

    @Test
    public void toggleSoundMutesPlaybackAndUpdatesLabel() {
        GameSettings gs = GameSettings.get();
        assertFalse(gs.toggleSound());
        assertFalse(gs.soundOn());
        assertEquals("Sound off", gs.soundLabel());
        assertEquals(0.0f, gs.scaledVolume(1.0f), 0.0001f);
        assertEquals(0.0f, gs.scaledVolume(0.45f), 0.0001f);
        assertTrue(gs.toggleSound());
        assertEquals("Sound on", gs.soundLabel());
        assertEquals(1.0f, gs.scaledVolume(1.0f), 0.0001f);
    }

    @Test
    public void cycleVolumeGoesHundredTwentyFiveFiftySeventyFive() {
        GameSettings gs = GameSettings.get();
        assertEquals(25, gs.cycleVolume());
        assertEquals("Volume: 25%", gs.volumeLabel());
        assertEquals(0.25f, gs.scaledVolume(1.0f), 0.0001f);
        assertEquals(0.1125f, gs.scaledVolume(0.45f), 0.0001f);
        assertEquals(50, gs.cycleVolume());
        assertEquals(0.225f, gs.scaledVolume(0.45f), 0.0001f);
        assertEquals(75, gs.cycleVolume());
        assertEquals(0.75f, gs.scaledVolume(1.0f), 0.0001f);
        assertEquals(100, gs.cycleVolume());
        assertEquals(1.0f, gs.scaledVolume(1.0f), 0.0001f);
    }

    @Test
    public void cycleDefaultSpeedGoesOneTwoFourOne() {
        GameSettings gs = GameSettings.get();
        assertEquals(2, gs.cycleDefaultSpeed());
        assertEquals("Speed: 2x", gs.defaultSpeedLabel());
        assertEquals(4, gs.cycleDefaultSpeed());
        assertEquals("Speed: 4x", gs.defaultSpeedLabel());
        assertEquals(1, gs.cycleDefaultSpeed());
        assertEquals("Speed: 1x", gs.defaultSpeedLabel());
    }

    @Test
    public void settersRejectJunkAndKeepLastValid() {
        GameSettings gs = GameSettings.get();
        gs.setVolumePercent(50);
        gs.setVolumePercent(0);
        gs.setVolumePercent(33);
        gs.setVolumePercent(101);
        assertEquals(50, gs.volumePercent());
        gs.setDefaultSpeed(4);
        gs.setDefaultSpeed(3);
        gs.setDefaultSpeed(0);
        gs.setDefaultSpeed(8);
        assertEquals(4, gs.defaultSpeed());
    }

    @Test
    public void muteStillKeepsStoredVolumeForWhenSoundReturns() {
        GameSettings gs = GameSettings.get();
        gs.setVolumePercent(50);
        gs.toggleSound();
        assertEquals(50, gs.volumePercent());
        assertEquals(0.0f, gs.scaledVolume(1.0f), 0.0001f);
        gs.toggleSound();
        assertEquals(0.5f, gs.scaledVolume(1.0f), 0.0001f);
    }
}
