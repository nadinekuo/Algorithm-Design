import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class AllPartialSums {


    /**  Brute-force would be O(2^n), since we have 2^n possible subsets of the ints in arr
     *
     *   0 must be in every set!! (sum of none)
     *
     *   Using Set (instead of List) will automatically get rid of duplicates!
     *
     * Computes all possible partial sums given an array of integers.
     *
     * @param arr - all values in the input set
     * @return set of sums
     */
    public static Set<Integer> partialSums(Integer[] arr) {
        if (arr.length == 0) {
            return new HashSet<>(Collections.singletonList(0));   // even empty set contains sum 0!
        }
        return partialSumsHelper(arr, 0, arr.length - 1);
    }



    public static Set<Integer> partialSumsHelper(Integer[] arr, int low, int high) {
        // BASE CASE: 1 element
        if (low == high) {
            return new HashSet<>(Arrays.asList(arr[low], 0));  // set of 0 and the single element itself
        }
        // DIVIDE
        int mid = (low + high) / 2;

        // CONQUER: Find all partial sums in left and right
        Set<Integer> leftSums = partialSumsHelper(arr, low, mid);
        Set<Integer> rightSums = partialSumsHelper(arr, mid + 1, high);
        Set<Integer> combinedSums = new HashSet<>();
        combinedSums.addAll(leftSums);   // Add all sums found in left and right to result set
        combinedSums.addAll(rightSums);

        // COMBINE: take cartesian product of left and right partial sums found --> gives all possible sums!
        // Worst case: all partial sums are unique --> O(2^n) ....
        // Else duplicates would not be added, since its a set!
        for (Integer a : leftSums) {
            for (Integer b : rightSums) {
                combinedSums.add(a + b);
//                int sum = i + j;
//                combinedSums.add(sum);   // 1 element from left, 1 element from right added up
//                System.out.println("\nAdding " + i + " from left and " + j + " from right == " + sum);
            }
        }
        return combinedSums;
    }






}
