package com.invertbit.games.gtw.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.invertbit.games.gtw.GtwGame;

public class Lwjgl3Launcher {
    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("GTW II (Port)");
        config.setWindowedMode(1280, 720);
        config.useVsync(true);
        new Lwjgl3Application(new GtwGame(), config);
    }
}
