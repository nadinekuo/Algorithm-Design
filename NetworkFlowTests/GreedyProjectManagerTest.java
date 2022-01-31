import java.util.Set;
import org.junit.jupiter.api.*;

public class GreedyProjectManagerTest {


    @Test
    public void example() {
        int n = 2;
        int[] b = { 0, 8, 2 };
        int[] c = { 0, 2, 4 };
        int[][] dep = { { 0, 0, 0 }, { 0, 0, 0 }, { 0, 0, 0 } };
        Set<Integer> answer = RecoverProjectsSelected.outputSelection(n, b, c, dep);
        Assertions.assertEquals(1, answer.size());
                //, "Should take only project 1 as that has a net profit: Size");
        Assertions.assertTrue(answer.contains(1));
                 //, "Should take only project 1 as that has a net profit: Project 1");
    }

    @Test
    public void example02() {
        int n = 2;
        int[] b = { 0, 8, 2 };
        int[] c = { 0, 2, 4 };
        int[][] dep = { { 0, 0, 0 }, { 0, 0, 1 }, { 0, 0, 0 } };
        Set<Integer> answer = RecoverProjectsSelected.outputSelection(n, b, c, dep);
        Assertions.assertEquals(2, answer.size());
                //, "Project 1 (profit 6) now requires 2 (loss 2), so we take both");
        Assertions.assertTrue(answer.contains(1));
                //, "Project 1 should be included");
        Assertions.assertTrue(answer.contains(2));
                //, "Project 2 should be included");
    }


}
