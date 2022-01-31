import org.junit.Test;
import static org.junit.Assert.*;


public class IntervalPartitioningTest {

    @Test
    public void example() {
        int n = 6;
        int[] starttimes = { 0, 6, 3, 1, 8, 3, 1 };
        int[] durations = { 0, 1, 5, 2, 3, 3, 4 };
        assertEquals(3, IntervalPartitioning.fixMyBikesPlease(n, starttimes, durations));
    }



}
