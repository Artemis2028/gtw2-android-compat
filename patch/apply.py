from pathlib import Path
import re,shutil,xml.etree.ElementTree as ET
root=Path(__file__).resolve().parents[1];d=root/'decoded'
ns='http://schemas.android.com/apk/res/android';a='{'+ns+'}';ET.register_namespace('android',ns)
p=d/'AndroidManifest.xml';tree=ET.parse(p);m=tree.getroot();m.set('package','com.invertbit.games.gtw.android.compat')
for el in list(m):
 if el.tag=='uses-permission':m.remove(el)
app=m.find('application');app.set(a+'label','GTW II (Compat)');app.set(a+'extractNativeLibs','true')
for el in list(app):
 if el.tag=='meta-data' or (el.tag=='activity' and el.get(a+'name')!='com.invertbit.games.gtw.android.AndroidLauncher'):app.remove(el)
activity=app.find('activity');activity.set(a+'exported','true');activity.set(a+'label','GTW II (Compat)')
tree.write(p,encoding='utf-8',xml_declaration=True)
p=d/'apktool.yml';s=p.read_text();s=re.sub(r'minSdkVersion: \d+','minSdkVersion: 21',s);s=re.sub(r'targetSdkVersion: \d+','targetSdkVersion: 28',s);s=re.sub(r'versionCode: \d+','versionCode: 6',s);s=re.sub(r'versionName: .*','versionName: 1.5-compat1',s);p.write_text(s)
base=d/'smali/com/invertbit/games/gtw/android';shutil.copyfile(root/'patch/AndroidLauncher.smali',base/'AndroidLauncher.smali')
p=base/'a.smali';s=p.read_text();s,n=re.subn(r'\.method public final a\(I\)V.*?\.end method','.method public final a(I)V\n    .locals 0\n    return-void\n.end method',s,flags=re.S);assert n==1;p.write_text(s)
(base/'AndroidLauncher$a.smali').unlink(missing_ok=True)
# Remove unreachable ad SDK bytecode after eliminating its manifest and launcher entry points.
shutil.rmtree(d/'smali/com/google/android/gms')
shutil.rmtree(d/'smali/com/google/ads')
# Existing 32-bit ARM/x86 devices retain the original native libraries.
p=d/'lib/arm64-v8a';p.mkdir(exist_ok=True)
for prefix,lib in [('gdx','libgdx.so'),('freetype','libgdx-freetype.so')]:shutil.copyfile(root/f'tools/{prefix}-1.9.4-arm64'/lib,p/lib)
print('Applied compatibility patch')

p=d/"lib/x86_64";p.mkdir(exist_ok=True)
for prefix,lib in [("gdx","libgdx.so"),("freetype","libgdx-freetype.so")]:shutil.copyfile(root/f"tools/{prefix}-1.9.4-x86_64"/lib,p/lib)
