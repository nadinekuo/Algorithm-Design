import java.util.*;

class MaxFlow {




    /**   BFS: O(n + m)
     * @param g - original graph
     * @param start - source
     * @param end - sink
     * @return List of all edges in order of path found, to use for AUGMENT
     */
    private static List<Edge> findPath(Graph g, Node start, Node end) {

        //  keep track of predecessors
        Map<Node, Edge> parents = new HashMap<Node, Edge>();  // K = dest node, value = incoming edge
        Queue<Node> q = new LinkedList<Node>();
        Node currentNode = start;
        q.add(currentNode);

        // BFS: loop until there are no nodes to explore, or we have found a path.
        while (!q.isEmpty() && currentNode != end) {
            currentNode = q.remove();
            for (Edge e : currentNode.getEdges()) {  // all outgoing edges Note this contains backward edges, but they will be ignored bc of (parents.get(to) == null)!
                Node to = e.getTo();
                // Note: Only consider the edge if:
                // 1) The neighbour is not the start/source
                // 2) We do not yet have a path to the neighbour Note: else there would be a cycle! We look for a simple path.
                // 3) We can travel via this edge, i.e. the residual capacity is larger than 0.
                if (to != start
                        && parents.get(to) == null           // OR (!parents.containsKey(to))
                                && e.getResidual() > 0) {   // OR: flow < capacity
                    q.add(e.getTo());      // add neighbour node
                    parents.put(to, e);    // set this edge as parent path (V) to neighbour (K)
                }
            }
        }
        if (q.isEmpty() && currentNode != end)  // Note: no s-t path found, since there are no nodes left, but sink not reached yet...
            return null;

        // Else, we must have reached end node: Reconstruct the path from the parents map Note: LinkedList allows efficient prepending :)
        LinkedList<Edge> path = new LinkedList<Edge>();
        Node walk = end;
        while (parents.get(walk) != null) {    // OR   while(walk != start)
            Edge parentEdge = parents.get(walk);
            path.addFirst(parentEdge);
            walk = parentEdge.getFrom();     // Walk backwards and continue path
        }
        return path;
    }


    /**  Augmenting per path ---> O(n), simple path is bounded by #vertices
     *  Total bound ---> O(m * C), where C is some upper bound on the number of iterations
     *                             e.g. the sum of all capacities leaving s
     *
     * @param g - graph
     * @return - max flow value, sum of all bottleneck values we augmented by
     */
    public static int maximizeFlow(Graph g) {

        int flow = 0;    // Sums all bottleneck values we augment by = max flow in the end  Note: we start with 0 flow
        Node sink = g.getSink();
        Node source = g.getSource();
        List<Edge> path;

        while ((path = findPath(g, source, sink)) != null) {  // We keep on finding new augmenting s-t paths!

            int bottleneck = Integer.MAX_VALUE;
            // Find the bottleneck = minimal residual (leftover) value = max value to augment by
            for (Edge e : path) {
                bottleneck = Math.min(bottleneck, e.getResidual());
            }
            // Augment the flow along these edges
            for (Edge e : path) {
                e.augmentFlow(bottleneck);
            }
            flow += bottleneck;  // flow += bottleneck value we augmented by = augmented flow
        }
        return flow;
    }



    public static void main(String[] args) {

        // Note: from peer odd student - blood supply to patients
        List<Node> nodes = new ArrayList<>();

        for (int i = 0; i <= 9; i++) {  // add inner nodes 1, 2, 3 and source, sink
            nodes.add(new Node(i));
        }
        nodes.get(0).addEdge(nodes.get(1), 71);
        nodes.get(0).addEdge(nodes.get(2), 67);
        nodes.get(0).addEdge(nodes.get(3), 37);
        nodes.get(0).addEdge(nodes.get(4), 5);

        nodes.get(1).addEdge(nodes.get(5), 1000);
        nodes.get(1).addEdge(nodes.get(6), 1000);
        nodes.get(1).addEdge(nodes.get(7), 1000);
        nodes.get(1).addEdge(nodes.get(8), 1000);

        nodes.get(2).addEdge(nodes.get(6), 1000);
        nodes.get(2).addEdge(nodes.get(8), 1000);

        nodes.get(3).addEdge(nodes.get(7), 1000);
        nodes.get(3).addEdge(nodes.get(8), 1000);

        nodes.get(4).addEdge(nodes.get(8), 1000);

        nodes.get(5).addEdge(nodes.get(9), 50);
        nodes.get(6).addEdge(nodes.get(9), 70);
        nodes.get(7).addEdge(nodes.get(9), 45);
        nodes.get(8).addEdge(nodes.get(9), 20);

        Graph g = new Graph(nodes, nodes.get(0), nodes.get(9));  // suorce = 0, sink = 10

        System.out.println(maximizeFlow(g));

    }

}

