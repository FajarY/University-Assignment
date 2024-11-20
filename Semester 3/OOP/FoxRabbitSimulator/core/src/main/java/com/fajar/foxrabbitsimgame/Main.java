package com.fajar.foxrabbitsimgame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.w3c.dom.Text;

import java.awt.*;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main implements ApplicationListener {
    private SpriteBatch batch;
    private Viewport viewport;
    private OrthographicCamera camera;

    private static Main singleton;

    //Sprites
    private Texture whitePixelTexture;
    private Texture gridTexture;

    //Others
    private Simulator simulator;

    @Override
    public void create() {
        singleton = this;

        batch = new SpriteBatch();

        camera = new OrthographicCamera(1200f, 1000f);
        viewport = new FitViewport(1200f, 1000f, camera);

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(1f, 1f, 1f, 1f);
        pixmap.drawPixel(0, 0, new Color(1f, 1f, 1f, 1f).toIntBits());
        whitePixelTexture = new Texture(pixmap);

        pixmap.dispose();

        pixmap = new Pixmap(10, 10, Pixmap.Format.RGBA8888);
        pixmap.setColor(0f, 0f, 0f, 1f);
        pixmap.fill();
        pixmap.setColor(1f, 1f, 1f, 1f);
        pixmap.fillRectangle(1, 1, 8, 8);

        gridTexture = new Texture(pixmap);

        pixmap.dispose();

        simulator = new Simulator();
    }

    public static Texture getWhitePixelTexture()
    {
        return singleton.whitePixelTexture;
    }
    public static Texture getGridTexture()
    {
        return singleton.gridTexture;
    }

    @Override
    public void render() {
        ScreenUtils.clear(0f, 0f, 0f, 1f);
        viewport.apply();
        simulator.setupDraw(camera);
        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();

        simulator.draw(batch, camera);

        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        batch.dispose();
        whitePixelTexture.dispose();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }
}
