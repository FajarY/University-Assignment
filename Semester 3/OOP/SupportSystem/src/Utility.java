import java.util.HashSet;
import java.util.List;

public class Utility {
    private static StringBuilder builder = new StringBuilder();
    public static HashSet<String> splitString(String value)
    {
        HashSet<String> set = new HashSet<>();
        value = value.toLowerCase();

        builder.setLength(0);
        for(int i = 0; i < value.length(); i++)
        {
            char current = value.charAt(i);
            if(current == ' ' || current == ',' || current == '?' || current == '.' || current == '!')
            {
                if(builder.length() > 0)
                {
                    set.add(builder.toString());
                    builder.setLength(0);
                }
            }
            else
            {
                builder.append(current);
            }
        }
        if(builder.length() > 0)
        {
            set.add(builder.toString());
        }

        return set;
    }
    public static void splitString(String value, List<String> outReference)
    {
        value = value.toLowerCase();

        builder.setLength(0);
        for(int i = 0; i < value.length(); i++)
        {
            char current = value.charAt(i);
            if(current == ' ' || current == ',' || current == '?' || current == '.' || current == '!')
            {
                if(builder.length() > 0)
                {
                    outReference.add(builder.toString());
                    builder.setLength(0);
                }
            }
            else
            {
                builder.append(current);
            }
        }
        if(builder.length() > 0)
        {
            outReference.add(builder.toString());
        }
    }
}
