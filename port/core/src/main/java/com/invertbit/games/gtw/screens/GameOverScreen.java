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
import com.invertbit.games.gtw.GtwGame;
import com.invertbit.games.gtw.audio.Sounds;
import com.invertbit.games.gtw.sim.Match;
import com.invertbit.games.gtw.sim.Player;
import com.invertbit.games.gtw.stats.MatchStats;

/** Post-match summary. The original "Defated" caption is kept verbatim. */
public class GameOverScreen extends ScreenAdapter {

    private final GtwGame game;
    private final Stage stage;

    public GameOverScreen(GtwGame game, Match match) {
        this.game = game;
        this.stage = new Stage(new StretchViewport(GtwGame.UI_WIDTH, GtwGame.UI_HEIGHT), game.batch);
        Table root = new Table();
        root.setFillParent(true);
        root.defaults().pad(6f);

        root.add(new Label("DEFCON " + match.defcon, game.skin, "title")).colspan(2).padBottom(12f);
        root.row();
        root.add(new Label("Losses:", game.skin)).colspan(2).left().padLeft(80f);
        root.row();

        for (Player side : match.sides) {
            MatchStats stats = MatchStats.of(side);
            Label name = new Label(stats.name, game.skin);
            root.add(name).left().padLeft(80f);
            Label flag = new Label(stats.defeated ? "Defated" : "", game.skin);
            flag.setColor(Color.YELLOW);
            root.add(flag).left().padLeft(24f);
            root.row();
            addRow(root, "Launches", stats.launchesLabel());
            addRow(root, "Casualties", stats.casualtiesLabel());
            addRow(root, "Cities", stats.citiesLabel());
            addRow(root, "Silos", stats.silosLabel());
            addRow(root, "Submarines", stats.subsLabel());
            addRow(root, "Bombers", stats.bombersLabel());
            root.row();
        }

        root.add(button("Back to menu", new Runnable() {
            @Override public void run() {
                game.setScreen(new MenuScreen(game));
            }
        })).colspan(2).size(480f, 120f).padTop(16f);

        stage.addActor(root);
    }

    private void addRow(Table root, String label, String value) {
        root.add(new Label(label, game.skin)).left().padLeft(120f);
        root.add(new Label(value, game.skin)).left().padLeft(24f);
        root.row();
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
