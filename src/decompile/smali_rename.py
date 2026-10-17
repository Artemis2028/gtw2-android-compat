#!/usr/bin/env python3
"""Normalise the original APK's smali so the game code can be decompiled to Java that javac accepts,
and so the libGDX bytecode can be compiled against. All changes are names/flags only.

 1. Rename obfuscated classes per decompile/classes.tiny (a -> Ca, nested a$b -> Ca$Ib).
 2. Rename obfuscated enum constants back to their declared names (Pixmap.Format.a -> RGBA8888),
    taken from the enum's own <clinit>.
 3. ProGuard left ACC_VARARGS on methods whose last parameter is not an array: drop it.
 4. ProGuard folded some real methods into their synthetic bridges (e.g. Vector3.scl(float)):
    a synthetic method with no non-synthetic twin (same name+params) loses synthetic/bridge.
 5. Game code (com/invertbit): private fields become package-private, because jadx inlines the
    synthetic access$ accessors that inner classes used.
 6. String literals naming a renamed class (libGDX loads AndroidInputThreePlus, AndroidAudio...
    via Class.forName) are rewritten too.
"""
import sys, re
from pathlib import Path
smali_dir, mapfile = Path(sys.argv[1]), sys.argv[2]
m = {}
for l in open(mapfile).read().splitlines()[1:]:
    _, o, n = l.split('\t'); m[o] = n
OURS = ('com/badlogic/', 'com/invertbit/')
desc = re.compile(r'L(com/(?:badlogic|invertbit)/[\w/$]+)(?=[;<"])')
cls_re = re.compile(r'^\.class .*?L([\w/$]+);', re.M)
meth_re = re.compile(r'^\.method((?: [\w-]+)*) ([^\s(]+)\(([^)]*)\)(\S+)$', re.M)
param_re = re.compile(r'\[*(?:L[^;]+;|[ZBSCIJFD])')

files = {}
for p in smali_dir.rglob('*.smali'):
    s = p.read_text(); files[p] = (cls_re.search(s).group(1), s)

# pass 1: class renames
for p, (me, s) in list(files.items()):
    t = desc.sub(lambda x: 'L' + m.get(x.group(1), x.group(1)), s)
    t = re.sub(r'(const-string(?:/jumbo)? \w+, )"(com\.(?:badlogic|invertbit)\.[\w.$]+)"',
               lambda x: x.group(1) + '"' + m.get(x.group(2).replace('.', '/'), x.group(2).replace('.', '/')).replace('/', '.') + '"', t)
    if me in m and '$' in me:
        new_simple, old_simple = m[me].rsplit('$', 1)[1], me.rsplit('$', 1)[1]
        t = re.sub(r'(\.annotation system Ldalvik/annotation/InnerClass;.*?name = )"' + re.escape(old_simple) + '"',
                   lambda x: x.group(1) + '"' + new_simple + '"', t, count=1, flags=re.S)
    files[p] = (m.get(me, me), t)

# pass 2: enum constant names
field_ren = {}  # (cls, old, type) -> new
for p, (me, s) in files.items():
    if not me.startswith(OURS) or not re.search(r'^\.class .*\benum\b', s, re.M): continue
    clinit = re.search(r'\.method static constructor <clinit>\(\)V(.*?)\.end method', s, re.S)
    if not clinit: continue
    body = clinit.group(1); strings = {}
    for line in body.splitlines():
        line = line.strip()
        mo = re.match(r'const-string(?:/jumbo)? (\w+), "(.*)"', line)
        if mo: strings[mo.group(1)] = mo.group(2); continue
        mo = re.match(r'invoke-direct(?:/range)? \{(\w+), (\w+)[,}].*L' + re.escape(me) + r';-><init>\(Ljava/lang/String;I', line)
        if mo and mo.group(2) in strings: strings['obj:' + mo.group(1)] = strings[mo.group(2)]; continue
        mo = re.match(r'sput-object (\w+), L' + re.escape(me) + r';->([\w$]+):L' + re.escape(me) + ';', line)
        if mo and 'obj:' + mo.group(1) in strings:
            name = strings['obj:' + mo.group(1)]
            if re.fullmatch(r'[A-Za-z_$][\w$]*', name) and name != mo.group(2):
                field_ren[(me, mo.group(2))] = name
by_cls = {}
for (c, old), new in field_ren.items(): by_cls.setdefault(c, {})[old] = new
for p, (me, s) in list(files.items()):
    def fref(x):
        c, f = x.group(1), x.group(2)
        return 'L%s;->%s:L%s;' % (c, by_cls.get(c, {}).get(f, f), x.group(3))
    t = re.sub(r'L([\w/$]+);->([\w$]+):L([\w/$]+);', lambda x: fref(x) if x.group(1) == x.group(3) else x.group(0), s)
    if me in by_cls:
        t = re.sub(r'^(\.field public static final enum )([\w$]+):', lambda x: x.group(1) + by_cls[me].get(x.group(2), x.group(2)) + ':', t, flags=re.M)
    files[p] = (me, t)

# pass 3+4+5
for p, (me, s) in list(files.items()):
    if not me.startswith(OURS): continue
    methods = meth_re.findall(s)
    real = {(n, a) for fl, n, a, r in methods if ' synthetic' not in fl}
    def fixm(mo):
        flags, name, args, ret = mo.groups()
        params = param_re.findall(args)
        if ' varargs' in flags and not (params and params[-1].startswith('[')):
            flags = flags.replace(' varargs', '')
        if ' synthetic' in flags and not name.startswith(('access$', '<')) and (name, args) not in real:
            flags = flags.replace(' synthetic', '').replace(' bridge', '')
        return '.method%s %s(%s)%s' % (flags, name, args, ret)
    t = meth_re.sub(fixm, s)
    if me.startswith('com/invertbit/'):
        t = re.sub(r'^(\.field) private ', r'\1 ', t, flags=re.M)
    files[p] = (me, t)

n = 0
for p, (me, t) in files.items():
    if p.read_text() != t: p.write_text(t); n += 1
print('rewrote', n, 'smali files;', len(field_ren), 'enum constants renamed')
