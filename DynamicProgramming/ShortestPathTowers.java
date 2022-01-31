import java.util.Arrays;

public class ShortestPathTowers {



    public static void main(String[] args) {
        int n = 2;
        int m = 3;
        int[][] graph = { { 3, 5, 6 },
                          { 4, 2, 1 } };
        System.out.println(minLadderLength(n, m, graph));   // most expensive edge in shortest path is 1! So thats the min ladder length needed.
    }



    /**
     *
     *    Edge weight = ladder length needed = diff in neighbouring tower heights
     *    Node = tower, each has different height
     *      Each node i, j is accessed by 2 indices!
     *
     * @param n - #rows
     * @param m - # columns
     * @param graph - n * m nodes (towers) in total
     * @return - min length of ladder needed to get to node t (bottom right) <-- most expensive edge in shortest path
     *
     *   M[i][j] = curr optimal (min) length of ladder needed to get to node [i][j] <-- idx into graph
     *
     *   Keep in mind we might have to jump up from this node and therefore we compare the graph values.
     */
    public static int minLadderLength(int n, int m, int[][] graph) {

        int[][] mem = new int[n][m];   // Note: 0-indexing to match graph[][]

        // NOTE: Initialize all values as ∞, so MIN will ignore initial curr mem[i][j] values, but look at neighbours instead (like in Dijkstra)
        for (int[] row : mem) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }

        // Note: Base case:  0 cost to get to start node (top left)  <-- should not be ∞ still!
        mem[0][0] = 0;

        // NOTE: Iterate over the entire grid n * m times, since it can take n * m - 1 steps for the result to propagate to the end! (zigzags e.g.)
        //     in Belman-Ford we had at most n-1 outer iterations, since we were looking for a simple path,
        //          but now we need the max ladder length (most expensive edge) needed to get to t

        for (int k = 0; k < n * m; k++) {
            for (int i = 0; i < n; i++) {      // For all rows, for all cols
                for (int j = 0; j < m; j++) {

                    if (i == 0 && j == 0)      // NOTE: base case mem[0][0] will be reached for all n*m iterations k again
                        continue;

                    //Update this Opt(i, j):
                    // Try all 4 cases: left, right, up and down.
                    // NOTE: Do an out-of-bounds check before indexing the array! If i or j is on edge, keep curr mem[i][j] value.

                    // For each dir, take MAX of M[left] and edge(left, j)  <-- weight = difference in tower height
                    // The MIN ladder length needed corresponds to the MAX edge in shortest path!

                    // Over all neighbours,
                    // Note: Check whether we found a shorter path to our current node (i, j), by taking the MIN of the current value M[i][j]
                    //  And the MAX of Opt(neigh) and edge weight from neigh to this

                    // Note: we don't "add" the edge weight like in shortest path, but take the MAX (ladder length) to store in M[i, j].

                    if (j > 0) {
                        mem[i][j] = Math.min(mem[i][j], Math.max(
                                mem[i][j - 1],
                                graph[i][j] - graph[i][j - 1]   // note: if this height < neighbour height, the edge weight will be < 0, so it will be ignored by Math.MAX later on anyways!
                        ));
                    }
                    if (i > 0) {
                        mem[i][j] = Math.min(mem[i][j], Math.max(
                                mem[i - 1][j],
                                graph[i][j] - graph[i - 1][j])
                        );
                    }
                    if (j + 1 < m) {
                        mem[i][j] = Math.min(mem[i][j], Math.max(
                                mem[i][j + 1],
                                graph[i][j] - graph[i][j + 1])
                        );
                    }
                    if (i + 1 < n) {
                        mem[i][j] = Math.min(mem[i][j], Math.max(
                                mem[i + 1][j],
                                graph[i][j] - graph[i + 1][j])
                        );
                    }
                }
            }
        }
        for (int i = 0; i < mem.length; i++) {
            System.out.println(Arrays.toString(mem[i]));
        }
        return mem[n - 1][m - 1];
    }




}
