package com.invertbit.games.gtw.settings;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GameSettingsTest {

    @Before
    public void reset() {
        GameSettings.resetForTest();
        Prefs.resetForTest();
    }

    @After
    public void tearDown() {
        Prefs.resetForTest();
        GameSettings.resetForTest();
    }

    @Test
    public void defaultsAreSoundOnFullVolumeAndOneX() {
        GameSettings gs = GameSettings.get();
        assertTrue(gs.soundOn());
        assertEquals(100, gs.volumePercent());
        assertEquals(1f, gs.defaultSpeed(), 0f);
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
    public void cycleDefaultSpeedGoesThroughAllFive() {
        GameSettings gs = GameSettings.get();
        assertEquals(2f, gs.cycleDefaultSpeed(), 0f);
        assertEquals("Speed: 2x", gs.defaultSpeedLabel());
        assertEquals(4f, gs.cycleDefaultSpeed(), 0f);
        assertEquals("Speed: 4x", gs.defaultSpeedLabel());
        assertEquals(0.25f, gs.cycleDefaultSpeed(), 0f);
        assertEquals("Speed: 0.25x", gs.defaultSpeedLabel());
        assertEquals(0.5f, gs.cycleDefaultSpeed(), 0f);
        assertEquals("Speed: 0.5x", gs.defaultSpeedLabel());
        assertEquals(1f, gs.cycleDefaultSpeed(), 0f);
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
        gs.setDefaultSpeed(4f);
        gs.setDefaultSpeed(3f);
        gs.setDefaultSpeed(0f);
        gs.setDefaultSpeed(8f);
        assertEquals(4f, gs.defaultSpeed(), 0f);
        gs.setDefaultSpeed(0.25f);
        assertEquals(0.25f, gs.defaultSpeed(), 0f);
        gs.setDefaultSpeed(0.5f);
        assertEquals(0.5f, gs.defaultSpeed(), 0f);
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

    @Test
    public void speedPersistsAsHundredthsAndStillReadsLegacyInts() {
        MemoryPrefStore mem = new MemoryPrefStore();
        Prefs.use(mem);
        GameSettings gs = GameSettings.get();
        gs.setDefaultSpeed(0.5f);
        gs.saveSpeed();
        assertEquals(50, mem.getInt("v1_speed"));
        gs.setDefaultSpeed(0.25f);
        gs.saveSpeed();
        assertEquals(25, mem.getInt("v1_speed"));

        GameSettings.resetForTest();
        mem.putInt("v1_speed", 4);
        GameSettings.loadFromPrefs();
        assertEquals(4f, GameSettings.get().defaultSpeed(), 0f);

        GameSettings.resetForTest();
        mem.putInt("v1_speed", 25);
        GameSettings.loadFromPrefs();
        assertEquals(0.25f, GameSettings.get().defaultSpeed(), 0f);
        assertEquals("Speed: 0.25x", GameSettings.get().defaultSpeedLabel());
    }
}
