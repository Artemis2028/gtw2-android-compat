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
import com.invertbit.games.gtw.AiOff;
import com.invertbit.games.gtw.GtwGame;
import com.invertbit.games.gtw.map.WorldMap;
import com.invertbit.games.gtw.nations.Nations;
import com.invertbit.games.gtw.nations.Side;
import com.invertbit.games.gtw.sim.Battle;
import com.invertbit.games.gtw.sim.BattlePause;
import com.invertbit.games.gtw.sim.City;
import com.invertbit.games.gtw.sim.Launcher;
import com.invertbit.games.gtw.sim.Match;
import com.invertbit.games.gtw.sim.Missile;
import com.invertbit.games.gtw.sim.Player;
import com.invertbit.games.gtw.sim.Region;
import com.invertbit.games.gtw.sim.WeaponKind;

import java.io.InputStream;
import java.util.Map;

public class BattleScreen extends ScreenAdapter {

    private final GtwGame game;
    private final Match match;
    private final Battle battle;
    private final BattlePause pause = new BattlePause();
    private final Stage hud;
    private final Stage overlay;
    private final OrthographicCamera camera = new OrthographicCamera();
    private final FitViewport mapViewport = new FitViewport(2880f, 1920f, camera);
    private final Vector3 touch = new Vector3();
    private WorldMap map;
    private TextButton speedButton;
    private TextButton pauseButton;
    private Label status;
    private Label ticker;
    private boolean shownGameOver;

    public BattleScreen(GtwGame game, Match match) {
        this.game = game;
        this.match = match;
        this.battle = new Battle(match);
        this.hud = new Stage(new StretchViewport(GtwGame.UI_WIDTH, GtwGame.UI_HEIGHT), game.batch);
        this.overlay = new Stage(new StretchViewport(GtwGame.UI_WIDTH, GtwGame.UI_HEIGHT), game.batch);
        loadMap();
        fitCamera();
        buildHud();
        buildOverlay();
        pause.start(System.currentTimeMillis());
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
        speedButton = button(pause.speedLabel(), new Runnable() {
            @Override public void run() {
                pause.cycleSpeed();
                speedButton.setText(pause.speedLabel());
            }
        });
        pauseButton = button("Pause", new Runnable() {
            @Override public void run() {
                pause.pause();
            }
        });
        root.add(speedButton).size(140f, 80f).top().left().pad(16f);
        root.add().expandX();
        root.add(pauseButton).size(180f, 80f).top().right().pad(16f);
        root.row();
        status = new Label("", game.skin);
        root.add(status).colspan(3).expandX().left().padLeft(20f);
        root.row();
        if (AiOff.isOn()) {
            Label ai = new Label("AI OFF", game.skin);
            ai.setColor(1f, 0.9f, 0.2f, 1f);
            root.add(ai).colspan(3).left().padLeft(20f);
            root.row();
        }
        ticker = new Label("", game.skin);
        ticker.setFontScale(0.8f);
        root.add(ticker).colspan(3).expand().bottom().padBottom(20f);
        hud.addActor(root);
    }

    private void buildOverlay() {
        Table root = new Table();
        root.setFillParent(true);
        root.add(button("Resume", new Runnable() {
            @Override public void run() {
                pause.resume(System.currentTimeMillis());
            }
        })).size(480f, 140f);
        root.row();
        root.add(button("Quit to menu", new Runnable() {
            @Override public void run() {
                pause.quit();
                game.setScreen(new MenuScreen(game));
            }
        })).size(480f, 140f).padTop(24f);
        overlay.addActor(root);
    }

