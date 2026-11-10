package com.invertbit.games.gtw.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
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
import com.invertbit.games.gtw.audio.Sounds;
import com.invertbit.games.gtw.settings.GameSettings;
import com.invertbit.games.gtw.map.ObjectGlyphs;
import com.invertbit.games.gtw.map.WorldMap;
import com.invertbit.games.gtw.nations.Nations;
import com.invertbit.games.gtw.nations.Side;
import com.invertbit.games.gtw.sim.Battle;
import com.invertbit.games.gtw.sim.BattlePause;
import com.invertbit.games.gtw.sim.City;
import com.invertbit.games.gtw.sim.Explosion;
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
    private ObjectGlyphs glyphs;
    private boolean loggedUnits;
    private TextButton speedButton;
    private TextButton overlaySpeedButton;
    private TextButton pauseButton;
    private int impactsSeen;
    private Label status;
    private Label ticker;
    private boolean shownGameOver;
    private Texture bloom;

    public BattleScreen(GtwGame game, Match match) {
        this.game = game;
        this.match = match;
        this.battle = new Battle(match);
        this.hud = new Stage(new StretchViewport(GtwGame.UI_WIDTH, GtwGame.UI_HEIGHT), game.batch);
        this.overlay = new Stage(new StretchViewport(GtwGame.UI_WIDTH, GtwGame.UI_HEIGHT), game.batch);
        loadMap();
        fitCamera();
        pause.setDefaultSpeed(GameSettings.get().defaultSpeed());
        pause.start(System.currentTimeMillis());
        buildHud();
        buildOverlay();
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
        InputStream og = null;
        try {
            og = Gdx.files.internal("objects.svg").read();
            glyphs = ObjectGlyphs.load(og);
        } catch (Exception e) {
            Gdx.app.error("GTW", "objects.svg", e);
        } finally {
            if (og != null) {
                try { og.close(); } catch (Exception ignored) {}
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

    /** Bright caption so the speed control stands out from the map and the status line. */
    private static final Color SPEED_TEXT = new Color(1f, 0.9f, 0.3f, 1f);

    private void cycleSpeed() {
        pause.cycleSpeed();
        refreshSpeedButtons();
    }

    private void refreshSpeedButtons() {
        String caption = pause.speedCaption();
        if (speedButton != null) {
            speedButton.setText(caption);
        }
        if (overlaySpeedButton != null) {
            overlaySpeedButton.setText(caption);
        }
    }

    private void buildHud() {
        Table root = new Table();
        root.setFillParent(true);
        root.top();
        speedButton = button(pause.speedCaption(), new Runnable() {
            @Override public void run() {
                cycleSpeed();
            }
        });
        speedButton.getLabel().setColor(SPEED_TEXT);
        pauseButton = button("Pause", new Runnable() {
            @Override public void run() {
                pause.pause();
            }
        });
        // Top bar: speed (left, large) · spacer · pause (right). Status sits on its own row
        // below so nothing overlaps the buttons.
        root.add(speedButton).size(380f, 120f).left().pad(16f);
        root.add().expandX();
        root.add(pauseButton).size(240f, 120f).right().pad(16f);
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
        root.add(new Label("PAUSED", game.skin, "title")).padBottom(24f);
        root.row();
        root.add(button("Resume", new Runnable() {
            @Override public void run() {
                pause.resume(System.currentTimeMillis());
            }
        })).size(480f, 130f);
        root.row();
        overlaySpeedButton = button(pause.speedCaption(), new Runnable() {
            @Override public void run() {
                cycleSpeed();
            }
        });
        overlaySpeedButton.getLabel().setColor(SPEED_TEXT);
        root.add(overlaySpeedButton).size(480f, 130f).padTop(20f);
        root.row();
        root.add(button("Quit to menu", new Runnable() {
            @Override public void run() {
                pause.quit();
                game.setScreen(new MenuScreen(game));
            }
        })).size(480f, 130f).padTop(20f);
        overlay.addActor(root);
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
        Gdx.input.setInputProcessor(new InputMultiplexer(new InputAdapter() {
            // Route touches to whichever stage is visible, so the hidden HUD's buttons
            // cannot be hit through the pause overlay and vice versa.
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                return (pause.overlayVisible() ? overlay : hud).touchDown(screenX, screenY, pointer, button);
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                return (pause.overlayVisible() ? overlay : hud).touchUp(screenX, screenY, pointer, button);
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                return (pause.overlayVisible() ? overlay : hud).touchDragged(screenX, screenY, pointer);
            }
        }, new InputAdapter() {
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
        if (battle.impacts > impactsSeen) {
            impactsSeen = battle.impacts;
            Sounds.playImpact();
        }
        if (battle.finished && !shownGameOver) {
            shownGameOver = true;
            game.setScreen(new GameOverScreen(game, match));
            return;
        }
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mapViewport.apply();
        game.shapes.setProjectionMatrix(camera.combined);
        drawMap();
        drawUnits();
        drawExplosions();
        logUnitScreens();
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
        for (WorldMap.Outline outline : map.drawableOutlines()) {
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
        // No Nations.ownerOf fallback: a home that is not in this match (e.g. USA /
        // Alaska in SOUTH AMERICA vs RUSSIA) stays unowned gray, as in the original.
        return null;
    }

    private void drawUnits() {
        game.shapes.begin(ShapeRenderer.ShapeType.Line);
        for (Player player : match.sides) {
            float[] rgb = Nations.rgb(player.side);
            for (com.invertbit.games.gtw.sim.MapUnit unit : player.units) {
                if (unit.position == null || unit instanceof Region || !battle.visible(unit)) {
                    continue;
                }
                if (unit.destroyed) {
                    game.shapes.setColor(0.4f, 0.4f, 0.4f, 1f);
                } else if (unit == battle.selected) {
                    game.shapes.setColor(1f, 1f, 1f, 1f);
                } else {
                    game.shapes.setColor(rgb[0], rgb[1], rgb[2], 1f);
                }
                ObjectGlyphs.Kind kind = glyphKind(unit, player.side);
                if (!drawGlyph(kind, unit.position.x, unit.position.z)) {
                    fallbackShape(unit, unit.position.x, -unit.position.z);
                }
            }
        }
        game.shapes.end();

        game.shapes.begin(ShapeRenderer.ShapeType.Filled);
        game.shapes.setColor(1f, 0.85f, 0.2f, 1f);
        for (Missile missile : battle.missiles) {
            game.shapes.circle(Missile.wrapX(missile.position.x), -missile.position.z, 5f, 8);
        }
        game.shapes.end();

        game.shapes.begin(ShapeRenderer.ShapeType.Line);
        game.shapes.setColor(1f, 0.6f, 0.1f, 1f);
        for (Missile missile : battle.missiles) {
            drawTrail(missile);
        }
        game.shapes.end();
    }

    private final Vector3 trailA = new Vector3();
    private final Vector3 trailB = new Vector3();

    /** Cubic Bézier trail from launch to the missile's current point, sampled in 24 steps. */
    private void drawTrail(Missile missile) {
        float t = missile.duration <= 0f ? 1f : Math.min(1f, missile.elapsed / missile.duration);
        int steps = 24;
        missile.bezier(0f, trailA);
        for (int i = 1; i <= steps; i++) {
            missile.bezier(t * i / steps, trailB);
            float ax = Missile.wrapX(trailA.x);
            float bx = Missile.wrapX(trailB.x);
            if (Math.abs(bx - ax) < 1440f) {
                game.shapes.line(ax, -trailA.z, bx, -trailB.z);
            }
            trailA.set(trailB);
        }
    }


    private ObjectGlyphs.Kind glyphKind(com.invertbit.games.gtw.sim.MapUnit unit, Side side) {
        if (unit instanceof City) {
            return ObjectGlyphs.Kind.city;
        }
        if (unit instanceof Launcher) {
            return ObjectGlyphs.forWeapon(((Launcher) unit).kind, side);
        }
        return ObjectGlyphs.Kind.city;
    }

    /** Stroke the original objects.svg outline at the unit, or return false to use a fallback mark. */
    private boolean drawGlyph(ObjectGlyphs.Kind kind, float ux, float uz) {
        if (glyphs == null || glyphs.paths(kind).isEmpty()) {
            return false;
        }
        float[] off = ObjectGlyphs.offset(kind);
        for (java.util.List<Vector3> path : glyphs.paths(kind)) {
            Vector3 prev = path.get(0);
            for (int i = 1; i < path.size(); i++) {
                Vector3 next = path.get(i);
                game.shapes.line(
                        ux + prev.x + off[0], -(uz + prev.z + off[1]),
                        ux + next.x + off[0], -(uz + next.z + off[1]));
                prev = next;
            }
        }
        return true;
    }

    private void fallbackShape(com.invertbit.games.gtw.sim.MapUnit unit, float x, float y) {
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

    private void logUnitScreens() {
        if (loggedUnits || !AiOff.isOn()) {
            return;
        }
        loggedUnits = true;
        for (Player player : match.sides) {
            for (com.invertbit.games.gtw.sim.MapUnit unit : player.units) {
                if (unit.position == null || unit instanceof Region || !battle.visible(unit)) {
                    continue;
                }
                touch.set(unit.position.x, -unit.position.z, 0f);
                mapViewport.project(touch);
                String kind = unit instanceof City ? "CITY" : unit instanceof Launcher
                        ? ((Launcher) unit).kind.name() : "?";
                Gdx.app.log("GTW", "unit " + player.side + " " + kind
                        + " world=" + unit.position.x + "," + unit.position.z
                        + " screen=" + (int) touch.x + "," + (int) touch.y);
            }
        }
    }

    private void updateLabels() {
        Player you = match.player();
        Player them = match.opponent();
        String countDown = battle.clock.countdownRunning()
                ? String.format("  START IN %.1fs", Math.max(0f, battle.clock.countdown))
                : "";
        String over = battle.finished ? "  GAME OVER" : "";
        status.setText(Nations.displayName(you.side) + " pop " + you.population + " wpn " + you.remainingWeapons
                + "   vs   " + Nations.displayName(them.side) + " pop " + them.population + " wpn " + them.remainingWeapons
                + "   [" + you.launchesUsed + "/" + you.launchBudget + "]" + countDown + over);
        if (!battle.clock.messages.isEmpty()) {
            ticker.setText(battle.clock.messages.get(0));
        } else {
            ticker.setText("");
        }
        pauseButton.setVisible(pause.pauseButtonVisible());
        speedButton.setVisible(pause.speedButtonVisible());
    }

    @Override
    public void resize(int width, int height) {
        mapViewport.update(width, height);
        hud.getViewport().update(width, height, true);
        overlay.getViewport().update(width, height, true);
        camera.update();
    }

    /**
     * 2D stand-in for the original white {@code explosionObject} / {@code rocketobject}
     * ellipsoid: a soft radial disc, white, alpha 0.5, additive blend (the shader
     * blew out RGB via {@code 1/sin} which reads as a bloom). Size and lifetime
     * come from {@link Explosion}.
     */
    private void drawExplosions() {
        if (battle.explosions.isEmpty()) {
            return;
        }
        Texture texture = bloomTexture();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
        game.batch.begin();
        for (Explosion flash : battle.explosions) {
            float radius = flash.radius();
            float x = Missile.wrapX(flash.x);
            float y = -flash.z;
            game.batch.setColor(1f, 1f, 1f, flash.alpha());
            game.batch.draw(texture, x - radius, y - radius, radius * 2f, radius * 2f);
            if (x - radius < 0f) {
                game.batch.draw(texture, x - radius + Explosion.WORLD_WIDTH, y - radius,
                        radius * 2f, radius * 2f);
            } else if (x + radius > Explosion.WORLD_WIDTH) {
                game.batch.draw(texture, x - radius - Explosion.WORLD_WIDTH, y - radius,
                        radius * 2f, radius * 2f);
            }
        }
        game.batch.end();
        game.batch.setColor(Color.WHITE);
        game.batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
    }

    private Texture bloomTexture() {
        if (bloom != null) {
            return bloom;
        }
        int size = 128;
        float center = (size - 1) * 0.5f;
        Pixmap pixmap = new Pixmap(size, size, Pixmap.Format.RGBA8888);
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                float dx = (x - center) / center;
                float dy = (y - center) / center;
                float dist = (float) Math.sqrt(dx * dx + dy * dy);
                float falloff = dist >= 1f ? 0f : (1f - dist) * (1f - dist);
                pixmap.setColor(1f, 1f, 1f, falloff);
                pixmap.drawPixel(x, y);
            }
        }
        bloom = new Texture(pixmap);
        bloom.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        pixmap.dispose();
        return bloom;
    }

    private void disposeBloom() {
        if (bloom != null) {
            bloom.dispose();
            bloom = null;
        }
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
        disposeBloom();
    }

    @Override
    public void dispose() {
        disposeBloom();
        hud.dispose();
        overlay.dispose();
    }
}
