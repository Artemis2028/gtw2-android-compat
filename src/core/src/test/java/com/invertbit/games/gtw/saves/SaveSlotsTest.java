package com.invertbit.games.gtw.saves;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.invertbit.games.gtw.Cb;
import com.invertbit.games.gtw.a.Cg;
import com.invertbit.games.gtw.a.Ci;
import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

/** Save format and difficulty mapping, against an in-memory libGDX Preferences. */
public class SaveSlotsTest {
    final Map<String, Object> store = new HashMap<>();

    @Before
    public void setUp() throws Exception {
        store.clear();
        final Object prefs = Proxy.newProxyInstance(getClass().getClassLoader(), new Class<?>[]{com.badlogic.gdx.Cn.class}, (proxy, m, args) -> {
            String n = m.getName();
            int argc = args == null ? 0 : args.length;
            if (n.equals("a") && argc == 2) { store.put((String) args[0], args[1]); return proxy; }   // put*
            if (n.equals("a") && argc == 0) { return null; }                                          // flush
            if (n.equals("a") && argc == 1) { Object v = store.get(args[0]); return v instanceof Boolean ? v : Boolean.FALSE; }
            if (n.equals("b")) { Object v = store.get(args[0]); return v == null ? "" : v; }          // getString
            if (n.equals("c")) { Object v = store.get(args[0]); return v == null ? 0 : v; }           // getInteger
            if (n.equals("d")) { return store.containsKey(args[0]); }                                  // contains
            throw new UnsupportedOperationException(n);
        });
        com.badlogic.gdx.Cf.a = (com.badlogic.gdx.Ca) Proxy.newProxyInstance(getClass().getClassLoader(), new Class<?>[]{com.badlogic.gdx.Ca.class}, (proxy, m, args) -> {
            if (m.getName().equals("a") && args != null && args.length == 1 && "GTWGame".equals(args[0])) {
                return prefs;
            }
            throw new UnsupportedOperationException(m.toString());
        });
        resetSingleton(com.invertbit.games.gtw.Cc.class);
        resetSingleton(Cb.class);
        SaveSlots.select(SaveSlots.FIRST);
    }

    static void resetSingleton(Class<?> c) throws Exception {
        Field f = c.getDeclaredField("a");
        f.setAccessible(true);
        f.set(null, null);
    }

    static int launchBudget(Ci ci) throws Exception {
        Field f = Ci.class.getDeclaredField("a");
        f.setAccessible(true);
        return f.getInt(ci);
    }

    static Cg game(Ci.Ia human, Ci.Ia ai) {
        Cg cg = new Cg();
        Ci h = new com.invertbit.games.gtw.a.Cf();
        h.e = human;
        cg.a(h);
        Ci a = new com.invertbit.games.gtw.a.Ca();
        a.e = ai;
        cg.a(a);
        cg.c = 3;
        return cg;
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
        store.put("v1_level", "HARD");                       // original global setting
        assertEquals(Cb.Ia.HARD, SaveSlots.level(1));
    }

    @Test
    public void slotsUseSeparatePrefixesAndKeepOriginalFormat() {
        Cb.b().b(Cb.Ia.NORMAL);
        SaveSlots.save(game(Ci.Ia.RUSSIA, Ci.Ia.USA), 3);
        assertEquals("RUSSIA", store.get("v1_s3_player.0"));
        assertEquals("USA", store.get("v1_s3_player.1"));
        assertEquals(3, store.get("v1_s3_defcon"));
        assertEquals("NORMAL", store.get("v1_s3_save.level"));
        assertTrue(store.containsKey("v1_s3_0.RUSSIA.AB.CITY.m5"));
        assertTrue(store.containsKey("v1_s3_save.time"));
        assertFalse(SaveSlots.exists(1));
        assertTrue(SaveSlots.describe(3).startsWith("Slot 3: RUSSIA vs USA  DEFCON 3  NORMAL  20"));
        assertEquals(Cb.Ia.NORMAL, SaveSlots.level(3));
    }

    @Test
    public void slot1WritesTheOriginalKeys() {
        SaveSlots.save(game(Ci.Ia.USA, Ci.Ia.CHINA), 1);
        assertEquals("USA", store.get("v1_player.0"));
        assertEquals(3, store.get("v1_defcon"));
    }

    @Test
    public void saveWritesCurrentSlotAndAutosave() {
        SaveSlots.select(2);
        SaveSlots.saveCurrent(game(Ci.Ia.CHINA, Ci.Ia.USA));
        assertTrue(SaveSlots.exists(2));
        assertTrue(SaveSlots.exists(SaveSlots.AUTOSAVE));
        assertFalse(SaveSlots.exists(1));
        assertTrue(store.containsKey("v1_auto_player.0"));
        SaveSlots.select(SaveSlots.AUTOSAVE);
        store.clear();
        SaveSlots.saveCurrent(game(Ci.Ia.CHINA, Ci.Ia.USA));
        assertTrue(SaveSlots.exists(SaveSlots.AUTOSAVE));
        assertFalse(SaveSlots.exists(2));
    }

    @Test
    public void difficultySetsHumanLaunchBudget() throws Exception {
        Cb.b().b(Cb.Ia.EASY);
        assertEquals(8, launchBudget(new com.invertbit.games.gtw.a.Cf()));
        Cb.b().b(Cb.Ia.NORMAL);
        assertEquals(6, launchBudget(new com.invertbit.games.gtw.a.Cf()));
        Cb.b().b(Cb.Ia.HARD);
        assertEquals(5, launchBudget(new com.invertbit.games.gtw.a.Cf()));
        assertEquals(4, launchBudget(new com.invertbit.games.gtw.a.Ca()));   // AI unchanged
    }

    @Test
    public void globalLevelDefaultsToEasyAndCycles() {
        assertEquals(Cb.Ia.EASY, Cb.b().d());
        assertEquals(Cb.Ia.NORMAL, Cb.Ia.EASY.next());
        assertEquals(Cb.Ia.HARD, Cb.Ia.NORMAL.next());
        assertEquals(Cb.Ia.EASY, Cb.Ia.HARD.next());
        Cb.b().a(Cb.Ia.NORMAL);
        assertEquals("NORMAL", store.get("v1_level"));
        assertEquals(Cb.Ia.NORMAL, Cb.b().e());
    }
}
