import java.util.Arrays;


public class MergeSortArray {

    // see VOID version below!

    /**
     * * O(n log n) time
     * * O(n) space
     * * RECURSIVE
     * * divide, conquer, combine
     *
     * @param elements Array of integers to be sorted.
     * @return NEW      ARRAY of sorted integers in ascending order. NOT IN-PLACE.
     */
    public static int[] mergeSort(int[] elements) {

        // base case (size 1)
        if (elements.length < 2) {
            return elements;            // if void: return
        }

        // Split into 2 sub-arrays (int division rounds down)
        //  not in-place: we use temporary arrays
        int mid = elements.length/2;
        int[] left = Arrays.copyOfRange(elements, 0, mid); // exclusive mid
        int[] right = Arrays.copyOfRange(elements, mid, elements.length);

        // Sort each sub-array using merge sort (2 recursive calls, 1 for each half)
        // (you can leave out left = and right = )
        left = mergeSort(left);
        right = mergeSort(right);

        // Merge 2 sorted sub-arrays to create 1 big sorted array
        return merge(left, right);
    }



    /** Helper method
     * assumes input arrays are sorted
     * will be executed for arrays with at least 2 elements
     * ONLY STABLE IF YOU USE <= instead of < ! (so equal element in left sub-array gets added first)
     * (here: unstable)
     */
    private static int[] merge(int[] s1, int[] s2) {
        int[] result = new int[s1.length + s2.length]; // enough capacity
        int i = 0;            // index array s1
        int j = 0;            // index array s2
        int r = 0;             // index result array
        while (i < s1.length && j < s2.length) {
            if (s1[i] < s2[j]) {          // if s1 has smallest currently
                result[r++] = s1[i++];    // insert smallest into larger res array and post-increment both indices
            } else {
                result[r++] = s2[j++];
            }
        }
        while (i < s1.length) result[r++] = s1[i++];       // if elements left still, due to unequal lengths
        while (j < s2.length) result[r++] = s2[j++];
        return result;

    }

// // helper method
// // will be executed for arrays with at least 2 elements
//   private static int[] merge(int[] s1, int[] s2) {
//       int[] result = new int[s1.length + s2.length]; // enough capacity
//       int i = 0;
//       int j = 0;
//       while (i + j < result.length) {
//           if (j == s2.length || i < s1.length && s1[i] < s2[j]) { // if only s1 has elements left or s1 has smallest currently
//               result[i+j] = s1[i++]; // insert smallest into larger res array and post-increment i
//           } else {
//               result[i+j] = s2[j++];
//           }
//       }
//       return result;

//   }


    /**
     * Takes an array and sorts it in an ascending order.
     * This has to be done by using merge sort.
     *
     * If the array is null, the metod should stop.
     *
     * @param arr
     *     - the array that needs to be sorted.
     */
    public static void mergeSort2(int[] arr) {
        // base cases
        if (arr == null || arr.length < 2) return;

        // split
        int mid = arr.length/2;
        int[] left = Arrays.copyOfRange(arr, 0, mid);
        int[] right = Arrays.copyOfRange(arr, mid, arr.length);

        // merge
        mergeSort2(left);
        mergeSort2(right);

        merge2(arr, left, right);
    }


    /** Helper method: you MUST pass arr as input, since this merge sort processes the input sequence!! (no result array)
     * assumes input arrays are sorted
     * will be executed for arrays with at least 2 elements
     * ONLY STABLE IF YOU USE <= instead of < ! (so equal element in left sub-array gets added first)
     * (here: unstable)
     */
    private static void merge2(int[] arr, int[] s1, int[] s2) {

        int i = 0;
        int j = 0;
        int r = 0;
        while (i < s1.length && j < s2.length) {
            if (s1[i] < s2[j]) {
                arr[r++]  = s1[i++];
            } else {
                arr[r++] = s2[j++];
            }
        }
        // remaining elements
        while (i < s1.length) {
            arr[r++] = s1[i++];
        }
        while (j < s2.length) {
            arr[r++] = s2[j++];
        }
    }

}
