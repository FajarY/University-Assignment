public class FirstClassTicket extends Ticket {
    public FirstClassTicket(String destination)
    {
        super(destination);

        bagageLimit += 4;
        otherFacilities.add("Single bedroom");
        otherFacilities.add("Luxury full course meal");
        otherFacilities.add("Noise reduction walls");
        otherFacilities.add("Free champagne");
        otherFacilities.add("In bedroom bathroom facility");
    }

    @Override
    public float calculateFare() {
        return super.calculateFare() * 1.5f;
    }

    @Override
    protected String getHeader() {
        return makeHeader("First Class");
    }
}
