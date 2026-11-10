package com.invertbit.games.gtw;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.invertbit.games.gtw.audio.Sounds;
import com.invertbit.games.gtw.screens.MenuScreen;
import com.invertbit.games.gtw.screens.NationPreviewLayout;
import com.invertbit.games.gtw.settings.GameSettings;

/** Application listener for the libGDX 1.14 port. */
public class GtwGame extends Game {

    public static final float UI_WIDTH = 1920f;
    public static final float UI_HEIGHT = 1080f;

    public SpriteBatch batch;
    public ShapeRenderer shapes;
    public BitmapFont font;
    public BitmapFont titleFont;
    public BitmapFont nationFont;
    public BitmapFont backFont;
    public Skin skin;
    public Texture whitePixel;

    @Override
    public void create() {
        batch = new SpriteBatch();
        shapes = new ShapeRenderer();
        font = loadTerminalFont(42);
        titleFont = loadTerminalFont(58);
        backFont = loadTerminalFont(52);
        nationFont = loadNationButtonFont();
        skin = buildSkin();
        try {
            GameSettings.loadFromPrefs();
        } catch (RuntimeException e) {
            com.badlogic.gdx.Gdx.app.error("GTW", "settings", e);
        }
        Sounds.load();
        setScreen(new MenuScreen(this));
    }

    /**
     * VT323 evokes an 1980s tactical/military terminal (DEFCON, WarGames-era
     * CRT type). Generated white; screens that need side colours tint labels
     * themselves. {@code uiSize} is in 1920x1080 UI units; glyphs are rasterized
     * at screen density so they stay crisp instead of being a scaled-up small font.
     */
    BitmapFont loadTerminalFont(int uiSize) {
        try {
            com.badlogic.gdx.files.FileHandle file = Gdx.files.internal("fonts/VT323-Regular.ttf");
            if (file.exists()) {
                FreeTypeFontGenerator generator = new FreeTypeFontGenerator(file);
                FreeTypeFontParameter parameter = new FreeTypeFontParameter();
                int genSize = Math.max(8, Math.round(uiSize * terminalFontScale()));
                parameter.size = genSize;
                parameter.color = Color.WHITE;
                parameter.minFilter = Texture.TextureFilter.Linear;
                parameter.magFilter = Texture.TextureFilter.Linear;
                BitmapFont generated = generator.generateFont(parameter);
                generator.dispose();
                if (genSize != uiSize) {
                    generated.getData().setScale(uiSize / (float) genSize);
                }
                return generated;
            }
        } catch (Throwable ignored) {
        }
        BitmapFont fallback = new BitmapFont();
        fallback.getData().setScale(2f);
        return fallback;
    }

    private BitmapFont loadNationButtonFont() {
        int target = Math.round(NationPreviewLayout.BUTTON_HEIGHT
                * NationPreviewLayout.NATION_FONT_HEIGHT_RATIO);
        BitmapFont generated = loadTerminalFont(target);
        float width = textWidth(generated, "SOUTH AMERICA");
        int fitted = NationPreviewLayout.nationFontUiSize(
                NationPreviewLayout.BUTTON_WIDTH, NationPreviewLayout.BUTTON_HEIGHT,
                width, target);
        if (fitted != target) {
            generated.dispose();
            generated = loadTerminalFont(fitted);
        }
        return generated;
    }

    static float textWidth(BitmapFont font, String text) {
        if (font == null || text == null || text.length() == 0) {
            return 0f;
        }
        GlyphLayout layout = new GlyphLayout(font, text);
        return layout.width;
    }

    private static float terminalFontScale() {
        float scale = 1f;
        try {
            if (Gdx.graphics != null && Gdx.graphics.getWidth() > 0 && Gdx.graphics.getHeight() > 0) {
                scale = Math.max(
                        Gdx.graphics.getWidth() / UI_WIDTH,
                        Gdx.graphics.getHeight() / UI_HEIGHT);
                float density = Gdx.graphics.getDensity();
                if (density > scale) {
                    scale = density;
                }
            }
        } catch (Throwable ignored) {
        }
        return scale < 1f ? 1f : scale;
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
        built.add("title", new Label.LabelStyle(titleFont, Color.WHITE));
        built.add("default", new Label.LabelStyle(font, Color.WHITE));

        built.add("default", buttonStyle(white, font));
        built.add("nation", buttonStyle(white, nationFont));
        built.add("back", buttonStyle(white, backFont));
        return built;
    }

    private static TextButton.TextButtonStyle buttonStyle(NinePatchDrawable white, BitmapFont face) {
        TextButton.TextButtonStyle button = new TextButton.TextButtonStyle();
        button.font = face;
        button.up = white.tint(new Color(0.07f, 0.14f, 0.18f, 1f));
        button.down = white.tint(new Color(0.15f, 0.32f, 0.38f, 1f));
        button.over = white.tint(new Color(0.12f, 0.24f, 0.30f, 1f));
        button.fontColor = Color.WHITE;
        button.downFontColor = new Color(0.8f, 0.95f, 1f, 1f);
        return button;
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
        if (titleFont != null && titleFont != font) {
            titleFont.dispose();
        }
        if (nationFont != null && nationFont != font) {
            nationFont.dispose();
        }
        if (backFont != null && backFont != font) {
            backFont.dispose();
        }
        if (skin != null) {
            skin.dispose();
        }
        if (whitePixel != null) {
            whitePixel.dispose();
        }
        Sounds.dispose();
    }
}
