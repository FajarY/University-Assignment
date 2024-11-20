public class Hewan extends MakhlukHidup {
    public Hewan(String name)
    {
        super(name);
    }

    @Override
    public void doSomething() {
        System.out.println(name + " sedang berlari dengan 4 kaki");
    }

    @Override
    public void breathe() {
        System.out.println(name + " membutuhkan oksigen untuk bernapas");
    }

    @Override
    public void grow() {
        System.out.println(name + " membutuhkan rumput untuk dimakan");
    }
}
