public class Polymorphism {
    //Polymorphism means "many forms", and it occurs when we have many classes that are related to each other by inheritance.
    //Inheritance lets us inherit attributes and methods from another class.
    //Polymorphism uses those methods to perform different tasks.
    //This allows us to perform a single action in different ways.

    //Base class
    public abstract class Item
    {
        protected int id;
        protected String name;
        protected int maxStack;

        public Item(int id)
        {
            this.id = id;
        }
        public abstract void use();
    }

    //A form of item
    public class WoodenSword extends Item
    {
        public WoodenSword(int id)
        {
            super(id);

            this.name = "Wooden Sword";
            this.maxStack = 1;
        }

        @Override
        public void use()
        {
            System.out.println("Shing shing...");
        }
    }

    //Other form of item
    public class WoodenBow extends Item
    {
        public WoodenBow(int id)
        {
            super(id);

            this.name = "Wooden Bow";
            this.maxStack = 1;
        }
        @Override
        public void use()
        {
            System.out.println("Whoosh");
        }
    }

    //A consumable item
    public class Apple extends Item
    {
        public Apple(int id)
        {
            super(id);

            this.name = "Apple";
            this.maxStack = 999;
        }
        @Override
        public void use()
        {
            System.out.println("Craukk nyam nyam");
        }
    }
}
