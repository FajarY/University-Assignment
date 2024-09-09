public class Inheritance {
    //Inheritance is a mechanism where a class can inherit the methods and properties of a class
    //The inherited class also can override the super class properties for extra functionality
    public class Item
    {
        public int id;
        public String name;
        public String asset;
        public int amount;

        public Item(int id, String name, String asset, int amount)
        {
            this.id = id;
            this.name = name;
            this.asset = asset;
            this.amount = amount;
        }

        //Normal implementation
        public void use(int amount)
        {
            System.out.println("Using item " + name + " amount " + amount);

            this.amount -= amount;
        }
    }
    public class WeaponItem extends Item
    {
        public int damage;

        //Since Item has a constructor, we also need a constructor for this class, that calls the
        //Constructor for the super class
        public WeaponItem(int id, String name, String asset, int amount, int damage)
        {
            super(id, name, asset, amount);

            this.damage = damage;
        }

        //Override the Item base function, adding extra functionality
        @Override
        public void use(int amount) {
            super.use(amount);

            System.out.println("Damage the enemies with power of " + damage);
        }
    }
    public class ConsumeableItem extends Item
    {
        public int replenishHunger;
        public int replenishThirst;

        public ConsumeableItem(int id, String name, String asset, int amount, int replenishHunger, int replenishThirst)
        {
            super(id, name, asset, amount);

            this.replenishHunger = replenishHunger;
            this.replenishThirst = replenishThirst;
        }
        @Override
        public void use(int amount) {
            super.use(amount);

            System.out.println("Replenish the user hunger with " + replenishHunger + " and thirst " + replenishThirst);
        }
    }
}
