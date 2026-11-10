# GTW II — libGDX 1.14.2 port

A clean rewrite of the decompiled `src/` game against stock Maven libGDX **1.14.2**
(`gdx`, `gdx-backend-android`, `gdx-backend-lwjgl3`, `gdx-freetype`) and 16 KB-aligned
natives for armeabi-v7a, arm64-v8a, x86 and x86_64. The existing `src/` Gradle build
is untouched.

```
cd port
./gradlew :core:test assembleDebug     # -> android/build/outputs/apk/debug/android-debug.apk
./gradlew :lwjgl3:run                  # desktop, optional
```

Package: `com.invertbit.games.gtw.android.port`, labelled **GTW II (Port)**. Signed
with the same public debug key as the source/compat builds (cert SHA-256
`1e08a903…5953`) via `../src/signing/debug.keystore`.

The Android module uses AGP **8.9.1** and Gradle **8.11.1** because
`gdx-backend-android:1.14.2` depends on AndroidX Core 1.17.

Assets (`world.svg`, fonts, sounds, …) are reused from
`../src/android/src/main/assets` — not copied.

## Milestones
- **M1 (this directory's first playable cut):** project builds, scene2d menu renders,
  world map draws from the original `world.svg` parser with side colours
  (USA blue, Russia orange, China green, Europe violet, Africa yellow,
  South America pink; Alaska painted as USA, Chile as South America).
- **M2:** match setup + battle sim (launches, cities, DEFCON, alliances/`RoundSetup`)
  with a scene2d HUD, `BattlePause`, 1x/2x/4x speed and AI-off. DEFCON countdown
  is 5 seconds (as in `Caj`). Missile flight time is `distance * (yield/2) / 100`
  seconds (as in `b.d.Cm`). Launch budget is concurrent in-flight missiles
  (human 8/6/5 by EASY/NORMAL/HARD, AI 4, +2 per ally). Silo/sub ammo is 10 and
  bomber ammo is 1, matching `b.b.Ca`. Setup defaults DEFCON to 5 (the original
  picker's default is not obvious from the decompile).
- **M3:** settings, save slots/autosave, post-match `MatchStats`, sound; parity
  against the decompiled `src/` build (nato11).

Game rules stay those of the decompiled source. Unclear behaviour is kept as-is
and noted rather than reinvented.
