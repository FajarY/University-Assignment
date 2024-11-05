import javax.xml.stream.events.Comment;
import java.lang.annotation.Retention;
import java.time.LocalDateTime;
import java.util.*;

public abstract class Post
{
    private UUID id;
    private UUID author;
    protected Network network;
    private HashSet<UUID> userLikesReference;
    private HashSet<UUID> userDislikesReference;
    private HashSet<UUID> commentsReference;
    private LocalDateTime created;

    public Post(Network network, UUID id, UUID author)
    {
        this.id = id;
        this.author = author;
        this.network = network;

        userLikesReference = new HashSet<>();
        userDislikesReference = new HashSet<>();
        commentsReference = new HashSet<>();
        created = LocalDateTime.now();
    }
    public UUID getId()
    {
        return id;
    }
    public UUID getAuthor()
    {
        return author;
    }
    public int getLikesCount()
    {
        return userLikesReference.size();
    }
    public int getDislikesCount()
    {
        return userDislikesReference.size();
    }
    public void like(UUID user)
    {
        network.like(user, id);
    }
    public void dislike(UUID user)
    {
        network.dislike(user, id);
    }
    public void addCommentReference(UUID networkSecret, UUID comment)
    {
        if(!network.isValidSecret(networkSecret))
        {
            System.out.println("Cannot make comment from unauthorized source");
            return;
        }

        commentsReference.add(comment);
    }
    public void addLikeReference(UUID networkSecret, UUID user)
    {
        if(!network.isValidSecret(networkSecret))
        {
            System.out.println("Cannot like from unauthorized source");
            return;
        }

        userLikesReference.add(user);
    }
    public void addDislikeReference(UUID networkSecret, UUID user)
    {
        if(!network.isValidSecret(networkSecret))
        {
            System.out.println("Cannot dislike from unauthorized source");
            return;
        }

        userDislikesReference.add(user);
    }
    public abstract String getDisplay(int tabCount);
    protected Iterator<UUID> getComments()
    {
        return commentsReference.iterator();
    }
    protected String getCommentDisplay(int tabCount)
    {
        String display = "";
        Iterator<UUID> iterator = getComments();

        if(iterator.hasNext())
        {
            display = "Comments\n";
        }
        while (iterator.hasNext())
        {
            UUID comment = iterator.next();
            display += network.getDisplay(comment, tabCount);
        }

        return display;
    }
    protected static String getTabs(int tabCount)
    {
        String tab = "";
        for(int i = 0; i < tabCount; i++)
        {
            tab += "\t";
        }
        return tab;
    }
}
