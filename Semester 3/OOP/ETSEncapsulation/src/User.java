public class User {
    private String name;
    private int age;
    private float balance;

    public User()
    {

    }
    public String getDisplay()
    {
        return name + ", age : " + age + " , balance : " + balance;
    }
    public void setName(String value)
    {
        if(value == null)
        {
            System.out.println("Cannot set with null name!");
            return;
        }
        this.name = value;
    }
    public void setAge(int age)
    {
        if(age < 0)
        {
            System.out.println("Cannot set a negative age!");
            return;
        }

        this.age = age;
    }
    public void setBalance(float balance)
    {
        if(balance < 0)
        {
            System.out.println("Cannot set a negative balance!");
            return;
        }
        this.balance = balance;
    }
    public String getName()
    {
        return name;
    }
    public int getAge()
    {
        return age;
    }
    public float getBalance()
    {
        return balance;
    }
}
