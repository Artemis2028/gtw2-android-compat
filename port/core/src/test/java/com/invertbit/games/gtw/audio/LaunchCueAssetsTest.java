package com.invertbit.games.gtw.audio;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/** The port-only launch cues ship as Ogg files in the port's own assets, not the original ones. */
public class LaunchCueAssetsTest {

    private static final File PORT_SOUNDS = new File("../android/src/main/assets/sounds");
    private static final File ORIGINAL_SOUNDS = new File("../../src/android/src/main/assets/sounds");

    @Test
    public void launchCuesArePortOnlyOggFiles() throws IOException {
        for (String name : new String[] {"launch_silo.ogg", "launch_sub.ogg", "launch_bomber.ogg"}) {
            File f = new File(PORT_SOUNDS, name);
            assertTrue(name + " missing from port assets", f.isFile());
            byte[] magic = new byte[4];
            try (FileInputStream in = new FileInputStream(f)) {
                assertEquals(4, in.read(magic));
            }
            assertEquals(name + " is not Ogg", "OggS", new String(magic, "US-ASCII"));
            assertFalse(name + " must not be added to the original assets",
                    new File(ORIGINAL_SOUNDS, name).exists());
        }
    }
}
