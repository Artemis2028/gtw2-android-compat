package com.invertbit.games.gtw.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
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
 * Arsenal type picker (original {@code c.Cs}): four cards — Population, ICBM silos,
 * Bombers, Submarines. Each card opens the yield editor ({@code c.Cw}).
 */
public class ArsenalScreen extends ScreenAdapter {

    private final GtwGame game;
    private final Match match;
    private final Stage stage;
    private ObjectGlyphs glyphs;

    public ArsenalScreen(GtwGame game, Match match) {
        this.game = game;
        this.match = match;
        this.stage = new Stage(new StretchViewport(GtwGame.UI_WIDTH, GtwGame.UI_HEIGHT), game.batch);
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
    }

    private void build() {
        Table root = new Table();
        root.setFillParent(true);
        root.defaults().pad(12f);
        Arsenal sheet = match.player().arsenal;
        root.add(card("Population", ObjectGlyphs.Kind.city, sheet.city,
                "Slots avaiable: " + sheet.city.count() + "/" + sheet.populationCount)).size(520f, 340f);
        root.add(card("ICBM silos", ObjectGlyphs.Kind.silo, sheet.silo,
                "Slots avaiable: " + (sheet.silo.count() + sheet.bomber.count()) + "/" + sheet.siloCount)).size(520f, 340f);
        root.row();
        root.add(card("Bombers", ObjectGlyphs.bomber(match.player().side), sheet.bomber,
                "Slots avaiable: " + (sheet.silo.count() + sheet.bomber.count()) + "/" + sheet.siloCount)).size(520f, 340f);
        root.add(card("Submarines", ObjectGlyphs.Kind.submarine, sheet.submarine,
                "Slots avaiable: " + sheet.submarine.count() + "/" + sheet.submarineCount)).size(520f, 340f);
        root.row();
        root.add(button("<", new Runnable() {
            @Override public void run() {
                game.setScreen(new PrepScreen(game, match));
            }
        })).colspan(2).size(150f, 120f).left().padLeft(40f);
        stage.addActor(root);
    }

    private Table card(final String title, ObjectGlyphs.Kind kind, final YieldRow row, String slots) {
        Table card = new Table();
        Label name = new Label(title, game.skin, "title");
        card.add(name).padTop(12f);
        card.row();
        Label icon = new Label(iconCaption(kind), game.skin);
        icon.setColor(new Color(Nations.rgb(match.player().side)[0],
                Nations.rgb(match.player().side)[1], Nations.rgb(match.player().side)[2], 1f));
        card.add(icon).pad(16f);
        card.row();
        Label sub = new Label(slots, game.skin);
        card.add(sub).padBottom(16f);
        card.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Sounds.playButton();
                game.setScreen(new ArsenalEditScreen(game, match, row));
            }
        });
        return card;
    }

    private static String iconCaption(ObjectGlyphs.Kind kind) {
        switch (kind) {
            case city: return "[ city ]";
            case silo: return "[ silo ]";
            case usabomber:
            case russiabomber:
            case chinabomber: return "[ bomber ]";
            default: return "[ sub ]";
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
        Gdx.gl.glClearColor(0f, 0f, 0.05f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
        if (glyphs != null) {
            drawCardGlyphs();
        }
    }

    private void drawCardGlyphs() {
        // Overlay original outlines on each card: Population, silos, bombers, subs.
        float[] rgb = Nations.rgb(match.player().side);
        ObjectGlyphs.Kind[] kinds = {
                ObjectGlyphs.Kind.city, ObjectGlyphs.Kind.silo,
                ObjectGlyphs.bomber(match.player().side), ObjectGlyphs.Kind.submarine
        };
        // Card centres in 1920x1080 UI: two columns (approx 640, 1280) x two rows (360, 720)
        float[][] centres = {{560f, 720f}, {1360f, 720f}, {560f, 380f}, {1360f, 380f}};
        game.shapes.setProjectionMatrix(stage.getViewport().getCamera().combined);
        game.shapes.begin(ShapeRenderer.ShapeType.Line);
        game.shapes.setColor(rgb[0], rgb[1], rgb[2], 1f);
        for (int i = 0; i < kinds.length; i++) {
            float[] off = ObjectGlyphs.offset(kinds[i]);
            // Scale glyphs ~2x so they read on the 500x360 card.
            float scale = 2.2f;
            float cx = centres[i][0];
            float cy = centres[i][1];
            for (java.util.List<Vector3> path : glyphs.paths(kinds[i])) {
                Vector3 prev = path.get(0);
                for (int p = 1; p < path.size(); p++) {
                    Vector3 next = path.get(p);
                    float x0 = cx + (prev.x + off[0]) * scale;
                    float y0 = cy - (prev.z + off[1]) * scale;
                    float x1 = cx + (next.x + off[0]) * scale;
                    float y1 = cy - (next.z + off[1]) * scale;
                    game.shapes.line(x0, y0, x1, y1);
                    prev = next;
                }
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
