import java.util.ArrayList;
import java.util.List;

public abstract class Ticket {
    private float baseFarePrice = 100;
    protected int bagageLimit = 2;
    protected List<String> otherFacilities = new ArrayList<>();
    protected String destination;

    public Ticket(String destination)
    {
        this.destination = destination;
    }
    public float calculateFare()
    {
        return baseFarePrice;
    }
    protected abstract String getHeader();
    public String getInformation()
    {
        String info = getHeader();
        info += "\nDestination: " + destination;
        info += "\nPrice: " + calculateFare() + "$";
        info += "\nBagage Limit: " + bagageLimit + "kg";

        if(otherFacilities.size() > 0)
        {
            info += "\nOther Facilities:";
            for(int i = 0; i < otherFacilities.size(); i++)
            {
                info += "\n" + (i + 1) + ". " + otherFacilities.get(i);
            }
        }

        return info;
    }
    public static String makeHeader(String header)
    {
        return "-------------------------------[" + header + "]-------------------------------";
    }
}
