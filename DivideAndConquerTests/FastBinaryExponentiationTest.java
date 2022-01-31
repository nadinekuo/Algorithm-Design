import org.junit.Test;
import static org.junit.Assert.*;


public class FastBinaryExponentiationTest {


    @Test
    public void testExampleA() {
        int[] a = new int[] { 3, -2, 6 };
        int[] b = new int[] { 2, 5, 8 };
        int[][] c = FastBinaryExponentiation.computeC(a, b);
        int[][] expected = new int[][] { { 9, 243, 6561 }, { 4, -32, 256 }, { 36, 7776, 1679616 } };
        assertArrayEquals(expected, c);
    }


}
