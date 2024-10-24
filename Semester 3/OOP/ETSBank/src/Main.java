public class Main {
    public static void main(String[] args) {
        Customer fajar = new Customer("Fajar", "BCA");
        Customer arya = new Customer("Arya", "Mandiri");

        fajar.deposit(100f);
        fajar.deposit(200f);
        fajar.deposit(-1f);

        arya.deposit(500f);

        fajar.withdraw(150f);

        arya.withdraw(-1f);
        arya.withdraw(300f);
        arya.withdraw(500f);
    }
}