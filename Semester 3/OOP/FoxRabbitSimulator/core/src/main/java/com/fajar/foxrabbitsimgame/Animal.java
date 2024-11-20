package com.fajar.foxrabbitsimgame;

import com.badlogic.gdx.graphics.Color;

public abstract class Animal extends GameObject {
    protected boolean alive;
    protected int age;
    protected int maxAge;
    protected int minBreedAge;
    protected Simulator simulator;
    protected boolean breeded = false;
    protected int breedRandom;
    protected int breedMinAmount;
    protected int breedMaxAmount;

    public Animal(Simulator simulator, Location location)
    {
        this.location = location;
        this.simulator = simulator;
    }
    public abstract void act();
}
