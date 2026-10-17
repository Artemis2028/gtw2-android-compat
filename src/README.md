# GTW II — decompiled, buildable source (branch `decompiled`)

Gradle project rebuilt from the original APK (`../original/GTW-II-1.5.apk`, GTW II 1.5) so new
features can be added in Java.

```sh
cd src
./gradlew assembleDebug        # -> android/build/outputs/apk/debug/android-debug.apk
```
Needs JDK 17+ and an Android SDK (`ANDROID_HOME` or `local.properties`). The debug APK
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
(libGDX 1.7.0); 64-bit (`arm64-v8a`, `x86_64`) come from Maven Central libGDX 1.9.4, same as
the compat build.

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

## Why libGDX is not a Maven dependency (yet)
The game calls libGDX through ProGuard-obfuscated names (`com.badlogic.gdx.utils.a` is `Array`,
and so on), and ProGuard also merged and shrank libGDX methods. Swapping in stock
`com.badlogicgames.gdx:gdx:1.9.x` means mapping every libGDX class, method and field the game uses
back to its real name and rewriting those call sites. That work is not done yet. Until then, the
game compiles against the exact libGDX bytecode it shipped with, which keeps its behaviour
identical. See "Next steps".

## How it was produced (`decompile/import.sh`)
1. apktool decodes the smali. `smali_rename.py` renames classes, restores enum-constant names,
   clears bogus `varargs` flags left by ProGuard, un-hides ProGuard-merged bridge methods
   (`Vector2/3.scl(float)`), and makes the game's private fields package-private (jadx inlines
   the `access$` accessors). Only names and flags change. apktool reassembles the result.
2. jadx 1.5.3 (`--no-imports`) decompiles the game code into `core/`.
3. dex2jar 2.4 turns the renamed libGDX bytecode into `libs/gdx-1.7.0-invertbit.jar`.
4. `fixes.py` applies 7 hand fixes (below). `drop_bad_casts.py` then removes about 65 casts that jadx
   inferred wrongly on generic libGDX calls (mostly `Table.add(actor)`), guided by javac errors.

### Hand fixes (`decompile/fixes.py`)
- `gtw/b/Ce`: ProGuard stripped the unused constructor. Added a private one so javac accepts it.
- `gtw/b/d/Cm`, `gtw/b/d/Cd`: `Vector2/3.scl(float)` survives only as the `Vector` interface bridge. Added a result cast.
- `b/a/Ca` (`Vector3.limit` inline): jadx lost the `len2` temporary (`r2`).
- `c/a/Cb` (SVG title handler): jadx lost the `List` temporary in `list.get(list.size()-1)`.
- `gtw/c/d/Ca`: checked `IOException` from an undeclared constructor, now wrapped in `UncheckedIOException`.
- `c/a/Ca` (SVG loader): declared `throws Exception`. Callers already catch it.

No game classes were lost. All 141 game source files decompiled cleanly enough to compile (the original launcher was replaced by hand). The one jadx warning is a
type-inference note in `gtw/b/e/Ch` (code is present). The ad SDK (`com.google.android.gms`,
`com.google.ads`) and `android.support` are dropped. The launcher that wired the ads was rewritten.

## Status
- `./gradlew assembleDebug` builds.
- Not yet run on a device or emulator. The decompiled build needs the same check as compat1
  (title screen, match start, map, AI turns, saves).

## Next steps
- Run it on a phone and compare behaviour with compat1.
- Give the game's own classes readable names (add to `classes.tiny` or use a jadx mapping).
- Map the bundled libGDX to real names, then move to stock libGDX 1.9.x or later from Maven, with matching natives on every ABI.
