
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;

public class WordSegmentation {

    public static void main(String[] args) {

        Map<String, Integer> qualities = Map.of(  // certain substrings have qualities
                "lawy", 1,
                "ersar", 1,
                "eawe", 1,
                "so", 2,
                "me", 3,
                "lawyers", 5,
                "are", 4,
                "awesome", 10
        );
        // HOF: substring --> value (or 0 if no quality defined for this substring)
        Function<String, Integer> qualityFunction = str -> qualities.getOrDefault(str, 0);

        String word = "lawyersareawesome";
        int[] mem = new int[word.length() + 1];  // 1-indexed
        System.out.println("Optimal Quality = " + computeOptimalQuality(word, qualityFunction, mem));
        System.out.println("Memory = " + Arrays.toString(mem));
        System.out.println();
        System.out.println("Optimal Word Split:");
        printOptimalWordSplit(word, qualityFunction, mem);
    }



    /**
     * Prints the optimal word split given the word, qualityFunction and initialized memory.
     */
    public static void printOptimalWordSplit(String word, Function<String, Integer> qualityFunction, int[] mem) {
        int s = printOptimalWordSplit(word, word.length(), qualityFunction, mem);
        if (s < 0) {  // the recursive method below returned -1, meaning there were no words...
            return;
        }
        // All substrings are printed in recursive printOptimalWordSplit(),
        // except for the final substring belonging to the initial rec call!
        System.out.println("********* Final substring: " + word.substring(s));  // starts at idx s
    }


    /**
     * Helper method to print word split recursively.
     *   j = final char idx to consider (n, n-1, ..., 1, 0)  <-- 1-indexed
     *
     *   Returns: idx of first char of FINAL substring (due to recursion)
     */
    private static int printOptimalWordSplit(String word, int j, Function<String, Integer> qualityFunction, int[] mem) {

        if (j <= 0) {
            System.out.println("********* Base case: no more words to split");
            return -1;
        }

        // Note: i was first char in prev substring found (from right), so now we continue from the first char before that
        int i = j - 1;    // so mem[i] = Opt(i-1)!
        while (i > 0
                && qualityFunction.apply(word.substring(i, j)) + mem[i] != mem[j]) {  // substring(i, j) not selected, walk backwards to find i
            i--;
        }
        // Note: We found i (first char in substring)

        int s = printOptimalWordSplit(word, i, qualityFunction, mem);  // Note: recurse from i (next call will use i = j-1 again)
        if (s >= 0) {
            // Prints all substrings except for the final one, since that corresponds to the first recursive call
            // Will be in ascending order of words!
            System.out.println(word.substring(s, i));  // excl. i
        }
        return i;  // Note: returns first char idx in FINAL substring, to be printed in main()
    }




    /**
     * Computes the optimal split quality of given word, qualityFunction and (empty) memory, iteratively.
     */
    public static long computeOptimalQuality(String word, Function<String, Integer> qualityFunction, int[] mem) {
        mem[0] = 0;
        // We advance j by 1 idx, bc String.substring is excl. end idx! (see line 69)
        for (int j = 1; j <= word.length(); j++) {

            int max = Integer.MIN_VALUE;   // find argmax(i){quality substring(i, j)}
            for (int i = 0; i < j; i++) {
                // updates mem[j] if we found a better substring(i, j) to split at
                max = Math.max(max, qualityFunction.apply(word.substring(i, j)) + mem[i]);  // Note: this is actually mem[i-1], bc iterator i starts at 0!!
            }
            mem[j] = max;
        }
        return mem[word.length()];  // mem[n]
    }
}