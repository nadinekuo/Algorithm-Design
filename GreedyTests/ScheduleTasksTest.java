import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

public class ScheduleTasksTest {

    @Test
    public void example() {
        int n = 4;
        String[] tasks = {
                "",
                "Sit in a cake, fastest wins",
                "Paint the best picture of a horse whilst riding a horse",
                "Do something that will look impressive in reverse",
                "Excite Alex. Greatest increase in heart rate wins."
        };
        int[] startTimes = {0, 6, 1, 5, 3};
        /** We can do task 2 from 1 to 6 and then start task 1 at 6 (taking untill 11). */
        Set<String> result = new HashSet<>();
        result.add(tasks[1]);
        result.add(tasks[2]);
        Assertions.assertEquals(result, ScheduleTasks.winningTheTrophy(n, tasks, startTimes));
    }


}
