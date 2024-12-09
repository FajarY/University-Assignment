package com.pbo.minefield;

import com.badlogic.gdx.*;
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
    private FitViewport viewport;
    private OrthographicCamera camera;

    private static Main singleton;

    //Others
    private Game game;
    private InputProcessor inputProcessor;

    public static boolean isTouchDown = false;
    public static boolean isTouch;
    public static boolean isTouchUp = false;

    public static boolean isRightTouchDown = false;

    @Override
    public void create() {
        singleton = this;

        batch = new SpriteBatch();

        camera = new OrthographicCamera(1000f, 1100f);
        viewport = new FitViewport(1000f, 1100f, camera);

        Assets.load();
        game = new Game();

        Gdx.input.setInputProcessor(new InputProcessor() {
            @Override
            public boolean keyDown(int keycode) {
                return false;
            }
            @Override
            public boolean keyUp(int keycode) {
                return false;
            }
            @Override
            public boolean keyTyped(char character) {
                return false;
            }
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if(button == Input.Buttons.LEFT)
                {
                    isTouchDown = true;
                    isTouch = true;

                    return true;
                }
                else if(button == Input.Buttons.RIGHT)
                {
                    isRightTouchDown = true;
                    return true;
                }

                return false;
            }
            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                if(button == Input.Buttons.LEFT)
                {
                    isTouchUp = true;
                    isTouch = false;

                    return true;
                }

                return false;
            }
            @Override
            public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
                return false;
            }
            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                return false;
            }
            @Override
            public boolean mouseMoved(int screenX, int screenY) {
                return false;
            }
            @Override
            public boolean scrolled(float amountX, float amountY) {
                return false;
            }
        });
    }


    @Override
    public void render() {
        ScreenUtils.clear(0f, 0f, 0f, 1f);

        game.update();

        game.preDraw(camera);
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();
        game.draw(batch, camera);
        batch.end();

        if(isTouchDown) isTouchDown = false;
        if(isTouchUp) isTouchUp = false;
        if(isRightTouchDown) isRightTouchDown = false;
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }
    public static Camera getCamera()
    {
        return singleton.camera;
    }
    public static FitViewport getFitViewport()
    {
        return singleton.viewport;
    }
}
