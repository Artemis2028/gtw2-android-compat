package com.invertbit.games.gtw.nations;

import com.invertbit.games.gtw.a.Ci;
import com.invertbit.games.gtw.b.Cf;
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

/** Guards for the Alliance-screen freeze (c/Cp spun forever in its render loop). */
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
        self.add(self);                      // a set that contains itself
        List<Collection<?>> owned = new ArrayList<Collection<?>>();
        owned.add(self);
        assertEquals(-1, AllianceMap.ownerIndex("europe", owned));
    }

    @Test(timeout = 5000)
    public void allianceTablesAreFiniteAndNeverOfferOwnHomeAtAnyDefcon() {
        com.invertbit.games.gtw.b.e.Cc cc = com.invertbit.games.gtw.b.e.Cc.a();
        for (Ci.Ia ia : Ci.Ia.values()) {
            for (int defcon = 0; defcon <= 12; defcon++) {
                List<Cf.Ib> list = cc.a(ia, defcon);
                assertNotNull(list);
                assertTrue(list.size() <= Cf.Ib.values().length);
                Set<Cf.Ib> unique = new HashSet<Cf.Ib>(list);
                assertEquals(ia + "@" + defcon + " has duplicates", unique.size(), list.size());
                assertFalse(ia + "@" + defcon + " offers its own home", list.contains(Nations.home(ia)));
                assertFalse(ia + " has nothing to ally with", defcon == 0 && list.isEmpty());
            }
        }
    }

    /**
     * jadx sometimes drops a loop exit and leaves {@code while (true) { if (it.hasNext()) { ... } }}
     * with no else/exit: an infinite loop once the iterator is exhausted (the Alliance freeze).
     * Fail the build if that shape reappears anywhere in the sources.
     */
    @Test
    public void noIteratorLoopWithoutExitInSources() throws Exception {
        Pattern head = Pattern.compile("^(\\s*)while \\(true\\) \\{\\s*$");
        Pattern ifNext = Pattern.compile("^(\\s*)if \\(\\w+\\.hasNext\\(\\)\\) \\{\\s*$");
        List<String> offenders = new ArrayList<String>();
        List<File> files = new ArrayList<File>();
        collect(new File("src/main/java"), files);
        assertTrue("sources not found", files.size() > 100);
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
}
