import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class KnapSack {



    /**   O(nW) time and space <-- pseudo-polynomial: does not depend on input size, but their values!
     *
     *   M[i, w] = max weight that can be achieved using i items, having a limit of w total weight
     *
     * @param n the number of items
     * @param W the maximum weight
     * @param w the weight of the items, indexed w[1] to w[n].  <--- limited
     * @param v the value of the items, indexed v[1] to v[n];  <--- maximize total value!
     * @return the maximum obtainable value.
     */
    public static int maxTotalValueIterative(int n, int W, int[] w, int[] v) {

        int[][] mem = new int[n + 1][W + 1];

        // Base cases (OR you can also do this in the loops below, but idx should start from 0 then)
        // OR leave out base case, since Java will store 0 by default
        for (int j = 0; j <= W; j++) {  // 1st row (item i = 0) are 0's
            mem[0][j] = 0;
        }
        for (int i = 0; i <= n; i++) {  // 1st col (weight w = 0) are 0's
            mem[i][0] = 0;
        }

        // Iteratively fill table:
        // For each item/row i, for each max weight/col w
        // Find highest total value that fits within max weight w
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= W; j++) {

                if (w[i] > j) {   // weight i does not fit in curr max weight j, so take prev Opt(i-1)
                    mem[i][j] = mem[i-1][j];
                } else {
                    int totalValueIncludingThisItem = v[i] + mem[i-1][j - w[i]];  // add this value, decrease weight left!
                    int totalPrevValue = mem[i-1][j];            // if we do not take this item i
                    mem[i][j] = Math.max(totalPrevValue, totalValueIncludingThisItem);
                }
            }
        }
        // Final maximized weight is in M[n, W]
        return mem[n][W];
    }




    /**
     * Retrieves the knapsack items used in an optimal solution.
     *
     *   Diagonally traverse the 2D array
     *
     * @param n the number of items.
     * @param W the maximum weight.
     * @param w the weight of the items, indexed w[1] to w[n].
     * @param v the value of the items, indexed v[1] to v[n].
     * @param mem is a (n x W) integer array, where element mem[i][j] is
     *            the maximum value using only elements 1 to i and max weight of j.
     *
     * @return list containing the id of the items used in the optimal solution, ordered increasingly!!
     */
    public static List<Integer> retrieveItemsSelectedIterative(int n, int W, int[] w, int[] v, int[][] mem) {

        LinkedList<Integer> itemsSelected = new LinkedList<>();  // Efficient prepending :)

        // Starting at n and W... walk backwards!

        while (n > 0) {
            // We selected item i if:
            // - its weight fits within W! (Note: avoids IdxOutOfBounds)
            // - we added this value vi to the prev Opt(i-1, w-w_i)
            if (w[n] <= W && mem[n][W] == v[n] + mem[n - 1][W - w[n]]) {  // We took this item n!
                itemsSelected.addFirst(n);
                W = W - w[n];    // Subtract weight of this item n!
            }
            n--;    // Note: Decrease item idx, even if we did not take item n
        }
// OR you check when we did NOT select i:
//        while (i > 0) {
//            if (w[i] > j) {
//                i--;
//            } else if (mem[i][j] == mem[i-1][j]) {
//                i--;
//            } else {
//                itemsSelected.addFirst(i);
//                j -= w[i];
//                i--;
//            }
//        }
//
        return itemsSelected;   // Ascending order of items added!
    }




    /**   Version using SackItem objects
     *
     * @param n the number of items
     * @param W the maximum weight
     * @param w the weight of the items, indexed w[1] to w[n].
     * @param v the value of the items, indexed v[1] to v[n];
     * @return the maximum obtainable value.
     */
    public static int maxTotalValueIterativeHelperClass(int n, int W, int[] w, int[] v) {

        Sackitem[] items = new Sackitem[n + 1];
        for (int i = 1; i <= n; i++) {
            items[i] = new Sackitem(w[i], v[i]);  // weight, profit to maximize
        }

        int[][] knapsack = new int[n + 1][W + 1];

        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= W; j++) {
                if (i == 0 || j == 0) {
                    knapsack[i][j] = 0;
                } else    // If weight is too much, take previous element. Otherwise, utilize recurrence to find desired value.
                    if (items[i].weight > j) {
                        knapsack[i][j] = knapsack[i - 1][j];
                    } else {
                        knapsack[i][j] = Math.max(items[i].value + knapsack[i - 1][j - items[i].weight], knapsack[i - 1][j]);
                    }
            }
        }
        // Returning the found maximized weight.
        return knapsack[n][W];
    }



    static class Sackitem {

        public int weight, value;

        public Sackitem(int weight, int value) {
            this.weight = weight;
            this.value = value;
        }
    }




    // Generally, we prefer iterative over recursive to avoid memory overhead...

    public static int maxTotalValueRecursive(int n, int W, int[] w, int[] v) {

        // Initialize 2D mem array:
        int[][] mem = new int[n + 1][W + 1];

        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= W; j++) {
                if (i == 0 || j == 0) {   // Base cases:  1st row (item i = 0) and 1st col (weight w = 0) are 0's
                    mem[i][j] = 0;
                } else {
                    mem[i][j] = -1;    // to be filled by rec helper
                }
            }
        }
        return maxTotalValueRecursiveHelper(n, W, w, v, mem);  // return Opt(n, W) recursively
    }


    public static int maxTotalValueRecursiveHelper(int i, int W, int[] w, int[] v, int[][] mem) {

        if (mem[i][W] != -1) return mem[i][W];   // Base case: already computed

        if (w[i] > W) {    // weight i does not fit in curr max weight j, so take prev Opt(i-1)
            mem[i][W] = maxTotalValueRecursiveHelper(i-1, W, w, v, mem);
            return mem[i][W];    // DON'T FORGET TO RETURN VALUE!
        }

        // add this value, decrease weight left!
        int totalValueIncludingThisItem = v[i] + maxTotalValueRecursiveHelper(i-1, W - w[i], w, v, mem);
        // if we do not take this item i
        int totalPrevValue = maxTotalValueRecursiveHelper(i-1, W, w, v, mem);
        int optimalValue = Math.max(totalPrevValue, totalValueIncludingThisItem);
        mem[i][W] = optimalValue;
        return optimalValue;
    }




    public static List<Integer> retrieveItemsSelectedRecursive(int n, int W, int[] w, int[] v, int[][] mem) {

        // Here we can just APPEND, since recursion gives ascending order!
        List<Integer> itemsSelected = new ArrayList<>();
        return addItemsRecursively(n, W, w, mem, itemsSelected);
    }


    // We don't prepend, but append, since we do it AFTER the recursive call!
    // Gives ascending order of items i
    public static List<Integer> addItemsRecursively(int i, int W, int[] w, int[][] mem, List<Integer> items) {

        // Base case
        if (i == 0 || W == 0) {
            return items;  // don't add item, we reached the first row/col
        }

        // Recursively walk backwards (traverse diagonally)
        // If this value is equal to the value in row above (same col/weight), we did not take it since it did not add anything!
        // TODO: if you check mem[i][W] == v[i] + mem[i-1][W-w[i]] first, it will give IdxOutOfBounds!!
        if (mem[i][W] == mem[i-1][W]) {
            addItemsRecursively(i-1, W, w, mem, items);
        } else {      // if unequal it means we added it, since it gave a more optimal total value by including it
            addItemsRecursively(i-1, W - w[i], w, mem, items);   // recurse back using decreased weight!
            items.add(i);
        }

        return items;
    }



}
