public class Main {
    public static void main(String[] args) {
        Manusia manusia = new Manusia("Fajar");

        Tumbuhan tumbuhan = new Tumbuhan("Pohon Mangga");

        Hewan hewan = new Hewan("Sapi");

        manusia.doSomething();
        manusia.breathe();
        manusia.grow();

        hewan.doSomething();
        hewan.breathe();
        hewan.grow();

        tumbuhan.doSomething();
        tumbuhan.breathe();
        tumbuhan.grow();
    }
}