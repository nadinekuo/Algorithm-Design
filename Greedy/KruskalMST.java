import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class KruskalMST {


    //The UnionFind(int n) constructor will create a structure to hold n different disjoint sets,
    // each holding a single element labelled from 0 to n - 1.

    // The operation boolean union(int i, int j) will merge the sets containing element i and j.
    // It returns true if i and j belonged to different sets, and false if they were already part of the same set.

    // The method int find(int u) returns the ID of the set that contains element u.

    // Edge implements the Comparable interface so that you can sort them increasingly on their cost.
    // PQ<Edge> will always return edge with smallest cost :)

    /**
     * Builds a Minimum Spanning Tree (MST) using <--- lowest weight edges
     * Kruskal's Algorithm (as learned in class).
     * Nodes are numbered from 0 to n - 1.
     * @param n the amount of nodes in the graph
     * @param edges the edges that comprise the graph
     * @return the LIST OF EDGES that should be included in the MST
     *
     * KEEP TRACK OF DIFFERENT CLUSTERS: Union-Find class (data-structure) <-- maintain disjoint sets (whether 2 vertices are connected)
     * DONT MODIFY edges LIST.
     * n = no. of vertices!!!
     */
    public static List<Edge> buildMST(int n, List<Edge> edges) {

        if (edges == null) return null;

        List<Edge> resultMST = new ArrayList<>();
        if (n == 0 || edges.isEmpty()) return resultMST;

        // Start: each vertex forms a single cluster
        // Union-Find: n disjoint sets each containing 1 element, labelled from 0 - (n-1)!!!
        UnionFind partitions = new UnionFind(n);

        // add all edges to PQ --> removeMin gives smallest weight (cost) (see compareTo())
        PriorityQueue<Edge> pq = new PriorityQueue<>(edges);        // inserts all at once

        // Repeat until 1 cluster formed of ALL VERTICES:
        while (!pq.isEmpty()) {                         // Pick edge(u,v) with smallest weight (cost) NOT part of MST yet <-- removeMin
            Edge smallest = pq.remove();
            // union() merges 2 clusters (using each vertex' ID)
            if (partitions.union(smallest.getFrom(), smallest.getTo())) {  // If clusters of u, v are different: merge
                resultMST.add(smallest);                                   // Else, don't include edge in MST.
            }
        }
        return resultMST;
    }



    static class UnionFind {

        // Start: n clusters created
        // Goal: reduce the amount of clusters
        // The UnionFind data structure allows us to maintain disjoint sets (such as components of a graph).

        private int[] parent;
        private int[] rank;

        // Union Find structure implemented with two arrays for Union by Rank
        public UnionFind(int size) {
            parent = new int[size];
            rank = new int[size];
            for (int i = 0; i < size; i++) parent[i] = i;
        }

        /**
         * Merge two clusters, if they are not already part of the same cluster.
         *
         * @param i a node in the first cluster
         * @param j a node in the second cluster
         * @return true iff i and j had different clusters.
         */
        boolean union(int i, int j) {
            int parent1 = find(i);
            int parent2 = find(j);
            if (parent2 == parent1)
                return false;
            if (rank[parent1] > rank[parent2]) {
                parent[parent2] = parent1;
            } else if (rank[parent2] > rank[parent1]) {
                parent[parent1] = parent2;
            } else {
                parent[parent2] = parent1;
                rank[parent1]++;
            }
            return true;
        }

        /**
         * NB: this function should also do path compression
         * @param i index of a node
         * @return the root of the subtree containing i.
         */
        int find(int i) {
            int parent = this.parent[i];
            if (i == parent) {
                return i;
            }
            return this.parent[i] = find(parent);
        }

        // Return the rank of the trees
        public int[] getRank() {
            return rank;
        }

        // Return the parent of the trees
        public int[] getParent() {
            return parent;
        }
    }


    class Edge implements Comparable<Edge> {
        private int from;
        private int to;
        private int cost;

        public Edge(int from, int to, int cost) {
            this.from = from;
            this.to = to;
            this.cost = cost;
        }

        public int getFrom() {
            return from;
        }

        public int getTo() {
            return to;
        }

        public int getCost() {
            return cost;
        }

        @Override
        public int compareTo(Edge o) {
            return Integer.compare(this.cost, o.cost);
        }

        @Override
        public String toString() {
            return "Edge{" +
                    "from=" + from +
                    ", to=" + to +
                    ", cost=" + cost +
                    '}';
        }
    }


}
