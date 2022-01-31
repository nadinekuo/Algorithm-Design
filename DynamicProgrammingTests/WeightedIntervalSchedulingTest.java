import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class WeightedIntervalSchedulingTest {

    @Test
    public void testScheduleRetrievedFiveJobs() {
        int[] s = { 0, 0, 1, 4, 2, 6};
        int[] f = { 0, 3, 5, 6, 7, 8 };
        int[] v = { 0, 2, 4, 4, 7, 2 };
        int[] p = { 0, -1, -1, 1, -1, 3 };
        int[] mem = { 0, 2, 4, 6, 7, 8 };
        LinkedList<Integer> expected = new LinkedList<>();
        expected.add(1);
        expected.add(3);
        expected.add(5);
        Assertions.assertEquals(expected, WeightedIntervalScheduling.retrieveScheduledJobsLinkedList(5, s, f, v, p, mem));
    }

    @Test
    public void testScheduleRetrieved() {
        int[] s = { 0, 0, 1, 3 };
        int[] f = { 0, 3, 4, 8 };
        int[] v = { 0, 3, 5, 7 };
        int[] p = { 0, -1, -1, 1 };
        int[] mem = { 0, 3, 5, 10 };
        LinkedList<Integer> expected = new LinkedList<>();
        expected.add(1);
        expected.add(3);
        Assertions.assertEquals(expected, WeightedIntervalScheduling.retrieveScheduledJobsLinkedList(3, s, f, v, p, mem));
    }


    @Test
    public void testMaxWeightScheduled() {
        int[] s = { 0, 0, 1, 3 };
        int[] f = { 0, 3, 4, 8 };
        int[] v = { 0, 3, 5, 7 };
        int[] p = { 0, -1, -1, 1 };
        Assertions.assertEquals(10, WeightedIntervalScheduling.computeMaximalWeight(3, s, f, v, p));
    }

    @Test
    public void testPredecessors() {
        int[] s = { 0, 0, 1, 3 };
        int[] f = { 0, 3, 4, 8 };
        int[] v = { 0, 3, 5, 7 };
        int[] p = { 0, -1, -1, 1 };
        int[] solution = WeightedIntervalScheduling.computePredecessors(3, s, f, v);
        solution[0] = 0;
        Assertions.assertArrayEquals(p, solution);
    }


}
