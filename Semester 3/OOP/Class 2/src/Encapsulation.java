import java.util.ArrayList;

public class Encapsulation {
    //Java Encapsulation is a way of hiding the implementation details of a
    //class from outside access and only exposing a public interface that can be used to interact with the class.

    //In Java, encapsulation is achieved by declaring the instance variables of a class as private,
    //which means they can only be accessed within the class. To allow outside access to the instance variables,
    //public methods called getters and setters are defined, which are used to retrieve and modify the values of
    //the instance variables, respectively.
    public class Item
    {
        //Bundling of data
        private String name;
        private int amount;

        public Item(String name, int amount)
        {
            this.name = name;
            this.amount = amount;
        }

        //To get data we use getter
        public String getName()
        {
            return name;
        }
        public int getAmount()
        {
            return amount;
        }

        //Some method for modify
        public void addAmount(int count)
        {
            amount += count;
        }
        public void use(int count)
        {
            amount -= count;
        }
        public void show()
        {
            System.out.println(name + ", amount : " + amount);
        }
    }

    //Other user
    public class Player
    {
        private ArrayList<Item> inventory;

        public Player()
        {
            inventory = new ArrayList<>();
        }
        public void addItem(Item item)
        {
            inventory.add(item);
        }
        public void showInventory()
        {
            for(int i = 0; i < inventory.size(); i++)
            {
                //We use the function that are shown
                inventory.get(i).show();
            }
        }
    }
}
