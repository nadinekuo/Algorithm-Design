import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ithSmallestElementTests {


    @Test
    public void testBelowPivot() {

        int[] array = new int[]{25, 21, 98, 100, 76, 22, 43, 60, 89, 87};
        // 21, 22, 25, 43, 60, 76, 87, 89, 98, 100     <--- idx 3 (0-based) gives 43 if sorted
        assertEquals(43, ithSmallestElement.findIthList(array, 3));

    }

    @Test
    public void testHigherThanPivot() {

        int[] array = new int[]{25, 21, 98, 100, 76, 22, 43, 60, 89, 87};
        assertEquals(76, ithSmallestElement.findIthList(array, 5));

    }

    @Test
    public void testEqualToPivot() {

        int[] array = new int[]{25, 21, 98, 100, 76, 22, 43, 60, 89, 87};
        assertEquals(98, ithSmallestElement.findIthList(array, 8));

    }

    @Test
    public void testFirst() {

        int[] array = new int[]{1, 2, 3, 4, 5, 1000, 8, 9, 99};
        assertEquals(1, ithSmallestElement.findIthList(array, 0));

    }

    @Test
    public void testRandom() {

        int[] array = new int[]{1, 2, 3, 4, 5, 1000, 8, 9, 99};
        assertEquals(99, ithSmallestElement.findIthList(array, 7));

    }



    @Test
    public void testOrderedArray() {

        int[] array = new int[]{1, 2, 3, 4, 5, 6};
        assertEquals(5, ithSmallestElement.findIthList(array, 4));

    }






}
