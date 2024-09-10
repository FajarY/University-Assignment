import java.util.HashMap;

public class TicketMachine {
    private int price;
    private HashMap<User, UserTicketItem> userTicketBalance;

    public TicketMachine(int initialPrice)
    {
        this.price = initialPrice;
        userTicketBalance = new HashMap<>();
    }
    public int getPrice()
    {
        return price;
    }
    private static void printTicketMachineLog(String type, String message)
    {
        System.out.println("##########################");
        System.out.println("######TICKET MACHINE######");
        System.out.println("######" + type + "########");
        System.out.println("--------------------------");
        System.out.println(message);
        System.out.println("");
    }
    private static void printTicketMachineLogWithUser(User user, String type, String message)
    {
        System.out.println("##########################");
        System.out.println("######TICKET MACHINE######");
        System.out.println("--------------------------");
        System.out.println("Username : " + user.getName());
        System.out.println("Id : " + user.getId());
        System.out.println("--------------------------");
        System.out.println("######" + type + "########");
        System.out.println("--------------------------");
        System.out.println(message);
        System.out.println("");
    }
    public void addUserBalance(User user, int amount)
    {
        if(amount < 0)
        {
            printTicketMachineLog("Add Balance", "Cannot insert a negative balance!");
        }

        if(!userTicketBalance.containsKey(user))
        {
            userTicketBalance.put(user, new UserTicketItem());
        }

        UserTicketItem userBalanceReference = userTicketBalance.get(user);

        int beforeAddBalance = userBalanceReference.getBalance();

        userBalanceReference.setBalance(beforeAddBalance + amount);

        String message = "Succesfully add balance\n" + "Before balance : " + beforeAddBalance + "\nAfter balance : " + userBalanceReference.getBalance();
        printTicketMachineLogWithUser(user, "Add Balance", message);
    }
    public void userBuyTicket(User user, int amount)
    {
        String message = null;
        if(userTicketBalance.containsKey(user))
        {
            UserTicketItem item = userTicketBalance.get(user);

            int calculatePrice = amount * price;

            if(item.getBalance() < calculatePrice)
            {
                message = "User balance is insufficient\n" + "Current Balance : " + item.getBalance() + "\nTotal Price : " + calculatePrice;
            }
            else
            {
                item.setBalance(item.getBalance() - calculatePrice);
                item.setTicketAmount(item.getTicketAmount() + amount);

                message = "Succesfully purchased tickets!\n" + "Current Balance : " + item.getBalance() + "\nTotal Tickets : " + item.getTicketAmount();
            }
            printTicketMachineLogWithUser(user, "Buy Ticket", message);
        }
        else
        {
            message = "Cannot find user!\nUser haven't done a single transaction once!";
            printTicketMachineLog("Buy Ticket", message);
        }
    }
    public void showUserBalanceAndTickets(User user)
    {
        String message;
        if(userTicketBalance.containsKey(user))
        {
            UserTicketItem item = userTicketBalance.get(user);
            message = "Balance : " + item.getBalance() + "\nTickets : " + item.getTicketAmount();
            printTicketMachineLogWithUser(user, "Show Balance & Tickets", message);
        }
        else
        {
            message = "Cannot find user!\nUser haven't done a single transaction once!";
            printTicketMachineLog("Show Balance & Tickets", message);
        }
    }
}
