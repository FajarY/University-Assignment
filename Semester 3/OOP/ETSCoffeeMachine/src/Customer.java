public class Customer {
    public String name;
    public float balance;

    public Customer(String name, float balance)
    {
        this.name = name;
        this.balance = balance;
    }
    public String getName()
    {
        return name;
    }
    public float getBalance()
    {
        return balance;
    }
    public boolean useBalance(float amount)
    {
        if(amount < 0f)
        {
            System.out.println("Cannot use negative balance amount!");
        }
        float check = balance - amount;

        if(check < 0f)
        {
            System.out.println("Balance is not sufficient!");
            return false;
        }

        balance = check;
        return true;
    }
    public void addBalance(float amount)
    {
        if(amount < 0f)
        {
            System.out.println("Cannot add negative balance!");
            return;
        }

        this.balance += amount;
    }
}
