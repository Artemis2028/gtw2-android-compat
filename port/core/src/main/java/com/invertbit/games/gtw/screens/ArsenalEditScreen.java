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
import com.invertbit.games.gtw.sim.Arsenal;
import com.invertbit.games.gtw.sim.Match;
import com.invertbit.games.gtw.sim.WeaponKind;
import com.invertbit.games.gtw.sim.YieldRow;

/**
 * Yield editor (original {@code c.Cw}): spend/refund 5 MT (and 10/15 for silos)
 * against the player's remaining points and slot caps.
 */
public class ArsenalEditScreen extends ScreenAdapter {

    private final GtwGame game;
    private final Match match;
    private final YieldRow row;
    private final Stage stage;
    private Label pointsLabel;
    private Label slotsLabel;
    private Label amount5;
    private Label amount10;
    private Label amount15;

    public ArsenalEditScreen(GtwGame game, Match match, YieldRow row) {
        this.game = game;
        this.match = match;
        this.row = row;
        this.stage = new Stage(new StretchViewport(GtwGame.UI_WIDTH, GtwGame.UI_HEIGHT), game.batch);
        build();
        refresh();
    }

    private void build() {
        Table root = new Table();
        root.setFillParent(true);
        root.defaults().pad(8f);
        root.add(new Label(titleFor(row.kind), game.skin, "title")).colspan(5).padBottom(12f);
        root.row();
        pointsLabel = new Label("", game.skin);
        slotsLabel = new Label("", game.skin);
        root.add(pointsLabel).colspan(5);
        root.row();
        root.add(slotsLabel).colspan(5).padBottom(16f);
        root.row();
        addYieldRow(root, 5);
        if (row.kind == WeaponKind.SILO) {
            addYieldRow(root, 10);
            addYieldRow(root, 15);
        }
        root.row();
        root.add(button("<", new Runnable() {
            @Override public void run() {
                game.setScreen(new ArsenalScreen(game, match));
            }
        })).colspan(5).size(150f, 120f).left().padLeft(40f).padTop(20f);
        stage.addActor(root);
    }

    private void addYieldRow(Table root, final int mt) {
        root.add(new Label(labelPrefix(mt), game.skin)).width(280f);
        root.add(button("+", new Runnable() {
            @Override public void run() {
                match.player().arsenal.spend(mt, row);
                refresh();
            }
        })).size(150f, 120f);
        root.add(button("-", new Runnable() {
            @Override public void run() {
                match.player().arsenal.refund(mt, row);
                refresh();
            }
        })).size(150f, 120f);
        Label amount = new Label("", game.skin);
        if (mt == 5) {
            amount5 = amount;
        } else if (mt == 10) {
            amount10 = amount;
        } else {
            amount15 = amount;
        }
        root.add(amount).width(280f).left().padLeft(20f);
        root.row();
    }

    private String labelPrefix(int mt) {
        return row.kind == WeaponKind.CITY ? (mt + "mln x ") : (mt + "MT x ");
    }

    private String amountText(int bucket) {
        return row.kind == WeaponKind.CITY ? (bucket + " mln") : (bucket + " MT");
    }

    static String titleFor(WeaponKind kind) {
        switch (kind) {
            case CITY: return "Population";
            case SILO: return "Intercontinental ballistic missiles";
            case BOMBER: return "Strategic nuclear bombers";
            case SUBMARINE: return "Tactical nuclear submarines";
            default: return kind.name();
        }
    }

    private void refresh() {
        Arsenal sheet = match.player().arsenal;
        pointsLabel.setText("Points: " + sheet.points + "/" + sheet.maxPoints);
        switch (row.kind) {
            case CITY:
                slotsLabel.setText("Slots: " + sheet.populationCount);
                break;
            case SILO:
            case BOMBER:
                slotsLabel.setText("Slots: " + (sheet.silo.count() + sheet.bomber.count()) + "/" + sheet.siloCount);
                break;
            case SUBMARINE:
                slotsLabel.setText("Slots: " + row.count() + "/" + sheet.submarineCount);
                break;
            default:
                break;
        }
        if (amount5 != null) {
            amount5.setText(amountText(row.m5));
        }
        if (amount10 != null) {
            amount10.setText(amountText(row.m10));
        }
        if (amount15 != null) {
            amount15.setText(amountText(row.m15));
        }
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
