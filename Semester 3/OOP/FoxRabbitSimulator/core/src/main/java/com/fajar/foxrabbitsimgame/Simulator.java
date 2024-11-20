package com.fajar.foxrabbitsimgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Simulator {
    private Field field;
    private float passedDelta;
    private float updateSecond = 1f;

    private final float scale = 10f;
    private final int width = 100;
    private final int height = 80;

    private HashSet<Animal> animalList = new HashSet<>();
    private HashSet<Food> foodList = new HashSet<>();
    private ArrayList<Object> removeObjList = new ArrayList<>();
    private ArrayList<SpawnInformation> spawnInformations = new ArrayList<>();

    public Simulator()
    {
        field = new Field(width, height);
        new Utility();

        spawnIntialPopulation();
    }
    protected Field getField()
    {
        return field;
    }
    private void spawnIntialPopulation()
    {
        int rabbitAmount = Utility.randInt(50, 70);
        int foxAmount = Utility.randInt(30, 40);
        int carrotAmount = Utility.randInt(80, 150);

        for(int i = 0; i < foxAmount; i++)
        {
            Fox fox = new Fox(this, new Location(Utility.randInt(0, width - 1), Utility.randInt(0, height - 1)));
            SpawnInformation info = new SpawnInformation(fox);

            spawnInformations.add(info);
        }
        for(int i = 0; i < rabbitAmount; i++)
        {
            Rabbit rabbit = new Rabbit(this, new Location(Utility.randInt(0, width - 1), Utility.randInt(0, height - 1)));
            SpawnInformation info = new SpawnInformation(rabbit);

            spawnInformations.add(info);
        }
        for(int i = 0; i < carrotAmount; i++)
        {
            Carrot carrot = new Carrot(this, new Location(Utility.randInt(0, width - 1), Utility.randInt(0, height - 1)));
            SpawnInformation info = new SpawnInformation(carrot);

            spawnInformations.add(info);
        }
    }
    private void update()
    {
        passedDelta += Gdx.graphics.getDeltaTime();

        if(passedDelta >= updateSecond)
        {
            passedDelta -= updateSecond;
            simulateOneStep();
        }
    }
    private void simulateOneStep()
    {
        //Act
        for(Animal animal : animalList)
        {
            if(!animal.alive) continue;

            animal.act();
        }
        for(Food food : foodList)
        {
            if(food.eaten) continue;

            food.act();
        }

        //Clear
        for(Animal animal : animalList)
        {
            if(!animal.alive)
            {
                removeObjList.add(animal);
            }
        }
        for(Food food : foodList)
        {
            if(food.eaten)
            {
                removeObjList.add(food);
            }
        }
        for(Object obj : removeObjList)
        {
            GameObject gameObject = (GameObject)obj;

            field.setObjectAt(gameObject.location.getCol(), gameObject.location.getRow(), null);

            animalList.remove(obj);
            foodList.remove(obj);
        }
        removeObjList.clear();

        //Add
        for(SpawnInformation spawnInformation : spawnInformations)
        {
            GameObject obj = spawnInformation.getObject();
            if(field.getFreeLocation(obj.location))
            {
                if(obj instanceof Animal)
                {
                    Animal animal = (Animal) obj;
                    animalList.add(animal);
                }
                else if(obj instanceof Food)
                {
                    Food food = (Food) obj;
                    foodList.add(food);
                }

                field.setObjectAt(obj.location.getCol(), obj.location.getRow(), obj);
            }
        }
        spawnInformations.clear();

        System.out.println("Animals Amount : " + animalList.size());
        System.out.println("Food Amount : " + foodList.size());
    }
    public void addSpawnInformation(GameObject object)
    {
        spawnInformations.add(new SpawnInformation(object));
    }
    public void setupDraw(OrthographicCamera camera)
    {
        camera.position.set(width / 2f * scale - (scale / 2f), height / 2f * scale - (scale / 2f), 0f);
    }
    public void draw(SpriteBatch batch, OrthographicCamera camera)
    {
        update();

        batch.setColor(0f, 0f, 0f, 1f);
        centerDraw(batch, Main.getWhitePixelTexture(), camera.position.x, camera.position.y, camera.viewportWidth, camera.viewportHeight);

        batch.setColor(1f, 1f, 1f, 1f);
        centerDraw(batch, Main.getGridTexture(), 0f, 0f, 10f, 10f);
        for(int x = 0; x < width; x++)
        {
            for(int y = 0; y < height; y++)
            {
                centerDraw(batch, Main.getGridTexture(), x * scale, y * scale, scale, scale);
            }
        }
        for(int x = 0; x < width; x++)
        {
            for(int y = 0; y < height; y++)
            {
                GameObject obj = field.getObjectAt(x, y);
                if(obj == null)
                {
                    continue;
                }

                batch.setColor(obj.getSimulatorColor());
                centerDraw(batch, Main.getWhitePixelTexture(), x * scale, y * scale, scale, scale);
            }
        }
    }
    public void centerDraw(SpriteBatch batch, Texture texture, float x, float y, float width, float height)
    {
        x -= width / 2f;
        y -= height / 2f;
        batch.draw(texture, x, y, width, height);
    }
}
