import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class PrefixSummingTest {


    @Test
    public void testPrefixSum() {

        int[] binaryTree = new int[]{77, 36, 41, 10, 26, 31, 10, 6, 4, 16, 10, 16, 15, 2, 8};

        PrefixSumming.prefixSum(binaryTree, 0, 3, 0);

        // Only the 8 leaf indices contain result values
        for (int i = 0; i < 8; i++) {
            System.out.println(binaryTree[i + 7]);  // skip first 7 internal nodes
        }


    }


    @Test
    public void evenSumTest() {

        int[] arr = new int[]{1, 2, 3, 4, 5, 6};
        assertEquals(21, PrefixSumming.parallelSum(arr, 0, arr.length - 1));

    }


    @Test
    public void oddSumTest() {

        int[] arr = new int[]{1, 2, 3, 4, 5, 6, 7};
        assertEquals(28, PrefixSumming.parallelSum(arr, 0, arr.length - 1));

    }




}
