import java.util.UUID;

public class User {
    private String id;
    private String name;

    public User(String name)
    {
        this.id = UUID.randomUUID().toString();
        this.name = name;
    }
    public String getName()
    {
        return name;
    }
    public String getId()
    {
        return id;
    }
}
