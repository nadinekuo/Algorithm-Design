import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class AllPartialSumsTest {

    @Test
    public void testExampleA() {
        Integer[] arr = new Integer[] { 1, 2 };
        Set<Integer> res = AllPartialSums.partialSums(arr);
        Set<Integer> expected = new HashSet<>(Arrays.asList(0, 1, 2, 3));
        assertEquals(expected, res);
    }

    @Test
    public void testExampleB() {
        Integer[] arr = new Integer[] { 1, 2, 3, 4 };
        Set<Integer> res = AllPartialSums.partialSums(arr);
        Set<Integer> expected = new HashSet<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        assertEquals(expected, res);
    }

    @Test
    public void testEmpty() {
        Integer[] arr = new Integer[] {};
        Set<Integer> res = AllPartialSums.partialSums(arr);
        Set<Integer> expected = new HashSet<>(Collections.singletonList(0));
        assertEquals(expected, res);
    }


}
