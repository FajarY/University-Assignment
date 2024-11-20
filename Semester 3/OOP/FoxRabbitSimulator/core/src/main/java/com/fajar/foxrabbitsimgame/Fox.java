package com.fajar.foxrabbitsimgame;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;

public class Fox extends Animal {
    private ArrayList<GameObject> searchList = new ArrayList<>();

    public Fox(Simulator simulator, Location location)
    {
        super(simulator, location);
        maxAge = 50;

        simulatorColor = Color.RED;

        alive = true;

        breedRandom = 15;
        minBreedAge = 20;
        breedMinAmount = 1;
        breedMaxAmount = 3;
    }

    @Override
    public void act() {
        age++;
        if(age > maxAge)
        {
            alive = false;
            return;
        }

        simulator.getField().getNeighbouringObjects(searchList, this, 1);
        boolean ate = false;
        for(int i = 0; i < searchList.size(); i++)
        {
            if(searchList.get(i) instanceof Rabbit)
            {
                Rabbit rabbit = (Rabbit) searchList.get(i);

                if(!rabbit.alive) continue;

                rabbit.alive = false;
                age = MathUtils.clamp(age - 20, 0, 999);
                ate = true;
                break;
            }
            else if(age > minBreedAge && searchList.get(i) instanceof Fox && !breeded && Utility.randInt(0, breedRandom) == 0)
            {
                Fox fox = (Fox) searchList.get(i);

                if(!fox.alive) continue;

                fox.breeded = true;

                int spawnAmount = Utility.randInt(breedMinAmount, breedMaxAmount);

                for(int j = 0; j < spawnAmount; j++)
                    simulator.addSpawnInformation(new Fox(simulator, new Location(location.getCol(), location.getRow())));
            }
        }
        breeded = false;
        searchList.clear();

        if(!ate && Utility.randInt(0, 2) == 0)
        {
            setLocationSyncronize(simulator.getField(), location.getCol() + Utility.randInt(-1, 2), location.getRow() + Utility.randInt(-1, 2));
        }
    }
}
