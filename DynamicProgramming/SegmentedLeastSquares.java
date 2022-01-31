import java.util.*;

public class SegmentedLeastSquares {



    public static class Point {
        private final double x;
        private final double y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    // Line: ax + b
    public static class Line {
        private final double a;
        private final double b;

        public Line(double a, double b) {
            this.a = a;
            this.b = b;
        }

        // Gives f(x)
        public double calc(double x) {
            return a * x + b;
        }

        @Override
        public String toString() {
            return "Line{" + a + "x + " + b + "}";
        }
    }



    public static void main(String[] args) {

        // Array containing all points we consider
        Point[] points = new Point[]{
                // Segment 1
                new Point(1, 1.5),
                new Point(1.5, 1.6),
                new Point(2, 1.7),
                new Point(2.5, 1.8),
                new Point(3, 1.9),
                new Point(3.5, 2),

                // Segment 2
                new Point(3.8, 2.5),
                new Point(3.9, 3),
                new Point(3.95, 3.45),
                new Point(4.1, 4),
                new Point(4.2, 4.5),
                new Point(4.3, 5),
                new Point(4.4, 5.5),
                new Point(4.55, 5.95),

                // Segment 3
                new Point(5, 6.1),
                new Point(5.5, 6),
                new Point(6, 6.2),
                new Point(6.5, 6.1),
                new Point(7, 6.2),
                new Point(7.5, 6.3)
        };

        double[] mem = new double[points.length];  // 0-indexed
        double segmentCost = 0.4;                  // C = cost for adding 1 extra segment

        System.out.println("Max Error = " + computeSegmentedLeastSquaresMaxError(points, segmentCost, mem));
        System.out.println("Memory = " + Arrays.toString(mem));
        System.out.println();
        System.out.println("Segments:");
        printSegmentedLeastSquares(points, segmentCost, mem);
    }


    /**
     * Prints the finalized segments as lines given the points, segmentCost and initialized memory.
     */
    public static void printSegmentedLeastSquares(Point[] points, double segmentCost, double[] mem) {

        // Note: Walk forwards starting at point 0
        for (int j = 0; j < mem.length; j++) {

            int i = j;  // Note: i will remain (start of segment), j will advance to FIRST idx of NEXT of segment

            // Note: Opt(j = start) + C as "offset": since adding C means that we started a new segment!!
            double maxCostCurrSegment = mem[j] + segmentCost;

            // Walk forwards to next point, as long as next Opt(j+1) is lower than Opt(j)
            //  -->  j still part of curr segment, since we did not add C yet!

            // If Opt(j+1) is lARGER than max possible cost for this segment, we added C, so start of new segment!
            while (j < (mem.length - 1) && mem[j + 1] < maxCostCurrSegment) {
                j++;
            }
            // Note: Now j is at first point idx of NEXT segment!
            System.out.println(bestLinearLine(points, i, j));  // excl. j
        }
    }

    /**
     * Computes the Segmented Least Squares max error while initializing the memory, iteratively.
     *  Note: points is 0-indexed
     */
    public static double computeSegmentedLeastSquaresMaxError(Point[] points, double segmentCost, double[] mem) {

        // Holds SSEs for all combinations of points i - j (possible segments)
        // Note: Row j = end of segment, Col i = start of segment, so reverse indexing
        double[][] errors = new double[points.length][];    // n * n
        for (int j = 0; j < points.length; j++) {
            errors[j] = new double[points.length];
            for (int i = 0; i <= j; i++) {          // points is 0-indexed, so i starts at 0
                errors[j][i] = computeError(points, i, j + 1);  // upper point is excluded!
            }
        }

        // Mem[j] = min costs until point j (0-indexed)
        for (int j = 0; j < points.length; j++) {
            mem[j] = errors[j][0] + segmentCost;  // Note: initialize as error of single segment(0, j) + C  <-- to be minimized

            // Iterate over all points 0 <= i <= j
            for (int i = 0; i < j; i++) {
                double costCurrSegment = errors[j][i] + segmentCost + mem[i];
                mem[j] = Math.min(mem[j], costCurrSegment);   // find argmin(i) {error(i, j) + C + Opt(i-1)}
            }
        }

        return mem[points.length - 1];  // Note: mem is 0-indexed
    }

    /**
     * Simple Sum of Squared Error function,
     *      for any subset defined by lower index (including) up to upper index (excluding).
     *
     *   Points is 0-indexed!
     */
    public static double computeError(Point[] points, int lower, int upper) {

        // Line of best fit from lower to upper point
        Line line = bestLinearLine(points, lower, upper);

        double error = 0;
        for (int i = lower; i < upper; i++) {
            Point p = points[i];
            double d = p.y - line.calc(p.x);   // f(x) - point.y is error
            error += d * d;                   // squared error
        }
        return Double.isNaN(error) ? 0 : error;
    }


    /**
     *    Fits the best linear line through a set of points,
     *     defined by lower index (including) up to upper index (excluding).
     */
    public static Line bestLinearLine(Point[] points, int lower, int upper) {
        double sumX = 0;
        double sumY = 0;
        double sumXY = 0;
        double sumXX = 0;
        for (int i = lower; i < upper; i++) {   // line from lower point to upper point (line of best fit)
            Point p = points[i];
            sumX += p.x;
            sumY += p.y;
            sumXY += p.x * p.y;
            sumXX += p.x * p.x;
        }

        int n = upper - lower;  // how many points we consider
        // Uses linear regression formula?
        double a = (n * sumXY - sumX * sumY) / (n * sumXX - sumX * sumX);
        double b = (sumY - a * sumX) / n;
        return new Line(a, b);
    }
}