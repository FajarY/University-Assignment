public class EconomyTicket extends Ticket {
    public EconomyTicket(String destination)
    {
        super(destination);
    }

    @Override
    public float calculateFare() {
        return super.calculateFare() * 0.9f;
    }

    @Override
    protected String getHeader() {
        return makeHeader("Economy");
    }
}
