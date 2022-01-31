import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GraphNodePathTest {



    @Test
    public void exampleMinCost() {
        int n = 7;
        int[] nodes = { 0, 1, 18, 18, 1, 18, 18, 1 };
        Assertions.assertEquals(3, GraphNodePath.minCostNodes(n, nodes));
    }


    @Test
    public void exampleLecture() {
        int n = 6;
        int[] nodes = { 0, 3, 1, 2, 4, 5, 6};
        Assertions.assertEquals(5, GraphNodePath.minCostNodes(n, nodes));
    }


    @Test
    public void exampleRetrieveNodeIndices() {
        int n = 7;
        int[] nodes = { 0, 1, 18, 18, 1, 18, 18, 1 };
        int[] mem = { 0, 1, 18, 18, 2, 20, 20, 3 };
        int optValue = 3;
        List<Integer> result = Arrays.asList(1, 4, 7);
        Assertions.assertEquals(result, GraphNodePath.recoverNodeIndicesReverseList(n, nodes, optValue, mem));
    }


    @Test
    public void testIndependentSet() {
        int n = 5;
        int[] nodes = { 0, 2, 1, 6, 8, 9 };
        Assertions.assertEquals(17, GraphNodePath.maxWeightIndependentSet(n, nodes));
    }



}
