public class ShortestPathDAG {


    public static void main(String[] args) {

        // [vertex][outgoing neigh]
        // 5 nodes:
        int[][] adjacencyList = new int[][] {{2, 3, 4},  // node 1 (a)
                                              {3, 4},    // node 2 (b)
                                                {5},    // node 3 (c)
                                                {5},     // node 4 (d)
                                                 {}};   // node 5 (e)

        int[][] edgeWeights = new int[6][6];

        // For all non-existing edges, store inf
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                edgeWeights[i][j]= (1 << 30); // or (1 << 30)
            }
        }

        // ignore idx 0
        edgeWeights[1][2] = 2;
        edgeWeights[1][3] = -2;
        edgeWeights[1][4] = 6;
        edgeWeights[2][3] = 1;
        edgeWeights[2][4] = 3;
        edgeWeights[3][5] = 6;
        edgeWeights[4][5] = -1;

        System.out.println(shortestPathDAGIterative(adjacencyList, edgeWeights));
    }


    /**  Iterative: Use topological order!
     *
     *  Time complexity   ---> O(m)
     *
     * 	- connected graph: m >= n-1
     * 	- We cannot visit an edge 2x (since we only look at outgoing edges)
     *    In a dense graph, it would be O(n^2)...
     *
     * @param adjacencyList - 0-indexed!
     * @param edgeWeights - 1-indexed!
     * @return shortest path from start (1) to end (numNodes n, here 5)
     */
    public static int shortestPathDAGIterative(int[][] adjacencyList, int[][] edgeWeights) {

        int numNodes = adjacencyList.length;
        int[] mem = new int[numNodes + 1];

        mem[1] = 0;      // Base case: it takes 0 cost to reach start node!
        for (int i = 2; i <= numNodes; i++) {
            mem[i] = (1 << 30);   // initially store inf
        }

        for (int i = 1; i <= numNodes; i++) {   // for each node i

            int numOutgoingEdges = adjacencyList[i-1].length;
            for (int k = 0; k < numOutgoingEdges; k++) {     // for each neighbour j
                int neigh_j = adjacencyList[i-1][k];
                // Either:
                // - Keep shortest path to node j
                // - Update shortest path to node j by adding this edge (i, j)
                mem[neigh_j] = Math.min(mem[neigh_j], mem[i] + edgeWeights[i][neigh_j]);
            }
        }
        return mem[numNodes];   // Shortest path to final node (5)
    }




}
