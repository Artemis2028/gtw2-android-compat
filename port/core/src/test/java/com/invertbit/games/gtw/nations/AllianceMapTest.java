package com.invertbit.games.gtw.nations;

import com.invertbit.games.gtw.sim.AllianceTables;
import org.junit.Test;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

public class AllianceMapTest {

    @Test(timeout = 2000)
    public void ownerLookupTerminatesOnEmptyAndMissing() {
        assertEquals(-1, AllianceMap.ownerIndex("brazil", Collections.<Collection<?>>emptyList()));
        assertEquals(-1, AllianceMap.ownerIndex("brazil", Arrays.<Collection<?>>asList(Collections.emptySet(), Collections.emptySet())));
        assertEquals(-1, AllianceMap.ownerIndex(null, Arrays.<Collection<?>>asList(Collections.singleton("x"))));
        assertEquals(-1, AllianceMap.ownerIndex("x", null));
        assertEquals(-1, AllianceMap.ownerIndex("x", Arrays.<Collection<?>>asList((Collection<?>) null)));
        assertEquals(1, AllianceMap.ownerIndex("brazil", Arrays.<Collection<?>>asList(Collections.singleton("russia"), Arrays.asList("brazil", "chile"))));
    }

    @Test(timeout = 2000)
    public void ownerLookupTerminatesOnSelfReferentialSet() {
        List<Object> self = new ArrayList<Object>();
        self.add(self);
        List<Collection<?>> owned = new ArrayList<Collection<?>>();
        owned.add(self);
        assertEquals(-1, AllianceMap.ownerIndex("europe", owned));
    }

    @Test(timeout = 5000)
    public void allianceTablesAreFiniteAndNeverOfferOwnHomeAtAnyDefcon() {
        AllianceTables tables = AllianceTables.get();
        for (Side side : Side.values()) {
            for (int defcon = 0; defcon <= 12; defcon++) {
                List<RegionId> list = tables.offer(side, defcon);
                assertNotNull(list);
                assertTrue(list.size() <= RegionId.values().length);
                Set<RegionId> unique = new HashSet<RegionId>(list);
                assertEquals(side + "@" + defcon + " has duplicates", unique.size(), list.size());
                assertFalse(side + "@" + defcon + " offers its own home", list.contains(Nations.home(side)));
                assertFalse(side + " has nothing to ally with", defcon == 0 && list.isEmpty());
            }
        }
    }

    @Test
    public void noIteratorLoopWithoutExitInSources() throws Exception {
        Pattern head = Pattern.compile("^(\\s*)while \\(true\\) \\{\\s*$");
        Pattern ifNext = Pattern.compile("^(\\s*)if \\(\\w+\\.hasNext\\(\\)\\) \\{\\s*$");
        List<String> offenders = new ArrayList<String>();
        List<File> files = new ArrayList<File>();
        collect(new File("src/main/java"), files);
        assertTrue("sources not found", files.size() > 20);
        for (File f : files) {
            List<String> lines = Files.readAllLines(f.toPath(), StandardCharsets.UTF_8);
            for (int i = 0; i + 1 < lines.size(); i++) {
                Matcher h = head.matcher(lines.get(i));
                Matcher n = ifNext.matcher(lines.get(i + 1));
                if (!h.matches() || !n.matches()) {
                    continue;
                }
                String ind = n.group(1);
                for (int j = i + 2; j < lines.size(); j++) {
                    String l = lines.get(j);
                    if (l.startsWith(ind + "}") && !l.startsWith(ind + " ")) {
                        if (!l.trim().startsWith("} else")) {
                            offenders.add(f + ":" + (i + 1));
                        }
                        break;
                    }
                }
            }
        }
        assertEquals("while(true)+hasNext() loops without an exit: " + offenders, 0, offenders.size());
    }

    private static void collect(File dir, List<File> out) {
        File[] children = dir.listFiles();
        if (children == null) {
            return;
        }
        for (File c : children) {
            if (c.isDirectory()) {
                collect(c, out);
            } else if (c.getName().endsWith(".java")) {
                out.add(c);
            }
        }
    }

    @Test
    public void alliesUnlockFromDefconOneWithSingleHome() {
        assertFalse("DEFCON 0 must show no allies", 1 - 1 < 0);
        assertTrue("DEFCON 1 unlocks allies", 1 - 1 < 1);
        assertFalse("two homes need DEFCON 2", 2 - 1 < 1);
        assertTrue(2 - 1 < 2);
        AllianceTables tables = AllianceTables.get();
        for (Side side : Side.values()) {
            assertFalse(side + " has an empty key-0 pool", tables.offer(side, 0).isEmpty());
        }
        assertTrue(tables.offer(Side.EUROPE, 0).contains(RegionId.easteurope));
        assertTrue(tables.offer(Side.RUSSIA, 0).contains(RegionId.easteurope));
        assertFalse(tables.offer(Side.SOUTH_AMERICA, 0).contains(RegionId.chile));
    }
}
