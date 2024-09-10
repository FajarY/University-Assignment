public class Main {
    public static void main(String[] args) {
        TicketMachine ticketMachine = new TicketMachine(200);

        System.out.println("---------------EXAMPLE----------------");
        runExample(ticketMachine);
        System.out.println("--------------------------------------");
    }
    public static void runExample(TicketMachine ticketMachine)
    {
        User fajar = new User("Fajar Yasodana");

        ticketMachine.addUserBalance(fajar, 200);
        ticketMachine.addUserBalance(fajar, 500);

        ticketMachine.userBuyTicket(fajar, 1);

        User john = new User("John Smith");

        ticketMachine.addUserBalance(john, 100);
        ticketMachine.userBuyTicket(john, 1);

        ticketMachine.showUserBalanceAndTickets(john);

        User putu = new User("Putu");
        ticketMachine.userBuyTicket(putu, 2);
    }
}