import java.util.Arrays;
import java.util.function.BiFunction;

public class SequenceAlignment {


    public static void main(String[] args) {
        {
            String a = "kitten";
            String b = "sitting";
            //System.out.println(minSequenceAlignmentCost(a, b));  // 3
            System.out.println(solveUsingCharArray(a, b));
        }
    }


    /**    O(n*m) time and space!
     *    We could also use a linear space algorithm of O(m)!
     *
     *    - cost 1 for mismatch
     *    - Gap penalty = 1 (implicitly)
     *
     * @param firstString - X of length n
     * @param secondString - Y of length m
     * @return minimal sequence alignment cost possible <---  M[n][m]
     */
    public static int minSequenceAlignmentCost(String firstString, String secondString) {

        int n = firstString.length();
        int m = secondString.length();
        int gapCost = 1;
        int mem[][] = new int[n + 1][m + 1];


        // Note: it does not matter whether X or Y is inner or outer loop
        for (int i = 0; i <= n; i++) {       // string X
            for (int j = 0; j <= m; j++) {    // string Y

                // Base cases:
                // S(0, 0) = 0
                //  S(i, 0) = i * gap penalty   and   S(0, j) = j * gap penalty
                if (i == 0) {
                    mem[i][j] = gapCost * j;  // gap penalty = 1
                } else if (j == 0) {
                    mem[i][j] = gapCost * i;
                } else {
                    // We check char i of string 1 and char j of string 2  <-- the rest was evaluated in the subproblems
                    // Variable to track whether the values are identical for penalty addition.
                    // OR int identicalValues = firstString.charAt(i - 1) == secondString.charAt(j - 1) ? 0 : 1;

                    int mismatchCost = 0;   // if char i matches char j
                    if (firstString.charAt(i-1) != secondString.charAt(j-1)) mismatchCost = 1;

                    // Calculating possible combinations in the memoization matrix.
                    int diagonal = mem[i - 1][j - 1] + mismatchCost;   // add edge, add possible cost for mismatch (0 or 1)
                    int left = mem[i][j - 1] + gapCost;                     // skip char in string 2, whole string 1 + gap penalty
                    int above = mem[i - 1][j] + gapCost;                    // skip char in string 1, whole string 2 + gap penalty
                    mem[i][j] = Math.min(Math.min(diagonal, left), above);  // Pick the optimal cost of the 3 cases
                }
            }
        }
        for (int i = 0; i <= n; i++) {
            System.out.println(Arrays.toString(mem[i]));
        }
        // Returning the answer found in the final index: minimal alignment cost for string 1 of length n and string 2 of length m
        return mem[n][m];
    }




    public static int solveUsingCharArray(String firstString, String secondString) {
        int gapPenalty = 1;
        char[] X = toChars(firstString);
        char[] Y = toChars(secondString);

        int n = firstString.length();
        int m = secondString.length();
        int[][] mem = new int[n + 1][m + 1];

        return solve(X, n, Y, m, gapPenalty,
                (x, y) -> x == y ? 0 : 1, mem);  // HOF: if x != y, we pay mismatch cost of 1, else 0
    }


    // Turns string into 1-indexed char array
    private static char[] toChars(String str) {
        char[] chars = new char[str.length() + 1];
        System.arraycopy(str.toCharArray(), 0, chars, 1, str.length());
        return chars;
    }


    // Mismatch cost is a Higher-Order Function here!    0 if x = y, else 1
    private static int solve(char[] X, int n, char[] Y, int m, int gapPenalty,
                             BiFunction<Character, Character, Integer> mismatchCost, int[][] mem) {
        for (int i = 1; i <= n; i++) {
            mem[i][0] = i * gapPenalty;
        }

        for (int j = 1; j <= m; j++) {
            mem[0][j] = j * gapPenalty;
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                mem[i][j] = Math.min(
                        mismatchCost.apply(X[i], Y[i]) + mem[i - 1][j - 1],
                        Math.min(
                                gapPenalty + mem[i - 1][j],
                                gapPenalty + mem[i][j - 1]
                        )
                );
            }
        }
        for (int i = 0; i < mem.length; i++) {
            System.out.println(Arrays.toString(mem[i]));
        }
        return mem[n][m];
    }

}
