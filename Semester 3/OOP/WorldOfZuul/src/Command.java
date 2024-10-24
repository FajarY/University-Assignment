public class Command {
    private String commandWord;
    private String secondWord;

    public Command(String commandWord, String secondWord)
    {
        this.commandWord = commandWord;
        this.secondWord = secondWord;
    }
    public boolean isUnknownCommand()
    {
        return commandWord == null;
    }
    public String getCommandWord(){
        return commandWord;
    }
    public String getSecondWord()
    {
        return secondWord;
    }
}
