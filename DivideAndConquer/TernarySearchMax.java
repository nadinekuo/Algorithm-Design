public class TernarySearchMax {


    /**
     * Finds the x coordinate with the highest value of an array with a unimodal function,
     *   by recursively evaluating the values at one-third and two-thirds of the range.
     *   Depending on which is higher, a new evaluation is made with a smaller range to find the x coordinate with the highest value.
     *
     * @param altitude the array with the unimodal function
     * @return the x coordinate with the highest value
     */
    public static int findPictureTime(double[] altitude) {
        return findPictureTimeRec(altitude, 0, altitude.length - 1);
    }

    public static /**
     * Finds the x coordinate with the highest value of an array with a unimodal function,
     *  by recursively evaluating the values at one-third and two-thirds of the range.
     * Depending on which is higher, a new evaluation is made with a smaller range to find the x coordinate with the highest value.
     *
     * @param altitude the array with the unimodal function
     * @param a lower bound on the x coordinate
     * @param an upper bound on the x coordinate
     * @return the x coordinate with the highest value <-- idx of max
     *
     *  Check m1 = arr[length/3]
     *  Check m2 = arr[length * 2/3]
     *  If m1 < m2, the max. must be between [1/3, high]
     *  If m1 > m2, the max. must be between [low, 2/3]
     *   If equal, pick arbitrary side
     *
     *    These conclusions can be drawn since we can only increase until max, and decrease after max.
     *
     */
    int findPictureTimeRec(double[] altitude, int low, int high) {

        // BASE CASE: size of subarray to consider <= 3     <-- not the altitude[] array size!

        if (high - low < 3) {  // 3 elements is the smallest size that can be reached (assuming normal input > 2)

            // Find maximum: could be 1st, middle or 3rd
            if (altitude[low + 1] > altitude[low] && altitude[low + 1] > altitude[high]) {
                return low + 1;     // idx of middle element
            }
            if (altitude[low] > altitude[high]) {
                return low;  // 1st idx
            } else {
                return high;  // 3rd idx
            }
        }

        // DIVIDE: Depending on m1 < m2 or m1 > m2, we recurse on 2/3 sized subarray

        int m1 = low + (high - low)/3;    // we want the 1/3 and 2/3 indices of SUBARRAY! (not the entire input array)
        int m2 = low + (high - low)*2/3;

        if (altitude[m1] > altitude[m2]) {      // CONQUER: max must lie in that range!
            return findPictureTimeRec(altitude, low, m2);
        } else {
            return findPictureTimeRec(altitude, m1, high);
        }

    }




}
