import java.util.Scanner;

public class Parser {
    private CommandWords commandWords;
    private Scanner scanner;

    public Parser()
    {
        scanner = new Scanner(System.in);
        commandWords = new CommandWords();
    }
    public Command getCommand()
    {
        System.out.print("> ");
        String input = scanner.nextLine();
        Scanner tokenizer = new Scanner(input);

        String first = null;
        String second = null;

        if(tokenizer.hasNext())
        {
            first = tokenizer.next();
            if(tokenizer.hasNext())
            {
                second = tokenizer.next();
            }
        }

        if(commandWords.isCommand(first))
        {
            return new Command(first, second);
        }
        else
        {
            return new Command(null, first);
        }
    }
}
