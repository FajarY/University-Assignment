package com.fajar.foxrabbitsimgame;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;

public class Rabbit extends Animal {
    private ArrayList<GameObject> searchList = new ArrayList<>();
    public Rabbit(Simulator simulator, Location location)
    {
        super(simulator, location);
        maxAge = 30;

        simulatorColor = Color.BLUE;

        alive = true;

        minBreedAge = 15;
        breedRandom = 8;
        breedMinAmount = 1;
        breedMaxAmount = 5;
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
            if(searchList.get(i) instanceof Carrot)
            {
                Carrot carrot = (Carrot) searchList.get(i);

                if(carrot.eaten) continue;

                carrot.eaten = true;
                age = MathUtils.clamp(age - 20, 0, 999);
                ate = true;
                break;
            }
            else if(age > minBreedAge && searchList.get(i) instanceof Rabbit && !breeded && Utility.randInt(0, breedRandom) == 0)
            {
                Rabbit rabbit = (Rabbit) searchList.get(i);

                if(!rabbit.alive) continue;

                rabbit.breeded = true;

                int spawnAmount = Utility.randInt(breedMinAmount, breedMaxAmount);

                for(int j = 0; j < spawnAmount; j++)
                    simulator.addSpawnInformation(new Rabbit(simulator, new Location(location.getCol(), location.getRow())));
            }
        }
        searchList.clear();

        if(!ate && Utility.randInt(0, 2) == 0)
        {
            setLocationSyncronize(simulator.getField(), location.getCol() + Utility.randInt(-1, 2), location.getRow() + Utility.randInt(-1, 2));
        }
    }
}
