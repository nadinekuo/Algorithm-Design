import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.List;

public class KnapSackTest {


    @Test
    public void totalValueTest() {
        int n = 3;
        int W = 10;
        int[] w = { 0, 8, 3, 5 };
        int[] v = { 0, 8, 4, 9 };  // we take weights 2 and 3
        Assertions.assertEquals(13, KnapSack.maxTotalValueIterative(n, W, w, v));
    }


    @Test
    public void itemSetRetrieval() {
        int n = 3;
        int W = 10;
        int[] w = { 0, 8, 3, 5 };
        int[] v = { 0, 8, 4, 9 };
        int[][] mem = { { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 8, 8, 8 },
                { 0, 0, 0, 4, 4, 4, 4, 4, 8, 8, 8 },
                { 0, 0, 0, 4, 4, 9, 9, 9, 13, 13, 13 } };
        Assertions.assertEquals(List.of(2, 3), KnapSack.retrieveItemsSelectedRecursive(n, W, w, v, mem));
    }


}
