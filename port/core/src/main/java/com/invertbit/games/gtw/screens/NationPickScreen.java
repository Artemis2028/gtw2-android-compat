package com.invertbit.games.gtw.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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
import com.invertbit.games.gtw.map.WorldMap;
import com.invertbit.games.gtw.nations.Nations;
import com.invertbit.games.gtw.nations.Side;
import com.invertbit.games.gtw.sim.Match;
import com.invertbit.games.gtw.sim.WorldRegions;

import java.io.InputStream;
import java.util.List;

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
    private final Actor silArea = new Actor();
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
        root.add(new Label(title, game.skin, "title")).colspan(3)
                .padTop(NationPreviewLayout.TITLE_PAD_TOP)
                .padBottom(NationPreviewLayout.TITLE_PAD_BOTTOM);
        root.row();
        root.add(silArea).colspan(3).expandY().fill();
        root.row();
        Side skip = hidden();
        int col = 0;
        for (final Side side : Nations.PICK_ORDER) {
            if (side == skip) {
                root.add().size(NationPreviewLayout.BUTTON_WIDTH + NationPreviewLayout.BUTTON_PAD * 2f,
                        NationPreviewLayout.BUTTON_HEIGHT + NationPreviewLayout.BUTTON_PAD * 2f);
            } else {
                TextButton button = button(
                        Nations.displayName(side).toUpperCase(java.util.Locale.US),
                        "nation",
                        new Runnable() {
                            @Override public void run() {
                                pick(side);
                            }
                        });
                float[] rgb = Nations.rgb(side);
                button.getLabel().setColor(new Color(rgb[0], rgb[1], rgb[2], 1f));
                root.add(button).size(NationPreviewLayout.BUTTON_WIDTH, NationPreviewLayout.BUTTON_HEIGHT)
                        .pad(NationPreviewLayout.BUTTON_PAD);
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
        root.add(button("Back", "back", new Runnable() {
            @Override public void run() {
                if (choosingOpponent) {
                    game.setScreen(NationPickScreen.choosePlayer(game));
                } else {
                    game.setScreen(new MenuScreen(game));
                }
            }
        })).colspan(3).size(280f, NationPreviewLayout.BACK_HEIGHT)
                .padTop(NationPreviewLayout.BACK_PAD_TOP)
                .padBottom(NationPreviewLayout.BACK_PAD_BOTTOM);
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

    private TextButton button(String caption, String style, final Runnable action) {
        TextButton button = new TextButton(caption, game.skin, style);
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
        hud.getViewport().apply();
        hud.act(delta);
        if (map != null && silArea.getWidth() > 1f && silArea.getHeight() > 1f) {
            game.shapes.setProjectionMatrix(hud.getCamera().combined);
            game.shapes.begin(ShapeRenderer.ShapeType.Line);
            Side grey = hidden();
            float[] cells = NationPreviewLayout.cells(
                    silArea.getX(), silArea.getY(), silArea.getWidth(), silArea.getHeight());
            for (int i = 0; i < Nations.PICK_ORDER.length; i++) {
                Side side = Nations.PICK_ORDER[i];
                List<List<Vector3>> rings = NationPreviewLayout.homeRings(map, side);
                float[] box = NationPreviewLayout.bounds(rings);
                if (box == null) {
                    continue;
                }
                float[] fit = NationPreviewLayout.fit(
                        box[0], box[1], box[2], box[3],
                        cells[i * 4], cells[i * 4 + 1], cells[i * 4 + 2], cells[i * 4 + 3],
                        NationPreviewLayout.CELL_PAD);
                Color color = side == grey ? WorldMap.UNOWNED : WorldMap.colorFor(Nations.home(side).name(), side);
                game.shapes.setColor(color);
                for (int r = 0; r < rings.size(); r++) {
                    List<Vector3> ring = rings.get(r);
                    Vector3 prev = ring.get(0);
                    for (int p = 1; p < ring.size(); p++) {
                        Vector3 next = ring.get(p);
                        game.shapes.line(
                                NationPreviewLayout.screenX(prev.x, fit),
                                NationPreviewLayout.screenY(prev.z, fit),
                                NationPreviewLayout.screenX(next.x, fit),
                                NationPreviewLayout.screenY(next.z, fit));
                        prev = next;
                    }
                }
            }
            game.shapes.end();
        }
        hud.draw();
    }

    @Override
    public void resize(int width, int height) {
        hud.getViewport().update(width, height, true);
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
