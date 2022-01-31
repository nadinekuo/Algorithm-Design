import java.io.InputStream;
import java.io.InputStream;
import java.util.*;


public class PrettyPrinting {


    public static List<String> solve(InputStream in) {

        // Read the input file
        Scanner sc = new Scanner(in);

        int L = sc.nextInt();      // max char count on 1 line
        int n = sc.nextInt();      // how many words in text

        String[] words = new String[n];    // Note: 0-indexed!
        for (int i = 0; i < n; i++) {
            words[i] = sc.next();    // add all words in text to array
        }

        sc.close();

        int[][] slacks = computeAllSlacks(L, n, words);
        // Note: we wanna minimize the sum of squared slacks, we don't care about the words here:
        int[] opt = calculateOptSumOfSquaredSlacks(n, slacks);     // mem array containing the minimal sum of squared slacks up to word i
        return recoverLinesIterative(n, opt, slacks, words);    // recovers all lines, Note: starting at word n
    }


    private static List<String> recoverLinesIterative(int n, int[] mem, int[][] slacks, String[] words) {

        LinkedList<String> optLines = new LinkedList<>();
        int j = n;                   // Start at final word

        // Note: For THIS sequence having final word j, find start word (i), by keeping track of curr min sum
        while (j > 0) {
            for (int i = 1; i <= j; i++) {

                if (slacks[i - 1][j - 1] == Integer.MAX_VALUE) {
                    continue;
                }
                int squared = slacks[i - 1][j - 1] * slacks[i - 1][j - 1];
                if (squared + mem[i - 1] == mem[j]) {
                    // Build this line: " " in between words
                    // Take a subset of all words: incl start, excl end  <-- Note words is 0-indexed so we take (i, j+1 excl)
                    String line = String.join(" ", Arrays.asList(words).subList(i - 1, j));
                    optLines.addFirst(line);
                    j = i - 1;           // continue at last char of prev line!
                    break;
                }
                // Now we found the best first word i of new line until j
            }
        }
        return optLines;
    }



    private static List<String> recoverLinesIterativeKeepTrackOfMin(int n, int[] mem, int[][] slacks, String[] words) {

        LinkedList<String> optLines = new LinkedList<>();
        int j = n;                   // Start at final word

        while (j > 0) {
            int currMinSquared = Integer.MAX_VALUE;
            int currBestLineStart = 1;

            for (int i = 1; i <= j; i++) {

                if (slacks[i-1][j-1] == Integer.MAX_VALUE) {
                    continue;
                }
                int squared = slacks[i-1][j-1] * slacks[i-1][j-1];
                if (squared + mem[i-1] < currMinSquared) {
                    currMinSquared = squared + mem[i-1];
                    currBestLineStart = i;
                }
                // Now we found the best first word i of new line until j
            }
            String line = String.join(" ", Arrays.asList(words).subList(currBestLineStart - 1, j));
            optLines.addFirst(line);

            j = currBestLineStart - 1;  // continue at last char of prev line!
        }
        return optLines;
    }




    /**   Note: Recursively recovers solution until j: walks backwards, to trace back which word i each line starts with
     *
     * @param j - final word in sequence of word 1....j we consider (outer loop)
     *            Each rec call, j--
     * @param opt - mem array
     * @param slacks - all slacks pre-computed for word sequences from word i ... word j <--- 0-indexed
     * @param words - All words in the text  <--- 0-indexed!
     *
     * @return  List of all sequences of words that minimize the total SSS, example:
     *
     *    ["The Answer to the Great Question of Life,", "the Universe and Everything is Forty-two."]
     */
    private static List<String> recoverLinesRecursive(int j, int[] opt, int[][] slacks, String[] words) {

        // Base case: each rec call, j--
        if (j == 0) {
            return new ArrayList<String>();
        }

        int firstIdxNewLine = 1;
        int bestSquaredSlacks = Integer.MAX_VALUE;     // Keep track of current best value
        for (int i = 1; i <= j; i++) {                  // Note: j is method param

            if (slacks[i - 1][j - 1] == Integer.MAX_VALUE) {         // skip max values due to overflows when squaring.
                continue;
            }

            int squared = slacks[i - 1][j - 1] * slacks[i - 1][j - 1];

            // Find the current minimal SSS for this line (word i ... word j) by checking mem[]
            if (squared + opt[i - 1] < bestSquaredSlacks) {
                bestSquaredSlacks = squared + opt[i - 1];
                firstIdxNewLine = i;                              // first word in line to be added
            }
        }
        // Now we found the best first word i of new line

        // Recursively traverse the words backwards, start with the last word of the previous sentence
        List<String> result = recoverLinesRecursive(firstIdxNewLine - 1, opt, slacks, words);

        String res = String.join(" ", Arrays.asList(words).subList(firstIdxNewLine - 1, j));

        result.add(res);   // Note: Since we add AFTER recursing, the lines will be added in order to the list! Base case returns empty list.
        return result;
    }




