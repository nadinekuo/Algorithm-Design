public class Fibonacci {


    public static void main(String[] args) {
        Long[] mem = new Long[92];
        fib(91, mem);

        for (int i = 0; i < mem.length; i++) {   // Largest non overflowing value for signed longs
            System.out.println("Fib(" + i + ") = " + mem[i]);
        }

        //assertEquals(mem[91], 4660046610375530309L);
        //assertEquals(mem[91], fibonacciDynamicIterative(91));
    }





    /**   O(n) using memoization!!!
     *    {0, 1, 1, 2, 3, 5, 8, 13, 21, 34}     (note that we start counting at zero).
     *
     *    Returns the n'th Fibonacci number
     */
    public static int fibonacciDynamicIterative(int n) {

        // The array in which you must implement your memoization.
        int[] fibonacci = new int[n + 1];

        // Base cases
        fibonacci[0] = 0;
        fibonacci[1] = 1;

        // After that, iterate through all fibonacci numbers from index 2 up to n.
        for (int i = 2; i <= n; i++) {
            fibonacci[i] = fibonacci[i-1] + fibonacci[i-2];  // Get the values stored for the 2 previous
        }
        // Returning the obtained fibonacci value at index n.
        return fibonacci[n];
    }


    /**
     * Returns the n'th Fibonacci number
     */
    public static int fibDynamicRecursive(int n) {
        // The array in which you must implement your memoization.
        int[] mem = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            mem[i] = -1;  // means nothing computed yet
        }
        mem[0] = 0;  // base cases
        mem[1] = 1;
        return fibDynamicRecursiveHelper(n, mem);
    }

    /**
     * Returns the n'th Fibonacci number
     *  Updates the mem[] array passed
     */
    public static int fibDynamicRecursiveHelper(int n, int[] mem) {

        if (mem[n] != -1) {  // Base case: already computed
            return mem[n];
        } else {
            // No value existed yet, so compute and store value
            mem[n] = fibDynamicRecursiveHelper(n-1, mem) + fibDynamicRecursiveHelper(n-2, mem);
            return mem[n];
        }
    }



    public static long fib(int n) {
        return fib(n, new Long[n + 1]);
    }

    /**
     * DC Fibonacci.
     */
    public static long fib(int n, Long[] mem) {
        if (mem[n] != null) return mem[n];

        if (n == 0) {
            mem[n] = 0L;
        } else if (n == 1) {
            mem[n] = 1L;
        } else {
            mem[n] = fib(n - 1, mem) + fib(n - 2, mem);
        }
        return mem[n];
    }



    /** VERSION 1: O(n) TIME + RECURSIVE HELPER
     * Computes the nth number in the Fibonacci sequence.
     * @param n - the index of the number in the Fibonacci sequence.
     * @return nth number in the Fibonacci sequence
     * USING ONLY 1 RECURSIVE CALL; LINEAR RECURSION --> O(n) time!
     */
    public static int fibonacci2(int n) {
        return fibonacci_helper(n, 1, 1);
    }



    /**
     * Helper function for computing the nth number in the Fibonacci sequence.
     * @param n - the index of the number in the Fibonacci sequence.
     * @param acc1 - accumulator for the previous number in the Fibonacci sequence.
     * @param acc2 -accumulator for the previous number in the Fibonacci sequence.
     * @return
     */
    public static int fibonacci_helper(int n, int acc1, int acc2) {
        if (n <= 1) {
            return n;
        } if (n == 3) {
            return acc1 + acc2;
        }
        return fibonacci_helper(n - 1, acc2, acc1 + acc2);  // you change acc2 so it becomes f(n-1)
    }


    /** VERSION 2: O(n) TIME NON-RECURSIVE
     * Computes the nth number in the Fibonacci sequence.
     * @param n - the index of the number in the Fibonacci sequence.
     * @return nth number in the Fibonacci sequence
     */
    public static int betterFibonacci(int n) {
        int n_2 = 0, n_1 = 1;
        int temp;
        if(n == 0) return n_2;
        else if(n == 1) return n_1;
        else {
            for(int i = 2; i <= n; i++){
                temp = n_1 + n_2;
                n_2 = n_1;
                n_1 = temp;
            }
            return n_1;
        }
    }// the new acc1 is the previous acc2





    

    /** BAD FIBONACCI: BINARY RECURSION --> O(2^n) time...
     * Computes the nth number in the Fibonacci sequence.
     * @param n - the index of the number in the Fibonacci sequence.
     * @return nth number in the Fibonacci sequence
     */
    public static int fibonacci(int n) {
        if (n <= 1) {
            return n;
        } else {
            return fibonacci(n-1) + fibonacci(n-2);
        }
    }


    /** RETURNS  {F(n-1), F(n-2)}
     * LINEAR RECURSION with n-1 --> O(n) time
     * Computes the nth number in the Fibonacci sequence.
     * @param n - the index of the number in the Fibonacci sequence.
     * @return an array containing the pair of f(n) and f(n-1)
     * See book p. 203 and notebook
     */
    public static long[] fibonacciArray(int n) {
        if (n<=1) {
            long[] answer = {n, 0};
            return answer;
        } else {
            long[] temp = fibonacciArray(n-1);              // returns {F(n-1), F(n-2)}
            long[] answer = {temp[0] + temp[1], temp[0]};       // we want {F(n), F(n-1)}
            return answer;
        }

    }



}
