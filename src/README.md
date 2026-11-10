# GTW II — decompiled, buildable source (branch `decompiled`)

Gradle project rebuilt from the original APK (`../original/GTW-II-1.5.apk`, GTW II 1.5) so new
features can be added in Java.

```sh
cd src
./gradlew assembleDebug        # -> android/build/outputs/apk/debug/android-debug.apk
```
Needs JDK 17+ and an Android SDK (`ANDROID_HOME` or `local.properties`).

**Signing:** debug builds are signed with the same public debug key as the compat build: the
`debug.keystore` embedded in Uber APK Signer 1.3.0 (alias `androiddebugkey`, password
`android`). Certificate SHA-256:
`1e08a903aef9c3a721510b64ec764d01d3d094eb954161b62544ea8f187b5953`.
`python3 signing/fetch-debug-key.py` extracts it from the checksum-pinned jar listed in
`../toolchain.json`, the same one `rebuild.py` uses. CI does this and fails if the certificate
differs, so every source build installs over the previous one. The keystore is not committed. Without it,
Gradle signs with your machine's own debug key, and that APK will not update a CI build. The debug APK
installs as a third package, `com.invertbit.games.gtw.android.src` ("GTW II (Source)"),
next to the original and the compat build.

## Layout
| Path | What |
|------|------|
| `core/src/main/java/com/invertbit/**` | **Game code, decompiled to Java (141 source files).** Edit here. |
| `core/src/main/resources/com/badlogic/**` | libGDX classpath resources (default font, g3d shaders). |
| `android/src/main/java/.../AndroidLauncher.java`, `Platform.java` | Hand-written launcher. Ads removed. |
| `android/src/main/assets/` | Game assets (shaders, SVG map, fonts, sounds, tutorial). |
| `libs/gdx-1.7.0-invertbit.jar` | The game's own copy of libGDX 1.7.0 + Android backend, as shipped in the APK (ProGuard-obfuscated and shrunk), converted to a jar. |
| `decompile/` | Scripts that regenerate all of the above from the APK. |

Native libraries: 32-bit (`armeabi`, `armeabi-v7a`, `x86`) are copied from the original APK
(libGDX 1.7.0, 4 KB ELF pages; 16 KB-page devices are 64-bit and will not load them).
64-bit `libgdx.so` still comes from Maven libGDX **1.9.4** (JNI-compatible with the
obfuscated 1.7.0 Java: it still exports `AndroidGL20_*` and the old `Matrix4` overloads).
The arm64 1.9.4 `libgdx.so` already has 64 KB LOAD alignment (compatible with 16 KB pages);
the x86_64 1.9.4 `libgdx.so` is still 4 KB. 64-bit `libgdx-freetype.so` comes from Maven
libGDX **1.14.2** (16 KB LOAD alignment, JNI a superset of 1.9.4). Natives are packaged
uncompressed (`extractNativeLibs=false`, `useLegacyPackaging false`) so AGP 8.5+ can
`zipalign -P 16` them.

## Names
The original was obfuscated by ProGuard, so names are single letters. The game's classes
and the bundled libGDX share one renaming scheme (in `decompile/classes.tiny`):
top-level `a` -> `Ca`, nested `a$b` -> `Ca$Ib`. Packages, methods and fields keep their
obfuscated names. That way every name maps straight back to the APK and smali. Enum constants
are restored to their real names (`Pixmap.Format.RGBA8888`, game enums such as `CITY`, `SILO`,
`BOMBER`, `SUBMARINE`).

Handy anchors:
- `com.invertbit.games.gtw.Ca`: the game's `ApplicationListener` (entry point)
- `com.invertbit.games.a.Cb` / `Cc`: platform interface (links / ad hook)
- `com.badlogic.gdx.backends.android.Ca` = `AndroidApplication`, `Ce` = `AndroidApplicationConfiguration`
- `com.badlogic.gdx.math.Ck` = `Vector2`, `Cl` = `Vector3`, `Cj` = `Vector`; `com.badlogic.gdx.graphics.Cm` = `Pixmap`
- `com.badlogic.gdx.f.a` = scene2d, `com.badlogic.gdx.f.a.a` = scene2d.ui (`Ce` = `Table`)

