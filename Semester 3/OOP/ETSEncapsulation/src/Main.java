public class Main {
    public static void main(String[] args) {
        User user = new User();

        user.setName("Fajar");
        user.setAge(-1);
        user.setBalance(-100f);

        user.setAge(19);
        user.setBalance(9999999f);

        System.out.println(user.getDisplay());
    }
}