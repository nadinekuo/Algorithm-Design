import java.util.*;

public class WirelessNetworkMST {


    public long cost;   // minimum total costs to connect all locations
    public long number;   // number of connections that can be built using the budget

    public WirelessNetworkMST(long cost, long number) {
        this.cost = cost;   // minimum total costs to connect all locations
        this.number = number;  // number of connections that can be built using the budget
    }


    /**  VERSION 1: Without PQ, but sorting array by passing comparator
     *
     *   We don't necessarily need PQ, since we don't insert anything new constantly, like with Dijkstra
     *   But both are O(n log n)
     *
     * @param n - number of stations / vertices
     * @param m - number of edges
     * @param b - budget available
     * @param edges - edges/connections to build (but we can't afford to choose all)
     * @return
     */
    public static WirelessNetworkMST MSTWithoutPQ(int n, int m, int b, Edge[] edges) {

        int edgeCount = 0;
        Edge[] tree = new Edge[n - 1];    // tree has n-1 edges!

        // BUT WE DON'T NECESSARILY NEED TO STORE THE MST, see commented version. (but it may be useful for K-clustering)

        Arrays.sort(edges, 1, m + 1, Comparator.comparingInt(e -> e.l));  // Exclude idx 0!!! Will give NullPointerException
        UnionFind uf = new UnionFind(n);

        for (int i = 1; i <= m; i++) {
            Edge e = edges[i];
            if (uf.join(e.x, e.y))
                tree[edgeCount++] = e;
            if (edgeCount == n - 1)  // if n-1 edges added to MST, all vertices are connected. stop.
                break;
        }

//        for (int i = 1; i < edges.length; i++) {
//            WirelessNetworkMST.Edge e = edges[i];
//            if (uf.join(e.x, e.y)) {    // implicitly builds MST
//                sumWeights += e.l;
//                if (sumWeights <= b) {  // Will add weights until budget reached
//                    numEdgesAffordable++;
//                }
//            }
//        }

        long sumOfWeightsMST = 0;
        long numEdgesAffordable = 0;
        for (Edge edge : tree) {
            sumOfWeightsMST += edge.l;
            if (sumOfWeightsMST <= b)
                numEdgesAffordable++;
        }
        return new WirelessNetworkMST(sumOfWeightsMST, numEdgesAffordable);
    }



    public static WirelessNetworkMST setUpTheNetwork(int n, int m, int b, Edge[] edges) {

        // Build MST: min cost = cost of all edges in MST!
        List<Edge> MST = buildMST(edges, n);   // ordered from cheapest to most expensive (bc we selected cheapest first)

        long totalMinCost = 0;
        long numberOfEdgesAffordable = 0;
        int total = 0;
        for (Edge e : MST) {
            totalMinCost += e.l;
            if (total + e.l <= b) {
                total += e.l;  // update current costs (should stay below budget b)
                numberOfEdgesAffordable++;
            }
        }
        return new WirelessNetworkMST(totalMinCost, numberOfEdgesAffordable);
    }



    private static List<Edge> buildMST(Edge[] edges, int n) {

        List<Edge> MST = new ArrayList<>();
        UnionFind unionFind = new UnionFind(n);   // n vertices, so n initial clusters
        PriorityQueue<Edge> edgesPQ = new PriorityQueue<>();

        // Insert all edges / distances into PQ to pick the lowest weight
        for (int i = 1; i < edges.length; i++) {   // idx 0 is empty!!
            edgesPQ.add(edges[i]);
        }

        while (!edgesPQ.isEmpty()) {

            Edge smallestEdge = edgesPQ.poll();

            if (unionFind.join(smallestEdge.x, smallestEdge.y)) {  // If 2 vertices in different clusters, we merge and grow MST
                MST.add(smallestEdge);
            }
        }
        return MST;
    }




    static class Edge implements Comparable<Edge> {

        // from, to and length
        int x, y, l;

        public Edge(int from, int to, int length) {
            x = from;
            y = to;
            l = length;
        }

        @Override
        public int compareTo(Edge o) {
            return Integer.compare(this.l, o.l);
        }  // to insert in PQ
    }





    static class UnionFind {

        private int[] parent;       // leader of cluster
        private int[] rank;         // length of tree path

        UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) parent[i] = i;  // initially, each cluster leader is the node itself
        }

        /**  Union: merges 2 clusters
         * Joins two disjoint sets together, if they are not already joined.
         *
         * @return false if x and y are in same set, true if the sets of x and y are now joined.
         */
        boolean join(int x, int y) {
            int xrt = find(x);  // setX
            int yrt = find(y);  // setY
            if (rank[xrt] > rank[yrt])  // merge into largest cluster (longest tree path)
                parent[yrt] = xrt;
            else if (rank[xrt] < rank[yrt])
                parent[xrt] = yrt;
            else if (xrt != yrt)      // no equal clusters!
                rank[parent[yrt] = xrt]++;   // only if equal ranks, the path gets increased by 1
            return xrt != yrt;     // true if different clusters!! Else, we create a cycle
        }



        // Path compression: we recursively find the leader of the cluster and set parent[x] to that leader
        // so next lookup can be done in O(1)
        private int find(int x) {
            return parent[x] == x ? x : (parent[x] = find(parent[x]));
        }

    }



}
