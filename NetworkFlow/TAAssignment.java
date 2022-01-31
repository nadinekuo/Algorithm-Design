import java.util.*;

public class TAAssignment {



    public static void main(String[] args) {

        // Example: all hours can be fulfilled
        {
            int n = 2;
            int m = 2;
            int[] available = { 0, 75, 75 };
            int[] need = { 0, 100, 50 };
            boolean[][] match = new boolean[n + 1][m + 1];
            match[1][1] = true;
            match[1][2] = true;
            match[2][1] = true;
            match[2][2] = true;
            /*
             * This test models the situation where:
             * Course 1 requires 100 TA hours and course 2 requires 50
             * Both TAs have 75 hours available.
             * Both TAs can work both courses.
             * So we are not short any hours.
             */
            //assertEquals(0, TAAssignment.shortageOfTAs(n, available, m, need, match));
        }

        // Example: 25 hours needed still
        {
            int n = 2;
            int m = 2;
            int[] available = {0, 250, 25};
            int[] need = {0, 100, 50};
            boolean[][] match = new boolean[n + 1][m + 1];
            match[1][1] = true;
            match[2][1] = true;
            match[2][2] = true;
            /*
             * This test models the situation where:
             * Course 1 requires 100 TA hours and course 2 requires 50
             * TA 1 can work on course 1 for 250 hours.
             * TA 2 can work on both courses for 25 hours.
             * So we are short a total of 25 hours for course 2 by 25 hours when optimally assigning our current TAs.
             */
            //assertEquals(25, TAAssignment.shortageOfTAs(n, available, m, need, match));
        }
    }


    /**  Matching problem ---> no minimal hours required, so no lower bounds
     *                          we just check whether max flow = totalHoursNeeded
     *
     *
     * @param n the number of TAs
     * @param available the number of hours a TA is available from index _1_ to _n_. You should ignore a[0].
     * @param m the number of courses
     *  @param compatible a matrix indicating whether a TA is available to assist in a course.
     *                    Note that this is both the min and max hours required for that course!
     *              The value c[i][j] is true iff TA i can assist course j. You should ignore c[0][j] and c[i][0].
     *
     * @return the number of hours we are short when optimally using the available TAs.
     */
    public static int shortageOfTAs(int n, int[] available, int m, int[] hoursNeeded, boolean[][] compatible) {

        List<Node> nodes = new ArrayList<>(2 + n + m);  // source, sink, n TAs, m courses
        Node s = new Node(-1, 0);
        Node t = new Node(-2, 0);
        nodes.add(t);

        int totalTaHoursNeeded = 0;
        for (int i = 1; i <= m; i++) {   // insert m nodes for all courses, connect to sink
            Node course = new Node(i, 0);
            // Note: We don't have lower bound! (even though each course requires certain hours)
            //  Instead of circulation problem, we model this as max flow problem, so we use the max flow value to check whether all hours have been fulfilled
            course.addEdge(t, 0, hoursNeeded[i]);   // Note: max capacity is also min hours needed!
            nodes.add(course);
            totalTaHoursNeeded += hoursNeeded[i];
        }

        for (int i = 1; i <= n; i++) {  // insert n nodes for all TAs, connect to source
            Node ta = new Node(i, 0);
            s.addEdge(ta, 0, available[i]);  // Note: capacity is how many hours that TA is available, no more than that can flow!


            boolean[] availability = compatible[i];
            for (int j = 1; j <= m; j++) {
                // connect TA to course if compatible
                if (availability[j]) {
                    ta.addEdge(nodes.get(j), 0, Integer.MAX_VALUE/2);  // Note: let all possible hours flow, any cap > 0 works
                }
            }

            nodes.add(ta);
        }
        nodes.add(s);

        Graph g = new Graph(nodes, s, t);
        // Note: how many hours do we lack??
        // Note: max flow value = how many hours that could be done by TAs, if this is lower than total TA hours needed, we could not fulfill all hours...
        return Math.max(0, totalTaHoursNeeded - MaxFlow.maximizeFlow(g));
        // Note that max flow > totalHoursRequired is never possible, since the sum of all capacities on edges (course, sink) = totalHoursRequired
    }


}
