package com.invertbit.games.gtw.saves;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.invertbit.games.gtw.nations.Side;
import com.invertbit.games.gtw.settings.MemoryPrefStore;
import com.invertbit.games.gtw.settings.Prefs;
import com.invertbit.games.gtw.sim.Difficulty;
import com.invertbit.games.gtw.sim.Match;
import com.invertbit.games.gtw.sim.Player;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/** Save format and difficulty mapping, against an in-memory preferences store. */
public class SaveSlotsTest {
    final MemoryPrefStore mem = new MemoryPrefStore();
    final Map<String, Object> store = mem.data;

    @Before
    public void setUp() {
        store.clear();
        Prefs.use(mem);
        Difficulty.reset();
        SaveSlots.select(SaveSlots.FIRST);
    }

    @After
    public void tearDown() {
        Difficulty.reset();
        Prefs.resetForTest();
        SaveSlots.select(SaveSlots.FIRST);
    }

    static Match game(Side human, Side ai) {
        Match match = Match.create(human, ai, 3);
        return match;
    }

    @Test
    public void originalSingleSaveShowsAsSlot1() {
        store.put("v1_player.0", "USA");
        store.put("v1_player.1", "CHINA");
        store.put("v1_defcon", 2);
        assertTrue(SaveSlots.exists(1));
        assertFalse(SaveSlots.exists(2));
        assertFalse(SaveSlots.exists(SaveSlots.AUTOSAVE));
        assertEquals("Slot 1: USA vs CHINA  DEFCON 2  EASY  (older save)", SaveSlots.describe(1));
        store.put("v1_level", "HARD");
        assertEquals(Difficulty.HARD, SaveSlots.level(1));
    }

    @Test
    public void slotsUseSeparatePrefixesAndKeepOriginalFormat() {
        Difficulty.setInProgress(Difficulty.NORMAL);
        SaveSlots.save(game(Side.RUSSIA, Side.USA), 3);
        assertEquals("RUSSIA", store.get("v1_s3_player.0"));
        assertEquals("USA", store.get("v1_s3_player.1"));
        assertEquals(3, store.get("v1_s3_defcon"));
        assertEquals("NORMAL", store.get("v1_s3_save.level"));
        assertTrue(store.containsKey("v1_s3_0.RUSSIA.AB.CITY.m5"));
        assertTrue(store.containsKey("v1_s3_save.time"));
        assertFalse(SaveSlots.exists(1));
        assertTrue(SaveSlots.describe(3).startsWith("Slot 3: RUSSIA vs USA  DEFCON 3  NORMAL  20"));
        assertEquals(Difficulty.NORMAL, SaveSlots.level(3));
    }

    @Test
    public void slot1WritesTheOriginalKeys() {
        SaveSlots.save(game(Side.USA, Side.CHINA), 1);
        assertEquals("USA", store.get("v1_player.0"));
        assertEquals(3, store.get("v1_defcon"));
    }

    @Test
    public void saveWritesCurrentSlotAndAutosave() {
        SaveSlots.select(2);
        SaveSlots.saveCurrent(game(Side.CHINA, Side.USA));
        assertTrue(SaveSlots.exists(2));
        assertTrue(SaveSlots.exists(SaveSlots.AUTOSAVE));
        assertFalse(SaveSlots.exists(1));
        assertTrue(store.containsKey("v1_auto_player.0"));
        SaveSlots.select(SaveSlots.AUTOSAVE);
        store.clear();
        SaveSlots.saveCurrent(game(Side.CHINA, Side.USA));
        assertTrue(SaveSlots.exists(SaveSlots.AUTOSAVE));
        assertFalse(SaveSlots.exists(2));
    }

    @Test
    public void difficultySetsHumanLaunchBudget() {
        Difficulty.setInProgress(Difficulty.EASY);
        assertEquals(8, new Player(Side.USA, true).launchBudget);
        Difficulty.setInProgress(Difficulty.NORMAL);
        assertEquals(6, new Player(Side.USA, true).launchBudget);
        Difficulty.setInProgress(Difficulty.HARD);
        assertEquals(5, new Player(Side.USA, true).launchBudget);
        assertEquals(4, new Player(Side.USA, false).launchBudget);
    }

    @Test
    public void globalLevelDefaultsToEasyAndCycles() {
        assertEquals(Difficulty.EASY, Difficulty.global());
        assertEquals(Difficulty.NORMAL, Difficulty.EASY.next());
        assertEquals(Difficulty.HARD, Difficulty.NORMAL.next());
        assertEquals(Difficulty.EASY, Difficulty.HARD.next());
        Difficulty.setGlobal(Difficulty.NORMAL);
        assertEquals("NORMAL", store.get("v1_level"));
        assertEquals(Difficulty.NORMAL, Difficulty.current());
    }

    @Test
    public void loadRestoresSidesDefconLevelAndArsenalWithoutDoubling() {
        Difficulty.setInProgress(Difficulty.HARD);
        Match saved = game(Side.SOUTH_AMERICA, Side.RUSSIA);
        saved.player().arsenal.silo.m10 = 40;
        saved.player().arsenal.points = 40;
        int cityMt = saved.player().arsenal.city.m5;
        SaveSlots.save(saved, 2);
        Difficulty.reset();

        Match loaded = SaveSlots.load(2);
        assertEquals(2, SaveSlots.current());
        assertEquals(Difficulty.HARD, Difficulty.current());
        assertEquals(Side.SOUTH_AMERICA, loaded.player().side);
        assertEquals(Side.RUSSIA, loaded.opponent().side);
        assertEquals(3, loaded.defcon);
        assertEquals(saved.player().regionCount(), loaded.player().regionCount());
        assertEquals(40, loaded.player().arsenal.silo.m10);
        assertEquals(40, loaded.player().arsenal.points);
        assertEquals("rows are replaced, not added on top", cityMt, loaded.player().arsenal.city.m5);
        assertEquals(5, loaded.player().launchBudget);
    }
}
