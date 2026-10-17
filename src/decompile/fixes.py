#!/usr/bin/env python3
"""Manual fixes for jadx output that does not compile. Run after import.sh. Idempotent."""
from pathlib import Path
ROOT = Path(__file__).resolve().parents[1]
FIXES = []
def fix(path, old, new, count=1):
    FIXES.append((path, old, new, count))

# ProGuard stripped the (unused) constructor of this subclass; javac needs one.
fix('core/src/main/java/com/invertbit/games/gtw/b/Ce.java',
    'public class Ce extends com.invertbit.games.gtw.b.Ca {\n',
    'public class Ce extends com.invertbit.games.gtw.b.Ca {\n    private Ce() {\n        super(null); // never instantiated in the original game\n    }\n\n')

# Vector2/Vector3.scl(float) only survives as the bridge returning the Vector interface (Cj).
V2 = 'com.badlogic.gdx.math.Ck'; V3 = 'com.badlogic.gdx.math.Cl'
fix('core/src/main/java/com/invertbit/games/gtw/b/d/Cm.java',
    V2 + ' ckB = ck.a(ck3)', V2 + ' ckB = (' + V2 + ') ck.a(ck3)')
fix('core/src/main/java/com/invertbit/games/gtw/b/d/Cd.java',
    V3 + ' clB = cl2.a(cc2.h)', V3 + ' clB = (' + V3 + ') cl2.a(cc2.h)')

# Vector3.limit(): jadx lost the len2 temporary.
fix('core/src/main/java/com/invertbit/games/b/a/Ca.java',
    '            if (clB.b() > f) {\n                clB.b((float) java.lang.Math.sqrt(f / r2));',
    '            float r2 = clB.b();\n            if (r2 > f) {\n                clB.b((float) java.lang.Math.sqrt(f / r2));')

# jadx lost a temporary (list.get(list.size() - 1)).
fix('core/src/main/java/com/invertbit/games/c/a/Cb.java',
    'com.invertbit.games.c.a.a.Cb cb = this.a.c.a().get(r0.a().size() - 1);',
    'java.util.List<com.invertbit.games.c.a.a.Cb> r0 = this.a.c.a();\n        com.invertbit.games.c.a.a.Cb cb = r0.get(r0.size() - 1);')

# The bytecode throws IOException without declaring it; javac needs it handled.
fix('core/src/main/java/com/invertbit/games/gtw/c/d/Ca.java',
    '            com.invertbit.games.gtw.c.d.Cd.a = new com.invertbit.games.gtw.c.d.Cd();\n',
    '            try {\n                com.invertbit.games.gtw.c.d.Cd.a = new com.invertbit.games.gtw.c.d.Cd();\n            } catch (java.io.IOException e) {\n                throw new java.io.UncheckedIOException(e);\n            }\n')

# SVG loader: checked exceptions undeclared in bytecode (callers catch Exception).
fix('core/src/main/java/com/invertbit/games/c/a/Ca.java',
    'public final com.invertbit.games.c.a.a.Cd a(java.lang.String str) {',
    'public final com.invertbit.games.c.a.a.Cd a(java.lang.String str) throws java.lang.Exception {')

# jadx dropped the loop exit of the mesh-building loop (bytecode: if-ge i, size -> next
# outer iteration). Without it the loading screen spins forever on the GL thread.
fix('core/src/main/java/com/invertbit/games/b/a/Ca.java',
    '                    i3 = i4 + 1;\n                }\n            }\n        }\n        return this.b.b();',
    '                    i3 = i4 + 1;\n                } else {\n                    break;\n                }\n            }\n        }\n        return this.b.b();')

def main():
    bad = 0
    for path, old, new, count in FIXES:
        p = ROOT / path; s = p.read_text()
        if new in s: continue
        n = s.count(old)
        if n != count:
            print(f'FAILED ({n} matches): {path}: {old[:60]!r}'); bad += 1; continue
        p.write_text(s.replace(old, new))
    print(f'{len(FIXES) - bad}/{len(FIXES)} fixes applied')
    raise SystemExit(1 if bad else 0)
if __name__ == '__main__': main()
