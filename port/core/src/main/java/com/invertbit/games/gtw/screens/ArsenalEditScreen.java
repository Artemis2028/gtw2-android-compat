package com.invertbit.games.gtw.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.invertbit.games.gtw.GtwGame;
import com.invertbit.games.gtw.audio.Sounds;
import com.invertbit.games.gtw.map.ObjectGlyphs;
import com.invertbit.games.gtw.nations.Nations;
import com.invertbit.games.gtw.sim.Arsenal;
import com.invertbit.games.gtw.sim.Match;
import com.invertbit.games.gtw.sim.WeaponKind;
import com.invertbit.games.gtw.sim.YieldRow;

import java.io.InputStream;

/**
 * Yield editor (original {@code c.Cw}): spend/refund 5 MT (and 10/15 for silos)
 * against the player's remaining points and slot caps.
 */
public class ArsenalEditScreen extends ScreenAdapter {

    private final GtwGame game;
    private final Match match;
    private final YieldRow row;
    private final Stage stage;
    private final Actor glyphSlot = new Actor();
    private ObjectGlyphs glyphs;
    private ObjectGlyphs.Kind glyphKind;
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
        glyphKind = ObjectGlyphs.forWeapon(row.kind, match.player().side);
        InputStream in = null;
        try {
            in = Gdx.files.internal("objects.svg").read();
            glyphs = ObjectGlyphs.load(in);
        } catch (Exception e) {
            Gdx.app.error("GTW", "objects.svg", e);
        } finally {
            if (in != null) {
                try { in.close(); } catch (Exception ignored) {}
            }
        }
        build();
        refresh();
    }

    private void build() {
        Table root = new Table();
        root.setFillParent(true);
        root.defaults().pad(8f);
        root.add(new Label(titleFor(row.kind), game.skin, "title")).colspan(5).padBottom(8f);
        root.row();
        root.add(glyphSlot).size(220f, 140f).colspan(5).padBottom(8f);
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
        drawGlyph();
    }

    private void drawGlyph() {
        if (glyphs == null || glyphKind == null) {
            return;
        }
        float[] rgb = Nations.rgb(match.player().side);
        float[] off = ObjectGlyphs.offset(glyphKind);
        Vector2 centre = new Vector2(glyphSlot.getWidth() * 0.5f, glyphSlot.getHeight() * 0.5f);
        glyphSlot.localToStageCoordinates(centre);
        float scale = 2.2f;
        game.shapes.setProjectionMatrix(stage.getViewport().getCamera().combined);
        game.shapes.begin(ShapeRenderer.ShapeType.Line);
        game.shapes.setColor(rgb[0], rgb[1], rgb[2], 1f);
        for (java.util.List<Vector3> path : glyphs.paths(glyphKind)) {
            if (path.isEmpty()) {
                continue;
            }
            Vector3 prev = path.get(0);
            for (int i = 1; i < path.size(); i++) {
                Vector3 next = path.get(i);
                float x0 = centre.x + (prev.x + off[0]) * scale;
                float y0 = centre.y - (prev.z + off[1]) * scale;
                float x1 = centre.x + (next.x + off[0]) * scale;
                float y1 = centre.y - (next.z + off[1]) * scale;
                game.shapes.line(x0, y0, x1, y1);
                prev = next;
            }
        }
        game.shapes.end();
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
