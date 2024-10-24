public class Customer {
    private String name;
    private BankAccount bankAccount;

    public Customer(String name, String bankType)
    {
        this.name = name;
        this.bankAccount = new BankAccount(bankType);
    }
    public boolean deposit(float amount)
    {
        if(bankAccount.deposit(amount))
        {
            System.out.println(name + " ,successfully deposited " + amount + " to bank account " + bankAccount.getBankType());
            System.out.println("Current balance : " + bankAccount.getBalance());
            System.out.println();

            return true;
        }

        System.out.println(name + " ,Invalid amount for depositting");
        System.out.println();

        return false;
    }
    public float withdraw(float amount)
    {
        if(amount <= 0)
        {
            System.out.println(name + " ,Invalid amount for withdrawing");
            System.out.println();
            return 0f;
        }

        float withdrawAmount = bankAccount.withdraw(amount);
        if(withdrawAmount != amount)
        {
            System.out.println(name + " ,Insufficient balance amount, but successfully withdrawn all balance left from bank " + bankAccount.getBankType());
        }
        else
        {
            System.out.println(name + " ,Successfully withdrawn the specified amount from bank " + bankAccount.getBankType());
        }

        System.out.println("Withdrawn : " + withdrawAmount);
        System.out.println("Current Balance : " + bankAccount.getBalance());
        System.out.println();

        return withdrawAmount;
    }
    public String getName()
    {
        return name;
    }
}
