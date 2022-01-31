import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import org.junit.jupiter.api.*;

public class ShortestPathHolyGrailTest {

    @Test
    public void example_one_edge() {
        int n = 2;
        int m = 1;
        int g = 2;
        ShortestPathHolyGrail.Node[] V = new ShortestPathHolyGrail.Node[n + 1];
        for (int i = 1; i <= n; i++) {
            V[i] = new ShortestPathHolyGrail.Node(i);
        }
        Set<ShortestPathHolyGrail.Edge> E = new HashSet<>();
        E.add(new ShortestPathHolyGrail.Edge(V[1], V[2], 8));
        /* Only one edge to go, so might as well use the team!
         * Costs are therefore 8/2 = 4.
         */
        Assertions.assertEquals(4, ShortestPathHolyGrail.shortestPathOutgoingEdges(n, m, g, V, E), 1e-4);
    }

    @Test
    public void example_two_edges() {
        int n = 3;
        int m = 2;
        int g = 3;
        ShortestPathHolyGrail.Node[] V = new ShortestPathHolyGrail.Node[n + 1];
        for (int i = 1; i <= n; i++) {
            V[i] = new ShortestPathHolyGrail.Node(i);
        }
        Set<ShortestPathHolyGrail.Edge> E = new HashSet<>();
        E.add(new ShortestPathHolyGrail.Edge(V[1], V[2], 8));
        E.add(new ShortestPathHolyGrail.Edge(V[2], V[3], 3));
        /* Only two edge to go, so use it on the most expensive one.
         * Costs are therefore 8/2 + 3 = 7.
         */
        Assertions.assertEquals(7, ShortestPathHolyGrail.shortestPathOutgoingEdges(n, m, g, V, E), 1e-4);
    }

    @Test
    public void example_two_paths() {
        int n = 4;
        int m = 4;
        int g = 3;
        ShortestPathHolyGrail.Node[] V = new ShortestPathHolyGrail.Node[n + 1];
        for (int i = 1; i <= n; i++) {
            V[i] = new ShortestPathHolyGrail.Node(i);
        }
        Set<ShortestPathHolyGrail.Edge> E = new HashSet<>();
        E.add(new ShortestPathHolyGrail.Edge(V[1], V[2], 8));
        E.add(new ShortestPathHolyGrail.Edge(V[2], V[3], 3));
        E.add(new ShortestPathHolyGrail.Edge(V[1], V[4], 3));
        E.add(new ShortestPathHolyGrail.Edge(V[4], V[3], 6));
        /* Two paths to chose from, best option is 3 + 6/2
         */
        Assertions.assertEquals(6, ShortestPathHolyGrail.shortestPathOutgoingEdges(n, m, g, V, E), 1e-4);
    }




}
