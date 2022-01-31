import java.util.Arrays;

public class Median {




    public static double solve(int n, double[] list) {

        // // We sort the list first, so that we can easily determine the median element.
        // Arrays.sort(list);
        list = mergeSort(list);

        // For even sized arrays, we average the 2 middle elements
        if (n % 2 == 0) {
            double sum = list[n/2 - 1] + list[n/2];
            return sum/2;
        } else {
            return list[n/2];   // integer div rounds down, which is the idx of the median!
        }

    }



    /**
     * * O(n log n) time
     * * O(n) space
     * * RECURSIVE
     * * divide, conquer, combine
     *
     * @param arr Array of integers to be sorted.
     * @return NEW      ARRAY of sorted integers in ascending order. NOT IN-PLACE.
     */
    public static double[] mergeSort(double[] arr) {

        // Base case: size 1

        if (arr.length < 2) return arr;

        // Split into 2 sub-arrays (not in-place)
        int mid = arr.length / 2;
        double[] left = Arrays.copyOfRange(arr, 0, mid);  // exlusive end idx
        double[] right = Arrays.copyOfRange(arr, mid, arr.length);

        // Sort each sub-array using merge sort (2 recursive calls, 1 for each half)
        // (you can leave out left = and right = )
        left = mergeSort(left);
        right = mergeSort(right);

        // Merge 2 sorted arrays into 1 big sorted array by calling helper
        return merge(left, right);
    }


    /** Helper method
     * assumes input arrays are sorted
     * will be executed for arrays with at least 2 elements
     * ONLY STABLE IF YOU USE <= instead of < ! (so equal element in left sub-array gets added first)
     * (here: unstable)
     */
    private static double[] merge(double[] s1, double[] s2) {

        double[] res = new double[s1.length + s2.length];
        int i = 0;  // idx array s1
        int j = 0;  // idx array s2
        int r = 0; // idx result array

        while(i < s1.length && j < s2.length) {
            if (s1[i] <= s2[j]) {
                res[r++] = s1[i++];
            } else {
                res[r++] = s2[j++];
            }
        }
        // Remaining elements (if unequal sizes)

        while (i < s1.length) res[r++] = s1[i++];
        while (j < s2.length) res[r++] = s2[j++];
        return res;
    }





}
