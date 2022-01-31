import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BitcoinOptimalTradeTest {

    @Test
    public void testExampleA() {
        int t = 3;
        double[] r = { 0.0, 500.0, 100.0, 10000.0 };
        // Best course of action is to sell bitcoins at t = 1, buy more bitcoins at t = 2, and sell again at t = 3
        Assertions.assertEquals(4037.5, BitcoinOptimalTrade.optimalTrade(t, r), 0.001);
    }

    @Test
    public void testExampleB() {
        int t = 5;
        double[] r = { 0.0, 2010.0, 1950.0, 2020.0, 1607.5, 1904.42 };
        // Best course of action is to sell bitcoins at t = 3, buy more bitcoins at t = 4, sell at t = 5
        Assertions.assertEquals(210.35103769828928, BitcoinOptimalTrade.optimalTrade(t, r), 1e-6);
    }

    @Test
    public void testExampleBExtended() {
        int t = 7;
        double[] r = { 0.0, 2010.0, 1950.0, 2020.0, 1607.5, 1904.42, 2010.0, 1904.42 };
        // Best course of action is to sell bitcoins at t = 3, buy more bitcoins at t = 4, sell at t = 5, buy more at t = 6, and again sell at t = 7
        Assertions.assertEquals(222.01278382581648, BitcoinOptimalTrade.optimalTrade(t, r), 1e-6);
    }

    @Test
    public void testExampleDontBuyMore() {
        int t = 3;
        double[] r = { 0.0, 500.0, 480.0, 510.0 };
        // Best course of action is to only sell at t = 3
        Assertions.assertEquals(48.45, BitcoinOptimalTrade.optimalTrade(t, r), 0.001);
    }


}
