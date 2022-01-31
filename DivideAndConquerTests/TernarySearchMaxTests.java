import org.junit.Test;
import static org.junit.Assert.*;

public class TernarySearchMaxTests {

    @Test
    public void example() {
        double[] input = { 2, 4, 6, 8, 10, 12, 14, 16, 6, 2 };
        assertEquals(7, TernarySearchMax.findPictureTime(input));
    }

    @Test
    public void testLeftEdge() {
        double[] input = { 9, 8, 7, 6, 5, 4, 3, 2, 1, 0 };
        assertEquals(0, TernarySearchMax.findPictureTime(input));
    }

    @Test
    public void testLeft() {
        double[] input = { 1, 2, 9, 8, 7, 6, 5, 4, 3, 1 };
        assertEquals(2, TernarySearchMax.findPictureTime(input));
    }

    @Test
    public void testRight() {
        double[] input = { 1, 2, 4, 5, 6, 7, 8, 9, 3, 1 };
        assertEquals(7, TernarySearchMax.findPictureTime(input));
    }

    @Test
    public void testMiddle() {
        double[] input = { 3, 4, 5, 6, 8, 12, 3, 2, 1, 0 };
        assertEquals(5, TernarySearchMax.findPictureTime(input));
    }




}
