import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SalesItemTest {
    @Test
    public void testComment() {
        SalesItem item = new SalesItem("Innova 2.4V", 1000000000);
        assertTrue(item.addComment("Fajar", "A very reliable car", 5));
        assertFalse(item.addComment("Randi", "Bad car not good on gas mileage", -1));
        assertTrue(item.addComment("Arya", "A good car but i think avanza is better", 4));

        assertEquals(item.getNumberOfComments(), 2);
    }

    @Test
    public void testUpvote()
    {
        SalesItem item = new SalesItem("Innova 2.4V", 1000000000);
        item.addComment("Fajar", "A very reliable car", 5);
        item.addComment("Arya", "A good car but i think avanza is better", 4);

        for(int i = 0; i < 5; i++)
        {
            item.upvoteComment(0);
            item.upvoteComment(1);
        }

        item.downvoteComment(1);
        item.downvoteComment(1);
        item.upvoteComment(0);

        assertEquals(6, item.findMostHelpfulComment().getVoteCount());
    }

    @Test
    public void testRemoveComment()
    {
        SalesItem item = new SalesItem("Innova 2.4V", 1000000000);
        assertTrue(item.addComment("Fajar", "A very reliable car", 5));
        assertTrue(item.addComment("Arya", "A good car but i think avanza is better", 4));
        assertTrue(item.addComment("Nanda", "Bad car", 0));

        item.removeComment(2);

        assertEquals(2, item.getNumberOfComments());
    }
}