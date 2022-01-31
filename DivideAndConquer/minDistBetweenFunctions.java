import java.util.ArrayList;
import java.util.List;

public class minDistBetweenFunctions {


    /**  DISCRETE function (not continuous): only the integer x values are used!
     *
     * Finds the x coordinate with the smallest distance between two linear equations,
     * by recursively evaluating the median of the range and that integer + 1   <-- divide space into 2
     * Depending on the value, a new evaluation is made with a smaller range to find the x coordinate with the smallest distance.
     *
     * @param e1   the first equation to evaluate
     * @param e2   the second equation to evaluate
     * @param low  the lower boundary of the range to consider
     * @param high the upper boundary of the range
     * @return the x coordinate with the minimum difference between e1 and e2
     *
     *   -  linear polynomials (a = 0)  --> 1 intersection if not parallel
     *   - Quadratic polynomials (a != 0) --> more intersections possible
     *
     */
    public static long findMin(Equation e1, Equation e2, long low, long high) {

        if (low == high) {   // Base case: this one point has the minimal dist between the 2 functions! (not necessarily intersection)
            return low;
        }

        // DIVIDE
        long mid = (low + high) / 2;   // median divides space in 2
        // Will give stackoverflow error for negative ranges!! low == high will never be reached.
        // you can add base case: if (low + 1 == high) .... (and make high exclusive?)

        // CONQUER
        // We go in the direction of convergence between the 2 functions!
        // At a certain point, dividing space gives 1 point --> base case

        long diffMid = Math.abs(e1.evaluate(mid) - e2.evaluate(mid));
        long diffRightOfMid = Math.abs(e1.evaluate(mid + 1) - e2.evaluate(mid + 1)); // not precise (no decimals), so does not work well for small ranges

        System.out.println("\nDifference for mid = " + diffMid);
        System.out.println("Difference right of mid = " + diffRightOfMid);

        if (diffMid <= diffRightOfMid) {
            return findMin(e1, e2, low, mid);
        } else {
            return findMin(e1, e2, mid + 1, high);
        }

        // no combine needed, since we pick 1 half to recurse on
    }






    static abstract class Equation {

        public abstract long evaluate(long x);
    }

    static class QuadraticEquation extends Equation {

        private long secondPolynomial;
        private long firstPolynomial;
        private long constant;

        /**
         * Constructs a quadratic equation in the form of:
         * f(x) = secondPolynomial * x^2 + firstPolynomial * x + constant
         *
         * @param secondPolynomial the parameter for the second degree polynomial
         * @param firstPolynomial  the parameter for the first degree polynomial
         * @param constant         the parameter for the constant
         */
        public QuadraticEquation(long secondPolynomial, long firstPolynomial, long constant) {
            this.secondPolynomial = secondPolynomial;
            this.firstPolynomial = firstPolynomial;
            this.constant = constant;
        }

        /**
         * Evaluates the equation with the given x.
         *
         * @param x value used to evaluate
         * @return the result of the equation1
         */
        public long evaluate(long x) {
            return secondPolynomial * x * x + firstPolynomial * x + constant;
        }

    }

}
