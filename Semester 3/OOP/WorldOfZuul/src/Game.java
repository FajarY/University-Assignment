public class Game {
    private Parser parser;
    private Room currentRoom;
    private boolean isPlaying;
    private boolean addRoomInfo;

    public Game()
    {
        createRooms();
        parser = new Parser();
    }
    private void createRooms()
    {
        Room outside = new Room("You are outside the main entrance of the building. It’s a sunny day.");
        Room theater = new Room("You are in a grand theater with a stage and plush seats.");
        Room pub = new Room("You are in a cozy pub filled with the sound of laughter and clinking glasses.");
        Room lab = new Room("You are in a laboratory filled with strange equipment and bubbling potions.");
        Room office = new Room("You are in a small office cluttered with papers and an old computer.");
        Room library = new Room("You are in a vast library filled with shelves of books and quiet whispers.");
        Room secretChamber = new Room("You have discovered a secret chamber hidden behind a bookshelf.");
        Room garden = new Room("You are in a beautiful garden with blooming flowers and chirping birds.");
        Room cafeteria = new Room("You are in a bustling cafeteria where people are enjoying their meals.");
        Room rooftop = new Room("You are on the rooftop, offering a stunning view of the city below.");

        outside.setExits(garden, theater, null, null);
        theater.setExits(cafeteria, null, lab, outside);
        pub.setExits(secretChamber, null, null, cafeteria);
        lab.setExits(theater, office, library, null);
        office.setExits(null, null, null, lab);
        library.setExits(lab, null, rooftop, null);
        secretChamber.setExits(null, null, pub, null);
        garden.setExits(null, cafeteria, outside, null);
        cafeteria.setExits(null, pub, theater, garden);
        rooftop.setExits(library, null, null, null);

        currentRoom = outside;
    }
    public void play()
    {
        isPlaying = true;
        addRoomInfo = true;
        printWelcome();

        while (isPlaying)
        {
            if(addRoomInfo)
                printCurrentRoomInfo(true);
            addRoomInfo = false;

            processCommand(parser.getCommand());
        }

        printQuit();
    }
    public void processCommand(Command command)
    {
        if(command.isUnknownCommand())
        {
            printUnknown();
            return;
        }

        if(command.getCommandWord().equals("go"))
        {
            goToRoom(command);
        }
        else if(command.getCommandWord().equals("help"))
        {
            printHelp();
        }
        else if(command.getCommandWord().equals("quit"))
        {
            isPlaying = false;
        }
        else
        {
            printUnknown();
        }
    }
    private void goToRoom(Command command)
    {
        if(command.getSecondWord() == null)
        {
            printUnknown();
            return;
        }

        if(command.getSecondWord().equals("north"))
        {
            goToRoomNow(currentRoom.getNorthExit(), command.getSecondWord());
        }
        else if(command.getSecondWord().equals("east"))
        {
            goToRoomNow(currentRoom.getEastExit(), command.getSecondWord());
        }
        else if(command.getSecondWord().equals("south"))
        {
            goToRoomNow(currentRoom.getSouthExit(), command.getSecondWord());
        }
        else if(command.getSecondWord().equals("west"))
        {
            goToRoomNow(currentRoom.getWestExit(), command.getSecondWord());
        }
        else
        {
            printUnknown();
        }
    }
    private void goToRoomNow(Room room, String type)
    {
        if(room == null)
        {
            System.out.println();
            System.out.println("Cannot go to " + type + ", its not connected to a room!");
            return;
        }

        currentRoom = room;

        addRoomInfo = true;
    }
    private void printUnknown()
    {
        System.out.println();
        System.out.println("Invalid command inputted!");
        System.out.println("Input from one of these commands:");
        System.out.println("1. go {direction}, directions = north, east, south, west");
        System.out.println("2. help");
        System.out.println("3. quit");
    }
    public void printWelcome()
    {
        System.out.println("Welcome to world of zuul!");
        System.out.println("Please insert command:");
        System.out.println("1. go {direction}, directions = north, east, south, west");
        System.out.println("2. help");
        System.out.println("3. quit");
    }
    public void printCurrentRoomInfo(boolean addEndl)
    {
        if(addEndl)
            System.out.println();
        System.out.println(currentRoom.getDescription());
        System.out.println("Avalaible room:");
        if(currentRoom.getNorthExit() != null)
        {
            System.out.println("north ");
        }
        if(currentRoom.getEastExit() != null)
        {
            System.out.println("east ");
        }
        if(currentRoom.getSouthExit() != null)
        {
            System.out.println("south ");
        }
        if(currentRoom.getWestExit() != null)
        {
            System.out.println("west ");
        }
    }
    public void printHelp()
    {
        System.out.println();
        System.out.println("Here is the commands list:");
        System.out.println("1. go {direction}, directions = north, east, south, west");
        System.out.println("2. help");
        System.out.println("3. quit");
        System.out.println("Room info");
        printCurrentRoomInfo(false);
    }
    public void printQuit()
    {
        System.out.println();
        System.out.println("Thanks for playing!");
    }
}
