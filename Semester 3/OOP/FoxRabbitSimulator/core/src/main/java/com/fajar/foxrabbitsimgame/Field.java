package com.fajar.foxrabbitsimgame;

import java.util.List;

public class Field {
    private int width;
    private int height;
    private GameObject[][] objects;

    public Field(int width, int height)
    {
        objects = new GameObject[width][height];
        this.width = width;
        this.height = height;
    }
    public GameObject getObjectAt(int x, int y)
    {
        return objects[x][y];
    }
    public boolean withinBounds(int x, int y)
    {
        return x >= 0 && y >= 0 && x < width && y < height;
    }
    public boolean setObjectAt(int x, int y, GameObject obj)
    {
        if(!withinBounds(x, y))
        {
            return false;
        }

        if(objects[x][y] == null)
        {
            objects[x][y] = obj;
            return true;
        }
        else if(obj == null)
        {
            objects[x][y] = null;
            return true;
        }

        return false;
    }
    public void getNeighbouringObjects(List<GameObject> outObj, GameObject currentObj, int checkSize)
    {
        if(currentObj == null)
        {
            return;
        }

        int startX = currentObj.location.getCol() - checkSize;
        int endX = currentObj.location.getCol() + checkSize;
        int startY = currentObj.location.getRow() - checkSize;
        int endY = currentObj.location.getRow() + checkSize;

        for(int x = startX; x <= endX; x++)
        {
            for(int y = startY; y <= endY; y++)
            {
                if(!withinBounds(x, y)) continue;

                GameObject obj = getObjectAt(x, y);
                if(obj != null && obj != currentObj)
                {
                    outObj.add(obj);
                }
            }
        }
    }
    public int getWidth()
    {
        return width;
    }
    public int getHeight()
    {
        return height;
    }
    public boolean getFreeLocation(Location location)
    {
        int tries = 100;

        if(!withinBounds(location.getCol(), location.getRow()))
        {
            location.set(0, 0);
        }

        while (objects[location.getCol()][location.getRow()] != null)
        {
            tries--;

            if(tries <= 0)
            {
                return false;
            }

            int colBefore = location.getCol();
            int rowBefore = location.getRow();
            location.translate(Utility.randInt(-1, 1), Utility.randInt(-1, 1));

            if(!withinBounds(location.getCol(), location.getRow()))
            {
                location.set(colBefore, rowBefore);
            }
        }

        return true;
    }
}
