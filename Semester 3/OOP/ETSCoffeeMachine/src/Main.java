public class Main {
    private static CoffeeMachine coffeeMachine;
    private static Customer customer;

    public static void main(String[] args) {
        initializeCoffeeMachine();
        initializeCustomer();

        run();
    }
    private static void initializeCoffeeMachine()
    {
        coffeeMachine = new CoffeeMachine();
        coffeeMachine.addCoffee(new Coffee("Americano", 5f));
        coffeeMachine.addCoffee(new Coffee("Mocachino", 15f));
        coffeeMachine.addCoffee(new Coffee("Good Day", 2f));
        coffeeMachine.addCoffee(new Coffee("Luwak", 30f));
        coffeeMachine.addCoffee(new Coffee("Black", 20f));
        coffeeMachine.addCoffee(new Coffee("Latte", 25f));
    }
    private static void initializeCustomer()
    {
        customer = new Customer("Fajar", 100f);
    }
    private static void run()
    {
        coffeeMachine.buyCoffee("Americano", customer);
        coffeeMachine.buyCoffee("Mocachino", customer);

        coffeeMachine.buyCoffee("Starbucks", customer);

        coffeeMachine.buyCoffee("Latte", customer);
        coffeeMachine.buyCoffee("Latte", customer);
        coffeeMachine.buyCoffee("Luwak", customer);

        coffeeMachine.buyCoffee("Good Day", customer);
    }
}