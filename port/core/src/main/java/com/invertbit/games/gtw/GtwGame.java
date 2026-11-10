package com.invertbit.games.gtw;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.invertbit.games.gtw.screens.MenuScreen;

/** Application listener for the libGDX 1.14 port. */
public class GtwGame extends Game {

    public static final float UI_WIDTH = 1920f;
    public static final float UI_HEIGHT = 1080f;

    public SpriteBatch batch;
    public ShapeRenderer shapes;
    public BitmapFont font;
    public Skin skin;
    public Texture whitePixel;

    @Override
    public void create() {
        batch = new SpriteBatch();
        shapes = new ShapeRenderer();
        font = new BitmapFont();
        font.getData().setScale(2f);
        skin = buildSkin();
        setScreen(new MenuScreen(this));
    }

    private Skin buildSkin() {
        Pixmap pixmap = new Pixmap(4, 4, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        whitePixel = new Texture(pixmap);
        pixmap.dispose();
        NinePatchDrawable white = new NinePatchDrawable(
                new NinePatch(new TextureRegion(whitePixel), 1, 1, 1, 1));

        Skin built = new Skin();
        built.add("default", font);
        Label.LabelStyle title = new Label.LabelStyle(font, new Color(0.35f, 0.75f, 1f, 1f));
        built.add("title", title);
        Label.LabelStyle body = new Label.LabelStyle(font, Color.WHITE);
        built.add("default", body);

        TextButton.TextButtonStyle button = new TextButton.TextButtonStyle();
        button.font = font;
        button.up = white.tint(new Color(0.07f, 0.14f, 0.18f, 1f));
        button.down = white.tint(new Color(0.15f, 0.32f, 0.38f, 1f));
        button.over = white.tint(new Color(0.12f, 0.24f, 0.30f, 1f));
        button.fontColor = Color.WHITE;
        button.downFontColor = new Color(0.8f, 0.95f, 1f, 1f);
        built.add("default", button);
        return built;
    }

    @Override
    public void dispose() {
        if (getScreen() != null) {
            getScreen().dispose();
        }
        if (batch != null) {
            batch.dispose();
        }
        if (shapes != null) {
            shapes.dispose();
        }
        if (font != null) {
            font.dispose();
        }
        if (skin != null) {
            skin.dispose();
        }
        if (whitePixel != null) {
            whitePixel.dispose();
        }
    }
}
