package com.fajar.foxrabbitsimgame;

import com.badlogic.gdx.graphics.Color;

public class Carrot extends Food {
    public Carrot(Simulator simulator, Location location)
    {
        super(simulator, location);
        simulatorColor = Color.ORANGE;

        eaten = false;
    }
    @Override
    public void act() {

    }
}
