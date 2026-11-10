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
import com.invertbit.games.gtw.AiOff;
import com.invertbit.games.gtw.GtwGame;
import com.invertbit.games.gtw.audio.Sounds;
import com.invertbit.games.gtw.nations.Nations;
import com.invertbit.games.gtw.saves.SaveSlots;
import com.invertbit.games.gtw.sim.Match;
import com.invertbit.games.gtw.sim.Player;
import com.invertbit.games.gtw.sim.RoundSetup;

/**
 * Round-preparation screen (original {@code c.Ck}): side names, Start DEFCON N,
 * Alliances. Start saves the slot then deploys, matching {@code c.Cl}.
 */
public class PrepScreen extends ScreenAdapter {

    private final GtwGame game;
    private final Match match;
    private final Stage stage;
    private final Label hint;

    public PrepScreen(GtwGame game, Match match) {
        this.game = game;
        this.match = match;
        this.stage = new Stage(new StretchViewport(GtwGame.UI_WIDTH, GtwGame.UI_HEIGHT), game.batch);
        Table root = new Table();
        root.setFillParent(true);
        root.defaults().pad(10f);

        root.add(new Label(vsLine(match), game.skin, "title")).colspan(2).padBottom(24f);
        root.row();
        root.add(button("Start\nDEFCON " + match.defcon, new Runnable() {
            @Override public void run() {
                startRound();
            }
        })).size(520f, 180f);
        root.add(button("Alliances", new Runnable() {
            @Override public void run() {
                game.setScreen(new AllianceScreen(game, match));
            }
        })).size(420f, 180f);
        root.row();
        hint = new Label(hintText(), game.skin);
        hint.setWrap(true);
        root.add(hint).colspan(2).width(1400f).padTop(16f);
        root.row();
        root.add(button("Back", new Runnable() {
            @Override public void run() {
                game.setScreen(new MenuScreen(game));
            }
        })).colspan(2).size(280f, 110f).padTop(20f);
        if (AiOff.isOn()) {
            root.row();
            Label ai = new Label("AI OFF", game.skin);
            ai.setColor(1f, 0.9f, 0.2f, 1f);
            root.add(ai).colspan(2);
        }
        stage.addActor(root);
    }

    static String vsLine(Match match) {
        String line = null;
        for (Player side : match.sides) {
            String name = Nations.displayName(side.side);
            line = line == null ? name : line + " vs " + name;
        }
        return line == null ? "" : line;
    }

    private String hintText() {
        StringBuilder sb = new StringBuilder();
        if (match.player().arsenal.empty()) {
            sb.append("Nuclear arsenal is not defined. It'll be deployed automatically.");
        }
        if (match.alliancesOpen()) {
            if (sb.length() > 0) {
                sb.append("\n");
            }
            sb.append("You can choose new alliance.");
        }
        return sb.toString();
    }

    private void startRound() {
        try {
            SaveSlots.saveCurrent(match);
        } catch (RuntimeException e) {
            Gdx.app.error("GTW", "save", e);
        }
        try {
            RoundSetup.deploy(match, RoundSetup.gridFromWorld());
        } catch (RuntimeException e) {
            hint.setText("Deploy failed: " + e.getMessage());
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
