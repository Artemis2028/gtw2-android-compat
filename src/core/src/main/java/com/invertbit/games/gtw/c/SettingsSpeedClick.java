package com.invertbit.games.gtw.c;

final class SettingsSpeedClick extends com.badlogic.gdx.f.a.Cg {
    final /* synthetic */ com.badlogic.gdx.f.a.a.Cc a;

    SettingsSpeedClick(com.badlogic.gdx.f.a.a.Cc cc) {
        this.a = cc;
    }

    @Override // com.badlogic.gdx.f.a.Cg
    public final boolean a() {
        com.invertbit.games.gtw.settings.GameSettings gs = com.invertbit.games.gtw.settings.GameSettings.get();
        gs.cycleDefaultSpeed();
        gs.saveSpeed();
        this.a.a(gs.defaultSpeedLabel());
        return true;
    }
}
