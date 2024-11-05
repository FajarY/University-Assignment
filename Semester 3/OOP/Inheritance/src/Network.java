import java.io.Console;
import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;
import java.util.function.Supplier;

public class Network
{
    private UUID secretKey;
    private HashMap<UUID, Post> postDatabase;
    private HashMap<UUID, User> userDatabase;
    private HashMap<Class, PostFactory> factory;

    public Network()
    {
        secretKey = UUID.randomUUID();

        postDatabase = new HashMap<>();
        userDatabase = new HashMap<>();
        factory = new HashMap<>();

        factory.put(MessagePost.class, new PostFactory.MessagePostFactory());
        factory.put(PhotoPost.class, new PostFactory.PhotoPostFactory());
    }
    public void like(UUID user, UUID post)
    {
        Post postInstance = postDatabase.getOrDefault(post, null);
        User userInstance = userDatabase.getOrDefault(user, null);
        if(postInstance == null || userInstance == null)
        {
            System.out.println("Cannot like post! invalid parameters");
            return;
        }

        postInstance.addLikeReference(secretKey, user);
    }
    public void dislike(UUID user, UUID post)
    {
        Post postInstance = postDatabase.getOrDefault(post, null);
        User userInstance = userDatabase.getOrDefault(user, null);
        if(postInstance == null || userInstance == null)
        {
            System.out.println("Cannot dislike post! invalid parameters");
            return;
        }

        postInstance.addDislikeReference(secretKey, user);
    }
    public UUID createUser(String username)
    {
        User user = new User();
        UUID id = UUID.randomUUID();
        user.set(this, id, username);

        userDatabase.put(id, user);

        return id;
    }
    public String getUsername(UUID id)
    {
        return userDatabase.get(id).getUsername();
    }
    public String getDisplay(UUID post, int tabCount)
    {
        return postDatabase.get(post).getDisplay(tabCount);
    }
    public String getAllDisplay()
    {
        String display = "Social Network\n";
        Iterator<UUID> postIterator = postDatabase.keySet().iterator();
        display += "-------------------------------------------------------------------------------" +
                "------------------------------------------------------------------------------------------\n";

        while (postIterator.hasNext())
        {
            UUID id = postIterator.next();

            Post post = postDatabase.get(id);
            if(post instanceof CommentPost)
            {
                continue;
            }

            display += getDisplay(id, 0);
            display += "-------------------------------------------------------------------------------" +
                    "------------------------------------------------------------------------------------------\n";
        }

        return display;
    }
    public <T extends Post> T createPost(Class<T> type, UUID user)
    {
        User userInstance = userDatabase.getOrDefault(user, null);
        if(userInstance == null)
        {
            System.out.println("Cannot create post, unknown user id!");
            return null;
        }

        PostFactory factory = this.factory.get(type);

        UUID id = UUID.randomUUID();
        T post = (T)factory.create(this, id, user);

        postDatabase.put(id, post);
        userInstance.addPostReference(id);

        return post;
    }
    public CommentPost createComment(UUID user, UUID post)
    {
        Post postInstance = postDatabase.getOrDefault(post, null);
        User userInstance = userDatabase.getOrDefault(user, null);
        if(postInstance == null || userInstance == null)
        {
            System.out.println("Cannot create comment! invalid parameters");
            return null;
        }

        UUID id = UUID.randomUUID();
        CommentPost comment = new CommentPost(this, id, user);
        postDatabase.put(id, comment);

        userInstance.addCommentReference(id);
        postInstance.addCommentReference(secretKey, id);

        return comment;
    }
    public void follow(UUID user, UUID target)
    {
        if(user == target)
        {
            return;
        }

        User targetInstance = userDatabase.get(target);
        targetInstance.addFollowReference(secretKey, user);
    }
    public void unfollow(UUID user, UUID target)
    {
        if(user == target)
        {
            return;
        }

        User targetInstance = userDatabase.get(target);
        targetInstance.unfollowReference(secretKey, user);
    }
    public int getFollowers(UUID user)
    {
        return userDatabase.get(user).getFollowersCount();
    }
    public boolean isValidSecret(UUID other)
    {
        return other.equals(secretKey);
    }
}
