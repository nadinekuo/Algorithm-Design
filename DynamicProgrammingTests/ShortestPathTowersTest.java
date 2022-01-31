import static org.junit.jupiter.api.Assertions.*;
import java.util.Scanner;
import org.junit.jupiter.api.*;

public class ShortestPathTowersTest {

    @Test
    public void exampleLadderLength1() {
        int n = 2;
        int m = 3;
        int[][] graph = { { 3, 5, 6 },   // 3 -> 4 -> 2 -> 1
                          { 4, 2, 1 } };
        Assertions.assertEquals(1, ShortestPathTowers.minLadderLength(n, m, graph));
    }



}
