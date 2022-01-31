public class LongestCommonPrefix {


    /**
     * You should implement this method.
     *
     * @param n the number of encodings
     * @param encodings the encodings to analyse. Note that you should use entries encodings[1] to
     *     encodings[n]!
     * @return the longest common prefix amongst all encodings.
     */
    public static String longestPrefix(int n, String[] encodings) {

        if (n == 0) return "";
        if (n == 1) return encodings[1];   // 1 string only (forms longest common prefix)

        return longestPrefixRec(encodings, 1, n);
    }



    // Top down approach: divide array into smaller sub-arrays until base case
    public static String longestPrefixRec(String[] arr, int low, int high) {

        // Base case: size 1, return whole string
        if (high == low) {
            return arr[low];
        }

        // Divide: split arr into 2
        int mid = (low + high) / 2;

        // Conquer: find longest prefix recursively on left and right
        String longestLeft = longestPrefixRec(arr, low, mid);
        String longestRight = longestPrefixRec(arr, mid + 1, high);

        // Combine: get longest prefix of left and right results found, if they don't match, return ""
        // For each char in both strings, add to res as long as they match

        String res = "";

        if (longestLeft.length() > 0 && longestRight.length() > 0) {

            int idx = 0;
            int maxLength = Math.min(longestLeft.length(), longestRight.length());  // result is bounded by largest prefix of the 2

            while (idx < maxLength) {
                if (longestLeft.toCharArray()[idx] == longestRight.toCharArray()[idx]) {  // matching char found
                    res += longestLeft.toCharArray()[idx];   // add the matching char to res
                    idx++;   // next char checked
                } else {     // No more matching chars
                    return res;  // return the longest common prefix up to now (may be "" still)
                }
            }
        }
        return res;
    }

}
