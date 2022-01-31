import org.junit.Test;
import static org.junit.Assert.*;


public class MinimizeLatenessTest {




    @Test
    public void example() {
        int n = 5;
        int m = 2;
        int[] deadlines = { 0, 3, 1, 1, 1, 2 };
        assertEquals(1, MinimizeLateness.maxLatenessSortArray(n, m, deadlines));
    }


    @Test
    public void testOneJob() {
        int n = 1;
        int m = 1;
        int[] deadlines = { 0, 1};
        assertEquals(0, MinimizeLateness.maxLatenessSortArray(n, m, deadlines));
    }

    @Test
    public void testTwoJobsOneProcessor() {
        int n = 2;
        int m = 1;
        int[] deadlines = { 0, 1, 1};
        assertEquals(1, MinimizeLateness.maxLatenessSortArray(n, m, deadlines));
    }


    @Test
    public void testThreeJobsOneProcessor() {
        int n = 3;
        int m = 1;
        int[] deadlines = { 0, 1, 1, 1};
        assertEquals(2, MinimizeLateness.maxLatenessSortArray(n, m, deadlines));
    }



    @Test
    public void testTwoJobsTwoProcessors() {
        int n = 2;
        int m = 2;
        int[] deadlines = { 0, 1, 1};
        assertEquals(0, MinimizeLateness.maxLatenessSortArray(n, m, deadlines));
    }


    @Test
    public void testThreeJobsTwoProcessors() {
        int n = 3;
        int m = 2;
        int[] deadlines = { 0, 1, 1, 1};
        assertEquals(1, MinimizeLateness.maxLatenessSortArray(n, m, deadlines));
    }


    @Test
    public void testFourJobsOneProcessorAllOnTime() {
        int n = 4;
        int m = 1;
        int[] deadlines = { 0, 1, 2, 3, 4};
        assertEquals(0, MinimizeLateness.maxLatenessSortArray(n, m, deadlines));
    }

    @Test
    public void testFourJobsOneProcessorAllOnTimeReversed() {
        int n = 4;
        int m = 1;
        int[] deadlines = { 0, 4, 3, 2, 1};
        assertEquals(0, MinimizeLateness.maxLatenessSortArray(n, m, deadlines));
    }


    @Test
    public void testFourJobsOneProcessor() {
        int n = 4;
        int m = 1;
        int[] deadlines = { 0, 1, 1, 2, 2};
        assertEquals(2, MinimizeLateness.maxLatenessSortArray(n, m, deadlines));
    }



    @Test
    public void testFourJobsTwoProcessorsEqualDeadlines() {
        int n = 4;
        int m = 2;
        int[] deadlines = { 0, 1, 1, 1, 1};
        assertEquals(1, MinimizeLateness.maxLatenessSortArray(n, m, deadlines));
    }


    @Test
    public void testFiveJobsTwoProcessors() {
        int n = 5;
        int m = 2;
        int[] deadlines = { 0, 1, 1, 2, 1, 2};
        assertEquals(1, MinimizeLateness.maxLatenessSortArray(n, m, deadlines));
    }


    @Test
    public void testSixJobsTwoProcessors() {
        int n = 6;
        int m = 2;
        int[] deadlines = { 0, 2, 1, 2, 1, 2, 3};
        assertEquals(1, MinimizeLateness.maxLatenessSortArray(n, m, deadlines));
    }


}
