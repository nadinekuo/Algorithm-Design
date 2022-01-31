public class MergeSortInPlace {


    /**  O(n log n) like non in-place version, since we have a merge tree of height O(log n) and O(n) work per level
     *
     *
     * Takes an array and sorts it in an ascending order.
     *
     * @param arr - the array that needs to be sorted.
     */
    public void sort(int[] arr) {
        sortHelper(arr, 0, arr.length - 1);
    }

    /**
     * Helper method that recursively calls sort on the subarrays and merges them together.
     *
     * @param arr  - the array that needs to be sorted.
     * @param low  - lower boundary of the subarray.
     * @param high - upper boundary of the subarray <--- INDICES!
     *             BOTH INDICES INCLUSIVE.
     */
    public void sortHelper(int[] arr, int low, int high) {

        if (low >= high) {   // base case: low and high cross, so sub array has <= 2 elements
            return;
        }
        // DIVIDE: split in 2 halves
        int mid = (low + high) / 2;
        // CONQUER: recurse on left and right
        sortHelper(arr, low, mid);
        sortHelper(arr, mid + 1, high);
        // COMBINE: merge left and right sub-arrays (increasing order)
        merge(arr, low, mid, high);
    }

    /**
     * Helper method that merges two partially sorted subarrays <-- but its still in-place since we don't create a new result array
     *
     * @param arr  - array that needs to be sorted.
     * @param low  - lower boundary of the lower subarray.
     * @param mid  - index to indicate the boundary between the two subarrays.
     * @param high - upper boundary of the upper subarray.
     */
    public void merge(int[] arr, int low, int mid, int high) {

        int leftSize = mid - low + 1;       // convert from indices to size
        int rightSize = high - mid;
        int[] left = new int[leftSize];     // 2 temporary auxiliary arrays for left and right
        int[] right = new int[rightSize];
        for (int i = 0; i < leftSize; i++) {   // Fill left[] with left subarray (incl mid)
            left[i] = arr[low + i];           // Add offset!!!
        }
        for (int i = 0; i < rightSize; i++) {   // Fill right[] with right subarray (excl mid)
            right[i] = arr[mid + 1 + i];
        }

        int i = 0;   // idx into left[]
        int j = 0;   // idx into right[]
        int k = low;   // idx into result array (original arr, cause in-place)
        while (i < leftSize && j < rightSize) {    // Process the 2 auxiliary left and right sub-arrays
            if (left[i] <= right[j]) {             // Stable increasing sort
                arr[k++] = left[i++];
            } else {
                arr[k++] = right[j++];
            }
        }
        while (i < leftSize) {    // Add remaining elements into original array
            arr[k++] = left[i++];
        }
        while (j < rightSize) {
            arr[k++] = right[j++];
        }
    }






    // Without creating auxiliary arrays (CSDojo)
    // The modulo and division stuff is so they can store two numbers in one integer
//    void mergeInPlace(int a[], int l, int m, int r)
//    {
//        // increment the maximum_element by one to avoid
//        // collision of 0 and maximum element of array in modulo
//        // operation
//        int mx = Math.max(a[m], a[r]) + 1;
//
//        int i = l, j = m + 1, k = l;
//        while (i <= m && j <= r && k <= r) {
//
//            // recover back original element to compare
//            int e1 = a[i] % mx;
//            int e2 = a[j] % mx;
//            if (e1 <= e2) {
//                a[k] += (e1 * mx);
//                i++;
//                k++;
//            }
//            else {
//                a[k] += (e2 * mx);
//                j++;
//                k++;
//            }
//        }
//
//        // process those elements which are left in the array
//        while (i <= m) {
//            int el = a[i] % mx;
//            a[k] += (el * mx);
//            i++;
//            k++;
//        }
//
//        while (j <= r) {
//            int el = a[j] % mx;
//            a[k] += (el * mx);
//            j++;
//            k++;
//        }
//
//        // finally update elements by dividing with maximum
//        // element
//        for (int x = l; x <= r; i++)
//            a[x] /= mx;
//    }

}
