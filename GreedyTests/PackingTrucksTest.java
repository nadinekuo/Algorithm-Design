import org.junit.Test;


import static org.junit.Assert.*;

public class PackingTrucksTest {



        @Test
        public void example() {
            int n = 4;
            int[] weights = { 0, 41, 29, 12, 26 };   // ignore weights[0] !
            int maxWeight = 48;
            assertEquals(3, PackingTrucks.minAmountOfTrucks(n, weights, maxWeight));
        }



}
