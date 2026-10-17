#!/usr/bin/env python3
"""Compare game classes in the original (renamed) smali with a decoded build of this project.
usage: check_sigdiff.py <apkr2/smali dir> <apktool -r decoded debug apk dir>
Lists per-class differences in invoked method signatures (catches overload drift such as
Cell.width(float) -> Cell.colspan(int) from dropped int-to-float casts)."""
import re, os, collections, sys
inv = re.compile(r'invoke-\w+(?:/range)? \{[^}]*\}, (L[^;]+;->[^\s]+)')
def collect(root):
    res = {}
    for dp, dn, fn in os.walk(root):
        for f in fn:
            if not f.endswith('.smali'): continue
            txt = open(os.path.join(dp, f)).read()
            m = re.search(r'^\.class[^\n]* (L[^;]+;)', txt, re.M)
            cls = m.group(1)
            if not cls.startswith('Lcom/invertbit/games/') or '/android/' in cls: continue
            res[cls] = collections.Counter(x for x in inv.findall(txt) if 'access$' not in x and '$SwitchMap' not in x)
    return res
orig = collect(sys.argv[1])
ours = {}
for d in os.listdir(sys.argv[2]):
    if d.startswith('smali'): ours.update(collect(os.path.join(sys.argv[2], d)))
noise = re.compile(r'Ljava/lang/(StringBuilder|Integer;->valueOf|Float;->valueOf|Object;-><init>|Integer;->intValue|Float;->floatValue|String;->valueOf)|Ljava/util/Objects|requireNonNull|Ljava/lang/Enum;-><init>|values\(\)|clone\(\)|ordinal')
for cls in sorted(orig):
    if cls not in ours: print('MISSING', cls); continue
    a, b = orig[cls], ours[cls]
    only_o = {k:v for k,v in (a-b).items() if not noise.search(k)}
    only_n = {k:v for k,v in (b-a).items() if not noise.search(k)}
    if only_o or only_n:
        print('==', cls)
        for k,v in only_o.items(): print('   orig only', v, k)
        for k,v in only_n.items(): print('   ours only', v, k)
