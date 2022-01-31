public class SortAndCount {


    /**  Number of swaps of CONSECUTIVE elements needed to get an ordered list
     *    (If it were not consecutive, you could use fewer swaps)
     *
     *    in-place!
     *    Total: O(n log n)
     *
     * @param array
     * @return
     */
    static int countInversions(int[] array) {
        return sortAndCount(array, 0, array.length-1);
    }


    /**  Helper summing up all inversions found during the whole merge sort process <-- essentially merge sort
     *   Sort input array using recursion and return number of inversions
     *
     * @param array - array
     * @param left - idx of mostleft element
     * @param right - idx of mostright element
     * @return - Number of swaps of CONSECUTIVE elements needed to get an ordered list
     */
    public static
    int sortAndCount(int[] array, int left, int right) {
        int inversions = 0;
        int mid;
        if (right > left) {     // BASE CASE: if left >= right, there is only 1 element!

            // DIVIDE: array into two parts and do mergeAndCount sort on both parts
            // If even n, mid will be left middle
            mid = (right + left) / 2;

            // CONQUER:
            // Inversions are the sum of left-part inversions, right-part inversions,
            // and inversions during merging
            inversions += sortAndCount(array, left, mid);   // left includes mid
            inversions += sortAndCount(array, mid + 1, right);

            // COMBINE: merge and count on sorted left and right
            inversions += mergeAndCount(array, left, mid, right);  // mid = ceiling(n/2)
        }
        return inversions;
    }



    public static // Merge two sorted arrays and return number of inversions that occurred.
    int mergeAndCount(int[] array, int left, int mid, int right) {

        int inversions = 0;
        int firstInRight = mid + 1;   // mid was final element in left sub-array
        int[] temp = new int[array.length];

        // We use relative indices into original full array!

        int i = left;     // i is relative index for left subarray INTO ORIGINAL ARRAY!
        int j = firstInRight;      // j is index for right subarray INTO ORIGINAL ARRAY!
        int k = left;      //  relative first idx in original full array!

        while ((i <= mid ) && (j <= right)) {
            if (array[i] <= array[j]) {     // Add the smaller element (should be i if ordered!)
                temp[k++] = array[i++];
            } else {
                temp[k++] = array[j++];
                // no. of consecutive swaps needed = firstIdxInRight (mid) - i
                inversions = inversions + (firstInRight - i);   // No. of elements left in i
            }
        }
        // Copy remaining elements of left subarray to temp
        while (i <= mid - 1) {
            temp[k++] = array[i++];
        }
        // Copy remaining elements of right subarray to temp
        while (j <= right) {
            temp[k++] = array[j++];
        }
        // Copy back merged elements to original array ("in-place", since we modify the orginal array)
        for (i = left; i <= right; i++) {
            array[i] = temp[i];
        }
        return inversions;
    }



}
