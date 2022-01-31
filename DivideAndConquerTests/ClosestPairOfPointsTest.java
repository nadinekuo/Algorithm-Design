import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ClosestPairOfPointsTest {

    @Test()
    public void testTwoPoints() {
        List<ClosestPairOfPoints.Point> points = new ArrayList<>();
        points.add(new ClosestPairOfPoints.Point(1, 2));
        points.add(new ClosestPairOfPoints.Point(4, 6));
        assertEquals(5, ClosestPairOfPoints.closestPair(points), 5e-6);
    }

    @Test()
    public void testSmall() {
        List<ClosestPairOfPoints.Point> points = new ArrayList<>();
        points.add(new ClosestPairOfPoints.Point(2, 3));
        points.add(new ClosestPairOfPoints.Point(12, 30));
        points.add(new ClosestPairOfPoints.Point(40, 50));
        points.add(new ClosestPairOfPoints.Point(5, 1));
        points.add(new ClosestPairOfPoints.Point(12, 10));
        points.add(new ClosestPairOfPoints.Point(3, 4));
        assertEquals(1.4142135623730951, ClosestPairOfPoints.closestPair(points), 1e-6);
    }




}
