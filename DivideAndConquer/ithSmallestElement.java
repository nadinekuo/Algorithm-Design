import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ithSmallestElement {


    /**     https://brilliant.org/wiki/median-finding-algorithm/#
     *    Basically QUICK SELECT, but now we guarantee the pivot is balanced by picking it based on medians
     *
     *   We assume UNIQUE values!
     *
     *    ---> Worst case run-time complexity O(n)
     *   No sorting! Since that's O(n log n)
     *
     * Takes an array and returns the ith smallest number.
     *  0 based!
     *   So if list were sorted, rank 0 is pos 1, rank 3 is pos 4...
     *
     * @param arr   - the array to get the element from.
     * @param index - the index of the return value if the array had been sorted <--- i
     *
     *    passing idx = arr.length - 1 gives the LARGEST value!
     *
     *   HOW TO GET THE ith LARGEST VALUE???
     *   - Using i = arr.length - x - 1
     *   - Swapping the recursive calls on low and high
     *
     *
     */
    public static int findIthArrayInPlace(int[] arr, int index) {

        // DIVIDE: "create subarrays" of size 5 (in-place)

        int numSublists = (arr.length - 1) / 5 + 1;  // How many sublists of size 5 we can make (can have < 5)
        // int numSublists = Math.ceil(arr.length/5);   <--- does not work since div gives double...

        int[] medians = new int[numSublists];    // will store median of each sublist

        //  Sort the sub-lists (in-place) and find their medians --> O(n) since its just size 5
        for (int i = 0; i < numSublists; i++) {

            int idxFirstInSublist = i * 5;   // 0, 5, 10, 15, ...

            // incl. start, excl. end
            Arrays.sort(arr, idxFirstInSublist, Math.min(idxFirstInSublist + 5, arr.length - 1));  // size can be < 5, so upper idx is not always first + 4
            int relativeIdxMedian = idxFirstInSublist + Math.min(2, (arr.length - idxFirstInSublist)/2);  // If size < 5 it must be the final group!
            medians[i] = arr[relativeIdxMedian];
        }

        // Calculate the pivot (median of medians)
        int pivot;

        if (numSublists <= 5) {    // Only sort if < or <= 5 medians, to keep it O(n)!
            Arrays.sort(medians);
            pivot = medians[numSublists / 2];   // pivot is median of SORTED list of medians
        } else {
            pivot = findIthArrayInPlace(medians, numSublists / 2);  // We reuse this method on M to get the pivot recursively!
        }

        // First count how many will be in the 'low' array.
        // We could skip this step when using arrayLists, but there is no nice conversion
        // from ArrayList<Integer> to int[], so this is faster and does not affect the time complexity.
        int lowCount = -1;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] <= pivot)     // pivot itself is included in low!
                lowCount++;
        }

        // ------  PARTITION, but we only create 1 subarray!

        // CONQUER: recurse on left/low if i < pivot idx, or right/high  if i > pivot idx
            // lowCount = k = pivot idx  !!!!!!!!!!!!!

        if (index < lowCount) {     // create left subarray (< pivot value)    if i < pivot idx
            int[] low = new int[lowCount];
            int lowIdx = 0;
            boolean skipped = false;   // Will be false until element equal to pivot is found.
            // Once they find one, they set the boolean to true, and leave it out
            // And if they then find another value that is equal to the pivot they do include it

            for (int i = 0; i < arr.length; i++) {
                if (arr[i] <= pivot) {
                    if (arr[i] != pivot || skipped) {
                        low[lowIdx++] = arr[i];   // insert value < pivot value
                    } else {
                        skipped = true;
                    }
                }
            }
            return findIthArrayInPlace(low, index);  // if multiple values equal to pivot, 1 single pivot value is left out of low

        } else if (index > lowCount) {     // Create right sublist (> pivot value)    if i > pivot idx

            int[] high = new int[arr.length - lowCount - 1];   // exclude pivot, so -1
            int highIdx = 0;
            for (int i = 0; i < arr.length; i++) {
                if (arr[i] > pivot) {
                    high[highIdx++] = arr[i];    // insert value > pivot value
                }
            }
            // lowCount = k = pivot idx
            return findIthArrayInPlace(high, index - lowCount - 1);   // relative rank i in right/high sublist, -1 cause rank is 0-indexed
        }

        else {  // index i == pivot idx
            return pivot;
        }
    }


    public static int findIthList(int[] arr, int index) {

        if (index > arr.length - 1) return -1;   // not possible

        // Create sub-lists of length 5 (in-place): FINAL sublist may be smaller
        int numSublists = (arr.length - 1) / 5 + 1;    // Ceil(arr.length/5)

        // Find median of each sublist, put in list of medians M
        int[] medians = new int[numSublists];

        for (int i = 0; i < numSublists; i++) {
            // Sort sublist (pass ranges)
            int firstIdxSublist = i * 5;
            Arrays.sort(arr, firstIdxSublist, Math.min(firstIdxSublist + 5, arr.length - 1));  // size may be < 5
            int medIdx = firstIdxSublist + Math.min(2, (arr.length - firstIdxSublist)/2);
            medians[i] = arr[medIdx];
        }

        // Pivot = median of M  <-- k = idx of pivot
        int pivot;

        if (medians.length <= 5) {  // Length small enough to sort, still O(n)
            Arrays.sort(medians);
            pivot = medians[medians.length / 2];   // median of medians :)
            System.out.println("Pivot = " + pivot);

        } else {    // Recursively find pivot using THIS METHOD on medians list!
            pivot = findIthList(medians, medians.length/2);    // medians.length == numSublists
            System.out.println("Pivot found recursively = " + pivot);
        }

        // Partition
        // Left contains all values < pivot
        // (equal contains all values = pivot) <-- not needed since we assume unique values?
        // Right contains all values > pivot

        List<Integer> lower = new ArrayList<>();
        List<Integer> equal = new ArrayList<>();
        List<Integer> higher = new ArrayList<>();

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] < pivot) {
                lower.add(arr[i]);
            } else if (arr[i] > pivot) {
                higher.add(arr[i]);
            } else {   // equal to pivot
                equal.add(arr[i]);
            }
        }
        //int pivotIdx = lower.size();   // First element equal to pivot is pivot

        int[] lowerArray = lower.stream().mapToInt(i->i).toArray();
        int[] higherArray = higher.stream().mapToInt(i->i).toArray();

        // recur on ONE sub-sequence only: prune and search  --> O(n)

        if (index < lower.size()) {   // If i < k: recurse on left
            return findIthList(lowerArray, index);
        } else if (index < lower.size() + equal.size()) {   // If i == k: return pivot
            return pivot;
        } else {                 // If i > k: recurse on right (relative idx i!)
            return findIthList(higherArray, index - lower.size() - equal.size());  // relative idx into higher sub-array
        }

    }



}
