import java.util.ArrayList;
import java.util.List;

public class Abstraction {
    public static void Run()
    {
        //Hiding the implementation. the only thing that we care is it is a list.
        ArrayList<String> abstraction = new ArrayList<>();

        abstraction.add("The implementation is hidden");
        abstraction.add("Only important functionality is shown");
        abstraction.add("Like");

        //Add
        abstraction.add("Adding");

        //Get by Index
        abstraction.get(1);

        //Contains
        abstraction.contains("Like");

        //Clear & Other Things!
        abstraction.clear();
    }
}
