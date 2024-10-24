import java.util.HashSet;

public class CommandWords {
    private HashSet<String> validCommands = new HashSet<>();

    public CommandWords()
    {
        validCommands.add("go");
        validCommands.add("quit");
        validCommands.add("help");
    }
    public boolean isCommand(String command)
    {
        if(command == null)
        {
            return false;
        }

        return validCommands.contains(command);
    }
}
