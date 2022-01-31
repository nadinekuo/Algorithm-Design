import java.util.*;

public class PrimJarnikMST {

    /**
     * @param n the number of nodes
     * @param m the number of edges
     * @param edges the set of edges of the graph, with endpoints labelled between 1 and n inclusive.
     *
     * @return the MST as List<Edge>
     */
    public static List<Edge> primJarnik(int n, int m, Set<Edge> edges) {

        Map<Integer, Node> nodes = new HashMap<>();

        for (int i = 1; i <= n; i++) {
            nodes.put(i, new Node(i));
        }
        for (Edge e : edges) {
            nodes.get(e.from).outgoingEdges.add(e);  // Add all outgoing EDGES, since we need the weights!
        }
        return buildMST(nodes);
    }


    /**
     * Prim jarnik: Minimum Spanning Tree
     *
     * @return the weighted graph as Minimum Spanning Tree (must contain all vertices)
     *
     *  Each vertex has a collection of VertexNumPairs
     *  for all adjacent vertices: Vertex + Num (edge weight between them)
     *               Datastructures:
     *    int[ ] Distance    <--- current minimal weight of 1 edge!! (in MST) (Dijksta: current minimal sum of weights)
     *    boolean [] visited  <-- INDEX: Vertex id, VALUE: true/false
     *    Vertex[] parents    <-- You could also use a HashMap, but that essentially creates an array! Both O(1). But HashMap allows other keys than ints.
     *    Adaptable PQ <KEY: D[v], VALUE: Vertex>
     *            // Only connected part of vertex 0 is traversed!!
     */
    public static List<Edge> buildMST(Map<Integer, Node> nodes) {

        // Initialize all data structures we need
        int[] dist = new int[nodes.size() + 1];
        int[] parents = new int[nodes.size() + 1];
        PriorityQueue<VertexNumPair> pq = new PriorityQueue<>();
        List<Edge> MST = new ArrayList<>();

        // Initialize all distances to inf: we take INT_MAX / 2 to avoid INT_MAX + ... = -INT_MAX
        for (int i = 1; i < nodes.size() + 1; i++) {
            dist[i] = Integer.MAX_VALUE / 2;
        }
        dist[1] = 0;    // Random starting node 1 has dist 0

        // Insert neighbours of start node 1
        for (Edge e : nodes.get(1).outgoingEdges) {
            pq.add(new VertexNumPair(e.to, e.weight));
            parents[e.to] = 1;  // node 1 is parent
        }

        while (!pq.isEmpty()) {

            VertexNumPair curr = pq.poll();  // Will pick vertex having min dist, since we implemented compareTo()
            nodes.get(curr).marked = true;
            MST.add(new Edge(parents[curr.id], curr.id, dist[curr.id]));  // from, to, weight

            // Relax all unvisited neighbours
            for (Edge neighEdge : nodes.get(curr.id).outgoingEdges) {

                Node neigh = nodes.get(neighEdge.to);

                // if current edge weight < Dist[v]   ----> update Dist[v]
                // else, keep current Dist[v]
                if (!neigh.marked && neighEdge.weight < dist[neigh.id]) {
                    dist[neigh.id] = neighEdge.weight;    // Shorter path found to neighbour
                    parents[neigh.id] = curr.id;
                    pq.add(new VertexNumPair(neigh.id, neighEdge.weight));  // Don't add to PQ if sum > current cost! You'd replace the best path we found already by a larger dist!
                }
            }
        }
        return MST;
    }




    /**
     * Container class used to store a Vertex and an int.
     * Prim-Jarnik: current minimum weight of 1 edge
     * Dijkstra: current sum of minimum weight (of all edges to this vertex)
     */
    // To be inserted in Adaptable PQ
    static class VertexNumPair implements Comparable<VertexNumPair> {

        int id;        // node id
        int cost;      // dist to this node from start node

        VertexNumPair(int id, int cost) {
            this.id = id;
            this.cost = cost;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            VertexNumPair tuple = (VertexNumPair) o;
            return id == tuple.id;
        }

        @Override
        public int hashCode() {
            return id;
        }

        @Override
        public int compareTo(VertexNumPair other) {
            int res = Integer.compare(this.cost, other.cost);  // Make sure PQ compares distances to this vertex!
            if (res == 0) {
                return Integer.compare(this.id, other.id);  // If equal distances, pick lowest id
            }
            return res;
        }
    }


    static class Node {

        List<Edge> outgoingEdges;
        int id;
        boolean marked;

        public Node(int id) {
            this.outgoingEdges = new ArrayList<>();
            this.marked = false;
            this.id = id;
        }

        public String toString() {
            return Integer.toString(id);
        }

        @Override
        public int hashCode() {
            return id;
        }
    }


    static class Edge {

        int from, to, weight;

        public Edge(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            Edge edge = (Edge) o;
            return from == edge.from && to == edge.to && weight == edge.weight;
        }

        @Override
        public int hashCode() {
            return Objects.hash(from, to, weight);
        }
    }

}
