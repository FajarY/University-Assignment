import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        
    }

    //Object
    public void obj()
    {
        //Object is an instance of a class that is instatiated by a constructor.
        //It is stored in the heap
        List<String> list = new ArrayList<>();
    }

    //Class is a blueprint or template for an object
    public class Vector3
    {
        public int x;
        public int y;
        public int z;

        public void set(int x, int y, int z)
        {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }
}