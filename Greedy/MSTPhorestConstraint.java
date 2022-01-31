import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class MSTPhorestConstraint {

    /**
     * Optimizes the provided Phorest to be as close to an MST as possible. <--- Kruskal, since we already have an array of edges!
     *
     * Prim-Jarnik would not be a good idea here, cause we we'd have to insert VertexNumPairs in PQ...
     *
     * @param nodes the number of nodes in the network
     * @param graph all edges in the full graph
     * @param phorest the edges in the Phorest
     * @return total edge weight of optimized Phorest
     */
    public String fakeMSTStoreTree(int nodes, Edge[] graph, Edge[] phorest) {

        // Create the Phorest: edges already present, so must be in result "MST"
        Edge[] tree = new Edge[nodes - 1];    // "MST" has n-1 edges
        UnionFind uf = new UnionFind(nodes);
        int p = phorest.length;
        for (int i = 0; i < p; i++) {
            tree[i] = phorest[i];        // Add all edges already present
            uf.join(tree[i].x, tree[i].y);   // Add to uf too!!
        }

        // Optimize the Phorest by adding edges (lowest weight first) until tree created

        Arrays.sort(graph);      // Pick the lowest weight edges first!
        for (Edge e : graph) {
            if (uf.join(e.x, e.y)) {   // add if no cycle created (or if already added to UF)
                tree[p] = e;           // insert new edge in result  "MST"
                ++p;                  // next idx to insert into
            }
//            if (p == nodes - 1)       // stop when n-1 edges added, but uf will stop anyways when cycle
//                break;
        }

        // Return optimized phorest total edge weight
        int result = 0;
        for (Edge e : tree) {
            result += e.l;
        }
        return Integer.toString(result);
    }


    /**
     * Optimizes the provided Phorest to be as close to an MST as possible.
     *    Phorest may contain cycles!!! (so it can never become a real MST anymore)
     *
     * @param nodes the number of nodes in the network
     * @param m the number of edges in the network
     * @param graph all edges in the full graph from index 1 to m
     * @param p the number of edges in the Phorest
     * @param phorest the edges in the Phorest from index 1 to p
     *
     * @return total edge weight of optimized Phorest
     */
    public static int fakeMSTCountClusters(int nodes, int m, Edge[] graph, int p, Edge[] phorest) {

        UnionFind uf = new UnionFind(nodes);
        int numClusters = nodes;   // initially each node forms 1 cluster, we stop when 1 cluster left
        // but actually not needed to keep track of, cause uf won't add cycles anyways. BUT: its useful to get the minimal spacing in K-clustering!

        int cost = 0;              // stores total cost of final "MST"

        for (int i = 1; i <= p; i++) {  // Add all edges already in phorest to UF

            cost += phorest[i].l;    // Even if union is false (cycle in phorest), we include the edge cost!

            if (uf.join(phorest[i].x, phorest[i].y)) {     // NOTE: if cycle in phorest, it will not be added to UF!
                numClusters--;          // 2 clusters merged, so 1 fewer cluster
            }
        }

        // Add edges to phorest until all nodes connected (1 cluster left), not necessarily n-1 edges, since phorest may contain cycles!
        Arrays.sort(graph);   // sort on edges increasingly

        for (int i = 1; i <= m; i++) {    // go over all edges in full graph
            if (uf.join(graph[i].x, graph[i].y)) {
                numClusters--;
                cost += graph[i].l;     // Now we only add cost if union = true (we don't add extra cycles)
            }
            if (numClusters == 1) break;
        }
        return cost;
    }



    static class Edge implements Comparable<Edge> {

        // from, to and length
        int x, y, l;

        public Edge(int from, int to, int length) {
            x = from;
            y = to;
            l = length;
        }

        public int compareTo(Edge o){
            return this.l - o.l;
        }
    }


    static class UnionFind {

        private int[] parent;

        private int[] rank;

        public UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) parent[i] = i;
        }

        // returns false if x and y are in same set
        private boolean join(int x, int y) {
            int xrt = find(x);
            int yrt = find(y);
            if (rank[xrt] > rank[yrt])
                parent[yrt] = xrt;
            else if (rank[xrt] < rank[yrt])
                parent[xrt] = yrt;
            else if (xrt != yrt)
                rank[parent[yrt] = xrt]++;   // only increase rank if equal tree depths are merged
            return xrt != yrt;
        }

        private int find(int x) {   // path compression: find leader recursively and point to it
            return parent[x] == x ? x : (parent[x] = find(parent[x]));
        }
    }



    // used to scan test files

    public Edge[] makeGraph(Scanner sc) {
        int m = sc.nextInt();
        Edge[] edges = new Edge[m];
        for (int i = 0; i < m; i++) edges[i] = new Edge(sc.nextInt(), sc.nextInt(), sc.nextInt());
        Arrays.sort(edges, Comparator.comparingInt(e -> e.l));
        return edges;
    }



}
