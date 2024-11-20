package com.fajar.foxrabbitsimgame;

import java.util.Random;

public class Utility {
    private static Utility singleton;
    private Random randomizer = new Random();

    public Utility()
    {
        singleton = this;
    }
    public static int randInt(int min, int max)
    {
        return singleton.randomizer.nextInt(min, max);
    }
}
