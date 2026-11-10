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
import com.invertbit.games.gtw.saves.SaveSlots;
import com.invertbit.games.gtw.sim.Match;
import com.invertbit.games.gtw.sim.RoundSetup;
import com.invertbit.games.gtw.sim.WorldRegions;

import java.io.InputStream;

/** Continue-game picker: autosave plus slots 1–4. */
public class SlotScreen extends ScreenAdapter {

    private final GtwGame game;
    private final Stage stage;
    private final Label status;

    public SlotScreen(GtwGame game) {
        this.game = game;
        this.stage = new Stage(new StretchViewport(GtwGame.UI_WIDTH, GtwGame.UI_HEIGHT), game.batch);
        Table root = new Table();
        root.setFillParent(true);
        root.defaults().pad(8f);
        root.add(new Label("Continue", game.skin, "title")).padBottom(20f);
        root.row();
        if (!SaveSlots.anyExists()) {
            root.add(new Label("No saved games", game.skin)).padBottom(12f);
            root.row();
        }
        for (int i = SaveSlots.AUTOSAVE; i <= SaveSlots.LAST; i++) {
            final int slot = i;
            TextButton button = button(SaveSlots.describe(slot), new Runnable() {
                @Override public void run() {
                    loadSlot(slot);
                }
            });
            button.setDisabled(!SaveSlots.exists(slot));
            root.add(button).size(1400f, 90f);
            root.row();
        }
        root.add(button("Back", new Runnable() {
            @Override public void run() {
                game.setScreen(new MenuScreen(game));
            }
        })).size(420f, 110f).padTop(12f);
        root.row();
        status = new Label("", game.skin);
        root.add(status);
        stage.addActor(root);
    }

    private void loadSlot(int slot) {
        if (!SaveSlots.exists(slot)) {
            status.setText("Empty slot");
            return;
        }
        InputStream in = null;
        try {
            in = Gdx.files.internal("world.svg").read();
            WorldRegions.get().install(WorldRegions.load(in));
        } catch (Exception e) {
            status.setText("Failed to load world.svg: " + e.getMessage());
            Gdx.app.error("GTW", "world.svg", e);
            return;
        } finally {
            if (in != null) {
                try { in.close(); } catch (Exception ignored) {}
            }
        }
        try {
            Match match = SaveSlots.load(slot);
            RoundSetup.deploy(match, RoundSetup.gridFromWorld());
            game.setScreen(new BattleScreen(game, match));
        } catch (Exception e) {
            status.setText("Load failed: " + e.getMessage());
            Gdx.app.error("GTW", "load", e);
        }
    }

    private TextButton button(String caption, final Runnable action) {
        TextButton button = new TextButton(caption, game.skin);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!button.isDisabled()) {
                    Sounds.playButton();
                    action.run();
                }
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
