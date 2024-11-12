import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        EconomyTicket economyTicket = new EconomyTicket("Bali");
        System.out.println(economyTicket.getInformation());
        System.out.println();

        BusinessTicket businessTicket = new BusinessTicket("Bali");
        System.out.println(businessTicket.getInformation());
        System.out.println();

        FirstClassTicket firstClassTicket = new FirstClassTicket("Bali");
        System.out.println(firstClassTicket.getInformation());
        System.out.println();
    }
}