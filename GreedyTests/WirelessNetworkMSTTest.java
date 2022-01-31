import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class WirelessNetworkMSTTest {

    @Test
    public void budgetExample() {
        int n = 7;
        int m = 6;
        int b = 3;
        WirelessNetworkMST.Edge[] edges = new WirelessNetworkMST.Edge[m + 1];
        edges[1] = new WirelessNetworkMST.Edge(0, 1, 1);
        edges[2] = new WirelessNetworkMST.Edge(1, 2, 2);
        edges[3] = new WirelessNetworkMST.Edge(2, 3, 3);
        edges[4] = new WirelessNetworkMST.Edge(3, 4, 1);
        edges[5] = new WirelessNetworkMST.Edge(4, 5, 2);
        edges[6] = new WirelessNetworkMST.Edge(5, 6, 1);
        WirelessNetworkMST sol = WirelessNetworkMST.setUpTheNetwork(n, m, b, edges);
        assertEquals(10, sol.cost);
        assertEquals(3, sol.number);
    }

    @Test
    public void example() {
        int n = 4;
        int m = 5;
        int b = 8;
        WirelessNetworkMST.Edge[] edges = new WirelessNetworkMST.Edge[m + 1];
        edges[1] = new WirelessNetworkMST.Edge(0, 1, 6);
        edges[2] = new WirelessNetworkMST.Edge(1, 2, 9);
        edges[3] = new WirelessNetworkMST.Edge(0, 2, 7);
        edges[4] = new WirelessNetworkMST.Edge(1, 3, 2);
        edges[5] = new WirelessNetworkMST.Edge(0, 3, 8);
        WirelessNetworkMST sol = WirelessNetworkMST.setUpTheNetwork(n, m, b, edges);
        assertEquals(15, sol.cost);
        assertEquals(2, sol.number);
    }


}
