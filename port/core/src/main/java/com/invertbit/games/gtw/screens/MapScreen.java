package com.invertbit.games.gtw.screens;

import com.badlogic.gdx.Gdx;
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
import com.invertbit.games.gtw.map.WorldMap;
import com.invertbit.games.gtw.nations.Nations;
import com.invertbit.games.gtw.nations.Side;

import java.io.InputStream;

/**
 * Draws every named outline from {@code world.svg} in the owning side's colour.
 * SVG y is stored as Vector3.z (as in the original parser); we plot (x, -z) so
 * north stays up.
 */
public class MapScreen extends ScreenAdapter {

    private final GtwGame game;
    private final Stage hud;
    private final OrthographicCamera camera;
    private final FitViewport mapViewport;
    private WorldMap map;
    private String error;

    public MapScreen(GtwGame game) {
        this.game = game;
        this.hud = new Stage(new StretchViewport(GtwGame.UI_WIDTH, GtwGame.UI_HEIGHT), game.batch);
        this.camera = new OrthographicCamera();
        this.mapViewport = new FitViewport(2880f, 1920f, camera);
        loadMap();
        fitCamera();
        buildHud();
    }

    private void loadMap() {
        InputStream in = null;
        try {
            in = Gdx.files.internal("world.svg").read();
            map = WorldMap.load(in);
        } catch (Exception e) {
            error = e.getMessage();
            Gdx.app.error("GTW", "Failed to load world.svg", e);
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
                float x = p.x;
                float y = -p.z;
                if (x < minX) minX = x;
                if (x > maxX) maxX = x;
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
        TextButton back = new TextButton("<", game.skin);
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen(game));
            }
        });
        root.add(back).size(120f, 80f).top().left().pad(20f);
        root.add(new Label("World map", game.skin, "title")).expandX().left().padTop(24f);
        root.row();
        StringBuilder legend = new StringBuilder();
        for (Side side : Side.values()) {
            if (legend.length() > 0) {
                legend.append("   ");
            }
            legend.append(Nations.displayName(side));
        }
        Label legendLabel = new Label(legend.toString(), game.skin);
        legendLabel.setFontScale(0.7f);
        root.add(legendLabel).colspan(2).expand().bottom().padBottom(24f);
        if (error != null) {
            root.row();
            root.add(new Label(error, game.skin)).colspan(2);
        }
        hud.addActor(root);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputMultiplexer(hud));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mapViewport.apply();
        game.shapes.setProjectionMatrix(camera.combined);
        if (map != null) {
            game.shapes.begin(ShapeRenderer.ShapeType.Line);
            for (WorldMap.Outline outline : map.outlines) {
                Color color = WorldMap.colorFor(outline.name);
                game.shapes.setColor(color);
                Vector3 prev = outline.points.get(0);
                for (int i = 1; i < outline.points.size(); i++) {
                    Vector3 next = outline.points.get(i);
                    game.shapes.line(prev.x, -prev.z, next.x, -next.z);
                    prev = next;
                }
            }
            game.shapes.end();
        }
        hud.getViewport().apply();
        hud.act(delta);
        hud.draw();
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
