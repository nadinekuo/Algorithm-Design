import java.util.*;

public class IntervalSchedulePresentations {

    /**
     * You should implement this method.
     *
     * @param n              the number of Presentations
     * @param presenterNames the names of the presenters p_1 through p_n. Note you should only use entries presenterNames[1] up to and including presenterNames[n].
     * @param startTimes     the start times of the presentations s_1 through s_n. Note you should only use entries startTimes[1] up to and including startTimes[n].
     * @param endTimes       the end times of the presentations e_1 through e_n. Note you should only use entries endTimes[1] up to and including endTimes[n].
     * @return a largest possible set of presenters whose presentation we can attend.
     */
    public static Set<String> whatPresentations(int n, String[] presenterNames, int[] startTimes, int[] endTimes) {

        // Interval scheduling: sort by end times

        // Insert all presentations
        List<Presentation> presentationList = new ArrayList<>();

        for (int i = 1; i <= n; i++) {
            presentationList.add(new Presentation(presenterNames[i], startTimes[i], endTimes[i]));
        }
        Collections.sort(presentationList);

        // Add to set if no overlap
        Set<String> compatiblePresenters = new HashSet<>();

        int currEndTime = 0;
        for (Presentation p : presentationList) {  // process presentations by finish times increasing
            if (p.start >= currEndTime) {
                compatiblePresenters.add(p.name);
                currEndTime = p.end;      // advance time by presentation picked
            }
        }

        // Return set of presenter names that are compatible
        return compatiblePresenters;
    }



    public static class Presentation implements Comparable<Presentation> {

        String name;
        int start;
        int end;

        public Presentation(String name, int start, int end) {
            this.name = name;
            this.start = start;
            this.end = end;
        }

        @Override
        public int compareTo(Presentation o) {
            return this.end - o.end;
        }
    }





    public static Set<String> solveQuadraticCheck(int n, String[] presenterNames, int[] startTimes, int[] endTimes) {
        Presentation[] Presentations = new Presentation[n];
        for (int i = 0; i < n; i++) {
            Presentations[i] = new Presentation(presenterNames[i + 1], startTimes[i + 1], endTimes[i + 1]);
        }
        Arrays.sort(Presentations);
        Set<String> result = new HashSet<>();
        Set<Presentation> chosenPresentations = new HashSet<>();
        for (int i = 0; i < n; i++) {
            if (!conflict(Presentations[i], chosenPresentations)) {
                result.add(Presentations[i].name);
                chosenPresentations.add(Presentations[i]);
            }
        }
        return result;
    }


    // O(n^2) ..... since we check BOTH start time and end time, for ALL elements in the Set....
    // whereas we only need to check the end time in O(1) ...

    private static boolean conflict(Presentation Presentation, Set<Presentation> chosenPresentations) {
        for (Presentation s : chosenPresentations) {
            if (s.end > Presentation.start && s.start <= Presentation.start) {
                return true;
            } else if (s.start >= Presentation.start && s.end <= Presentation.end) {
                return true;
            } else if (s.start < Presentation.end && Presentation.end <= s.end) {
                return true;
            }
        }
        return false;
    }




}
