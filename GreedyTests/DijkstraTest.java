import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class DijkstraTest {



    @Test
    public void example() {
        int n = 7;
        int m = 7;
        int s = 1;
        int t = 5;

        Set<DijkstraMaze.Edge> edges = new HashSet<>();
        edges.add(new DijkstraMaze.Edge(1,2,2));
        edges.add(new DijkstraMaze.Edge(2,3,100));
        edges.add(new DijkstraMaze.Edge(3,4,10));
        edges.add(new DijkstraMaze.Edge(4,5,10));
        edges.add(new DijkstraMaze.Edge(2,6,10));
        edges.add(new DijkstraMaze.Edge(6,7,10));
        edges.add(new DijkstraMaze.Edge(7,4,80));

        assertEquals(118, DijkstraMaze.getMeOuttaHere(n, m, s, t, edges));

    }


    //  1
    @Test
    public void SingleVertex() {
        int n = 1;
        int m = 0;
        int s = 1;
        int t = 1;

        Set<DijkstraMaze.Edge> edges = new HashSet<>();
        assertEquals(0, DijkstraMaze.getMeOuttaHere(n, m, s, t, edges));
    }




    //     1 ----- 42 ----- 2
    @Test
    public void twoVertices() {
        int n = 2;
        int m = 1;
        int s = 1;
        int t = 2;

        Set<DijkstraMaze.Edge> edges = new HashSet<>();
        edges.add(new DijkstraMaze.Edge(1,2,42));
        assertEquals(43, DijkstraMaze.getMeOuttaHere(n, m, s, t, edges));
    }


    //     1 ----- 42 -----> 2
    //       <----- 40 -----
    @Test
    public void twoVerticesTwoOppositeDirectedEdges() {
        int n = 2;
        int m = 1;
        int s = 1;
        int t = 2;

        Set<DijkstraMaze.Edge> edges = new HashSet<>();
        edges.add(new DijkstraMaze.Edge(1,2,42));
        edges.add(new DijkstraMaze.Edge(2,1,40));
        assertEquals(43, DijkstraMaze.getMeOuttaHere(n, m, s, t, edges));
    }




    //     1 ----- 42 -----> 2
    //       <----- 40 -----
    @Test
    public void twoVerticesTwoOppositeDirectedEdgesSwapped() {
        int n = 2;
        int m = 2;
        int s = 2;
        int t = 1;

        Set<DijkstraMaze.Edge> edges = new HashSet<>();
        edges.add(new DijkstraMaze.Edge(1,2,42));
        edges.add(new DijkstraMaze.Edge(2,1,40));
        assertEquals(41, DijkstraMaze.getMeOuttaHere(n, m, s, t, edges));
    }




    //      1 ---55--- 2        4 ---- 22 --- 5
    //       \       /
    //        11    44
    //         \   /
    //           3
    @Test
    public void disconnectedMaze() {
        int n = 5;
        int m = 4;
        int s = 1;
        int t = 4;

        Set<DijkstraMaze.Edge> edges = new HashSet<>();
        edges.add(new DijkstraMaze.Edge(1,2,55));
        edges.add(new DijkstraMaze.Edge(1,3,11));
        edges.add(new DijkstraMaze.Edge(2,3,44));
        edges.add(new DijkstraMaze.Edge(4,5,22));
        assertEquals(-1, DijkstraMaze.getMeOuttaHere(n, m, s, t, edges));
    }


}

