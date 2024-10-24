public class Room {
    private Room northExit = null;
    private Room eastExit = null;
    private Room southExit = null;
    private Room westExit = null;
    private String description;

    public Room(String description)
    {
        this.description = description;
    }
    public void setExits(Room north, Room east, Room south, Room west)
    {
        northExit = north;
        eastExit = east;
        southExit = south;
        westExit = west;
    }
    public String getDescription()
    {
        return description;
    }
    public Room getNorthExit() {
        return northExit;
    }
    public Room getEastExit() {
        return eastExit;
    }
    public Room getSouthExit() {
        return southExit;
    }
    public Room getWestExit() {
        return westExit;
    }
}
