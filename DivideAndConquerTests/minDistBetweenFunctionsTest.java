import org.junit.Test;
import static org.junit.Assert.*;

public class minDistBetweenFunctionsTest {

    // https://www.desmos.com/calculator?lang=nl
    // f(x) = 8x - 240
    // g(x) = 6x - 156
    @Test
    public void testTwoLinear1Intersection() {
        minDistBetweenFunctions.Equation eq1 = new minDistBetweenFunctions.QuadraticEquation(0, 8, -240);
        minDistBetweenFunctions.Equation eq2 = new minDistBetweenFunctions.QuadraticEquation(0, 6, -156);
        assertEquals(42, minDistBetweenFunctions.findMin(eq1, eq2, 0, 100));
    }


    // f(x) = 3x + 1
    // g(x) = 3x + 4
    @Test
    public void testTwoParallelLinearNoIntersection() {
        minDistBetweenFunctions.Equation eq1 = new minDistBetweenFunctions.QuadraticEquation(0, 3, 1);
        minDistBetweenFunctions.Equation eq2 = new minDistBetweenFunctions.QuadraticEquation(0, 3, 4);
        assertEquals(0, minDistBetweenFunctions.findMin(eq1, eq2, 0, 100));
    }


    // f(x) = 1.5x^2
    // g(x) = x + 2
    @Test
    public void testQuadraticAndLinear2Intersections() {
        minDistBetweenFunctions.Equation eq1 = new minDistBetweenFunctions.QuadraticEquation((long) 1.2, 0, 0);
        minDistBetweenFunctions.Equation eq2 = new minDistBetweenFunctions.QuadraticEquation(0, 1, 2);
        assertEquals(2, minDistBetweenFunctions.findMin(eq1, eq2, 0, 10));

        // ranges -2, 2 does not work, as long is not precise enough
    }




}
