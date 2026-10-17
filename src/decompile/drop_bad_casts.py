#!/usr/bin/env python3
"""Error-driven fixer: remove casts that jadx inserted with the wrong type (generic method
arguments). Reads javac output, finds 'incompatible types: X cannot be converted to Y' errors
whose caret sits on an expression preceded by a '(…Y) ' cast, and deletes that cast."""
import re, sys
from pathlib import Path
log = Path(sys.argv[1]).read_text().splitlines()
edits = {}
for i, line in enumerate(log):
    mo = re.match(r'(/\S+\.java):(\d+): error: incompatible types: .* cannot be converted to (\S+)$', line)
    if not mo or i + 2 >= len(log): continue
    path, ln, target = mo.group(1), int(mo.group(2)), mo.group(3)
    col = log[i + 2].index('^')
    edits.setdefault(path, []).append((ln, col, target.split('<')[0].split('.')[-1]))
n = 0
for path, es in edits.items():
    lines = Path(path).read_text().split('\n')
    for ln, col, simple in sorted(es, key=lambda e: (e[0], -e[1])):
        s = lines[ln - 1]
        before = s[:col]
        mo = re.search(r'\(([\w.$]+(?:<.*?>)?)\) ?(?=(?:this|[\w$]+)?$)', before)
        if mo and mo.group(1).split('<')[0].split('.')[-1] == simple:
            lines[ln - 1] = before[:mo.start()] + before[mo.end():] + s[col:]; n += 1
    Path(path).write_text('\n'.join(lines))
print('removed', n, 'casts')
