#!/usr/bin/env python3
"""Rebuild the GTW II compat APK from input/original.apk.

Requires Python 3 and Java 17+, plus internet access on first run. Downloads
checksum-pinned Apktool, Uber APK Signer and libGDX 1.9.4 natives (Maven Central),
patches the original APK and signs it.

Signing:
  * Default: Uber APK Signer's embedded public debug key (same key as compat1,
    so CI builds install as an update over compat1).
  * Release key: set KEYSTORE_PATH, KEYSTORE_PASSWORD, KEY_ALIAS and optionally
    KEY_PASSWORD (defaults to KEYSTORE_PASSWORD).
Output: dist/GTW-II-1.5-compat.apk
"""
from pathlib import Path
import hashlib, json, os, shutil, subprocess, sys, urllib.request, zipfile

root = Path(__file__).resolve().parent
tools = root / 'tools'; tools.mkdir(exist_ok=True)
native = root / 'native'; native.mkdir(exist_ok=True)
cfg = json.loads((root / 'toolchain.json').read_text())

def sha256(p): return hashlib.sha256(p.read_bytes()).hexdigest()

def fetch(dest, info):
    if not dest.exists():
        print(f'Downloading {info["url"]}')
        urllib.request.urlretrieve(info['url'], dest)
    if sha256(dest) != info['sha256']:
        raise SystemExit(f'Checksum mismatch: {dest}')

for name, info in cfg['tools'].items(): fetch(tools / name, info)
for name, info in cfg['natives'].items(): fetch(native / name, info)

original = root / 'input/original.apk'
if sha256(original) != cfg['original_apk_sha256']:
    raise SystemExit('input/original.apk does not match the pinned original APK hash')

for jar in native.glob('*.jar'):
    with zipfile.ZipFile(jar) as z: z.extractall(tools / jar.stem)

def run(*args): subprocess.run([str(a) for a in args], cwd=root, check=True)

run('java', '-jar', tools / 'apktool.jar', 'd', 'input/original.apk', '-o', 'decoded', '-f')
run(sys.executable, root / 'patch/apply.py')
run('java', '-jar', tools / 'apktool.jar', 'b', 'decoded', '-o', 'GTW-II-compat-unsigned.apk')

shutil.rmtree(root / 'signed', ignore_errors=True)
sign = ['java', '-jar', tools / 'signer.jar', '--apks', 'GTW-II-compat-unsigned.apk', '--out', 'signed']
ks = os.environ.get('KEYSTORE_PATH')
if ks:
    pw = os.environ['KEYSTORE_PASSWORD']
    sign += ['--ks', ks, '--ksAlias', os.environ['KEY_ALIAS'], '--ksPass', pw,
             '--ksKeyPass', os.environ.get('KEY_PASSWORD') or pw]
    print('Signing with provided keystore')
else:
    print('Signing with the embedded public debug key')
run(*sign)
out = sorted((root / 'signed').glob('*.apk'))
assert out, 'Signer did not produce an APK'
dist = root / 'dist'; dist.mkdir(exist_ok=True)
final = dist / 'GTW-II-1.5-compat.apk'
shutil.copyfile(out[0], final)
print(f'Built {final} sha256={sha256(final)}')
print('This is not a runtime test; install on a test device next.')
