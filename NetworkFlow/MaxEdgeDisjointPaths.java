import java.util.*;

public class MaxEdgeDisjointPaths {

    public static void main(String[] args) {
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
        disjointPaths(n, 1, 7, connections);
        //Assertions.assertEquals(1, MaxEdgeDisjointPaths.disjointPaths(n, 1, 7, connections));
    }

    /**
     * O(m * n)
     *  - O(mC) for Ford-Fulkerson max flow, where C = sum of all capacities leaving source
     *  - Worst case source has n outgoing edges (all nodes) so n * 1 = n = C
     *
     * @param n the number of nodes
     * @param sourceId the id (index) of the source node (1 <= sourceId <= n)
     * @param sinkId the id (index) of the sink node (1 <= sinkId <= n)
     * @param connections set of connections between one object from X and one object from Y.
     *     Objects in X and Y are labelled 1 <= label <= n
     * @return the maximum number of disjoint paths
     */
    public static int disjointPaths(int n, int sourceId, int sinkId, Set<Connection> connections) {

        List<Node> nodesList = new ArrayList<>();
        Node source, sink;

        Node[] nodes = new Node[n + 1];  // 1-indexed!
        // Add all nodes, note that this includes source and sink!
        for (int i = 1; i <= n; i++) {
            nodes[i] = new Node(i);
            nodesList.add(nodes[i]);  // 0-indexed!
        }
        // Note Source & sink references we created above!
        source = nodes[sourceId];
        sink = nodes[sinkId];

        // Model the connections in the network flow graph.
        for (Connection c : connections) {
            nodes[c.x].addEdge(nodes[c.y], 0, 1);   // Capacity 1 ensures no edge will be used more than 1x!
        }

        Graph g = new Graph(nodesList, source, sink);
        int numDisjointPaths = MaxFlow.maximizeFlow(g);

        // Note: we perform DFS to recover all paths found!
        Map<Integer, List<Edge>> paths = recoverDisjointPaths(g, numDisjointPaths, sourceId, sinkId);

        for (int i = 1; i <= numDisjointPaths; i++) {
            System.out.println("All edges in path no. " + i + ": " + paths.get(i));
        }

        return numDisjointPaths;  // max flow = no. of edge disjoint paths!
    }




    // K = path no.
    // V = all (disjoint!) edges in this path
    public static Map<Integer, List<Edge>> recoverDisjointPaths(Graph g, int numDisjointPaths, int start, int end) {

        Map<Integer, List<Edge>> result = new HashMap<>();  // K = path no. (1-indexed), V = List of sorted edges in this path

        // Find path using DFS from start to end node, following only edges having flow values = 1
        List<Edge> path;
        int count = 0;     // keep track of number of path (Key)

        while (count < numDisjointPaths && (path = findDisjointPathDFS(g, start, end)) != null) {    // Stop when all k edge disjoint paths found

            count++;
            result.put(count, path);

            // Note: After having found the s-t path, set all flow values in this path to 0 so we will ignore this path in next DFS
            for (Edge e : path) {
                e.flow = 0;
            }
        }
        return result;
    }



    // Performs DFS recursively
    // OR iterative using stack
    private static List<Edge> findDisjointPathDFS(Graph g, int start, int end) {

        boolean[] visited = new boolean[g.getNodes().size() + 1];
        List<Edge> resultPath = new ArrayList<>();

        return dfsRecursiveHelper(g, start, end, visited, resultPath);
    }



    private static List<Edge> dfsRecursiveHelper(Graph g, int start, int end, boolean[] visited, List<Edge> resultPath) {

        if (start == end) return resultPath;   // base case: end node reached, so return the start-end path

        visited[start] = true;

        // TODO: node.getEdges() contains backwards edges?!
        Node startNode = g.getNodes().get(start - 1);
        for (Edge e : startNode.getEdges()) {  // Look at outgoing edges Note: 0-indexed!

            // TODO: may lead to cycle!! But in this case we just "remove" the cycle by setting values to 0 ?
            if (!visited[e.to.getId()] && !resultPath.contains(e)
                                        && e.flow == 1) {    // flow of 1 means this edge is part of one of the paths
                resultPath.add(e);
                dfsRecursiveHelper(g, e.getTo().getId(), end, visited, resultPath);  // Pass result containing this edge to next recursive call on neigh!
                // In the end, the base case (end reached) will return the full path containing all edges
//                resultPath.remove(new Integer(start));
            }
        }
//        visited[start] = false;
        // Base case not reached? This means end node was not reached!
        return resultPath;
    }



}
