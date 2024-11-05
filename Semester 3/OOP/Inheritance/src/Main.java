import java.util.UUID;

public class Main
{
    public static void main(String[] args)
    {
        Network network = new Network();

        UUID fajar = network.createUser("Fajar");
        UUID randi = network.createUser("Randi");
        UUID arya = network.createUser("Arya");
        UUID arda = network.createUser("Arda");
        UUID nanda = network.createUser("Nanda");

        network.follow(fajar, randi);
        network.follow(randi, fajar);
        network.follow(arda, arya);
        network.follow(randi, nanda);
        network.follow(randi, arya);
        network.follow(fajar, arya);

        MessagePost fajarMessage = network.createPost(MessagePost.class, fajar);
        fajarMessage.setCaption("Im in departement");
        fajarMessage.setMessage("Hello guys im currently in informatika");

        Comment(network, fajarMessage, arya, "Where are you i can't seem to find you");

        fajarMessage.like(randi);
        fajarMessage.like(arya);

        MessagePost randiMessage = network.createPost(MessagePost.class, randi);
        randiMessage.setCaption("Im doing my PBO Assignment");
        randiMessage.setMessage("Its very easy bro, i need harder assignment");

        Comment(network, randiMessage, fajar, "You should teach me king");
        Comment(network, randiMessage, arya, "Me too bro!");

        randiMessage.dislike(nanda);
        randiMessage.dislike(arda);
        randiMessage.dislike(fajar);
        randiMessage.dislike(arya);

        PhotoPost aryaPhoto = network.createPost(PhotoPost.class, arya);
        aryaPhoto.setCaption("Me with my friends!");
        aryaPhoto.setPhoto("/img/arya/3123jhjhhwjer1432423.jpg");

        CommentPost randiComment = Comment(network, aryaPhoto, randi, "Sorry i missclick dislike");

        aryaPhoto.dislike(randi);
        aryaPhoto.like(fajar);

        aryaPhoto.like(arda);
        aryaPhoto.like(nanda);

        randiComment.like(fajar);
        randiComment.like(arya);

        System.out.println(network.getAllDisplay());
    }
    public static CommentPost Comment(Network network, Post post, UUID userComment, String message)
    {
        CommentPost postInstance = network.createComment(userComment, post.getId());
        postInstance.setComment(message);

        return postInstance;
    }
}