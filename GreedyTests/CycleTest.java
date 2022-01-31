import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class CycleTest {


    @Test
    public void example() {
        int n = 5;
        int m = 6;
        int s = 1;
        Set<CycleDetectionDFS.Edge> edges = new HashSet<>();
        edges.add(new CycleDetectionDFS.Edge(1, 2));
        edges.add(new CycleDetectionDFS.Edge(3, 2));
        edges.add(new CycleDetectionDFS.Edge(1, 3));
        edges.add(new CycleDetectionDFS.Edge(4, 5));
        edges.add(new CycleDetectionDFS.Edge(2, 4));
        edges.add(new CycleDetectionDFS.Edge(5, 3));
        assertTrue(CycleDetectionDFS.isThereACycle(n, m, edges, s));
    }


    @Test
    public void testCycleOf2Vertices() {
        int n = 2;
        int m = 2;
        int s = 1;
        Set<CycleDetectionDFS.Edge> edges = new HashSet<>();
        edges.add(new CycleDetectionDFS.Edge(1, 2));
        edges.add(new CycleDetectionDFS.Edge(2, 1));
        assertTrue(CycleDetectionDFS.isThereACycle(n, m, edges, s));
    }


}
