public class Main {
    public static void main(String[] args) {
        Trip trip = new Trip("Denpasar", 200f, 10f, 5);

        Penumpang fajar = new Penumpang("Fajar");
        Penumpang arya = new Penumpang("Arya");
        Penumpang randi = new Penumpang("Randi");

        trip.addPenumpang(fajar);
        trip.addPenumpang(arya);
        trip.addPenumpang(randi);

        trip.printPenumpangs();
        trip.startTrip();

        Penumpang nanda = new Penumpang("Nanda");
        Penumpang arda = new Penumpang("Arda");
        Penumpang sudewa = new Penumpang("Sudewa");

        trip.addPenumpang(nanda);
        trip.addPenumpang(arda);
        trip.addPenumpang(sudewa);

        trip.startTrip();
    }
}