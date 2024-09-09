public class Overloading {
    //Overloading is a concept in which a method with the same name is defined by multiple methods

    //Log for class Integer
    public void log(Integer message)
    {
        System.out.println("printed " + message.getClass().getSimpleName() + " : " + message.toString());
    }

    //Log for class Float
    public void log(Float message)
    {
        System.out.println("printed " + message.getClass().getSimpleName() + " : " + message.toString());
    }

    //Log for class String
    public void log(String message)
    {
        System.out.println("printed " + message.getClass().getSimpleName() + " : " + message.toString());
    }

    //Some dummy item
    public class Item
    {
        public int id;
        public String name;
        @Override
        public String toString() {
            return "Item id : " + id + ", name : " + name;
        }
    }
    //Overload that takes parameter item, and return the item itself
    public Item log(Item message)
    {
        System.out.println("printed " + message.getClass().getSimpleName() + " : " + message.toString());

        return  message;
    }
}
