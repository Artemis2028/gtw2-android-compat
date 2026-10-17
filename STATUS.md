# Status — 2026-10-17

State: **WORKS ON A REAL PHONE.** compat1 installs and plays on a real Android phone (Xiaomi), confirmed by the user on 2026-10-17. Full checklist below is not yet completed.

## Runtime results
- **Real device (Xiaomi, arm64):** installs, launches, and plays. The phone's security scanner flagged it as grayware (debug signature heuristic, see README); installing through the warning worked.
- **x86_64 emulator, software rendering (no GPU, `-accel off`):** installs and launches with no crash; the 64-bit libGDX 1.9.4 natives load; the title and player-select screens render. After starting a match (USA vs Russia) the screen stayed black for 6+ minutes while the process kept running. The black screen did not happen on the real phone, so it is most likely the emulator's software renderer, not the APK.
- Not yet checked on a device: tutorial, AI turns through a full match, sound, pause/background/resume, save/reload, and Android 17 / 16 KiB page-size devices.

## Earlier checkpoint (build-only)

- Original engine identified as libGDX 1.7.0 by exact x86 binary matches for both native libraries.
- ARM64/x86_64 compatibility candidate built using 1.9.4 native libraries.
- Build, signing, ARM64 exported-symbol coverage, and ARM64 ELF alignment checks pass.
- Packaged rebuild script executed successfully from the supplied original APK and patches.
- Android 16/API 36 software emulator attempted. No /dev/kvm acceleration is available. Emulator hung/exited during boot; installation and gameplay tests were NOT reached. This is an environment failure, not evidence of an APK crash.
- Android 17/API 37, ARM64 devices, and 16 KiB runtime execution have NOT been tested.

## Next work
1. Install GTW-II-1.5-compat1.apk on a modern ARM64 Android phone or a working test emulator. Record Android version/API, CPU ABI, and page size.
2. Confirm title screen, tutorial, new match, map rendering, unit selection/orders, AI turns and match progression, sound, pause/background/resume, and saving/reloading where the original provides it.
3. Record screenshots and crash logs if anything fails. Diagnose those results before making more compatibility changes.
4. Test at least one actual ARM64 device on the latest stable Android and a 16 KiB environment before declaring full modern-device compatibility.
5. A newer targetSdk is separate work: this candidate deliberately uses 28 for legacy behavior and is for sideloading, not Play Store publishing.

Do not retry the same non-accelerated emulator indefinitely. Do not mark runtime checks passed based on a successful build. Resume from this archive; local SDK/emulator installations are disposable and not included.

## Follow-up checkpoint — 2026-10-17
- Compared all 268 native method declarations in six upstream libGDX Java source files, 1.7.0 versus 1.9.4. Return types, names, and argument type lists match; no added or removed declarations in those files. This augments exported-symbol checks, not runtime evidence.
- Added checksum-pinned source verification (`verification/compare-native.py`) and a device evidence collector (`test-device.py`). The collector's command-line help was checked; it has NOT been exercised against a device here.
- APK bytes unchanged. No emulator retry: boot environment remains blocked.
- Next required input: a working authorized Android device/emulator, or results from installing and testing the included APK. Report phone model, Android version, and the first failure encountered. With a computer and adb, run `python3 test-device.py --serial DEVICE_SERIAL` and return report.json and relevant logs. Inspect screenshots before sharing.
- No recurring task has been scheduled. Work is paused at this device-access blocker.
