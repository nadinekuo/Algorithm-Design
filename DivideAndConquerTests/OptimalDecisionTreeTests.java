import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class OptimalDecisionTreeTests {


    @Test
    public void example() {
        List<OptimalDecisionTree.Pair<Integer[], Integer>> samples = new ArrayList<>();
        samples.add(new OptimalDecisionTree.Pair<Integer[], Integer>(new Integer[] { 0, 0, 0 }, 0));
        samples.add(new OptimalDecisionTree.Pair<Integer[], Integer>(new Integer[] { 0, 1, 0 }, 1));
        assertEquals(2, OptimalDecisionTree.decisionTree(1, samples));
    }



}
