import java.util.*;

public class IntervalPartitioning {


    // Greedy: we wanna process all requests/intervals using as few resources (employees) as possible!


    /**
     *  Depth d of set of intervals = optimal no. of resources needed = max. no. of jobs overlapping at any time
     *
     * @param n - number of jobs / requests / intervals to schedule across resources (employees)
     * @param starttimes - unordered array of all start times
     * @param durations - unordered array of all durations
     * @return - minimal number of employees needed for all repairs
     *
     * We use a count here, since we don't really care about the actual labels 1, 2, ..., d assigned
     *
     *  Sorting: O(n log n)
     *  Nested loop: worst case O(n^2)
     */
    public static int fixMyBikesPlease(int n, int[] starttimes, int[] durations) {

        if (n == 0) return 0;
        if (n == 1) return 1;

        Job[] jobs = new Job[n];
        for (int i = 0; i < n; i++) {   // jobs[0] corresponds to starttimes[1], and so job n is at jobs[n-1]
            jobs[i] = new Job(starttimes[i + 1], durations[i + 1]);
        }
        Arrays.sort(jobs);   // Sort all requests by start time: O(n log n)

        int maxDepth = 0;    // Will store max depth reached = = optimal no. of resources/employees needed

        // For each interval i, we check if it overlaps with PRECEDING jobs j ---> overlap counter for each job i
        // If you'd check the jobs AFTER i, it creates much overhead!
        for (int i = 1; i < n; i++) {  // Start at i = 1, so job 2 (job 1 does not have preceding jobs)

            int numJobsOverlapping = 1;   // Assign first label/resource to each current job to be compared against all previous!

            for (int j = 0; j < i; j++) {   // For each interval that precedes the curr interval, exclude that label for the curr interval
                if (jobs[j].s + jobs[j].l > jobs[i].s) {
                    numJobsOverlapping++;   // overlaps with previous, so new this job is assigned to next resource available
                }
            }
            if (numJobsOverlapping > maxDepth) {
                maxDepth = numJobsOverlapping;    // More resources were added due to overlap
            }
        }
        return maxDepth;
    }



    private static class Job implements Comparable<Job> {

        // start time and length
        int s, l;

        public Job(int start, int length) {
            this.s = start;
            this.l = length;
        }

        @Override
        public int compareTo(Job j) {
            return this.s - j.s;
        }  // ascending

        @Override
        public String toString() {
            return "Job: " + s + ", " + l;
        }
    }

}
