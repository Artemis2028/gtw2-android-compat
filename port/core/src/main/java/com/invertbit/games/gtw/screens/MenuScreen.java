package com.invertbit.games.gtw.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.invertbit.games.gtw.AiOff;
import com.invertbit.games.gtw.GtwGame;
import com.invertbit.games.gtw.audio.Sounds;

public class MenuScreen extends ScreenAdapter {

    private final GtwGame game;
    private final Stage stage;

    public MenuScreen(GtwGame game) {
        this.game = game;
        this.stage = new Stage(new StretchViewport(GtwGame.UI_WIDTH, GtwGame.UI_HEIGHT), game.batch);
        Table root = new Table();
        root.setFillParent(true);
        root.defaults().pad(18f);

        Label title = new Label("Global Thermonuclear War II", game.skin, "title");
        root.add(title).colspan(2).padBottom(48f);
        root.row();

        root.add(button("New game", new Runnable() {
            @Override public void run() {
                game.setScreen(new SlotScreen(game, true));
            }
        })).size(620f, 200f);
        root.add(button("Continue", new Runnable() {
            @Override public void run() {
                game.setScreen(new SlotScreen(game, false));
            }
        })).size(620f, 200f);
        root.row();
        root.add(button("Settings", new Runnable() {
            @Override public void run() {
                game.setScreen(new SettingsScreen(game));
            }
        })).size(620f, 200f);
        root.add(button("Help", new Runnable() {
            @Override public void run() {
                /* later milestone */
            }
        })).size(620f, 200f);
        root.row();
        if (AiOff.isOn()) {
            Label ai = new Label("AI OFF", game.skin);
            ai.setColor(1f, 0.9f, 0.2f, 1f);
            root.add(ai).colspan(2).padTop(12f);
        }
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

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void hide() {
        if (Gdx.input.getInputProcessor() == stage) {
            Gdx.input.setInputProcessor(null);
        }
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
