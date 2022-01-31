
import java.util.Arrays;

public class RNABasePairs {



    /**
     *  Opt(i, j) = Max { Opt(i, j-1),
     *                    1 + argmax(t) { Opt(i, t-1) + Opt(t+1, j-1) }
     *                                 }
     *
     *    O(n^3) !!!
     *
     * @param n the number of bases.
     * @param b an array of size n+1, containing the bases b_1 through b_n. Note that you should use b[1] through b[n]
     * @param validPair an array of size n+1 by n+1, containing the validity for pair (i,j).
     *          A pair (i,j) is valid iff p[i][j] == true. Note that you should use p[1][1] through p[n][n].
     *
     *          Validity of pairs is not based on reality here!!
     *
     * @return The size of the largest set of base pairs = Opt(1, n) = |S|
     */
    public static int maxBasePairs(int n, char[] b, boolean[][] validPair) {

        int[][] mem = new int[n + 1][n + 1];  // Note: max no. of base pairs for all substrings possible

        for (int k = 1; k <= n-1; k++) {         // curr substring length-1 we consider (NOTE: you may also start at k = 5, since there must be 4 bases between anyways!)
            for (int i = 1; i <= n - k; i++) {   //  i is start of substring
                int j = i + k;                   // j is end of substring

                int currMaxPairs = mem[i][j-1];  // Note: initial max value as Opt(i, j-1)

                // Find valid base pair (t, j)
                //  Opt(i, j) = Max { Opt(i, j-1),
                //                       1 + argmax(t) { Opt(i, t-1) + Opt(t+1, j-1) } }
                for (int t = i; t < j - 4; t++) {       // t, j must be >= 4 bases apart!

                    if (validPair[t][j]) {
                        // 1. take prev value (initially: substring i to j-1)
                        // 2. make pair(t, j), so add 1 to remaining string
                        currMaxPairs = Math.max(currMaxPairs,
                                                 1 + mem[i][t-1] + mem[t+1][j-1]);
                    }
                }
                // Update mem[i][j]: currMaxPairs now stores the highest no. of base pairs between i and j!
                mem[i][j] = currMaxPairs;
            }
        }
        System.out.println("Max no. of base pairs: " + mem[1][n] + "\n\n");
        for (int i = 0; i < mem.length; i++) {
            System.out.println(Arrays.toString(mem[i]));
        }
        return mem[1][n];
    }


    public static int solveNestedLoop(int n, char[] b, boolean[][] p) {

        int[][] mem = new int[n + 1][n + 1];

        for (int j = 1; j <= n; j++) {
            for (int i = 1; i <= j - 4; i++) {  // Note: instead of seq length k, we just consider i, j in nested loop

                //  if (i == 0 || j == 0) continue;
                //  if (i >= j - 4) continue;  // pair never possible!

                int currMaxPairs = Integer.MIN_VALUE;

                for (int t = i; t < j - 4; t++) {
                    if (p[t][j]) {
                        currMaxPairs = Math.max(currMaxPairs, 1 + mem[i][t - 1] + mem[t + 1][j - 1]);
                    }
                }
                mem[i][j] = Math.max(mem[i][j - 1], currMaxPairs);
            }
        }
        return mem[1][n];
    }



    public static int solveDifferentMaxOrder(int n, char[] b, boolean[][] p) {

        int[][] mem = new int[n + 1][n + 1];

        for (int k = 1; k < n; k++) {
            for (int i = 1; i <= n - k; i++) {
                int j = i + k;

                int currMaxPairs = Integer.MIN_VALUE;   // instead of initializing as mem[i][j-1]

                for (int t = i; t < j - 4; t++) {
                    if (p[t][j]) {
                        currMaxPairs = Math.max(currMaxPairs, 1 + mem[i][t - 1] + mem[t + 1][j - 1]);
                    }
                }
                mem[i][j] = Math.max(mem[i][j - 1], currMaxPairs);
            }
        }
        return mem[1][n];
    }





    public static void main(String[] args) {
        String rnaString = "ACCGGUAGU";
        char[] chars = rnaString.toCharArray();  // 0-indexed

        int n = chars.length;
        int[][] mem = new int[n + 1][n + 1];    // 1-indexed
        System.out.println("Total pairs made from base 1 to n: " + opt(chars, n, mem));

        for (int i = n; i >= 1; i--) {
            System.out.println(i + " " + Arrays.toString(mem[i]));
        }
    }


    public static boolean validPair(char b1, char b2) {
        return (b1 == 'A' && b2 == 'U')
                || (b1 == 'U' && b2 == 'A')
                || (b1 == 'C' && b2 == 'G')
                || (b1 == 'G' && b2 == 'C');
    }


    // Note: uses predefined base pairs above
    /**
     * @param bases - 0-indexed array of all bases "ACCGGUAGU" e.g.
     * @param n - how many bases
     * @param mem - 1-indexed --> mem[i][j] = max. no. of pairs that can be made from base i to j
     * @return - max. no of base pairs to be made from base 1 to n
     */
    public static int opt(char[] bases, int n, int[][] mem) {

        for (int k = 5; k <= n - 1; k++) {   // curr substring length to consider
            for (int i = 1; i <= n - k; i++) {  // start of substring (cannot be > n-k since j would be out of bounds...)
                int j = i + k;                   // end of substring

                // Find valid base pair (t, j)
                //  Opt(i, j) = Max { Opt(i, j-1),
                //                       1 + argmax(t) { Opt(i, t-1) + Opt(t+1, j-1) } }

                int maxPairs = mem[i][j - 1];
                // There must be >= 4 bases between t and j!
                for (int t = i; t < j - 4; t++) {

                    if (!validPair(bases[t - 1], bases[j - 1]))  continue;    // bases[] is 0-indexed!

                    maxPairs = Math.max(maxPairs,
                            1 + mem[i][t - 1] + mem[t + 1][j - 1]);     // add 1 pair + values for remaining substring
                }
                mem[i][j] = maxPairs;
            }
        }
        return mem[1][n];  // max. no of pairs from base 1 to n
    }




}