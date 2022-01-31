

public class PrefixSumming {


    /**  Brute-force: O(n) .....
     *
     * @param arr - array to sum values of
     * @param start - start idx, incl
     * @param end - end idx, incl
     * @return - Sum from start to end values
     */
    public static int sequentialSum(int[] arr, int start, int end) {
        int sum = 0;

        for (int i = start; i <= end; i++) {
            sum += arr[i];
        }
        return sum;
    }


    /**   Parallelized: O(n) too, but now we parallelize, so only 2 consecutive elements communicate
     *   T(n) = 2T(n/2) + O(1)
     *
     * @param arr - array to sum values of
     * @param start - start idx, incl
     * @param end - end idx, incl
     * @return - Sum from start to end values
     */
    public static int parallelSum(int[] arr, int start, int end) {

        // if end idx would be exclusive, we do if (start + 1 == end)
        if (start == end) {
            return arr[start];    // BASE CASE: single value!
        }
        // DIVIDE
        int midIdx = (start + end)/2;  // if even n, will be left middle
        // CONQUER: find sum on left and right
        int leftSum = parallelSum(arr, start, midIdx);
        int rightSum = parallelSum(arr, midIdx + 1, end);

        assert sequentialSum(arr, start, end) == leftSum + rightSum;  // CHECK IF SOLUTION IS EQUAL TO BRUTE-FORCE VERSION

        return leftSum + rightSum;
    }


    // ----------------- HELPERS for accessing children in binary tree --------------------------

    public static int leftChildIdx(int nodeIdx) {
        return 2 * nodeIdx + 1;
    }

    public static int rightChildIdx(int nodeIdx) {
        return 2 * nodeIdx + 2;
    }


    /** Traverse binary sum tree top down --> O(n) on number of nodes
     *   T(h) = 2T(h-1) + O(1)
     *
     * @param binaryTree - array based binary tree containing all recursive sums that would be computed using the methods above
     * @param rootIdx - root of whole sum tree = total sum of initial array of digits
     * @param depth - root level = d, ..., leaf layer = 0
     * @param passDown - value passed by parent
     *                 For left child: same pass down value as parent
     *                 For right child: pass down value of parent + left sibling original value
     */
    public static void prefixSum(int[] binaryTree, int rootIdx, int depth, int passDown) {

        // BASE CASE: leaf layer reached ---> modify array values (of leaves only)
        if (depth == 0) {
            binaryTree[rootIdx] += passDown;   // add the pass down value (from parent) to itself
            return;       // DON'T FORGET THIS!!!!
        }

        // DIVIDE: we fo down 1 layer
        int passDownLeft = passDown;
        int passDownRight = passDown + binaryTree[leftChildIdx(rootIdx)];  // add left child original digit

        // CONQUER: continue at children
        prefixSum(binaryTree, leftChildIdx(rootIdx), depth - 1, passDownLeft);
        prefixSum(binaryTree, rightChildIdx(rootIdx), depth - 1, passDownRight);
    }






}
