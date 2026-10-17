import zipfile,re,json
from pathlib import Path
from collections import Counter
import urllib.request
root=Path(__file__).resolve().parents[1]
cache=root/'verification/source-cache';cache.mkdir(exist_ok=True)
import hashlib
for name,info in json.loads((root/'verification/source-lock.json').read_text()).items():
 p=cache/name
 if not p.exists():urllib.request.urlretrieve(info['url'],p)
 assert hashlib.sha256(p.read_bytes()).hexdigest()==info['sha256'], f'Source checksum mismatch: {name}'

files={'gdx':['com/badlogic/gdx/math/Matrix4.java','com/badlogic/gdx/graphics/glutils/ETC1.java','com/badlogic/gdx/graphics/g2d/Gdx2DPixmap.java','com/badlogic/gdx/utils/BufferUtils.java'],'gdx-freetype':['com/badlogic/gdx/graphics/g2d/freetype/FreeType.java'],'gdx-backend-android':['com/badlogic/gdx/backends/android/AndroidGL20.java']}
def signatures(s):
 s=re.sub(r'/\*.*?\*/|//[^\n]*','',s,flags=re.S)
 out=[]
 for m in re.finditer(r'\bnative\s+([\w\[\]]+)\s+(\w+)\s*\((.*?)\)',s,re.S):
  args=[]
  for p in m[3].split(','):
   p=p.strip()
   if p:args.append(' '.join(p.replace('final ','').split()[:-1]))
  out.append(m[1]+' '+m[2]+'('+','.join(args)+')')
 return Counter(out)
report={}
for artifact,paths in files.items():
 for path in paths:
  versions=[]
  for v in ['1.7.0','1.9.4']:
   with zipfile.ZipFile(cache/f'{artifact}-{v}-sources.jar') as z:versions.append(signatures(z.read(path).decode()))
  a,b=versions
  report[path]={'original_declaration_count':sum(a.values()),'missing_or_changed_declarations':list((a-b).elements()),'added_declarations':list((b-a).elements())}
print(json.dumps(report,indent=2))
(root/'logs/native-declaration-comparison.json').write_text(json.dumps(report,indent=2))

assert all(not r["missing_or_changed_declarations"] for r in report.values())