## Why libGDX is not a Maven Java dependency (yet)
The game calls libGDX through ProGuard-obfuscated names (`com.badlogic.gdx.utils.a` is `Array`,
and so on), and ProGuard also merged and shrank libGDX methods. Swapping in stock
`com.badlogicgames.gdx:gdx:1.14.2` (or 1.13.x) means mapping every libGDX class, method and field
the game uses back to its real name and rewriting those call sites. That work is not done yet.
Until then, the game compiles against the exact libGDX 1.7.0 bytecode it shipped with.

Natives are a separate constraint: libGDX 1.13.0+ `.so` files are 16 KB page-aligned, but they
dropped the `AndroidGL20_*` JNI and the old `Matrix4` overload symbols that the 1.7.0 Java
backend still calls. Using those newer `libgdx.so` files without first moving the Java side
onto the real API would `UnsatisfiedLinkError` on the first GL call. FreeType's JNI did not
lose symbols, so `libgdx-freetype.so` is already on 1.14.2. See "Next steps".

## How it was produced (`decompile/import.sh`)
1. apktool decodes the smali. `smali_rename.py` renames classes, restores enum-constant names,
   clears bogus `varargs` flags left by ProGuard, un-hides ProGuard-merged bridge methods
   (`Vector2/3.scl(float)`), and makes the game's private fields package-private (jadx inlines
   the `access$` accessors). Only names and flags change. apktool reassembles the result.
2. jadx 1.5.3 (`--no-imports`) decompiles the game code into `core/`.
3. dex2jar 2.4 turns the renamed libGDX bytecode into `libs/gdx-1.7.0-invertbit.jar`.
4. `fixes.py` applies 12 hand fixes (below). `drop_bad_casts.py` then removes about 65 casts that jadx
   inferred wrongly on generic libGDX calls (mostly `Table.add(actor)`), guided by javac errors.

### Hand fixes (`decompile/fixes.py`)
- `gtw/b/Ce`: ProGuard stripped the unused constructor. Added a private one so javac accepts it.
- `gtw/b/d/Cm`, `gtw/b/d/Cd`: `Vector2/3.scl(float)` survives only as the `Vector` interface bridge. Added a result cast.
- `b/a/Ca` (`Vector3.limit` inline): jadx lost the `len2` temporary (`r2`).
- `c/a/Cb` (SVG title handler): jadx lost the `List` temporary in `list.get(list.size()-1)`.
- `gtw/c/d/Ca`: checked `IOException` from an undeclared constructor, now wrapped in `UncheckedIOException`.
- `c/a/Ca` (SVG loader): declared `throws Exception`. Callers already catch it.

