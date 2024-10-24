import java.util.ArrayList;
import java.util.List;

public class CoffeeMachine {
    public List<Coffee> coffeeList;
    public CoffeeMachine()
    {
        coffeeList = new ArrayList<>();
    }
    public void addCoffee(Coffee coffee)
    {
        for(int i = 0; i < coffeeList.size(); i++)
        {
            if(coffee.getName().equals(coffeeList.get(i).getName()))
            {
                System.out.println("Cannot add same coffee type!");
                return;
            }
        }

        coffeeList.add(coffee);
    }
    public boolean buyCoffee(String coffeeName, Customer customer)
    {
        Coffee coffee = null;
        for(int i = 0; i < coffeeList.size(); i++)
        {
            if(coffeeList.get(i).getName().equals(coffeeName))
            {
                coffee = coffeeList.get(i);
            }
        }
        if(coffee == null)
        {
            System.out.println("There is no coffee with name " + coffeeName);
            return false;
        }

        boolean succeed = customer.useBalance(coffee.getCost());
        if(succeed)
        {
            System.out.println("Customer " + customer.getName() + " successfully purchased coffee " + coffee.getName());
            System.out.println("Current balance : " + customer.getBalance());
            return true;
        }

        return false;
    }
}
