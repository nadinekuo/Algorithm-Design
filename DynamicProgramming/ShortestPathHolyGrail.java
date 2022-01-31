import java.io.*;
import java.util.*;

public class ShortestPathHolyGrail {



    /**
     *
     * @param n the number of nodes.
     * @param m the number of edges.
     * @param g the index of the holy grail in V.
     * @param V a list of Nodes, where V[1] is the current state and V[g] is the holy grail. You should not use V[0].
     * @param E a set of Edges
     * @return The length of the shortest path that uses the research team at most once.
     */
    public static double shortestPathOutgoingEdges(int n, int m, int g, Node[] V, Set<Edge> E) {

        double[][] mem = new double[n + 1][2];   // shortest path to node i, using c collabs at most, Note here c = 1 is fixed

        for (double[] row : mem) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }

        mem[1][0] = 0;     // Note Base case: cost to start node 1 is 0! If you keep this inf, you will add edge cost of node 1 to inf later...
        mem[1][1] = 0;

        // Note: like Bellman-Ford we need n-1 outer iterations for shortest (simple) path to propagate
        for (int k = 0; k < n - 1; k++) {

            for (int i = 1; i <= n; i++) {     // For each vertex, look at outgoing edges, Note: starts at node 1, V[0] = null!
                Node vertex = V[i];

                for (Edge e : E) {
                    if (e.from.getId() == vertex.getId()) {   // Note: there is no node.getOutgoing()...not adjacency list so O(n^2 * m) ....

                        int from = vertex.getId();
                        int to = e.getTo().getId();

                        mem[to][0] = Math.min(mem[to][0], mem[from][0] + e.cost);  // is this incoming edge better?
                        mem[to][1] = Math.min(mem[to][1],
                                Math.min(mem[from][1] + e.cost,           // no collab for this edge e (even tho 1 was allowed)
                                        mem[from][0] + 0.5 * e.cost));   // 1 collab on this edge e, so half cost, but 1 less collab! 1 - 1 = 0
                    }
                }
            }
        }

        if (mem[g][1] == Integer.MAX_VALUE) return -1;  // Note: no shortest path to holy grail g!

        for (int i = 0; i < mem.length; i++) {
            System.out.println(Arrays.toString(mem[i]));
        }

        return mem[g][1];
    }



    public static double shortestPathIncomingEdges(int n, int m, int g, Node[] V, Set<Edge> E) {

        // Opt(i, c) = min cost of shortest path to node i, using at most c collaborations (Note: here c is fixed to 1)
        double[][] mem = new double[n + 1][2];

        // OR:
        //        for (double[] row : mem) {
        //            Arrays.fill(row, Integer.MAX_VALUE);
        //        }
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j < 2; j++) {          // NOTE: j = 0 means no collabs allowed, j = 1 means 1 collab allowed
                mem[i][j] = Integer.MAX_VALUE;   // initialize as inf
            }
        }

        mem[1][1] = mem[1][0] = 0;   // base case

        for (int outer = 1; outer <= n - 1; outer++) {

            for (int i = 1; i <= n; i++) {  // for each node/vertex

                // Note: 2 entries for best INCOMING neigh path until THIS node i, using 0 or 1 collab
                double[] minEdges = new double[2];
                minEdges[0] = Integer.MAX_VALUE;
                minEdges[1] = Integer.MAX_VALUE;

                for (Edge e : E) {             // Note: look at ALL edges to find INCOMING edges of node i (predecessor) (Belman-Ford looks at outgoing)
                    if (e.to.getId() == i) {

                        // min { e.cost(neigh, i) + opt(neigh, c) }
                        minEdges[0] = Math.min(minEdges[0],
                                e.cost + mem[e.from.getId()][0]);   // no collab
                        // min { min {e.cost(neigh, i} + Opt(neigh, c),
                        //                                  0.5 * e.cost(neigh, i) + Opt(neigh, c-1) }
                        minEdges[1] = Math.min(minEdges[1],
                                Math.min(mem[e.from.getId()][1] + e.cost,         // no collab for this edge e
                                        mem[e.from.getId()][0] + e.cost * 0.5)); // 1 collab, so half cost, but 1 less collab! 1 - 1 = 0
                        // Note: We took the edge from incoming neigh to this i, but that means the path until neigh could not contain any collabs!
                    }
                }
                mem[i][0] = Math.min(mem[i][0], minEdges[0]);
                mem[i][1] = Math.min(mem[i][1], minEdges[1]);
            }
        }

        if (mem[g][1] == Integer.MAX_VALUE) {
            return -1;
        }

        for (int i = 0; i < mem.length; i++) {
            System.out.println(Arrays.toString(mem[i]));
        }

        return mem[g][1];   // min cost of path to holy grail g, using 1 collab only
    }







    static class Node {

        protected int id;

        public Node(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public boolean equals(Object other) {
            if (other instanceof Node) {
                Node that = (Node) other;
                return this.id == that.id;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
    }

    static class Edge {

        protected int cost;

        protected Node from;

        protected Node to;

        protected Edge(Node from, Node to, int cost) {
            this.from = from;
            this.to = to;
            this.cost = cost;
        }

        public Node getFrom() {
            return from;
        }

        public Node getTo() {
            return to;
        }

        public int getCost() {
            return cost;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            Edge edge = (Edge) o;
            return cost == edge.cost && Objects.equals(from, edge.from) && Objects.equals(to, edge.to);
        }

        @Override
        public int hashCode() {
            return Objects.hash(cost, from, to);
        }
    }

}