    private TextButton button(String caption, final Runnable action) {
        TextButton button = new TextButton(caption, game.skin);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                action.run();
            }
        });
        return button;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputMultiplexer(hud, overlay, new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if (pause.isPaused() || battle.finished) {
                    return false;
                }
                touch.set(screenX, screenY, 0f);
                mapViewport.unproject(touch);
                battle.handleTap(touch.x, -touch.y);
                return true;
            }

            @Override
            public boolean keyDown(int keycode) {
                if (keycode == com.badlogic.gdx.Input.Keys.BACK || keycode == com.badlogic.gdx.Input.Keys.ESCAPE) {
                    if (pause.back()) {
                        pause.quit();
                        game.setScreen(new MenuScreen(game));
                    }
                    return true;
                }
                return false;
            }
        }));
    }

    @Override
    public void render(float delta) {
        pause.step(System.currentTimeMillis(), battle);
        if (battle.finished && !shownGameOver) {
            shownGameOver = true;
        }
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mapViewport.apply();
        game.shapes.setProjectionMatrix(camera.combined);
        drawMap();
        drawUnits();
        updateLabels();
        if (pause.overlayVisible()) {
            overlay.getViewport().apply();
            overlay.act(delta);
            overlay.draw();
        } else {
            hud.getViewport().apply();
            hud.act(delta);
            hud.draw();
        }
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
        Map<String, com.invertbit.games.gtw.sim.Region> regions =
                com.invertbit.games.gtw.sim.WorldRegions.get().all();
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
        return Nations.ownerOf(name);
    }

    private void drawUnits() {
        game.shapes.begin(ShapeRenderer.ShapeType.Filled);
        for (Player player : match.sides) {
            float[] rgb = Nations.rgb(player.side);
            for (com.invertbit.games.gtw.sim.MapUnit unit : player.units) {
                if (unit.position == null || unit instanceof Region) {
                    continue;
                }
                float x = unit.position.x;
                float y = -unit.position.z;
                if (unit.destroyed) {
                    game.shapes.setColor(0.4f, 0.4f, 0.4f, 1f);
                } else if (unit == battle.selected) {
                    game.shapes.setColor(1f, 1f, 1f, 1f);
                } else {
                    game.shapes.setColor(rgb[0], rgb[1], rgb[2], 1f);
                }
                if (unit instanceof City) {
                    game.shapes.circle(x, y, 10f, 12);
                } else if (unit instanceof Launcher) {
                    Launcher launcher = (Launcher) unit;
                    if (launcher.kind == WeaponKind.SILO) {
                        game.shapes.rect(x - 6f, y - 6f, 12f, 12f);
                    } else if (launcher.kind == WeaponKind.BOMBER) {
                        game.shapes.triangle(x, y + 9f, x - 8f, y - 7f, x + 8f, y - 7f);
                    } else {
                        game.shapes.circle(x, y, 7f, 8);
                    }
                }
            }
        }
        game.shapes.setColor(1f, 0.85f, 0.2f, 1f);
        for (Missile missile : battle.missiles) {
            game.shapes.circle(missile.position.x, -missile.position.z, 5f, 8);
        }
        game.shapes.end();

        game.shapes.begin(ShapeRenderer.ShapeType.Line);
        game.shapes.setColor(1f, 0.6f, 0.1f, 1f);
        for (Missile missile : battle.missiles) {
            game.shapes.line(missile.start.x, -missile.start.z, missile.position.x, -missile.position.z);
        }
        game.shapes.end();
    }

    private void updateLabels() {
        Player you = match.player();
        Player them = match.opponent();
        String countDown = battle.clock.countdownRunning()
                ? String.format("  START IN %.1fs", Math.max(0f, battle.clock.countdown))
                : "";
        String over = battle.finished ? "  GAME OVER" : "";
        status.setText(you.side + " pop " + you.population + " wpn " + you.remainingWeapons
                + "   vs   " + them.side + " pop " + them.population + " wpn " + them.remainingWeapons
                + "   [" + you.launchesUsed + "/" + you.launchBudget + "]" + countDown + over);
        if (!battle.clock.messages.isEmpty()) {
            ticker.setText(battle.clock.messages.get(0));
        } else {
            ticker.setText("");
        }
        pauseButton.setVisible(pause.pauseButtonVisible());
    }

    @Override
    public void resize(int width, int height) {
        mapViewport.update(width, height);
        hud.getViewport().update(width, height, true);
        overlay.getViewport().update(width, height, true);
        camera.update();
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        hud.dispose();
        overlay.dispose();
    }
}
