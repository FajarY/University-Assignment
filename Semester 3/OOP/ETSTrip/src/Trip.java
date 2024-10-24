import java.util.ArrayList;
import java.util.List;

public class Trip {
    private List<Penumpang> penumpangList;
    private String destination;
    private float distanceKM;
    private float costPerKM;
    private int minimumPenumpang;

    public Trip(String destination, float distanceKM, float costPerKM, int minimumPenumpang)
    {
        this.destination = destination;
        this.distanceKM = distanceKM;
        this.costPerKM = costPerKM;
        this.minimumPenumpang = minimumPenumpang;

        penumpangList = new ArrayList<>();
    }
    public void addPenumpang(Penumpang penumpang)
    {
        this.penumpangList.add(penumpang);
    }
    public void printPenumpangs()
    {
        System.out.println("Penumpang list");
        for(int i = 0; i < penumpangList.size(); i++)
        {
            System.out.println("[" + (i + 1) + "], " + penumpangList.get(i).getName());
        }
        System.out.println();
    }
    public void startTrip()
    {
        if(penumpangList.size() < minimumPenumpang)
        {
            System.out.println("Tidak bisa memulai perjalanan, penumpang kurang");
            System.out.println("Minimum : " + minimumPenumpang);
            System.out.println("Jumlah Penumpang : " + penumpangList.size());
            System.out.println();

            return;
        }

        System.out.println("Memulai perjalanan menuju " + destination);
        System.out.println("Dengan biaya : " + getCost());
        System.out.println("Dengan penumpang");
        printPenumpangs();
    }
    public float getCost()
    {
        return distanceKM * costPerKM;
    }
}
