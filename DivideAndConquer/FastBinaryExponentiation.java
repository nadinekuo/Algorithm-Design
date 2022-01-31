public class FastBinaryExponentiation {

    /**
     * Computes the matrix C, containing the values for a^b, for all values in a and b.
     *
     *   WITHOUT USING Math.power() !!!
     *
     * @param a array containing the bases
     * @param b array containing the exponents
     * @return matrix C
     */
    public static int[][] computeC(int[] a, int[] b) {

        int[][] exponentMatrix = new int[a.length][b.length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b.length; j++) {
                exponentMatrix[i][j] = fastBinaryExponentiation(a[i], b[j]);
            }
        }
        return exponentMatrix;
    }


    // RECURSIVE HELPER
    // O(log n), since f(n-1) if exponent is odd can only happen 1x!
    public static int fastBinaryExponentiation(int a, int b) {

        if (b == 0) {   // exponent 0 gives 1
            return 1;
        }
        if (b == 1) {   // exponent 1 gives a
            return a;
        }
        // If even exponent, we calculate the power using half the exponent b!
        if (b % 2 == 0) {
            int half = fastBinaryExponentiation(a, b / 2);
            return half * half;       // DIVIDE: combine the 2 half computed powers into 1!
        } else {
            return a * fastBinaryExponentiation(a, b - 1);   // CONQUER: If odd exponent, we subtract 1 to make it even, and multiply 1x by the base!
        }
    }


}



