#!/usr/bin/env python3
"""Install the separate compatibility APK and collect an Android smoke-test report.
Requires adb on PATH, USB debugging, and one explicitly selected authorized device.
Does not clear app data or uninstall any app. Logs may contain device information.
"""
from pathlib import Path
import argparse, subprocess, json, time, hashlib, sys, datetime
P='com.invertbit.games.gtw.android.compat'
root=Path(__file__).resolve().parent
parser=argparse.ArgumentParser(description=__doc__)
parser.add_argument('--serial',required=True,help='Serial shown by adb devices')
parser.add_argument('--adb',default='adb')
parser.add_argument('--output',default='device-test-results')
args=parser.parse_args()
out=Path(args.output).resolve();out.mkdir(parents=True,exist_ok=True)
base=[args.adb,'-s',args.serial]
report={'timestamp_utc':datetime.datetime.now(datetime.timezone.utc).isoformat(),'package':P,'checks':{},'manual_checks':'NOT PERFORMED: menus, tutorial, match progression, controls, audio, background/resume, saves; opening the app does not verify these.'}
def adb(*cmd,required=True):
 r=subprocess.run(base+list(cmd),capture_output=True,text=True,timeout=120)
 if required and r.returncode:raise RuntimeError(f'adb {cmd[0]} failed: {r.stderr} {r.stdout}')
 return r
try:
 if adb('get-state').stdout.strip()!='device':raise RuntimeError('Device must be connected and authorized')
 props={k:adb('shell','getprop',k).stdout.strip() for k in ['ro.build.version.release','ro.build.version.sdk','ro.product.cpu.abi','ro.product.cpu.abilist','ro.product.manufacturer','ro.product.model']}
 report['device']=props
 report['page_size']=adb('shell','getconf','PAGE_SIZE',required=False).stdout.strip()
 report['latest_android_api_37_or_newer']=int(props['ro.build.version.sdk'])>=37
 apk=root/'GTW-II-1.5-compat1.apk'
 report['apk_sha256']=hashlib.sha256(apk.read_bytes()).hexdigest()
 r=adb('install','-r',str(apk));(out/'install.txt').write_text(r.stdout+r.stderr)
 if 'Success' not in r.stdout:raise RuntimeError('Installation did not report Success')
 report['checks']['installation']='PASS'
 # Stop only the compatibility package so the next launch is a cold start. Never clear data.
 adb('shell','am','force-stop',P)
 r=adb('shell','am','start','-W','-n',P+'/com.invertbit.games.gtw.android.AndroidLauncher')
 (out/'launch.txt').write_text(r.stdout+r.stderr)
 if 'Status: ok' not in r.stdout:raise RuntimeError('Activity launch did not report Status: ok')
 time.sleep(15)
 pid=adb('shell','pidof',P,required=False).stdout.strip()
 report['checks']['process_alive_after_15_seconds']='PASS' if pid else 'FAIL'
 report['checks']['visual_gameplay']='UNVERIFIED: inspect screenshot and perform manual checks'
 # Capture only this process's messages when alive; no unrelated system log dump.
 if pid:
  r=adb('logcat','-d','--pid='+pid.split()[0]);(out/'app-logcat.txt').write_text(r.stdout)
 r=subprocess.run(base+['exec-out','screencap','-p'],capture_output=True,timeout=30)
 if r.returncode==0 and r.stdout.startswith(b'\x89PNG'):(out/'screen.png').write_bytes(r.stdout)
 print('Collected smoke-test evidence. Review the screenshot and complete manual gameplay checks.')
except Exception as e:
 report['error']=str(e);print(str(e),file=sys.stderr)
finally:
 (out/'report.json').write_text(json.dumps(report,indent=2))
 print('Report:',out/'report.json')
if report.get('error') or report['checks'].get('process_alive_after_15_seconds')=='FAIL':sys.exit(1)
