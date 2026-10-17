#!/usr/bin/env python3
"""Extract the public debug keystore that the compat build signs with.

It is the debug.keystore embedded in Uber APK Signer 1.3.0 (alias androiddebugkey,
password "android", certificate SHA-256 1e08a903...5953), fetched from the same
checksum-pinned URL as rebuild.py (../../toolchain.json). The keystore is not committed;
it lands in src/signing/debug.keystore (git-ignored) and android/build.gradle uses it.
"""
import hashlib, json, pathlib, urllib.request, zipfile, io

HERE = pathlib.Path(__file__).resolve().parent
KS = HERE / 'debug.keystore'
KS_SHA256 = '161a0018f33277e842534f5443fdd36683359b4950f4dc006c1648895cb073b2'

def main():
    if KS.exists() and hashlib.sha256(KS.read_bytes()).hexdigest() == KS_SHA256:
        print(f'{KS} already present'); return
    tool = json.loads((HERE.parents[1] / 'toolchain.json').read_text())['tools']['signer.jar']
    local = HERE.parents[1] / 'tools' / 'signer.jar'
    data = local.read_bytes() if local.exists() else urllib.request.urlopen(tool['url']).read()
    got = hashlib.sha256(data).hexdigest()
    if got != tool['sha256']:
        raise SystemExit(f'signer.jar sha256 mismatch: {got} != {tool["sha256"]}')
    ks = zipfile.ZipFile(io.BytesIO(data)).read('debug.keystore')
    if hashlib.sha256(ks).hexdigest() != KS_SHA256:
        raise SystemExit('embedded debug.keystore has an unexpected checksum')
    KS.write_bytes(ks)
    print(f'wrote {KS}')

if __name__ == '__main__':
    main()
