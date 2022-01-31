import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ClosestPairOfPoints {

    /**
     * Takes a list of points and returns the distance between the closest pair.
     * This is done with divide and conquer.
     *
     * @param points - list of points that need to be considered.
     * @return smallest pair-wise distance between points.
     *
     *  O(n log n) --> sorting
     *  O(n)  ---> For each point, we only have to compare the next 11 points (sorted by Y-coord)
     *
     *  No O(n^2) brute force needed :)
     *
     */
    public static double closestPair(List<Point> points) {

        if (points.size() <= 1) return Double.POSITIVE_INFINITY;  // Do not recurse if only 1 point!

        // We only sort points by x- and y-coordinates 1X! By passing as param to recursive method.
        List<Point> pointsX = new ArrayList<>(points);
        Util.sortByX(pointsX);
        List<Point> pointsY = new ArrayList<>(points);  // we don't modify the original list
        Util.sortByY(pointsY);

        return closestPairRec(pointsX, pointsY);
    }


    public static double closestPairRec(List<Point> pointsSortedByX, List<Point> pointsSortedByY) {

        int n = pointsSortedByX.size();

        // BASE CASE: we have <= 3 points only, so do brute force on them   O(n^2) but only 3 points :)
        if(n <= 3){
            return Util.bruteForce(pointsSortedByX);
        }

        // DIVIDE: we will recurse on the left and right points from median L
        int midIdx = n/2;
        double midXValue = pointsSortedByX.get(midIdx).x;   // L value

//        Point L = points.get(mid);
//        List<Point> left = new ArrayList<>();
//        List<Point> right = new ArrayList<>();
//
//        for(Point p : points){
//            if(p.x < L.x){
//                left.add(p);
//            }
//            else{
//                right.add(p);
//            }
//        }

        List<Point> leftX = pointsSortedByX.subList(0, midIdx);  // inclusive start, exclusive end:    so median itself goes to right
        List<Point> rightX = pointsSortedByX.subList(midIdx, n);

        // Now we have to get the left and right side points sorted by y-coordinates too
        List<Point> leftY = new ArrayList<>();
        List<Point> rightY = new ArrayList<>();

        for (Point p : pointsSortedByY) {
            if (p.x < midXValue) {
                leftY.add(p);
            } else {
                rightY.add(p);
            }
        }
        // CONQUER: Keep the closest dist between 2 points on left and right
        double delta1 = closestPairRec(leftX, leftY);
        double delta2 = closestPairRec(rightX, rightY);
        double delta = Math.min(delta1,delta2);

        // COMBINE: BUT..... now we need to check pairs for which 1 point is on BOTH sides!!
        List<Point> twoDeltaStrip = new ArrayList<>();

        for (Point p : pointsSortedByY) {   // Now the list of points in 2 delta strip will be sorted by Y-coord!
            if (Math.abs(p.x - midXValue) < delta) {
                twoDeltaStrip.add(p);
            }
        }

        // For all points on 2 * delta strip,
        // Compare to AT MOST next 11 points (7, 8...) in points sorted by Y-coord
        // If smaller than curr delta, update: we found a closer pair!

        for (int i = 0; i < twoDeltaStrip.size() - 1; i++) {   // For the final point, there is no point above it!!

            double currY = twoDeltaStrip.get(i).y;

            // We only compare upper points for which y values differ by at most delta! (instead of fixing it to 11)
            //for (int j = i+1; j < twoDeltaStrip.size() && (j-i < 11); j++) {

            // If the y coordinates differ by >= delta, we know for sure the Euclidean distance is higher too!
            //  (Pythagoras theorem)
            for (int j = i+1; j < twoDeltaStrip.size() && (twoDeltaStrip.get(j).y - currY < delta); j++) {

                double distance = Util.distance(twoDeltaStrip.get(i), twoDeltaStrip.get(j));
                if (distance < delta) {
                    delta = distance;    // We found a smaller dist, so update delta
                }
            }
        }
        return delta;
    }



    /**
     * Class representing a 2D point.
     */
    static class Point {

        public double x;

        public double y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    /**
     *
     * Useful methods for this assignment.
     */
    static class Util {

        /**
         * Takes two points and computes the euclidean distance between the two points.
         *
         * @param point1 - first point.
         * @param point2 - second point.
         * @return euclidean distance between the two points.
         * @see <a href="https://en.wikipedia.org/wiki/Euclidean_distance">https://en.wikipedia.org/wiki/Euclidean_distance</a>
         */
        public static double distance(Point point1, Point point2) {
            return Math.sqrt(Math.pow(point1.x - point2.x, 2.0D) + Math.pow(point1.y - point2.y, 2.0D));
        }

        /**
         * Takes a list of points and sorts it on x (ascending).
         *
         * @param points - points that need to be sorted.
         */
        public static void sortByX(List<Point> points) {
            Collections.sort(points, Comparator.comparingDouble(point -> point.x));
        }

        /**
         * Takes a list of points and sorts it on y (ascending) .
         *
         * @param points - points that need to be sorted.
         */
        public static void sortByY(List<Point> points) {
            Collections.sort(points, Comparator.comparingDouble(point -> point.y));
        }

        /**   Used when input list has 3 points only.  ---> O(n^2) ....
         *
         * Takes a list of points and returns the distance between the closest pair.
         * This is done by brute forcing.
         *
         * @param points - list of points that need to be considered.
         * @return smallest pair-wise distance between points.
         */
        public static double bruteForce(List<Point> points) {
            int size = points.size();
            if (size <= 1)
                return Double.POSITIVE_INFINITY;   // not possible if input <= 1 point ....

            double bestDist = Double.POSITIVE_INFINITY;
            for (int i = 0; i < size - 1; i++) {
                Point point1 = points.get(i);
                for (int j = i + 1; j < size; j++) {
                    Point point2 = points.get(j);
                    double distance = Util.distance(point1, point2);
                    if (distance < bestDist)
                        bestDist = distance;
                }
            }
            return bestDist;
        }
    }


}
