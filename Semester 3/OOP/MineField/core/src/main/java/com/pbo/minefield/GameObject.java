package com.pbo.minefield;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameObject {
    protected Game game;
    public boolean destroyed;

    public GameObject(Game game)
    {
        this.game = game;
    }
    public void awake()
    {

    }
    public void start()
    {

    }
    public void update()
    {

    }
    public void draw(SpriteBatch batch)
    {

    }
    public void destroy()
    {
        destroyed = true;
    }
}
