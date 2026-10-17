# GTW II 1.5 — Android compatibility build

[![Build compat APK](../../actions/workflows/build.yml/badge.svg)](../../actions/workflows/build.yml)

Unofficial compatibility patch for *Global Thermonuclear War II* 1.5 by InvertBit (free, no longer maintained) so it installs and runs on modern 64-bit Android phones. Confirmed installing and playing on a real Xiaomi phone. This is a sideload build, not an official release.

**Download:** grab the `GTW-II-1.5-compat-apk` artifact from the latest green [Actions run](../../actions/workflows/build.yml), or an APK attached to a [Release](../../releases) (releases are built automatically when a `v*` tag is pushed). `GTW-II-1.5-compat1.apk` in the repo root is the original hand-built compat1 reference.

> **Xiaomi / MIUI / HyperOS security scanner:** it may flag this APK as grayware (e.g. a name ending in `a.gray`). That is a heuristic hit because this is a modified copy of a store app re-signed with a public debug/test key instead of the developer's key. The build requests no permissions (not even internet). Inspect the source here and decide for yourself; you can tap through the warning to install.

Install `GTW-II-1.5-compat1.apk`. The separate package `com.invertbit.games.gtw.android.compat`, labelled **GTW II (Compat)**, coexists with the original. Do not uninstall the original: this copy has its own saves and preferences and does not migrate old data.

## Changes
- Preserves original game bytecode and assets; patches the Android launcher and advertisement callback in smali rather than recompiling decompiled Java.
- Adds ARM64 and x86_64 libGDX/FreeType 1.9.4 libraries. Original 32-bit libraries remain.
- Original x86 libGDX and FreeType binaries both exactly match Maven libGDX 1.7.0. That version did not provide the tested ARM64 Maven artifacts. 1.9.4 is used as a compatibility candidate, not asserted to be behaviorally identical.
- ARM64 replacement libraries expose every original JNI export and use 64 KiB LOAD alignment (compatible with the 16 KiB alignment requirement). Symbol checks alone do not prove runtime compatibility.
- Removes old Google advertising startup, ad activities, ad callback behavior, ad SDK bytecode, and network permissions. The game's external developer/store links remain browser intents.
- Uses minSdk 21 and targetSdk 28 to retain legacy Android behavior for sideloading. This is **not** a migration to targetSdk 37 and is **not** a Play Store submission build. Modern Android may display an older-app warning.
- Explicit exported launcher; native libraries extracted at install time.

## Verification
- Apktool 2.12.1 build succeeds.
- APK alignment and v1/v2/v3 signatures verified with Uber APK Signer 1.3.0.
- ARM64 native export coverage and segment alignment pass; see logs.
- Emulator outcome and remaining checks: see STATUS.md.
- ARM64 device behavior and actual 16 KiB runtime behavior remain untested.

## Rebuild
Run `python3 rebuild.py` with Python 3, Java 17+, and internet access. It downloads checksum-pinned Apktool 2.12.1, Uber APK Signer 1.3.0 and the libGDX 1.9.4 arm64-v8a/x86_64 natives from Maven Central (all pinned in `toolchain.json`), verifies `input/original.apk` against its pinned hash, applies the patch, and writes `dist/GTW-II-1.5-compat.apk`.

### CI
`.github/workflows/build.yml` runs on pushes to `main`, pull requests, `v*` tags and manual dispatch. It rebuilds the APK, checks it with `zipalign -c` and `apksigner verify`, uploads it as the `GTW-II-1.5-compat-apk` artifact, and attaches it to a GitHub Release for tags. Rebuilds are not byte-identical to compat1 (Apktool/signing timestamps), but are functionally the same.

### Signing
- **Default (no secrets):** signed with Uber APK Signer's embedded *public* Android debug key (cert SHA-256 `1e08a903…5953`). This is the same key compat1 used, so CI builds install as updates over compat1. Anyone can sign with this key, so it proves nothing about who built the APK.
- **Your own key (recommended for ongoing releases):** add repository secrets `KEYSTORE_BASE64` (`base64 -w0 release.jks`), `KEYSTORE_PASSWORD`, `KEY_ALIAS`, and optionally `KEY_PASSWORD`. Never commit the keystore.
- **Changing keys breaks updates:** Android refuses to install an APK over an existing install signed with a different key. Switching from the debug key to your own key (or back) means uninstalling the existing *GTW II (Compat)* first, which deletes its saves. Pick a key once and keep it backed up.

The original developer's signing key is not available, so the compat build is always a separate package from the original.

## Provenance
Original APK SHA-256: b37f9de6e39d52df472c49ff5d987a20ad36569baf310067281ed37eafc3b21c.
Native binaries are from Maven Central `com.badlogicgames.gdx` artifacts (actual group path: `com/badlogicgames/gdx`), version 1.9.4: gdx-platform and gdx-freetype-platform, natives-arm64-v8a and natives-x86_64. libGDX: https://github.com/libgdx/libgdx (Apache 2.0); FreeType: https://freetype.org/license.html. Original game credits and assets remain in the APK; this is an unofficial modification.

Android guidance: https://developer.android.com/guide/practices/page-sizes

## Device test and evidence
On a computer with adb, connect an authorized Android test device, obtain its serial with `adb devices`, then run:

```sh
python3 test-device.py --serial DEVICE_SERIAL
```

This installs/replaces only the compatibility package, preserves its data, cold-starts it, and checks whether its process survives 15 seconds. It records device/API/ABI/page-size details, screenshot, and process-filtered logs. A running process does not prove rendering or gameplay. Perform the manual tests in STATUS.md separately. The script itself has not been exercised against a device in this environment.

Without a computer, extract the archive on your Android phone and install `GTW-II-1.5-compat1.apk`. Keep the original app installed. Report the phone model, Android version, whether the title screen opens, and any error message.

`python3 verification/compare-native.py` downloads checksum-pinned source archives and repeats the 268-declaration comparison. Its result does not prove native implementation equivalence or runtime compatibility.
