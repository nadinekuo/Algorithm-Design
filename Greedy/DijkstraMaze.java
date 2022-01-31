import javax.print.attribute.IntegerSyntax;
import java.util.*;

public class DijkstraMaze {


    /**
     * @param n the number of nodes
     * @param m the number of edges
     * @param s the starting vertex (1 <= s <= n)
     * @param t the ending vertex (1 <= t <= n)
     * @param edges the set of edges of the graph, with endpoints labelled between 1 and n inclusive.
     *
     * @return the time required to get out, or -1 if it is not possible to get out.
     */
    public static int getMeOuttaHere(int n, int m, int s, int t, Set<Edge> edges) {

        Map<Integer, Node> nodes = new HashMap<>();

        for (int i = 1; i <= n; i++) {
            nodes.put(i, new Node(i));
        }
        for (Edge e : edges) {
            nodes.get(e.from).outgoingEdges.add(e);  // Add all outgoing EDGES, since we need the weights!
        }
        return shortestPathDijkstra(nodes, s, t);
    }




    /** D[] STORES THE SHORTEST PATH TO STARTING VERTEX. --> return D[b]
     * Returns the LENGTH of the shortest path between vertex a and b in graph g.
     *
     * !!!!!!!!!!!!!   COST = edge weights + outDegree of traversed parent nodes (not t) !!!!!!!!!!!!111
     *
     * @param nodes Graph to consider.
     * @param s Vertex to start from.
     * @param t Vertex to go to.
     * @return The length/cost of the shortest path between a and b, or -1 IF NO SUCH PATH EXISTS!
     *
     * boolean[] visited      true or false  <-- we only "relax" unvisited neighbours
     * int[] distances        holds D[v], shortest path to this vertex so far
     * PQ                     KEY = weight D[v], VALUE = VertexNumPair
     *
     *   Time complexity ===>  O( (n+m) log n)
     *      - n PQ removals/replace keys -->  O(n log n)
     *      - n calls to outgoingEdges ---> O(2m) where 2m = sum of all outdegrees + indegrees
     *
     */
    public static int shortestPathDijkstra(Map<Integer, Node> nodes, int s, int t) {

        if (s == t) return 0;   // start = end
        if (nodes == null || !nodes.containsKey(s) || !nodes.containsKey(t)) return -1;

        // Initialize all data structures we need
        boolean[] visited = new boolean[nodes.size() + 1];  // Node ids start at 1
        int[] distances = new int[nodes.size() + 1];
        PriorityQueue<VertexNumPair> pq = new PriorityQueue<>();

        // Initialize all distances to inf: we take INT_MAX / 2 to avoid INT_MAX + ... = -INT_MAX
        for (int i = 1; i < nodes.size() + 1; i++) {
            distances[i] = Integer.MAX_VALUE / 2;
        }
        distances[s] = 0;    // Starting node has dist 0
        pq.add(new VertexNumPair(s, 0));  // Insert start vertex in PQ (we don't have to insert all vertices)

        while (!pq.isEmpty()) {

            VertexNumPair curr = pq.poll();  // Will pick vertex having min dist, since we implemented compareTo()

//            if (visited[curr.id]) {
//                continue;
//            }
            visited[curr.id] = true;

            //if (curr.id == t) return distances[curr.id];   // We reached the final vertex! STOP (theoretically impossible, since we only add to PQ if unvisited)

            // Relax all unvisited neighbours
            for (Edge neighEdge : nodes.get(curr.id).outgoingEdges) {

                Node neigh = nodes.get(neighEdge.to);

                // if current Dist[u] + edge weight < Dist[v]   ----> update Dist[v]
                // else, keep current Dist[v]
                int outDegree = nodes.get(curr.id).outgoingEdges.size();   // Add time step costs for each outgoing edge = outDegree of curr!
                int newDist = distances[curr.id] + neighEdge.weight + outDegree;

                if (!visited[neigh.id] && newDist < distances[neigh.id]) {
                    distances[neigh.id] = newDist;    // Shorter path found to neighbour
                    pq.add(new VertexNumPair(neigh.id, newDist));  // Don't add to PQ if sum > current cost! You'd replace the best path we found already by a larger dist!
                }
            }
        }
        if (visited[t]) {
            return distances[t];   // Cost of shortest path to t
        } else {
            return -1;  // end vertex t never reached, no shortest path found!
        }

    }



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
            int res = Integer.signum(this.cost - other.cost);  // Make sure PQ compares distances to this vertex!
            if (res == 0) {
                return Integer.signum(this.id - other.id);  // If equal distances, pick lowest id
            }
            return res;
        }

//        @Override
//        public int compareTo(VertexNumPair other) {
//            int res = Integer.compare(this.cost, other.cost);  // Make sure PQ compares distances to this vertex!
//            if (res == 0) {
//                return Integer.compare(this.id, other.id);  // If equal distances, pick lowest id
//            }
//            return res;
//        }
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
