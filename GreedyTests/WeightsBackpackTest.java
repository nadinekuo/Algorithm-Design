import org.junit.Test;
import static org.junit.Assert.*;

public class WeightsBackpackTest {


    @Test
    public void example() {
        int n = 2;
        int w = 5;
        int[] num = { 0, 2, 3 };
        int[] weight = { 0, 2, 1 };
        assertEquals(3, WeightsBackpack.minNumOfWeightsNeededForLoop(n, w, num, weight));
    }

    @Test
    public void example2() {
        int n = 4;
        int w = 50;
        int[] num = { 0, 2, 3, 18, 7 };
        int[] weight = { 0, 2, 1, 4, 21 };
        assertEquals(4, WeightsBackpack.minNumOfWeightsNeededForLoop(n, w, num, weight));
    }

    @Test
    public void example3() {
        int n = 4;
        int w = 30;
        int[] num = { 0, 1, 1, 1, 1 };
        int[] weight = { 0, 4, 5, 9, 21 };
        assertEquals(2, WeightsBackpack.minNumOfWeightsNeededForLoop(n, w, num, weight));
    }

    @Test
    public void example4() {
        int n = 2;
        int w = 30;
        int[] num = { 0, 30, 100 };
        int[] weight = { 0, 1, 42 };
        assertEquals(30, WeightsBackpack.minNumOfWeightsNeededForLoop(n, w, num, weight));
    }


    @Test
    public void testWeightsDontAddUp() {   // there is still 2kg left to fill!
        int n = 2;
        int w = 10;
        int[] num = { 0, 2, 1 };
        int[] weight = { 0, 4, 3 };
        assertEquals(2, WeightsBackpack.minNumOfWeightsNeededForLoop(n, w, num, weight));
    }


}
