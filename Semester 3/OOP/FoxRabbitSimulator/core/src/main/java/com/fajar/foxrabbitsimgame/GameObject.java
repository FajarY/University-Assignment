package com.fajar.foxrabbitsimgame;

import com.badlogic.gdx.graphics.Color;

public abstract class GameObject {
    protected Color simulatorColor;
    protected Location location;

    public Color getSimulatorColor()
    {
        return simulatorColor;
    }
    public boolean setLocationSyncronize(Field field, int col, int row)
    {
        if(!field.withinBounds(col, row))
        {
            return false;
        }

        GameObject otherObj = field.getObjectAt(location.getCol(), location.getRow());
        if(otherObj == this && field.getObjectAt(col, row) == null)
        {
            field.setObjectAt(location.getCol(), location.getRow(), null);
            field.setObjectAt(col, row, this);

            location.set(col, row);

            return true;
        }

        return false;
    }
}
