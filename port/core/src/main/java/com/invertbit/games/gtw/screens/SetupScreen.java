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
import com.invertbit.games.gtw.nations.Nations;
import com.invertbit.games.gtw.saves.SaveSlots;
import com.invertbit.games.gtw.nations.Side;
import com.invertbit.games.gtw.sim.AlliancePicker;
import com.invertbit.games.gtw.sim.Difficulty;
import com.invertbit.games.gtw.sim.Match;
import com.invertbit.games.gtw.sim.RoundSetup;
import com.invertbit.games.gtw.sim.WorldRegions;

import java.io.InputStream;

public class SetupScreen extends ScreenAdapter {

    private final GtwGame game;
    private final Stage stage;
    private Side playerSide;
    private Side opponentSide;
    private int defcon = 5;
    private Difficulty difficulty = Difficulty.global();
    private Label playerLabel;
    private Label opponentLabel;
    private Label defconLabel;
    private Label difficultyLabel;
    private Label status;
    private TextButton start;

    public SetupScreen(GtwGame game) {
        this.game = game;
        this.stage = new Stage(new StretchViewport(GtwGame.UI_WIDTH, GtwGame.UI_HEIGHT), game.batch);
        build();
    }

    private void build() {
        Table root = new Table();
        root.setFillParent(true);
        root.defaults().pad(8f);

        root.add(new Label("New game", game.skin, "title")).colspan(6).padBottom(16f);
        root.row();
        playerLabel = new Label("You play as: —", game.skin);
        root.add(playerLabel).colspan(6);
        root.row();
        addSideRow(root, true);
        opponentLabel = new Label("Opponent: —", game.skin);
        root.add(opponentLabel).colspan(6).padTop(16f);
        root.row();
        addSideRow(root, false);

        root.row();
        defconLabel = new Label(defconText(), game.skin);
        root.add(button("<", new Runnable() {
            @Override public void run() {
                defcon = Math.max(0, defcon - 1);
                defconLabel.setText(defconText());
            }
        })).size(120f, 80f);
        root.add(defconLabel).colspan(4);
        root.add(button(">", new Runnable() {
            @Override public void run() {
                defcon = Math.min(12, defcon + 1);
                defconLabel.setText(defconText());
            }
        })).size(120f, 80f);

        root.row();
        difficultyLabel = new Label("Difficulty: " + difficulty.name(), game.skin);
        TextButton diff = button("Cycle difficulty", new Runnable() {
            @Override public void run() {
                difficulty = difficulty.next();
                Difficulty.setGlobal(difficulty);
                difficultyLabel.setText("Difficulty: " + difficulty.name());
            }
        });
        root.add(difficultyLabel).colspan(3);
        root.add(diff).colspan(3).height(70f);

        root.row();
        start = button("Start DEFCON", new Runnable() {
            @Override public void run() {
                startMatch();
            }
        });
        start.setDisabled(true);
        root.add(start).colspan(3).size(420f, 110f).padTop(12f);
        root.add(button("Back", new Runnable() {
            @Override public void run() {
                game.setScreen(new MenuScreen(game));
            }
        })).colspan(3).size(280f, 110f).padTop(12f);

        root.row();
        status = new Label(AiOff.isOn() ? "AI OFF" : "", game.skin);
        status.setColor(1f, 0.9f, 0.2f, 1f);
        root.add(status).colspan(6);
        stage.addActor(root);
    }

    private String defconText() {
        return "DEFCON " + defcon;
    }

    private void addSideRow(Table root, final boolean choosingPlayer) {
        int col = 0;
        for (final Side side : Nations.PICK_ORDER) {
            TextButton button = button(Nations.displayName(side), new Runnable() {
                @Override public void run() {
                    pick(side, choosingPlayer);
                }
            });
            Color tint = new Color(Nations.rgb(side)[0], Nations.rgb(side)[1], Nations.rgb(side)[2], 1f);
            button.getLabel().setColor(tint);
            root.add(button).size(280f, 90f);
            col++;
            if (col == 3) {
                root.row();
                col = 0;
            }
        }
        if (col != 0) {
            root.row();
        }
    }

    private void pick(Side side, boolean choosingPlayer) {
        if (choosingPlayer) {
            playerSide = side;
            playerLabel.setText("You play as: " + Nations.displayName(side));
            if (opponentSide == playerSide) {
                opponentSide = null;
                opponentLabel.setText("Opponent: —");
            }
        } else {
            if (side == playerSide) {
                status.setText("Pick a different opponent");
                return;
            }
            opponentSide = side;
            opponentLabel.setText("Opponent: " + Nations.displayName(side));
        }
        status.setText(AiOff.isOn() ? "AI OFF" : "");
        start.setDisabled(playerSide == null || opponentSide == null);
    }

    private void startMatch() {
        if (playerSide == null || opponentSide == null) {
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
        Difficulty.setInProgress(difficulty);
        AlliancePicker.reset();
        Match match = Match.create(playerSide, opponentSide, defcon);
        AlliancePicker.get().assignAllies(defcon, match.player());
        SaveSlots.select(SaveSlots.FIRST);
        try {
            SaveSlots.saveCurrent(match);
        } catch (RuntimeException e) {
            Gdx.app.error("GTW", "save", e);
        }
        try {
            RoundSetup.deploy(match, RoundSetup.gridFromWorld());
        } catch (RuntimeException e) {
            status.setText("Deploy failed: " + e.getMessage());
            Gdx.app.error("GTW", "deploy", e);
            return;
        }
        game.setScreen(new BattleScreen(game, match));
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
