package com.fajar.foxrabbitsimgame;

public class Location {
    private int col;
    private int row;

    public Location(int col, int row)
    {
        this.col = col;
        this.row = row;
    }
    public void translate(int col, int row)
    {
        this.col += col;
        this.row += row;
    }
    public void set(int col, int row)
    {
        this.col = col;
        this.row = row;
    }
    public int getCol()
    {
        return col;
    }
    public int getRow()
    {
        return row;
    }
}
