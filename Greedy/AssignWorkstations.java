import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;

public class AssignWorkstations {

    /**  INTERVAL PARTITIONING, but we don't  necessarily aim to minimize the no. of resources needed (infinite)
     *    We wanna have as small gaps between 2 consecutive jobs as possible
     *
     * @param n number of researchers
     * @param m number of minutes AFTER which workstations lock themselves
     * @param start start times of jobs 1 through n. NB: you should ignore start[0]
     * @param duration duration of jobs 1 through n. NB: you should ignore duration[0]
     *
     * @return the number of unlocks that can be saved <-- how many processes we could concatenate with gap < m
     */
    public static int solve(int n, int m, int[] start, int[] duration) {

        int unlocksSaved = 0;

        ArrayList<Process> jobs = new ArrayList<>();   // Holds all processes/sessions
        for (int i = 1; i <= n; i++) {
            jobs.add(new Process(start[i], duration[i]));
        }
        Collections.sort(jobs);   // Sort all jobs start time ascending, so we the station becomes free as quickly as possible again

        PriorityQueue<WorkStation> stations = new PriorityQueue<>();

        // Assign all processes to the first available resource at start time (no overlap) <-- poll PQ (min heap)

        for (Process p : jobs) {

            while (!stations.isEmpty()) {    // initially empty (no user yet)

                // Case 1: Overlap for this workstation: we need a new workstation! Exit loop, leave this station, add new workstation to PQ
                // if the earliest available overlaps with this jobs start time, all later stations will clash too!
                if (p.startTime < stations.peek().timeWhenAvailable) break;

                // Case 2: No overlap, so we will use the earliest available station
                WorkStation currStation = stations.poll();

                // Case 2a: gap <= m, so we have saved 1 unlocking since its not locked yet!
                if (currStation.timeWhenLocked >= p.startTime) {   // If a new process starts EXACTLY after m minutes, it will NOT be locked
                    unlocksSaved++;
                    break;    // Don't poll further!!!!!!!!!!  We "update" station by inserting a new one
                }
                // Case 2b: gap > m, so we need to unlock again... DON'T BREAK.
                // Keep on polling until we find ANOTHER NON-OVERLAPPING work station which does not require unlocking
                // It does not matter that we have removed this workstation, since it will require unlocking for ALL next jobs too,
                //  as they are ordered by start time

                //  -  If the next one overlaps with this workstation, we "break" and just "update" this one by inserting a new one
                //  -  If the next does not overlap and we save an unlocking ...
                //  -  ..... or this case again
                //      .... if PQ empty (no stations left), we just add a new one
            }
            // First job will not poll any station, as its empty
            stations.add(new WorkStation(p.startTime + p.duration, p.startTime + p.duration + m));
        }
        return unlocksSaved;
    }



    private static class Process implements Comparable<AssignWorkstations.Process> {

        int startTime;
        int duration;  // start time + duration

        public Process(int start, int duration) {
            this.startTime = start;
            this.duration = duration;
        }

        @Override
        public int compareTo(AssignWorkstations.Process p) {
            return this.startTime - p.startTime;  // We wanna sort by earliest start time!
        }
    }




    private static class WorkStation implements Comparable<AssignWorkstations.WorkStation> {

        int timeWhenAvailable;
        int timeWhenLocked;     // timeWhenAvailable + m

        public WorkStation(int timeWhenAvailable, int timeWhenLocked) {
            this.timeWhenAvailable = timeWhenAvailable;
            this.timeWhenLocked = timeWhenLocked;   // If process starts BEFORE its locked again, we save 1 unlocking!
        }

        @Override
        public int compareTo(AssignWorkstations.WorkStation p) {
            return this.timeWhenAvailable - p.timeWhenAvailable;
        }

    }


}
