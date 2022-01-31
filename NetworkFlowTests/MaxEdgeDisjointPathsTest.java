import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.*;
import java.util.Scanner;
import org.junit.jupiter.api.*;

public class MaxEdgeDisjointPathsTest {

    /**
     * Tests a network of 4 nodes and 5 edges.
     */
    @Test
    public void n4m5() {
        ArrayList<Node> nodes = new ArrayList<>();
        for (int x = 0; x < 4; x++) nodes.add(new Node(x));
        nodes.get(0).addEdge(nodes.get(1), 10);
        nodes.get(0).addEdge(nodes.get(2), 10);
        nodes.get(1).addEdge(nodes.get(3), 10);
        nodes.get(2).addEdge(nodes.get(3), 10);
        nodes.get(1).addEdge(nodes.get(2), 2);
        Graph g = new Graph(nodes, nodes.get(0), nodes.get(3));
        Assertions.assertEquals(20, MaxFlow.maximizeFlow(g));
    }


    @Test()
    public void verySimpleTest() {
        int n = 3;
        Set<Connection> connections = new HashSet<>();
        connections.add(new Connection(1, 2));
        connections.add(new Connection(2, 3));
        Assertions.assertEquals(1, MaxEdgeDisjointPaths.disjointPaths(n, 1, 3, connections));
    }

    @Test()
    public void simpleTest() {
        int n = 4;
        Set<Connection> connections = new HashSet<>();
        connections.add(new Connection(1, 2));
        connections.add(new Connection(2, 3));
        connections.add(new Connection(1, 4));
        connections.add(new Connection(4, 3));
        Assertions.assertEquals(2, MaxEdgeDisjointPaths.disjointPaths(n, 1, 3, connections));
    }

    @Test()
    public void onePathTest() {
        int n = 7;
        Set<Connection> connections = new HashSet<>();
        connections.add(new Connection(1, 2));
        connections.add(new Connection(1, 3));
        connections.add(new Connection(1, 4));
        connections.add(new Connection(1, 5));
        connections.add(new Connection(2, 6));
        connections.add(new Connection(3, 6));
        connections.add(new Connection(4, 6));
        connections.add(new Connection(5, 6));
        // bottleneck
        connections.add(new Connection(6, 7));
        Assertions.assertEquals(1, MaxEdgeDisjointPaths.disjointPaths(n, 1, 7, connections));
    }

    @Test()
    public void bookExampleTest() {
        int n = 7;
        Set<Connection> connections = new HashSet<>();
        connections.add(new Connection(1, 2));
        connections.add(new Connection(2, 3));
        connections.add(new Connection(3, 7));
        connections.add(new Connection(1, 4));
        connections.add(new Connection(4, 5));
        connections.add(new Connection(5, 7));
        connections.add(new Connection(1, 6));
        connections.add(new Connection(6, 7));
        // cycles
        connections.add(new Connection(3, 5));
        connections.add(new Connection(5, 6));
        connections.add(new Connection(6, 4));
        connections.add(new Connection(4, 3));
        Assertions.assertEquals(3, MaxEdgeDisjointPaths.disjointPaths(n, 1, 7, connections));
    }

}
