
import static org.junit.jupiter.api.Assertions.*;
import java.util.Scanner;
import org.junit.jupiter.api.*;

public class MaxProfitFishmanTest {


    @Test
    public void example() {
        int n = 5;
        int[] P = { 0, 80, 30, 30, 70, 80 };
        int[] Q = { 0, 90, 60, 60, 50, 20 };
        Assertions.assertEquals(300, MaxProfitFishman.maxProfit2Stands(n, P, Q));
    }


}
