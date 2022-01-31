import java.util.Arrays;

public class EfficiencySort {


    /**
     * Return the id(s) of the tasks with the median value for their efficiency, where efficiency = value/time.
     *
     * @param tasks Array of tasks, in no particular order.
     * @return The id(s) of the median task(s) based on efficiency. If you have an odd number of tasks, return the ids of the two median
     * tasks with a space between. eg if the median is 4,5. "(id of 4th task) (id of 5th task)"
     *
     */
    public static String solve(Task[] tasks) {

         // Sort by efficiency (increasing) to find median

        Arrays.sort(tasks, (Task task1, Task task2) -> {
            if (Double.compare(task1.value / task1.time, task2.value / task2.time) == 0) {
                return Integer.compare(task1.id, task2.id);             // if equal efficiency, sort by id!
            }
            return Double.compare(task1.value / task1.time, task2.value / task2.time);
        });

        int median = (tasks.length + 1) / 2 - 1;       // idx of median

        if (tasks.length % 2 == 1) {                // if n is odd, there is 1 median (id)
            return "" + tasks[median].id;
        } else {                                 // if n is equal, return 2 ids! (separated by space)
            return tasks[median].id + " " + tasks[median + 1].id;
        }
    }



    class Task {

        int id;    // 0, 1, ..., n
        double time;
        double value;

        Task(double time, double value, int id) {
            this.time = time;
            this.value = value;
            this.id = id;
        }
    }


}
