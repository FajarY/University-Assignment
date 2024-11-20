package com.fajar.foxrabbitsimgame;

import com.badlogic.gdx.graphics.Color;

public abstract class Food extends GameObject {
    protected boolean eaten;
    protected Simulator simulator;
    public Food(Simulator simulator, Location location)
    {
        this.simulator = simulator;
        this.location = location;
    }
    public abstract void act();
}
