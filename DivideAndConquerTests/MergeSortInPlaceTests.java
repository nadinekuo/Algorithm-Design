import org.junit.Test;
import static org.junit.Assert.*;

public class MergeSortInPlaceTests {


    @Test
    public void example() {
        int[] input = { 4, 2, 5, 1, 3 };
        new MergeSortInPlace().sort(input);
        assertArrayEquals(new int[] { 1, 2, 3, 4, 5 }, input);
    }

}
