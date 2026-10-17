#!/usr/bin/env python3
"""usage: check_constdiff.py <apkr2/smali dir> <apktool -r decoded debug apk dir>
Lists literal constants present in the original class but missing from the build (catches
branches/switch cases jadx dropped). String concatenations folded by javac show up as noise."""
import re, os, collections, sys
cre = re.compile(r'^\s*const(?:/4|/16|/high16|-wide/16|-wide/32|-wide/high16|-wide)? [vp]\d+, (-?0x[0-9a-fA-F]+L?)', re.M)
sre = re.compile(r'const-string(?:/jumbo)? [vp]\d+, (".*")$', re.M)
def norm(h):
    return h
def collect(root):
    res = {}
    for dp, dn, fn in os.walk(root):
        for f in fn:
            if not f.endswith('.smali'): continue
            txt = open(os.path.join(dp, f)).read()
            cls = re.search(r'^\.class[^\n]* (L[^;]+;)', txt, re.M).group(1)
            if not cls.startswith('Lcom/invertbit/games/') or '/android/' in cls: continue
            c = collections.Counter()
            for line in txt.splitlines():
                m = re.match(r'\s*const(/4|/16|/high16|-wide/16|-wide/32|-wide/high16|-wide|)\s+[vp]\d+, (-?0x[0-9a-fA-F]+)L?', line)
                if m:
                    suf, v = m.groups(); v = int(v, 16)
                    if 'high16' in suf: v <<= (16 if 'wide' not in suf else 48)
                    if v in (0, 1, -1): continue
                    c['n%d' % v] += 1
            for s in sre.findall(txt): c['s' + s] += 1
            res[cls] = c
    return res
orig = collect(sys.argv[1])
ours = {}
for d in os.listdir(sys.argv[2]):
    if d.startswith('smali'): ours.update(collect(os.path.join(sys.argv[2], d)))
for cls in sorted(orig):
    if cls not in ours: continue
    a = set(orig[cls]); b = set(ours[cls])
    lost = sorted(a - b)
    if lost:
        print('==', cls, 'lost:', lost[:12], ('...+%d' % (len(lost)-12)) if len(lost) > 12 else '')
