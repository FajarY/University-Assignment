import java.util.UUID;

public abstract class PostFactory<T>
{
    public abstract T create(Network network, UUID id, UUID userId);

    public static class MessagePostFactory extends PostFactory<MessagePost> {
        @java.lang.Override
        public MessagePost create(Network network, UUID id, UUID userId)
        {
            return new MessagePost(network, id, userId);
        }
    }
    public static class  PhotoPostFactory extends PostFactory<PhotoPost>
    {
        @java.lang.Override
        public PhotoPost create(Network network, UUID id, UUID userId)
        {
            return new PhotoPost(network, id, userId);
        }
    }
}