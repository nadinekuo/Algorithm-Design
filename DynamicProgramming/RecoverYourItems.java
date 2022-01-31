import java.util.*;

public class RecoverYourItems {


    /**
     * Recover all selected items i
     *
     * @param n the number of items n.
     * @param m the maximum weight.
     * @param v the array containing the weights of the items, in index 1 through n.
     *   Note this means you should ignore v[0] and use v[1] through v[n].
     * @param mem the memory filled by the dynamic programming algorithm using the provided
     *     recursive formulation.
     * @return the set of indices of items that together form the optimal solution.
     */
    public static Set<Integer> solve(int n, int m, int[] v, int[][] mem) {

        // NOTE: 1. Init vars based on call used for result: Opt(n, 0)
        int i = n;
        int j = 0;

        // 2. NOTE: Create data structure to store result
        Set<Integer> result = new HashSet<>();

        // 3. NOTE: Loop through mem[] to figure out the answer using formula
        //      until base case i == 0 <-- while-condition

        // Opt(i, j) = Opt(i-1, j) if v[i] > m-j
        // Opt(i, j) = max(Opt(i-1, j),  v[i] * Opt(i-1, j + v[i]))
        while(i > 0) {
            if (v[i] > m - j) {
                i--;
            } else if (mem[i][j] == mem[i-1][j]) {
                i--;             // i was not added, look further
            } else {
                result.add(i);     // Add to result and update vars AFTER adding Note this is in reversed order!
                j += v[i];
                i--;                // Note: we decrement i AFTER adding v[i] to j!
            }
        }
        // OR: check when we DID include i, but be careful with IdxOutOfBounds!!
//        while (i > 0) {
//            if (j + v[i] <= m && mem[i-1][j] < v[i] * mem[i-1][j + v[i]]) {
//                result.add(i);
//                j += v[i];
//            }
//            i--;
//        }
        // 4. NOTE: Return answer: all selected items i
        return result;
    }



    public static void main(String[] args) {

        int n = 5;
        int m = 10;
        // The best answer is to take 5, 3 and 2 to get 30, so indices 1, 2, and 5
        int[] v = {0, 5, 3, 1, 8, 2};
        int[][] mem = {
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {5, 5, 5, 5, 5, 5, 1, 1, 1, 1, 1},
                {15, 15, 15, 5, 5, 5, 3, 3, 1, 1, 1},
                {15, 15, 15, 5, 5, 5, 3, 3, 1, 1, 1},
                {15, 15, 15, 5, 5, 5, 3, 3, 1, 1, 1},
                {30, 15, 15, 10, 6, 6, 3, 3, 2, 1, 1}
        };
        Set<Integer> ans = RecoverYourItems.solve(n, m, v, mem);
        Assertions.assertEquals(3, ans.size());
        Assertions.assertTrue(ans.contains(1));
        Assertions.assertTrue(ans.contains(2));
        Assertions.assertTrue(ans.contains(5));

    }

}
