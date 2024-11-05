import java.util.UUID;

public class PhotoPost extends Post
{
    private String caption;
    private String photo;

    public PhotoPost(Network network, UUID id, UUID user)
    {
        super(network, id, user);
    }

    public void setCaption(String caption)
    {
        this.caption = caption;
    }
    public void setPhoto(String photo)
    {
        this.photo = photo;
    }
    @Override
    public String getDisplay(int tabCount) {
        return getTabs(tabCount) + "Author : " + network.getUsername(getAuthor()) + ", Followers : " + network.getFollowers(getAuthor()) + ", Caption : " + caption +
                ", Photo : " + photo + ", Like/Dislike : " + getLikesCount() + "/" + getDislikesCount() +
                "\n" + getCommentDisplay(tabCount + 1);
    }
}
