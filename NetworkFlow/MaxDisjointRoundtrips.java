import java.util.*;

public class MaxDisjointRoundtrips {

    //  The idea is that you use the number of paths from /pub to the brewery to determine the number of round trips that can be made,
    //  assuming the exact paths that are given can be walked in reverse as well.
    //  Note that this is different from having undirected edges:
    //  a path either uses all its edges in forwards direction, or all of them in reverse direction


    /** 1
     *  UNDIRECTED GRAPHS!
     *
     * @param n           the number of nodes
     * @param startNodeId    the id (index) of the start node (1 <= startNodeId <= n)
     * @param endNodeId      the id (index) of the end node (1 <= endNodeId <= n)
     * @param connections set of connections between one object from X and one object from Y. Objects in X and Y are labelled 1 <= label <= n
     * @return the maximum number of disjoint roundtrips
     */
    public static int zombieFreeRuns(int n, int startNodeId, int endNodeId, Set<Connection> connections) {

        List<Node> nodesList = new ArrayList<>();  // 0-indexed
        Node[] nodes = new Node[n+1];  // 1-indexed
        Node source, sink;
        // Creating flow graph from model
        for (int i = 1; i <= n; i++) {
            nodes[i] = new Node(i);
            nodesList.add(nodes[i]);
        }
        // Source & sink
        source = nodes[startNodeId];
        sink = nodes[endNodeId];
        // Add edges with capacity 1
        for (Connection c : connections) {
            nodes[c.x].addEdge(nodes[c.y], 1);  // capacity 1 ensures each edge is used 1x only
        }
        Graph g = new Graph(nodesList, source, sink);

        // This problem has to take into account round trips as well!
        // Note: Floor down would give 1 cycle when 3 disjoint paths found e.g.,
        //  since there is only 1 roundtrip possible for which the road back has not been crossed already!
        return MaxFlow.maximizeFlow(g) / 2;
    }


}
