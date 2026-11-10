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
import com.invertbit.games.gtw.audio.Sounds;
import com.invertbit.games.gtw.map.WorldMap;
import com.invertbit.games.gtw.nations.Nations;
import com.invertbit.games.gtw.nations.Side;
import com.invertbit.games.gtw.sim.Match;
import com.invertbit.games.gtw.sim.WorldRegions;

import java.io.InputStream;

/**
 * Choose-player / choose-opponent screens (original {@code c.Cc} / {@code c.Cg} +
 * {@code nations.NationPicker}): home silhouettes in two rows of three, with matching
 * buttons underneath. The player's side is greyed and has no button on the opponent screen.
 */
public class NationPickScreen extends ScreenAdapter {

    private final GtwGame game;
    private final Match incoming;
    private final boolean choosingOpponent;
    private final Stage hud;
    private final OrthographicCamera camera = new OrthographicCamera();
    private final FitViewport mapViewport = new FitViewport(2880f, 1920f, camera);
    private WorldMap map;
    private String error;

    public static NationPickScreen choosePlayer(GtwGame game) {
        return new NationPickScreen(game, null, false);
    }

    public static NationPickScreen chooseOpponent(GtwGame game, Match match) {
        return new NationPickScreen(game, match, true);
    }

    private NationPickScreen(GtwGame game, Match incoming, boolean choosingOpponent) {
        this.game = game;
        this.incoming = incoming;
        this.choosingOpponent = choosingOpponent;
        this.hud = new Stage(new StretchViewport(GtwGame.UI_WIDTH, GtwGame.UI_HEIGHT), game.batch);
        load();
        camera.setToOrtho(false, 2880f, 1920f);
        camera.position.set(1440f, -960f, 0f);
        camera.update();
        buildHud();
    }

    private void load() {
        InputStream in = null;
        try {
            in = Gdx.files.internal("world.svg").read();
            java.io.ByteArrayOutputStream buf = new java.io.ByteArrayOutputStream();
            byte[] tmp = new byte[4096];
            int n;
            while ((n = in.read(tmp)) > 0) {
                buf.write(tmp, 0, n);
            }
            byte[] bytes = buf.toByteArray();
            map = WorldMap.load(new java.io.ByteArrayInputStream(bytes));
            WorldRegions.get().install(WorldRegions.load(new java.io.ByteArrayInputStream(bytes)));
        } catch (Exception e) {
            error = e.getMessage();
            Gdx.app.error("GTW", "world.svg", e);
        } finally {
            if (in != null) {
                try { in.close(); } catch (Exception ignored) {}
            }
        }
    }

    private Side hidden() {
        return choosingOpponent && incoming != null ? incoming.player().side : null;
    }

    private void buildHud() {
        Table root = new Table();
        root.setFillParent(true);
        root.top();
        String title = choosingOpponent ? "CHOOSE YOUR OPPONENT" : "CHOOSE PLAYER";
        root.add(new Label(title, game.skin, "title")).colspan(3).padTop(16f).padBottom(8f);
        root.row();
        root.add().colspan(3).expandY();
        root.row();
        Side skip = hidden();
        int col = 0;
        for (final Side side : Nations.PICK_ORDER) {
            if (side == skip) {
                root.add().size(640f, 150f);
            } else {
                TextButton button = button(Nations.displayName(side).toUpperCase(java.util.Locale.US), new Runnable() {
                    @Override public void run() {
                        pick(side);
                    }
                });
                float[] rgb = Nations.rgb(side);
                button.getLabel().setColor(new Color(rgb[0], rgb[1], rgb[2], 1f));
                root.add(button).size(620f, 140f).pad(8f);
            }
            col++;
            if (col == 3) {
                root.row();
                col = 0;
            }
        }
        if (error != null) {
            root.add(new Label(error, game.skin)).colspan(3);
            root.row();
        }
        root.add(button("Back", new Runnable() {
            @Override public void run() {
                if (choosingOpponent) {
                    game.setScreen(NationPickScreen.choosePlayer(game));
                } else {
                    game.setScreen(new MenuScreen(game));
                }
            }
        })).colspan(3).size(280f, 90f).padTop(8f).padBottom(16f);
        hud.addActor(root);
    }

    private void pick(Side side) {
        if (!choosingOpponent) {
            Match match = Match.newGame(side);
            game.setScreen(NationPickScreen.chooseOpponent(game, match));
            return;
        }
        if (incoming == null || side == incoming.player().side) {
            return;
        }
        incoming.addSide(side, false);
        game.setScreen(new PrepScreen(game, incoming));
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
            Side grey = hidden();
            for (WorldMap.Outline outline : map.drawableOutlines()) {
                Side owner = Nations.ownerOf(outline.name);
                if (owner == null) {
                    continue;
                }
                if (!Nations.isHome(owner, outline.name)) {
                    continue;
                }
                Color color = owner == grey ? WorldMap.UNOWNED : WorldMap.colorFor(outline.name, owner);
                game.shapes.setColor(color);
                float[] off = Nations.previewOffset(owner);
                Vector3 prev = outline.points.get(0);
                for (int i = 1; i < outline.points.size(); i++) {
                    Vector3 next = outline.points.get(i);
                    game.shapes.line(prev.x + off[0], -(prev.z + off[1]),
                            next.x + off[0], -(next.z + off[1]));
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
