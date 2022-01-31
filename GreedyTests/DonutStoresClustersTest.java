import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class DonutStoresClustersTest {


    @Test
    public void twoForOne() {
        int n = 2;
        int k = 1;
        List<DonutStoresClusters.House> houses = new ArrayList<>();
        houses.add(new DonutStoresClusters.House(0, 1, 1));
        houses.add(new DonutStoresClusters.House(1, 3, 3));
        Set<DonutStoresClusters.Store> expected = new HashSet<>();
        expected.add(new DonutStoresClusters.Store(2, 2));
        assertEquals(expected, DonutStoresClusters.donutTimeSortList(n, k, houses));
    }


}
