public class UserTicketItem {
    private int balance;
    private int ticketAmount;

    public UserTicketItem()
    {
        this.balance = 0;
        this.ticketAmount = 0;
    }

    public int getBalance()
    {
        return balance;
    }
    public int getTicketAmount()
    {
        return  ticketAmount;
    }
    public void setBalance(int amount)
    {
        balance = amount;
    }
    public void setTicketAmount(int amount)
    {
        ticketAmount = amount;
    }
}
