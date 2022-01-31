import java.util.*;

public class ScheduleTasks {


    /**
     * Each task takes 5 min.
     *
     * @param n the number of tasks available
     * @param taskNames the names of the tasks d_1 through d_n. Note you should only use entries
     *     taskNames[1] up to and including taskNames[n].
     * @param startTimes the start times of the tasks s_1 through s_n. Note you should only use
     *     entries startTimes[1] up to and including startTimes[n].
     * @return the names of a largest possible set of tasks you can complete.
     */
    public static Set<String> winningTheTrophy(int n, String[] taskNames, int[] startTimes) {

        List<Task> tasks = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            tasks.add(new Task(startTimes[i], taskNames[i]));
        }
        Collections.sort(tasks);  // sort from 1 ... n on finish times ascending

        Set<String> res = new HashSet<>();
        int currEnd = 0;
        for (int i = 0; i < n; i++) {
            if (tasks.get(i).start >= currEnd) {   // Add to set if compatible
                res.add(tasks.get(i).name);
                currEnd = tasks.get(i).finish;   // Advance end time
            }
        }
        return res;
    }



    public static class Task implements Comparable<Task> {

        int start;
        int finish;
        String name;

        public Task(int start, String name) {
            this.start = start;
            this.name = name;
            this.finish = this.start + 5;
        }

        @Override
        public int compareTo(Task o) {
            return this.finish - o.finish;
        }
    }

}
