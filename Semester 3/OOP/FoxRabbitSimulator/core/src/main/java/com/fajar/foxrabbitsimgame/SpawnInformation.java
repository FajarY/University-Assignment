package com.fajar.foxrabbitsimgame;

public class SpawnInformation {
    private GameObject object;

    public SpawnInformation(GameObject obj)
    {
        this.object = obj;
    }
    public GameObject getObject()
    {
        return object;
    }
}
