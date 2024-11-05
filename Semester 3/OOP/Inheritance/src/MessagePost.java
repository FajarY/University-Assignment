import java.util.UUID;

public class MessagePost extends Post
{
    private String caption;
    private String message;

    public MessagePost(Network network, UUID id, UUID user)
    {
        super(network, id, user);
    }
    public void setCaption(String caption)
    {
        this.caption = caption;
    }
    public void setMessage(String message)
    {
        this.message = message;
    }

    @Override
    public String getDisplay(int tabCount) {
        return getTabs(tabCount) + "Author : " + network.getUsername(getAuthor()) + ", Followers : " + network.getFollowers(getAuthor()) + ", Caption : " + caption +
                ", Message : " + message + ", Like/Dislike : " + getLikesCount() + "/" + getDislikesCount() + "\n" + getCommentDisplay(tabCount + 1);
    }
}
