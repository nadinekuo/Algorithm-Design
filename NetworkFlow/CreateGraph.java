import java.util.ArrayList;
import java.util.List;

public class CreateGraph {

    /** @return the graph from the image. */

    public static Graph graph1() {

        List<Node> nodes = new ArrayList<>();

        for (int i = 0; i <= 4; i++) {  // add inner nodes 1, 2, 3 and source, sink
            nodes.add(new Node(i));
        }
        nodes.get(0).addEdge(nodes.get(2), 5);
        nodes.get(0).addEdge(nodes.get(3), 3);
        nodes.get(1).addEdge(nodes.get(4), 7);
        nodes.get(2).addEdge(nodes.get(1), 2);
        nodes.get(2).addEdge(nodes.get(4), 2);
        nodes.get(3).addEdge(nodes.get(1), 5);

        Graph g = new Graph(nodes, nodes.get(0), nodes.get(4));  // suorce = 0, sink = 4

        return g;
    }



    /**
     * @param n the number of edges your graph should have
     * @param edges a matrix representing the edges between two nodes. For all 1 <= i,j, <= n
     *     edges[i][j] is the capacity of the edge or 0 if no such edge exists.
     * @return a graph containing the edges from the edges input, where node 1 is the source and
     *     node n is the sink.
     */
    public static Graph graph2(int n, int[][] edges) {

        List<Node> nodes = new ArrayList<>();
        Node[] nodeArr = new Node[n + 1];   // create 1-indexed array!!
        for (int i = 1; i <= n; i++) {
            nodeArr[i] = new Node(i);
            nodes.add(nodeArr[i]);
        }
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (i == j || edges[i][j] == 0) {  // ignore self-loops and 0 capacities
                    continue;
                }
                nodeArr[i].addEdge(nodeArr[j], edges[i][j]);
            }
        }
        return new Graph(nodes, nodes.get(0), nodes.get(n-1));

    }


}
