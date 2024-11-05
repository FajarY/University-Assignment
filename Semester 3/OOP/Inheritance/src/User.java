import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class User
{
    private Network network;
    private String username;
    private UUID id;
    private HashSet<UUID> postReference;
    private HashSet<UUID> commentReference;
    private HashSet<UUID> followersReference;

    public User()
    {
        postReference = new HashSet<>();
    }
    public void set(Network network, UUID id, String username)
    {
        this.id = id;
        this.network = network;
        this.username = username;
        postReference = new HashSet<>();
        commentReference = new HashSet<>();
        followersReference = new HashSet<>();
    }
    public String getUsername()
    {
        return username;
    }
    public UUID getId()
    {
        return id;
    }
    public int getFollowersCount()
    {
        return followersReference.size();
    }
    public void addPostReference(UUID id)
    {
        postReference.add(id);
    }
    public void addCommentReference(UUID id)
    {
        commentReference.add(id);
    }
    public void follow(UUID id)
    {
        network.follow(this.id, id);
    }
    public void unfollow(UUID id)
    {
        network.unfollow(this.id, id);
    }
    public void addFollowReference(UUID secret, UUID id)
    {
        if(!network.isValidSecret(secret))
        {
            System.out.println("Cannot follow from unauthorized source");
            return;
        }

        followersReference.add(id);
    }
    public void unfollowReference(UUID secret, UUID id)
    {
        if(!network.isValidSecret(secret))
        {
            System.out.println("Cannot unfollow from unauthorized source");
            return;
        }

        followersReference.remove(id);
    }
}
