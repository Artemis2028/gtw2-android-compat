package com.invertbit.games.gtw.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.invertbit.games.gtw.GtwGame;
import com.invertbit.games.gtw.audio.Sounds;
import com.invertbit.games.gtw.map.WorldMap;
import com.invertbit.games.gtw.nations.Nations;
import com.invertbit.games.gtw.nations.Side;
import com.invertbit.games.gtw.sim.AlliancePicker;
import com.invertbit.games.gtw.sim.Match;
import com.invertbit.games.gtw.sim.Player;
import com.invertbit.games.gtw.sim.Region;
import com.invertbit.games.gtw.sim.WorldRegions;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Manual alliance map (original {@code c.Cp} + {@code c.Cr}). Tinting uses match
 * ownership only, so unowned homes (USA / Alaska when they are not in the match)
 * stay gray.
 */
public class AllianceScreen extends ScreenAdapter {

    private final GtwGame game;
    private final Match match;
    private final Stage hud;
    private final OrthographicCamera camera = new OrthographicCamera();
    private final FitViewport mapViewport = new FitViewport(2880f, 1920f, camera);
    private final Vector3 touch = new Vector3();
    private WorldMap map;
    private Label title;
    private Label note;
    private List<Region> markers;

    public AllianceScreen(GtwGame game, Match match) {
        this.game = game;
        this.match = match;
        this.hud = new Stage(new StretchViewport(GtwGame.UI_WIDTH, GtwGame.UI_HEIGHT), game.batch);
        loadMap();
        fitCamera();
        buildHud();
        refreshMarkers();
    }

    private void loadMap() {
        InputStream in = null;
        try {
            in = Gdx.files.internal("world.svg").read();
            map = WorldMap.load(in);
        } catch (Exception e) {
            Gdx.app.error("GTW", "world.svg", e);
        } finally {
            if (in != null) {
                try { in.close(); } catch (Exception ignored) {}
            }
        }
    }

    private void fitCamera() {
        if (map == null || map.outlines.isEmpty()) {
            camera.setToOrtho(false, 2880f, 1920f);
            camera.position.set(1440f, -960f, 0f);
            camera.update();
            return;
        }
        float minX = Float.MAX_VALUE, maxX = -Float.MAX_VALUE;
        float minY = Float.MAX_VALUE, maxY = -Float.MAX_VALUE;
        for (WorldMap.Outline outline : map.outlines) {
            for (Vector3 p : outline.points) {
                if (p.x < minX) minX = p.x;
                if (p.x > maxX) maxX = p.x;
                float y = -p.z;
                if (y < minY) minY = y;
                if (y > maxY) maxY = y;
            }
        }
        float w = Math.max(100f, maxX - minX);
        float h = Math.max(100f, maxY - minY);
        float pad = 80f;
        mapViewport.setWorldSize(w + pad * 2f, h + pad * 2f);
        camera.position.set(minX + w * 0.5f, minY + h * 0.5f, 0f);
        camera.update();
    }

    private void buildHud() {
        Table root = new Table();
        root.setFillParent(true);
        root.top();
        TextButton back = button("<", new Runnable() {
            @Override public void run() {
                game.setScreen(new PrepScreen(game, match));
            }
        });
        root.add(back).size(120f, 80f).pad(20f).left();
        title = new Label(titleText(), game.skin, "title");
        root.add(title).expandX().left().padTop(24f);
        root.row();
        note = new Label(noteText(), game.skin);
        note.setColor(0.75f, 0.75f, 0.8f, 1f);
        root.add(note).colspan(2).padLeft(20f).left();
        hud.addActor(root);
    }

    private String titleText() {
        return match.alliancesOpen() ? "Choose alliance" : "Alliances map";
    }

    private String noteText() {
        return match.alliancesOpen() ? "" : "(No alliances avaiable at this defcon level)";
    }

    private void refreshMarkers() {
        markers = AlliancePicker.get().eligible(match);
        title.setText(titleText());
        note.setText(noteText());
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
        Gdx.input.setInputProcessor(new InputMultiplexer(hud, new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                touch.set(screenX, screenY, 0f);
                mapViewport.unproject(touch);
                pickAt(touch.x, -touch.y);
                return true;
            }
        }));
    }

    private void pickAt(float x, float z) {
        Region best = null;
        float bestD = 50f * 50f;
        for (Region region : markers) {
            Vector3 at = markerAt(region);
            if (at == null) {
                continue;
            }
            float dx = at.x - x;
            float dz = at.z - z;
            float d = dx * dx + dz * dz;
            if (d <= bestD) {
                bestD = d;
                best = region;
            }
        }
        if (best != null && AlliancePicker.get().tryPick(match, best)) {
            Sounds.playButton();
            refreshMarkers();
        }
    }

    private static Vector3 markerAt(Region region) {
        if (region.label != null && region.label.origin != null) {
            return region.label.origin;
        }
        if (region.grid != null && !region.grid.isEmpty()) {
            return region.grid.get(0);
        }
        return region.position;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mapViewport.apply();
        game.shapes.setProjectionMatrix(camera.combined);
        drawMap();
        drawMarkers();
        hud.getViewport().apply();
        hud.act(delta);
        hud.draw();
    }

    private void drawMap() {
        if (map == null) {
            return;
        }
        game.shapes.begin(ShapeRenderer.ShapeType.Line);
        for (WorldMap.Outline outline : map.outlines) {
            game.shapes.setColor(WorldMap.colorFor(outline.name, ownerOf(outline.name)));
            Vector3 prev = outline.points.get(0);
            for (int i = 1; i < outline.points.size(); i++) {
                Vector3 next = outline.points.get(i);
                game.shapes.line(prev.x, -prev.z, next.x, -next.z);
                prev = next;
            }
        }
        game.shapes.end();
    }

    private Side ownerOf(String name) {
        Map<String, Region> regions = WorldRegions.get().all();
        if (regions != null && regions.get(name) != null && regions.get(name).owner != null) {
            return regions.get(name).owner;
        }
        for (Player player : match.sides) {
            for (Region region : player.regions()) {
                if (region.name.equals(name)) {
                    return player.side;
                }
            }
        }
        return null;
    }

    private void drawMarkers() {
        if (markers == null || markers.isEmpty()) {
            return;
        }
        game.shapes.begin(ShapeRenderer.ShapeType.Filled);
        float[] rgb = Nations.rgb(match.player().side);
        game.shapes.setColor(rgb[0], rgb[1], rgb[2], 1f);
        for (Region region : markers) {
            Vector3 at = markerAt(region);
            if (at == null) {
                continue;
            }
            game.shapes.circle(at.x, -at.z, 14f, 12);
        }
        game.shapes.end();
        game.shapes.begin(ShapeRenderer.ShapeType.Line);
        game.shapes.setColor(Color.WHITE);
        for (Region region : markers) {
            Vector3 at = markerAt(region);
            if (at == null) {
                continue;
            }
            game.shapes.circle(at.x, -at.z, 14f, 12);
        }
        game.shapes.end();
    }

    @Override
    public void resize(int width, int height) {
        mapViewport.update(width, height);
        hud.getViewport().update(width, height, true);
        camera.update();
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        hud.dispose();
    }
}
