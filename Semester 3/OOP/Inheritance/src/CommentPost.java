import java.util.UUID;

public class CommentPost extends Post
{
    private String comment;
    public CommentPost(Network network, UUID id, UUID user)
    {
        super(network, id, user);
    }
    public void setComment(String comment)
    {
        this.comment = comment;
    }
    @Override
    public String getDisplay(int tabCount)
    {
        return getTabs(tabCount) + "Author : " + network.getUsername(getAuthor()) + ", Comment : " + comment +
                ", Like/Dislike : " + getLikesCount() + "/" + getDislikesCount() + "\n" + getCommentDisplay(tabCount + 1);
    }
}
