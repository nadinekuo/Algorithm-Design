public class LargestConsecutiveSum {

    
    // or: https://medium.com/@rsinghal757/kadanes-algorithm-dynamic-programming-how-and-why-does-it-work-3fd8849ed73d


    // Consecutive elements only! Not necessarily 2 ints, it can be a whole sequence!!
    public static int largestSum(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        return largestSumHelper(arr, 0, arr.length - 1);
    }


    // Take into account the pair in the middle when dividing / combining !!!
    public static int largestSumHelper(int[] arr, int low, int high) {

        // Base case: 1 element
        if (low == high) {
            return arr[low];
        }
        int mid = (low + high) / 2;   // DIVIDE
        // CONQUER:  Find largest sum with elements from left side
        // Note that we iterate starting from mid to the left!
        int temp = 0;
        int leftSum = Integer.MIN_VALUE;    // update if larger consecutive sum found
        for (int i = mid; i >= low; i--) {
            temp += arr[i];
            if (temp > leftSum) {   // Store the largest consecutive sum found in left part
                leftSum = temp;
            }
        }
        // Find largest sum with elements from right side
        // Note that we iterate starting from mid to the right!
        temp = 0;
        int rightSum = Integer.MIN_VALUE;
        for (int i = mid + 1; i <= high; i++) {
            temp += arr[i];
            if (temp > rightSum) {   // Store the largest consecutive sum found in right part
                rightSum = temp;
            }
        }

        // CONQUER: recurse on left and right to find largest sum

        int leftLargestRecursive = largestSumHelper(arr, low, mid);
        int rightLargestRecursive = largestSumHelper(arr, mid + 1, high);

        System.out.println("\nLargest sum left = " + leftSum + ", largest sum right = " + rightSum);
        System.out.println("Largest sum found left recursively = " + leftLargestRecursive);
        System.out.println("Largest sum found right recursively = " + rightLargestRecursive);

        // COMBINE: DON'T FORGET THE PART SPANNING BOTH SIDES!!!!
        // Get the max largest sum of left, right and combined!

        // A lot of max, in general takes the max of:
        // 1. The largest sequence in left side, recursive
        // 2. The largest sequence in right side, recursive   -->  1 and 2 may not be consecutive, so dont add the recursive calls!

        // 3. The largest sequence spanning both left and right in THIS CALL!
        //         We can add leftSum + rightSum bc they must be consecutive, since we iterated from middle outwards!

        return Math.max(Math.max(leftLargestRecursive, rightLargestRecursive), leftSum + rightSum);
    }


}
