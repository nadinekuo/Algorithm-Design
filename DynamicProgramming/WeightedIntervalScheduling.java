import java.util.*;

public class WeightedIntervalScheduling {



    /**
     * Using the memoized values from an iterative dynamic programming solution,
     * retrieve the schedule with the highest total weight.
     * NB: You may assume the jobs are sorted by ascending finishing time.
     *
     *   ----> Note: As a tiebreaker, when there are multiple job schedules possible, choose the one that finishes the earliest.
     *           Use the same rule for intermediate tiebreakers.
     *   So when the 2 arguments in MAX(v[i] + mem[P(i)], mem[i-1]) are equal, don't include this job i, but take mem[i-1]
     *
     * @param n the number of jobs
     * @param s the start times of the jobs for jobs 1 through n. Note that s[0] should be ignored.
     * @param f the finish times of the jobs for jobs 1 through n. Note that f[0] should be ignored.
     * @param v the values of the jobs for jobs 1 through n. Note that v[0] should be ignored.
     * @param p the predecessors of the jobs for jobs 1 through n. Note that p[0] should be ignored and that -1 represents there being no predecessor.
     * @param mem where the ith element is the maximum weight of a schedule using the first i jobs.
     * @return list containing the id of the jobs used in the optimal schedule, ordered by ascending finishing time.
     */
    public static List<Integer> retrieveScheduledJobsLinkedList(int n, int[] s, int[] f, int[] v, int[] p, int[] mem) {

        int j = n;
        LinkedList<Integer> solution = new LinkedList<>();   // Efficient prepending (since we want jobs ordered by finish time!)
        while (j > 0) {
            // Note that equal values can also mean there were multiple opt schedules until j, but in this case we dont add it.
            //  Only the FIRST j for which mem[j] > mem[j-1]  <--  leads to the schedule that finishes EARLIEST! (tiebreaker)
            if (mem[j - 1] == mem[j]) {
                j--;       // Optimal schedule doesn't include the current job, go to previous
            } else {      // Else, this value mem[j] > mem[j-1]
                solution.addFirst(j);
                j = p[j];   // Note: continue at predecessor! If this is -1 (no parent), the while-loop will stop
            }
        }
        return solution;

    }




        /**
         * Come up with an iterative dynamic programming solution to the weighted interval scheduling problem.
         * NB: You may assume the jobs are SORTED by ascending finishing time.
         *
         * @param n the number of jobs
         * @param s the start times of the jobs for jobs 1 through n. Note that s[0] should be ignored.
         * @param f the finish times of the jobs for jobs 1 through n. Note that f[0] should be ignored.
         * @param v the values of the jobs for jobs 1 through n. Note that v[0] should be ignored.
         * @param p the predecessors of the jobs for jobs 1 through n. Note that p[0] should be ignored and that -1 represents there being no predecessor.
         * @return the weight of the maximum weight schedule.
         */
    public static int computeMaximalWeight(int n, int[] s, int[] f, int[] v, int[] p) {

        int[] mem = new int[n + 1];  // 0 does not correspond to any job

        mem[0] = 0;    // Base case: value 0 cannot add anything to the next computations

        // From i = 1, we get MAX(v[i] + mem[P(i)], mem[i-1])  ---> iteratively fill our memorization table
        // Either skip this job and get mem[j-1]
        // or include this job v[j] and get the optimal value for the previous compatible job (parent)
        for (int i = 1; i <= n; i++) {
            if (p[i] != -1) {           // Note: check if job has parent! -1 will lead to IdxOutOfBounds!
                mem[i] = Math.max(v[i] + mem[p[i]], mem[i-1]);
            } else {
                mem[i] = Math.max(v[i], mem[i-1]);   // Note: if no predecessor, exclude Opt(P(i))
            }
        }

        // Returning the obtained obtained value at index n <--- will store the max weight (even if job n itself is not included)
        return mem[n];
    }



    /**
     * @param n - no. of jobs
     * @param s - start times (ignore s[0])
     * @param f - finish times (ignore f[0])
     * @param v - values (not needed here)
     * @return - array of all predecessor indices, so P[i] = predecessor of job i = first non-overlapping job
     *
     *   First idx does not matter
     *   If no predecessor, store -1
     */
    public static int[] computePredecessors(int n, int[] s, int[] f, int[] v) {

        // Note: sort by both start and end time for efficiency!
        Pair[] startSort = new Pair[n + 1];
        Pair[] endSort = new Pair[n + 1];
        for (int i = 1; i <= n; i++) {
            startSort[i] = new Pair(i, s[i]);  // s[] and f[] are 1-indexed!
            endSort[i] = new Pair(i, f[i]);
        }
        Arrays.sort(startSort, 1, n + 1);   // see compareTo(): will sort by time
        Arrays.sort(endSort, 1, n + 1);     // Note: Skip idx 0! (NullPointer...)

        int[] predecessors = new int[n + 1];  // P[i] will be idx of parent of i


        // Find parent of node i by letting j walk forwards OR walk backwards (see slides)
        // Note: i is idx into startSort[], which is not the original node idx!
        // Note: j is idx into endSort[], which is not the original node idx!
        for (int i = 1; i <= n; i++) {

            // Note: all jobs starting BEFORE the EARLIEST end time don't have a predecessor, as there was no job ending before it --> -1
            if (startSort[i].time < endSort[1].time) {
                predecessors[startSort[i].index] = -1;
            } else {
                int j = 0;   // Note: we start from 0, so first job 0+1 = 1 will not be ignored
                // Note: even tho we go from  j = 0...n for each i, we can stop early bc we sorted on endtimes!
                while (j < n) {            // loop until we find the biggest endtime smaller than the starttime
                    if (endSort[j+1].time > startSort[i].time) {   // j + 1 is first job ending after start(i), so j is parent(i)
                        predecessors[startSort[i].index] = endSort[j].index;     // OR you put this line after the while-loop
                        break;
                    }
                    j++;
                }
            }
        }
        return predecessors;
    }


    // Allows us to sort jobs by both start and end time
    private static class Pair implements Comparable<Pair> {

        int index;   // Note: job idx corresponding to s[] and f[]
        int time;    // either start or end time

        public Pair(int index, int time) {
            this.index = index;
            this.time = time;
        }

        @Override
        public int compareTo(Pair other) {
            int result = Integer.compare(this.time, other.time);
            return result == 0 ? Integer.compare(this.index, other.index) : result;  // if equal time, sort on idx
        }
    }


}
