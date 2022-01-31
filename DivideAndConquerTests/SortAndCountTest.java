import org.junit.Test;
import static org.junit.Assert.*;


public class SortAndCountTest {

    @Test
    public void countInversions() {
        int[] input = { 2, 1, 0, 8 };
        assertEquals(3, SortAndCount.countInversions(input));
    }



}
