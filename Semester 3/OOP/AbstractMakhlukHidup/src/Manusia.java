public class Manusia extends MakhlukHidup {
    public Manusia(String name)
    {
        super(name);
    }
    @Override
    public void doSomething() {
        System.out.println(name + " sedang berbicara dengan temannya");
    }

    @Override
    public void grow() {
        System.out.println(name + " membutuhkan asupan protein dan nutrisi yang cukup");
    }

    @Override
    public void breathe() {
        System.out.println(name + " membutuhkan oksigen untuk bernafas");
    }
}
