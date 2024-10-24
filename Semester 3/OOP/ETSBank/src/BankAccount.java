public class BankAccount {
    private String bankType;
    private float balance;
    public BankAccount(String bankType)
    {
        this.bankType = bankType;
    }
    public boolean deposit(float balance)
    {
        if(balance < 0)
        {
            return false;
        }

        this.balance += balance;

        return true;
    }
    public boolean setBalance(float balance)
    {
        if(balance < 0)
        {
            return false;
        }

        this.balance = balance;

        return true;
    }
    public float withdraw(float amount)
    {
        if(amount < 0)
        {
            return 0f;
        }

        if(amount > balance)
        {
            float tempBalance = balance;

            setBalance(0);
            return tempBalance;
        }

        setBalance(balance - amount);
        return amount;
    }
    public float getBalance()
    {
        return balance;
    }
    public String getBankType()
    {
        return bankType;
    }
}
