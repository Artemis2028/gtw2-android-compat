import sys
classes=[l.strip() for l in open(sys.argv[1])]
def ren(seg, depth): return (('C' if depth==0 else 'I'*depth)+seg) if len(seg)<=2 and not seg.isdigit() else seg
out=['v1\tofficial\tnamed']
for c in sorted(classes):
    if not c.startswith(('com/badlogic/','com/invertbit/')): continue
    pkg, simple = c.rsplit('/',1)
    new='$'.join(ren(s,i) for i,s in enumerate(simple.split('$')))
    if new!=simple: out.append(f'CLASS\t{c}\t{pkg}/{new}')
open(sys.argv[2],'w').write('\n'.join(out)+'\n'); print(len(out)-1)
