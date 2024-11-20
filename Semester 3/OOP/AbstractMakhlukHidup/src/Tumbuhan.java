public class Tumbuhan extends MakhlukHidup {
    public Tumbuhan(String name)
    {
        super(name);
    }
    @Override
    public void doSomething() {
        System.out.println(name + " sedang melakukan fotosintesis");
    }

    @Override
    public void grow() {
        System.out.println(name + " membutuhkan siraman air yang cukup dan tanah yang subur");
    }

    @Override
    public void breathe() {
        System.out.println(name + " menghirup CO2 pada siang hari untuk melakukan fotosintesis, menghirup O2 pada malam hari untuk merubah glukosa menjadi energi");
    }
}
