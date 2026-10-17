#!/usr/bin/env bash
# Regenerates the decompiled game sources and the prebuilt (obfuscated) libGDX jar
# from ../input/original.apk. Normally you do NOT need to run this: the output is committed.
#
# Needs: java 17+, python3, unzip, curl. Tools are downloaded to decompile/.tools/.
#   jadx 1.5.3, dex2jar 2.4, apktool 2.12.1.
#
# Pipeline:
#  1. List classes in classes.dex; make_class_map.py renames every obfuscated short class
#     name (a, b, ab...) to C<name> (top level) / I<name> (nested) so Java source can
#     express them (obfuscated code has class/package and class/field name collisions).
#     smali_rename.py applies it to the smali, which apktool reassembles into renamed.apk.
#  2. jadx decompiles renamed.apk; com.invertbit.* -> core/ sources.
#  3. dex2jar turns the renamed original libGDX 1.7.0 bytecode into
#     libs/gdx-1.7.0-invertbit.jar (the game code is compiled against it).
#  4. decompile/fixes.py applies the hand fixes needed for javac.
#  5. decompile/drop_bad_casts.py removes casts jadx inferred wrongly (javac-error driven).
set -euo pipefail
HERE=$(cd "$(dirname "$0")/.." && pwd)
APK="$HERE/../input/original.apk"
T="$HERE/decompile/.tools"; mkdir -p "$T"
get() { [ -f "$T/$2" ] || curl -sfL -o "$T/$2" "$1"; }
get https://github.com/skylot/jadx/releases/download/v1.5.3/jadx-1.5.3.zip jadx.zip
get https://github.com/pxb1988/dex2jar/releases/download/v2.4/dex-tools-v2.4.zip d2j.zip
get https://github.com/iBotPeaches/Apktool/releases/download/v2.12.1/apktool_2.12.1.jar apktool.jar
[ -d "$T/jadx" ] || unzip -qo "$T/jadx.zip" -d "$T/jadx"
[ -d "$T/d2j" ] || unzip -qo "$T/d2j.zip" -d "$T/d2j"
W=$(mktemp -d)
java -jar "$T/apktool.jar" d -r -f "$APK" -o "$W/apk" >/dev/null
(cd "$W/apk/smali" && find . -name '*.smali' | sed 's#^\./##; s#\.smali$##' | sort) > "$W/classes.txt"
python3 "$HERE/decompile/make_class_map.py" "$W/classes.txt" "$HERE/decompile/classes.tiny"
python3 "$HERE/decompile/smali_rename.py" "$W/apk/smali" "$HERE/decompile/classes.tiny"
java -jar "$T/apktool.jar" b "$W/apk" -o "$W/renamed.apk" >/dev/null

"$T/jadx/bin/jadx" -d "$W/jadx" --show-bad-code --no-imports -j 8 \
  "$W/renamed.apk" >/dev/null || true   # jadx exits non-zero on warnings in ad SDK code
C=$HERE/core/src/main/java
rm -rf "$C/com" "$HERE/core/src/main/resources/com"
mkdir -p "$C/com" "$HERE/core/src/main/resources" "$HERE/android/src/main/assets"
cp -r "$W/jadx/sources/com/invertbit" "$C/com/"
rm -rf "$C/com/invertbit/games/gtw/android"   # hand-written launcher lives in android/
cp -r "$W/jadx/resources/com" "$HERE/core/src/main/resources/"   # libGDX classpath shaders/fonts
cp -r "$W/jadx/resources/assets/." "$HERE/android/src/main/assets/"

D2J=$(find "$T/d2j" -name d2j-dex2jar.sh | head -1); chmod +x "$(dirname "$D2J")"/*.sh
"$D2J" -f -o "$W/remapped.jar" "$W/renamed.apk" >/dev/null 2>&1
mkdir -p "$HERE/libs"
python3 - "$W/remapped.jar" "$HERE/libs/gdx-1.7.0-invertbit.jar" <<'PY'
import sys, zipfile
src, dst = sys.argv[1:]
with zipfile.ZipFile(src) as zi, zipfile.ZipFile(dst, 'w', zipfile.ZIP_DEFLATED) as zo:
    for n in sorted(zi.namelist()):
        if n.startswith('com/badlogic/') and n.endswith('.class'):
            zo.writestr(zipfile.ZipInfo(n, (1980, 1, 1, 0, 0, 0)), zi.read(n))
PY
python3 "$HERE/decompile/fixes.py"
# 5. Drop jadx's wrongly-typed generic casts, driven by javac errors (a few rounds).
for i in 1 2 3 4; do
  (cd "$HERE" && ./gradlew -q --console=plain :core:compileJava > "$W/javac.log" 2>&1) && break
  python3 "$HERE/decompile/drop_bad_casts.py" "$W/javac.log" | grep -q 'removed 0 ' && break
done
rm -rf "$W"
echo "Imported."
