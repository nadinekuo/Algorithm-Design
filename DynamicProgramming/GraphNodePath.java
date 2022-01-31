import java.util.*;

public class GraphNodePath {



    /**
     * Implement this method to be an iterative approach for the recursive formula given in the slides
     *     2 nodes can max have 2 nodes in between
     *
     *   Memoization table[i] = best sum of weights so far, until this node i
     *
     *   You MUST pick the final node! So final total sum must be in final entry arr[n]
     *
     * @param n     The number of nodes
     * @param nodes the different weights. You should use nodes[1] to nodes[n]
     * @return the minimal weight
     */
    public static int minCostNodes(int n, int[] nodes) {

        // OR you leave out these checks and fill whole mem[] first, but now we can stop early :)
        if (n == 0) {     // no nodes
            return 0;
        } else if (n == 1) {
            return nodes[1];  // Note: We must pick the final one! So this is the only option.
        } else if (n == 2) {
            return nodes[2];
        }

        // Note: If n == 3, it would return mem[0] + weight[3] = 0 + weight[3] finally

        // Initialize memoization table
        int[] bestSumOfWeightsSoFar = new int[n + 1];  // start from 1

        // Base cases: Initialize mem[0], mem[1], mem[3]
        bestSumOfWeightsSoFar[0] = 0;
        bestSumOfWeightsSoFar[1] = nodes[1];
        bestSumOfWeightsSoFar[2] = nodes[2];
        // Note: initializing mem[3] is not necessary, since it will take mem[0] as minPrevValue!

        // From node 3 onwards, take the minimum of the previous 3 node weights (so max 2 nodes in between)
        for (int i = 3; i <= n; i++) {
            int minPrevValue = Math.min(Math.min(bestSumOfWeightsSoFar[i-1], bestSumOfWeightsSoFar[i-2]), bestSumOfWeightsSoFar[i-3]);
            bestSumOfWeightsSoFar[i] = minPrevValue + nodes[i];          // Add that to this node value i
        }

        // We must pick final node!
        return bestSumOfWeightsSoFar[n];  // TODO: if there was no constraint of having to pick the final one, result is minimal of the final 3 entries in table
    }



    public static int[] getResultArray(int n, int[] nodes) {
        if (n <= 2) {
            return nodes;
        }
        int[] mem = new int[n + 1];
        mem[0] = 0;
        mem[1] = nodes[1];
        mem[2] = nodes[2];
        for (int i = 3; i <= n; i++) {
            // Find node of max distance 3, that minimizes total weight.
            int result = nodes[i] + Integer.min(Integer.min(mem[i - 1], mem[i - 2]), mem[i - 3]);
            mem[i] = result;
        }
        return mem;
    }



    // (Note: the final node must be included, but does not matter implementation-wise)
    // Note: result = mem[n]
    public static List<Integer> recoverNodeIndicesLinkedList(int n, int[] nodes, int result, int[] mem) {

        // Note: Since we find the solution in reversed order, using a list allows us to prepend in O(1)
        LinkedList<Integer> path = new LinkedList<>();

        int i = n;
        int currRes = result;   // We wanna find all values that make up this minimal result sum
        while (i > 0) {
            if (mem[i] == result) {   // this nodes value was included in sum
                path.addFirst(i);
                currRes = currRes - nodes[i];    // subtract this nodes value, continue search backwards
            }
            i--;
        }
        return path;

//       OR for loop
//        for (int i = n; i >= 1; i--) {       // see while loop version below
//            if (mem[i] == currentResult) {
//                solution.addFirst(i);
//                currentResult -= nodes[i];
//            }
//        }
    }





    // 1-indexing!
    // int result must correspond to mem[n] <-- total sum in final node
    public static List<Integer> recoverNodeIndicesReverseList(int n, int[] nodes, int result, int[] mem) {

        // Base cases of n=1, n=2, n=3 will just give empty list

        List<Integer> nodeIndicesSelected = new ArrayList<>();

        // Start at final node (must have been picked, so mem[n] == result must hold)
        int currSum = result;
        int nodeIdx = n;

        while (nodeIdx > 0) {    // Walk backwards: subtract weight of this node from curr sum

            if (mem[nodeIdx] == currSum) {
                nodeIndicesSelected.add(nodeIdx);
                currSum -= nodes[nodeIdx];
            }
            // Find closest node that stores this value --> add to list (was selected)
            nodeIdx--;
        }
        Collections.reverse(nodeIndicesSelected);
        return nodeIndicesSelected;
    }



    /*   Note: no 2 nodes may be joined by an edge!
     * Note that entry node[0] should be avoided, as nodes are labelled node[1] through node[n].
     */
    public static int maxWeightIndependentSet(int n, int[] nodes) {
        int[] mem = new int[n + 1];

        // base cases
        mem[0] = 0;
        mem[1] = nodes[1];
        for (int i = 2; i <= n; i++) {
            // Either we add this weight i to Opt(i-2), since we cannot take the previous
            // Or we take Opt(i-1), so don't include this i
            mem[i] = Math.max(mem[i-2] + nodes[i], mem[i-1]);
        }
        return mem[n];
    }



}