    /**  O(n^2) !!  <-- nested for-loop
     *
     * @param n - how many words in text
     * @param slacks - all slacks pre-computed for word sequences from word i ... word j <--- 0-indexed
     * @return -  mem array containing the minimal sum of squared slacks up to word i
     */
    private static int[] calculateOptSumOfSquaredSlacks(int n, int[][] slacks) {

        int[] mem = new int[n + 1];  // Note: 1-indexed

        // Iteratively fill mem array
        for (int j = 1; j <= n; j++) {   // Note: j <= n is end of sequence i..j, we look for all possible starts i
            mem[j] = Integer.MAX_VALUE;

            // Find argmin(i) for which s_ij^2 + Opt(i-1) < Opt(j)
            for (int i = 1; i <= j; i++) {   // Note: i is is start of sequence i...j, and i == j means 1 word

                if (slacks[i - 1][j - 1] == Integer.MAX_VALUE) {  // this sequence of words i...j > limit L, so could never be more optimal than any other value.....
                    continue;                                        // Note: skip max values due to overflows when squaring!
                }
                int squared = slacks[i - 1][j - 1] * slacks[i - 1][j - 1];   // SSS

                // Check whether new opt was found for j <--- this sequence i...j gave lower SSS?
                if (squared + mem[i - 1] < mem[j]) {     // add SSS for this sequence i...j to prev Opt(i-1)!
                    mem[j] = squared + mem[i - 1];       // There was a more optimal splitting up to word j!
                }
            }
        }
        System.out.println("\nMem array: " + Arrays.toString(mem));
        return mem;   // mem[n] will contain the minimal SSS over all n words
    }



    /**  Computes slacks for all possible combinations of word groupings from word i ... word j! ---> O(n^2)
     *   (consecutive sequence of word i ... word j)
     *
     * @param L - max no. of chars on 1 line (line length)
     * @param n - no. of words in array
     * @param words - All words in the text  <--- 0-indexed
     * @return - 2D array
     */
    private static int[][] computeAllSlacks(int L, int n, String[] words) {

        int[][] slacks = new int[n][n];   // For each word i, it stores slacks for all different i-j sequences possible!

        for (int i = 0; i < n; i++) {
            // Note: each i means new line, so initialize lineCount for every i
            //  We go over all possible j's (end of seq starting at i)
            int lineCharCount = -1;          // Note: compensate for no initial space: 1 word has no space, so -1 + 1 = 0
            for (int j = i; j < n; j++) {    // j=i corresponds to 1 word, j=n means the whole text (or n-1, since we start at idx 0)

                lineCharCount = lineCharCount + words[j].length() + 1;   // Add one word incl. space (1)

                if (lineCharCount > L) {
                    slacks[i][j] = Integer.MAX_VALUE;  // limit L exceeded ...
                } else {
                    slacks[i][j] = L - lineCharCount;
                }
            }
        }
        // Note: this 2D array will be diagonal, since we only fill values for which j > i (bottom left triangle are all 0s)
        for (int i = 0; i < n; i++) {
            System.out.println(Arrays.toString(slacks[i]));
        }
        return slacks;
    }



}
