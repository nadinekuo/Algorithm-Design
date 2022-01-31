import org.junit.Test;
import static org.junit.Assert.*;


public class LargestConsecutiveSumTest {

    @Test
    public void testExample() {
        int[] input = new int[] { 2, -3, 2, 1 };
        assertEquals(3, LargestConsecutiveSum.largestSum(input));
    }

    @Test
    public void testExample1Value() {
        int[] input = new int[] { 3, -3, -2, 42, -11, 2, 4, 4 };
        assertEquals(42, LargestConsecutiveSum.largestSum(input));
    }

    @Test
    public void testArraySize1() {
        int[] input = new int[] {3};
        assertEquals(3, LargestConsecutiveSum.largestSum(input));
    }

    @Test
    public void testSpanningSumTwoElements() {
        int[] input = new int[] { 3, -3, -2, 42, 11, -2, -4, -4 };
        assertEquals(53, LargestConsecutiveSum.largestSum(input));
    }

    @Test
    public void testSpanningSumFiveElements() {
        int[] input = new int[] { 3, -3, -2, 42, 11, -2, 4, 4 };   // 42 + 11 + -2 + 4 + 4 = 59
        assertEquals(59, LargestConsecutiveSum.largestSum(input));
    }

    @Test
    public void testSpanningSumThreeElements() {
        int[] input = new int[] { 3, -3, 2, 42, 11, -2, 4, 4 };
        assertEquals(55, LargestConsecutiveSum.largestSum(input));
    }

}
