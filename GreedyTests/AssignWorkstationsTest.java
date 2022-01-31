import org.junit.Test;
import static org.junit.Assert.*;

public class AssignWorkstationsTest {


    @Test
    public void test3UnlocksSaved() {
        int n = 5;
        int m = 10;
        int[] start = { 0, 2, 1, 17, 3, 15 };
        int[] end = { 0, 6, 2, 7, 9, 6 };
        assertEquals(3, AssignWorkstations.solve(n, m, start, end));
    }


}
