import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InputReader {
    private Scanner scanner;
    private ArrayList<String> inputCache;
    public InputReader()
    {
        scanner = new Scanner(System.in);
        inputCache = new ArrayList<>();
    }
    public void read()
    {
        inputCache.clear();

        String input = scanner.nextLine();
        Utility.splitString(input, inputCache);
    }
    public int getInputCount()
    {
        return inputCache.size();
    }
    public String getInputAt(int index)
    {
        return inputCache.get(index);
    }
    public boolean contains(String string)
    {
        for (int i = 0; i < getInputCount(); i++)
        {
            if(string.equals(getInputAt(i)))
            {
                return true;
            }
        }
        return false;
    }
    public boolean containsIgnoreCasing(String string)
    {
        String lower = string.toLowerCase();
        for (int i = 0; i < getInputCount(); i++)
        {
            if(lower.equals(getInputAt(i).toLowerCase()))
            {
                return true;
            }
        }
        return false;
    }
}
