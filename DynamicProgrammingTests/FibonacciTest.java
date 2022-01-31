import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class FibonacciTest {

    @Test
    public void testTimeFibonacci() {
        List<Integer> numbers = new ArrayList<>(Arrays.asList(10, 20, 30, 40, 41, 42, 43, 44));
        for (int n : numbers) {
            runFibWithTime(n);
        }
    }


    public void runFibWithTime(int n) {
        long start = System.currentTimeMillis();
//        long[] res = FibonacciHW1.fibonacciArray(n);
        int res = Fibonacci.fibonacci(n);
        long end = System.currentTimeMillis();
        System.out.println(String.format("Elapsed time for %d: %d ms", n, end - start));
        System.out.println(res);
//        System.out.println(Arrays.toString(res));
    }

}