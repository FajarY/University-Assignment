public class BusinessTicket extends Ticket {
    public BusinessTicket(String destination)
    {
        super(destination);

        bagageLimit += 3;
        otherFacilities.add("Leather seats");
        otherFacilities.add("Full course meal");
    }

    @Override
    public float calculateFare() {
        return super.calculateFare() * 1.25f;
    }

    @Override
    protected String getHeader() {
        return makeHeader("Business");
    }
}