Runtime decompile bugs (compiled fine, but behaved differently from the original bytecode):
- `b/a/Ca.a(String)`: jadx dropped the exit of a `while (true)` loop, so the loading screen hung (GL thread at 100%).
- `gtw/c/a/Cc`, `gtw/c/a/Ce` (game buttons): dropped `int`->`float` casts made `Cell.width(float)` resolve to `Cell.colspan(int)`, so every button was laid out wrong.
- `gtw/c/a/Cf` (battle HUD): a `switch` on nation was emptied (USA/RUSSIA/CHINA colours).
- `gtw/c/Cw` (arsenal screen): jadx dropped the `break` after the BOMBER case's inner switch, so it fell through to SUBMARINE and every side's bomber screen showed a submarine. The icon choice is now `Cw.icon(row, side)`.
- `gtw/b/d/Cb`: after the search loop the found render instance was overwritten with `null`, so destroyed units were never removed from the render list.
- `backends.android.a.Cg` (libGDX 1.7.0 `GdxEglConfigChooser`, in the bundled jar): required an exact RGB565 match and returned `null` when the device only advertised RGBA8888 GLES2 configs (API 35+ emulators, including the user's Windows Android Studio AVD until it was switched to Software GLES). `Cb$Ib.createContext` then called `eglCreateContext` with that null config and threw `IllegalArgumentException`. Replaced by `android/src/main/java/com/badlogic/gdx/backends/android/a/Cg.java`, which falls back to RGBA8888 / RGB565 / any GLES2 config and never returns null when one exists. The fallback order lives in `gtw/egl/EglCompat` (JVM-tested). `:core`'s `filterGdxJar` task drops the original `a.Cg` from the jar so the replacement is the only copy. `Cb$Ib.createContext` already passed `{EGL_CONTEXT_CLIENT_VERSION (0x3098), 2, EGL_NONE}` and `EGL_NO_CONTEXT`.
- `gtw/c/Caj.a(int,int)` (battle resize): only the overlay stage `t` (Resume / Quit) had its viewport updated. The running HUD stage `u` (Pause button, and the AI OFF badge while running) kept the construction-time viewport, so on a 2400x1080 phone the button was never drawn or hittable. Both stages are now resized via `BattleHudLayout.stagesToResize`. The Pause button stays 160x100 in the bottom-right of the 1920x1080 stretch world (away from Alaska, the US and the munitions counters); `PauseGatedInput` still eats taps on it so they do not reach the map. `BattleHudLayoutTest` checks the button is on-screen at 1080x2400, 1344x2992, 2856x1280 and 2400x1080.


The last three were found with `decompile/check_sigdiff.py` (invoked method signatures per class,
original smali vs. the built APK) and `decompile/check_constdiff.py` (literal constants per class).
Run them after any re-import:
```sh
java -jar apktool.jar d -r -o /tmp/built android/build/outputs/apk/debug/android-debug.apk
python3 decompile/check_sigdiff.py   <apktool-decoded renamed.apk>/smali /tmp/built
python3 decompile/check_constdiff.py <apktool-decoded renamed.apk>/smali /tmp/built
```
Remaining differences are benign: `List` vs `ArrayList` receivers, javac bridge methods, and
string concatenations that javac folds.

No game classes were lost. All 141 game source files decompiled cleanly enough to compile (the original launcher was replaced by hand). The one jadx warning is a
type-inference note in `gtw/b/e/Ch` (code is present). The ad SDK (`com.google.android.gms`,
`com.google.ads`) and `android.support` are dropped. The launcher that wired the ads was rewritten.

## Status
- `./gradlew assembleDebug` builds; CI builds it on every push.
- Emulator (API 34 x86_64, software GL): reaches the title screen with the same layout as compat1,
  loads a save, and runs a DEFCON round (AI attacks, HUD). Not yet tested on a real phone.

## Added features (not in the original game)
Hand-written on top of the decompiled code. `import.sh` regenerates only the decompiled sources,
so these changes would have to be re-applied after a re-import.

### Save slots and autosave
- **Original mechanism:** one save in the libGDX preferences file `GTWGame` (Android
  `shared_prefs/GTWGame.xml`). Keys `v1_player.N`, `v1_player.N.country` (comma list of
  countries), `v1_N.NATION.AB.<CITY|SILO|BOMBER|SUBMARINE>.m5/m10/m15` and `v1_N.NATION.points`
  (arsenal budget), and `v1_defcon`. It is written by the round-summary screen (`gtw/c/Caf`) at the
  end of each DEFCON round and read by "Continue game" (`gtw/c/Cap`).
- **Now** (`gtw/saves/SaveSlots.java`): slots 1-4 plus an autosave, all in the same file with the
  same key format.
  - **Slot 1** is the original key set, unchanged, so an existing save appears as slot 1 and the
    original game can still read it.
  - **Slots 2-4** use prefix `v1_s2_` ... `v1_s4_`; the **autosave** uses `v1_auto_`.
  - Each save also gets `save.time` (epoch ms) and `save.level` (difficulty).
- **New game:** pick a slot (`gtw/c/SlotScreen`), then the original nation/opponent screens.
  A used slot is overwritten as soon as the first round starts (*Start DEFCON*).
- **Continue game:** lists the saves that exist (autosave first), e.g.
  `Slot 2: USA vs CHINA  DEFCON 3  HARD  2026-10-17 14:05`. Original saves show `(older save)`.
  With no saves at all it goes to New game, as before.
- **When saves are written:** the game's slot **and** the autosave, both when *Start DEFCON* is
  pressed (round setup, so a new game is in its slot right away and survives the app being
  killed mid-battle) and at the end of every round (the original save point).
  - A game continued from the autosave keeps saving only to the autosave.

### Difficulty (Easy / Normal / Hard)
- **Original:** a global EASY/HARD toggle in Settings (pref `v1_level`, default EASY). Read through
  `gtw.Cb.a()` in two places:
  - human launches per DEFCON round (`gtw/a/Cf.b()`): EASY 8, HARD 5;
  - AI target order (`gtw/a/Cg.d()`): EASY shuffles the AI's attack list (random targets), HARD
    keeps it sorted nearest-first.
  - The AI's own launch budget (4, plus 2 per allied country) and its arsenal split are not tied to the level.
- **Now:**

  | Level | Your launches per round (+2 per allied country) | AI target choice |
  |-------|------|------|
  | Easy (original) | 8 | random order |
  | Normal (new) | 6 | nearest first, each distance jittered by ±35 % |
  | Hard (original) | 5 | strictly nearest first |

  - Choose the level on the New game slot screen; Settings cycles the default.
  - The level is stored in the save (`save.level`) and restored on Continue. Original saves use
    the Settings level, as they did before.

### More playable sides: Europe, Africa, South America

The original game has three sides (`a/Ci.Ia`: USA, RUSSIA, CHINA). Everything that differed per
side was hardcoded in about ten switch statements and if-chains: border, unit and HUD colours, bomber
model, home region, the alliance table and the pick-screen previews. All of these now go through
`gtw/nations/Nations.java`, and the pick screens share `gtw/nations/NationPicker.java`.

**How a side is modelled.** A side is one *home region* of the map (`b/Cf.Ib`; outline and
unit-placement rects in `assets/world.svg`) plus an alliance table (`b/e/Cc`: which other regions
it may ally with at which DEFCON). A region's profile is (silos, submarines, population/cities),
e.g. USA/Russia/China (30, 26, 6). Cities, silos and subs are deployed on a 48-unit grid inside
the region's rects, and the AI (`a/Ca`, `b/e/Ca`) is side-agnostic.

**Added sides.** Each uses a region that already has a real outline in world.svg:

| Side | Home region (as drawn on the map) | Profile | Real cities |
|---|---|---|---|
| EUROPE | `europe`: Cold War European NATO (UK, Iceland, Norway, Denmark, Benelux, West Germany, France, Italy, Portugal, Spain, Greece, Turkey). Non-NATO Europe is `easteurope`. | 30/26/6 | London, Paris, Madrid, Lisbon, Rome, Bonn |
| AFRICA | `africa`: central and southern Africa below ~5°N, plus Madagascar | 30/26/6 | Kinshasa, Lubumbashi, Nairobi, Dar es Salaam, Johannesburg, Cape Town |
| SOUTH AMERICA | `brazil` + `chile` (whole continent; chile now has an outline for non-Brazil SA) | 30/26/6 on brazil (+ chile's ordinary stats) | São Paulo, Buenos Aires, Santiago, Lima, Bogotá, Caracas |

* **Arsenal.** When a new side owns its own home region it gets the superpower profile (30, 26, 6),
  the same as USA/Russia/China. As somebody else's ally the region keeps its original, smaller
  profile, so games between the original three are unchanged.
* **Alliances.** New sides may ally with every non-superpower region except their own home from
  DEFCON 0, and with USA, Russia and China from DEFCON 9 (the original sides' thresholds).
  `sahara`, `egipt`, `chile`, etc. stay ordinary alliable countries, like Canada/Mexico for the USA.
* **Cities are approximate.** The original sides place cities randomly on the grid. The new sides
  place their six cities at real positions, projected with a least-squares fit of the map
  (x = 1440.5 + 8.069·lon, z = 963.7 − 10.854·lat; typical error about 1°, worst around 2–3°).
  Coastal cities were nudged up to 12 map units inland when the rough coastline put them in the
  sea. `NationsTest` checks every city against the real outline in world.svg.
* **Looks.** Colours are violet (Europe), yellow (Africa) and pink (South America). There are only
  three bomber models, so new sides reuse one (Europe: US, Africa: Russian, South America: Chinese),
  tinted in the side colour.
* **Pick screens** show all six outlines in two rows of three, with two rows of buttons. The
  opponent screen leaves the player's own slot empty.
* **Not changed.** The tutorial (USA only) is unchanged. Saves store the side's `name()`
  (e.g. `SOUTH_AMERICA`), so a save that uses a new side can't be opened by older builds.

### Battle speed (1x / 2x / 4x)
The running HUD has a toggle next to Pause that cycles 1x → 2x → 4x → 1x. `BattlePause.frame()`
multiplies elapsed battle time (and AI turns) by that factor; while paused the multiplier is
ignored and the sim stays frozen. A new battle restores the default speed from Settings
(1x until changed). `BattlePauseTest` covers 1x/2x/4x, pause-at-4x, cycling, and start() reset.

### Sound and settings
The Settings screen (already home to Sound on/off and the default difficulty) now also has:

- **Volume: 25% / 50% / 75% / 100%** — scales the original play loudness (button click 0.45,
  impact 1.0). Sound off still mutes completely; the stored volume is kept for when sound
  is turned back on.
- **Speed: 1x / 2x / 4x** — the default battle-speed multiplier applied when a new match
  starts (`BattlePause.setDefaultSpeed` / `setSpeed` before the HUD is built, then
  `start()`). The in-battle HUD toggle still cycles the current match independently.

Prefs live in the existing `GTWGame` file under `v1_sound` (original boolean), `v1_volume`
(int percent) and `v1_speed` (int 1/2/4). `GameSettings` holds the values and the cycle
rules (JVM-tested); `Cp` loads them at first sound init and plays at `scaledVolume(base)`.

### Post-match stats
The end-of-round summary (`gtw/c/Caf`, shown when any side is defeated) now reads a
`MatchStats` snapshot per side instead of only remaining-loss MT. Every figure comes
from data the sim already stores:

- **Launches** — `Ci.f` / `Ci.a` (used / budget), the same pair `Ci.toString` prints
- **Casualties** — `Ci.j.f.b()` mln, the population-loss total the old "Population" row showed
- **Cities** — how many `b.Cd` units on that side have the sim's destroyed flag (`Ca.d`)
- **Silos / Submarines / Bombers** — `Ci.j.g/i/h.b()` MT, unchanged

There is no intercept counter in the sim, so the screen does not invent one.
`MatchStatsTest` builds a side, sets those fields, and checks the labels.

## Next steps
- Run it on a phone and compare behaviour with compat1.
- Give the game's own classes readable names (add to `classes.tiny` or use a jadx mapping).
- **Finish the libGDX Java swap** (blocked tonight by the ProGuard merge, not just class names):
  1. Using `decompile/classes.tiny`, map every obfuscated libGDX type/method/field the game
     calls onto the real 1.14.2 API (or keep a thin shim). ProGuard also merged and shrunk
     methods, so this is more than a rename.
  2. Replace `libs/gdx-1.7.0-invertbit.jar` with `com.badlogicgames.gdx:gdx:1.14.2` plus
     `gdx-backend-android` and `gdx-freetype`. The hand-written `backends.android.a.Cg`
     EGL fallback can be dropped if stock `GdxEglConfigChooser` already accepts RGBA8888.
  3. Then ship matching 1.14.2 `libgdx.so` for every ABI (already 16 KB-aligned, including
     x86_64). Until that Java move lands, x86_64 `libgdx.so` has to stay on 1.9.4 (4 KB
     LOAD alignment) so the 1.7.0 AndroidGL20 JNI still resolves.
- Play a match through to the summary screen on a phone (the new rows need a side to be defeated).
