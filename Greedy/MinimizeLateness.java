import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;

public class MinimizeLateness {



        // Minimize MAXIMUM lateness reached for any job, not total sum of latenesses!


    /**    VERSION 1: instead of PQ, just sort the arraylist of jobs, since we don't really need to insert new stuff
     *
     *   ----> Each job takes 1 hour! --> So we can just assign all m processors one by one
     *   No processor can do 2 jobs at the same time.
     *
     *   We rotate over all processors possible (modolo)
     *   Each time we reach the 1st processor again, advance t by 1
     *
     * @param n - no. of jobs
     * @param m - no. of processors
     * @param deadlines - array
     * @return max lateness reached
     */
    public static int maxLatenessSorting(int n, int m, int[] deadlines) {

        // Job class is not necessary, since we only care about 1 value deadline...

         if (n == 0) return 0;

         int maxLateness = 0;
         Arrays.sort(deadlines);

         int t = 0;              // current time
         int processorIdx = 1;  // current processor

         for (int i = 1; i <= n; i++) {

             int currLateness = Math.max(0, t + 1 - deadlines[i]);
             if (currLateness > maxLateness) {
                 maxLateness = currLateness;
             }

             if (processorIdx + 1 <= m) {
                 processorIdx++;
             } else {
                 processorIdx = 1;  // all m processors exploited, so start at 1 again
                 t++;        // "new round" starts, so advance time by 1 hour
             }
         }
         return maxLateness;

//        ArrayList<Job> jobList = new ArrayList<>(n);
//        for (int i = 1; i <= n; i++) {
//            jobList.add(new Job(deadlines[i]));
//        }
//        Collections.sort(jobList);
//        int maxLateness = Integer.MIN_VALUE;
//        int processorIndex = 1;    // 1st processor assigned
//        int t = 0;
//        for (int i = 0; i < jobList.size(); i++) {
//            Job job = jobList.get(i);
//            int lateness = t + 1 - job.deadline;
//            if (lateness > maxLateness) {
//                maxLateness = lateness;
//            }
//            if (processorIndex + 1 <= m) {   // max m processors available!
//                processorIndex++;
//            } else {
//                t++;           // Go back to first processor and advance time
//                processorIndex = 1;
//            }
//        }
//        return maxLateness;
    }



    /** VERSION 2:  Instead of sorting array in O (n log n) time, we use PQ which is also O (n log n) to pick earliest deadline
     *
     * @param n the number of jobs
     * @param m the number of processors
     * @param deadlines the deadlines of the jobs 1 through n. NB: you should ignore deadlines[0]
     * @return the minimised maximum lateness.
     *
     *
     */
    public static int maxLatenessPriorityQueue(int n, int m, int[] deadlines) {
        PriorityQueue<Job> queue = new PriorityQueue<>();
        for (int i = 1; i <= n; i++) {
            queue.add(new Job(deadlines[i]));   // we wanna process earliest deadlines first!
        }
        int maxLateness = Integer.MIN_VALUE;
        int processorIndex = 1;    // Assign job to first processor for now
        int t = 0;
        while (!queue.isEmpty()) {
            Job job = queue.poll();   // Job with earliest deadline
            int lateness = t + 1 - job.deadline;
            if (lateness > maxLateness) {   // Keep max lateness reached
                maxLateness = lateness;
            }
            if (processorIndex + 1 <= m) {    // Use next processor for next job, starting at the same t as this processor
                processorIndex++;
            } else {
                t++;            // Go back to first processor again, advance time by 1
                processorIndex = 1;
            }
        }
        return maxLateness;
    }





    static class Job implements Comparable<Job> {

        public int deadline;

        public Job(int deadline) {
            this.deadline = deadline;
        }

        @Override
        public int compareTo(Job otherJob) {
            return deadline - otherJob.deadline;
        }
    }






    /**  Lateness =  max(0, start + 1 - deadline)
     *
     * @param n the number of jobs
     * @param m the number of processors
     * @param deadlines the deadlines of the jobs 1 through n. NB: you should ignore deadlines[0]
     * @return the minimised maximum lateness.
     *
     *   ----> Each job takes 1 hour!
     *   No processor can do 2 jobs at the same time.
     *
     *    VERSION 3: We sort array by ascending deadline
     *               Pick processor using PQ based on lowest idx (earliest available) <-- not necessary ... (see version 1)
     *
     */
    public static int maxLatenessSortArray(int n, int m, int[] deadlines) {

        if (n == 0) return 0;

        // Sort jobs by earliest deadline
        Arrays.sort(deadlines);

        int maxLateness = Integer.MIN_VALUE;

        // Holds finish times for each processor: min heap, so will pop lowest finish time
        PriorityQueue<Processor> processorFinishTimes = new PriorityQueue<>();
        for (int p = 1; p <= m; p++) {
            processorFinishTimes.add(new Processor(0));   // All m processors start at time 0
        }

        for (int i = 1; i <= n; i++) {  // assign each job a resource (by increasing their counters)

            // Find the processor having the minimum count: will be available/free the earliest
            Processor currProcessor = processorFinishTimes.poll();  // We don't remove it!

            currProcessor.finishTime++;     // Increase assigned processor counter by 1
            processorFinishTimes.add(currProcessor);

            // If deadline >= processor count  ---> lateness = 0
            // If deadline < processor count   ---> lateness > 0
            int currLateness = Math.max(0, currProcessor.finishTime - deadlines[i]);  // essentially s(i) + 1 - d(1) since we advanced time already
            if (currLateness > maxLateness) {
                maxLateness = currLateness;
            }
        }
        return maxLateness;
    }


    private static class Processor implements Comparable<MinimizeLateness.Processor> {

        // start time and length
        int finishTime;

        public Processor(int counter) {
            this.finishTime = counter;
        }

        @Override
        public int compareTo(MinimizeLateness.Processor p) {
            return this.finishTime - p.finishTime;
        }

        @Override
        public String toString() {
            return "Processor: " + finishTime;
        }
    }



}
