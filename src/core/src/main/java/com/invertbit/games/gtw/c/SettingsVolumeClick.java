package com.invertbit.games.gtw.c;

final class SettingsVolumeClick extends com.badlogic.gdx.f.a.Cg {
    final /* synthetic */ com.badlogic.gdx.f.a.a.Cc a;

    SettingsVolumeClick(com.badlogic.gdx.f.a.a.Cc cc) {
        this.a = cc;
    }

    @Override // com.badlogic.gdx.f.a.Cg
    public final boolean a() {
        com.invertbit.games.gtw.settings.GameSettings gs = com.invertbit.games.gtw.settings.GameSettings.get();
        gs.cycleVolume();
        gs.saveVolume();
        this.a.a(gs.volumeLabel());
        return true;
    }
}
