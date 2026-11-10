package com.invertbit.games.gtw.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.invertbit.games.gtw.GtwGame;
import com.invertbit.games.gtw.audio.Sounds;
import com.invertbit.games.gtw.settings.GameSettings;
import com.invertbit.games.gtw.sim.Difficulty;

/** Sound, volume, default battle speed (including 0.25x / 0.5x) and difficulty. */
public class SettingsScreen extends ScreenAdapter {

    private final GtwGame game;
    private final Stage stage;
    private TextButton soundButton;
    private TextButton volumeButton;
    private TextButton speedButton;
    private TextButton levelButton;

    public SettingsScreen(GtwGame game) {
        this.game = game;
        this.stage = new Stage(new StretchViewport(GtwGame.UI_WIDTH, GtwGame.UI_HEIGHT), game.batch);
        Table root = new Table();
        root.setFillParent(true);
        root.defaults().pad(12f);

        root.add(new Label("Settings", game.skin, "title")).padBottom(28f);
        root.row();

        final GameSettings gs = GameSettings.get();
        soundButton = button(gs.soundLabel(), new Runnable() {
            @Override public void run() {
                gs.toggleSound();
                gs.saveSound();
                soundButton.setText(gs.soundLabel());
            }
        });
        volumeButton = button(gs.volumeLabel(), new Runnable() {
            @Override public void run() {
                gs.cycleVolume();
                gs.saveVolume();
                volumeButton.setText(gs.volumeLabel());
            }
        });
        speedButton = button(gs.defaultSpeedLabel(), new Runnable() {
            @Override public void run() {
                gs.cycleDefaultSpeed();
                gs.saveSpeed();
                speedButton.setText(gs.defaultSpeedLabel());
            }
        });
        levelButton = button("Level: " + Difficulty.global().name(), new Runnable() {
            @Override public void run() {
                Difficulty.setGlobal(Difficulty.global().next());
                levelButton.setText("Level: " + Difficulty.global().name());
            }
        });

        root.add(soundButton).size(720f, 110f);
        root.row();
        root.add(volumeButton).size(720f, 110f);
        root.row();
        root.add(speedButton).size(720f, 110f);
        root.row();
        root.add(levelButton).size(720f, 110f);
        root.row();
        root.add(button("Back", new Runnable() {
            @Override public void run() {
                game.setScreen(new MenuScreen(game));
            }
        })).size(420f, 110f).padTop(16f);

        stage.addActor(root);
    }

    private TextButton button(String caption, final Runnable action) {
        TextButton button = new TextButton(caption, game.skin);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Sounds.playButton();
                action.run();
            }
        });
        return button;
    }

    @Override public void show() { Gdx.input.setInputProcessor(stage); }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override public void resize(int width, int height) { stage.getViewport().update(width, height, true); }

    @Override
    public void hide() {
        if (Gdx.input.getInputProcessor() == stage) {
            Gdx.input.setInputProcessor(null);
        }
    }

    @Override public void dispose() { stage.dispose(); }
}
