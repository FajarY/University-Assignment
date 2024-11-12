public class Comment {
    private String author;
    private String text;
    private int rating; // rating between 1 and 5
    private int voteCount;

    public Comment(String author, String text, int rating) {
        this.author = author;
        this.text = text;
        this.rating = rating;
        this.voteCount = 0; // initialize vote count to 0
    }

    public String getAuthor() {
        return author;
    }

    public String getText() {
        return text;
    }

    public int getRating() {
        return rating;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void upvote() {
        voteCount++;
    }

    public void downvote() {
        voteCount--;
    }

    public String getFullDetails() {
        return "Author: " + author + "\n" +
                "Rating: " + rating + "\n" +
                "Vote Count: " + voteCount + "\n" +
                "Comment: " + text;
    }
}
