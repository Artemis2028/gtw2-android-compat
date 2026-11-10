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
import com.invertbit.games.gtw.sim.Difficulty;
import com.invertbit.games.gtw.sim.Match;
import com.invertbit.games.gtw.sim.WorldRegions;

import java.io.InputStream;

/**
 * Save-slot picker matching the original {@code c.SlotScreen}.
 * newGame: pick the slot the new game will save into, plus a difficulty toggle;
 * then the choose-player screen. Continue: load an existing save into prep.
 */
public class SlotScreen extends ScreenAdapter {

    private final GtwGame game;
    private final boolean newGame;
    private final Stage stage;
    private final Label status;
    private Difficulty level = Difficulty.global();

    public SlotScreen(GtwGame game) {
        this(game, false);
    }

    public SlotScreen(GtwGame game, boolean newGame) {
        this.game = game;
        this.newGame = newGame;
        this.stage = new Stage(new StretchViewport(GtwGame.UI_WIDTH, GtwGame.UI_HEIGHT), game.batch);
        Table root = new Table();
        root.setFillParent(true);
        root.defaults().pad(8f);
        root.add(new Label(newGame ? "New game: choose a save slot" : "Continue game", game.skin, "title"))
                .padBottom(20f);
        root.row();
        if (newGame) {
            final Label diff = new Label("Difficulty: " + level.name(), game.skin);
            TextButton cycle = button("Cycle difficulty", new Runnable() {
                @Override public void run() {
                    level = level.next();
                    diff.setText("Difficulty: " + level.name());
                }
            });
            root.add(diff).padBottom(4f);
            root.row();
            root.add(cycle).size(520f, 80f);
            root.row();
            root.add(new Label("A used slot is overwritten when the first DEFCON starts.", game.skin))
                    .padBottom(12f);
            root.row();
        } else if (!SaveSlots.anyExists()) {
            root.add(new Label("No saved games", game.skin)).padBottom(12f);
            root.row();
        }
        int first = newGame ? SaveSlots.FIRST : SaveSlots.AUTOSAVE;
        for (int i = first; i <= SaveSlots.LAST; i++) {
            final int slot = i;
            boolean exists = SaveSlots.exists(slot);
            if (!newGame && !exists) {
                continue;
            }
            TextButton button = button(SaveSlots.describe(slot), new Runnable() {
                @Override public void run() {
                    pick(slot);
                }
            });
            if (!newGame) {
                button.setDisabled(!exists);
            }
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

    private void pick(int slot) {
        if (newGame) {
            SaveSlots.select(slot);
            Difficulty.setGlobal(level);
            Difficulty.setInProgress(level);
            game.setScreen(NationPickScreen.choosePlayer(game));
            return;
        }
        if (!SaveSlots.exists(slot)) {
            status.setText("Empty slot");
            return;
        }
        if (!ensureWorld()) {
            return;
        }
        try {
            Match match = SaveSlots.load(slot);
            game.setScreen(new PrepScreen(game, match));
        } catch (Exception e) {
            status.setText("Load failed: " + e.getMessage());
            Gdx.app.error("GTW", "load", e);
        }
    }

    private boolean ensureWorld() {
        InputStream in = null;
        try {
            in = Gdx.files.internal("world.svg").read();
            WorldRegions.get().install(WorldRegions.load(in));
            return true;
        } catch (Exception e) {
            status.setText("Failed to load world.svg: " + e.getMessage());
            Gdx.app.error("GTW", "world.svg", e);
            return false;
        } finally {
            if (in != null) {
                try { in.close(); } catch (Exception ignored) {}
            }
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
