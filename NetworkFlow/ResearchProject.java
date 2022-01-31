import java.io.*;
import java.util.*;

/**
 * WARNING: The spec tests are not equal to your grade!
 * You can use them help you test for the correctness of your algorithm,
 * but the final grade is determined solely by a manual inspection of your implementation.
 */
class ResearchProject {

    /**
     *    Valid allocation of all n students to supervisor (groups), while meeting constraints?
     *
     *    ---> Lower bound: ensures all min/max constraints are met
     *    ----> Demand/supply: ensures ALL n students are allocated
     *                         not guaranteed by lower bound only, even tho there can be a valid circulation!
     *
     *   - Students and supervisor both need to be available on Mon or Tue (or both)
     *   - Each supervisor has [3, 12] students
     *   - A group meeting (on Mon/Tue) can have at most 5 students
     *   - 1 supervisor can have max 2 groups (meetings) per day
     *
     * @param n                            The number of students.
     * @param m                            The number of supervisors.
     * @param student_availability_mon     is an array of size (n+1) that is true iff student i is available on Monday.
     *                                     You should ignore student_availability_mon[0].
     * @param student_availability_tues    is an array of size (n+1) that is true iff student i is available on Tuesday. You should ignore student_availability_tues[0].
     * @param supervisor_availability_mon  is an array of size (m+1) that is true iff supervisor j is available on Monday.
     *                                     You should ignore supervisor_availability_mon[0].
     * @param supervisor_availability_tues is an array of size (m+1) that is true iff supervisor j is available on Tuesday.
     *                                     You should ignore supervisor_availability_tues[0].
     *
     * @param selected                     is an array of size (n+1)x(m+1) that is true iff student i selected supervisor j.
     *                                     You should use entries selected[1][1] through selected[n][m].
     *
     * @return true iff there is a valid allocation of students over supervisor (groups)  <--> G has circulation
     */
    public static boolean areThereGroups(int n, int m, boolean[] student_availability_mon, boolean[] student_availability_tues,
                                         boolean[] supervisor_availability_mon, boolean[] supervisor_availability_tues,
                                         boolean[][] selected) {

        return canAllStudentsBeAssignedGroup(n, m, student_availability_mon, student_availability_tues, supervisor_availability_mon, supervisor_availability_tues, selected);
        //return solveWithSeparateArrays(n, m, student_availability_mon, student_availability_tues, supervisor_availability_mon, supervisor_availability_tues, selected);
        // return solveProperArrayList(n, m, student_availability_mon, student_availability_tues, supervisor_availability_mon, supervisor_availability_tues, selected);
    }



    /**
     * You should implement the method below. Note that you can use the graph structure below.
     *
     * @param n                            The number of students.
     * @param m                            The number of supervisors.
     * @param student_availability_mon     is an array of size (n+1) that is true iff student i is available on Monday. You should ignore student_availability_mon[0].
     * @param student_availability_tues    is an array of size (n+1) that is true iff student i is available on Tuesday. You should ignore student_availability_tues[0].
     * @param supervisor_availability_mon  is an array of size (m+1) that is true iff supervisor j is available on Monday. You should ignore supervisor_availability_mon[0].
     * @param supervisor_availability_tues is an array of size (m+1) that is true iff supervisor j is available on Tuesday. You should ignore supervisor_availability_tues[0].
     * @param selected                     is an array of size (n+1)x(m+1) that is true iff student i selected supervisor j. You should use entries selected[1][1] through selected[n][m].
     * @return true iff there is a valid allocation of students over supervisors.
     */
    // Note: this version does not create Student-Mon or -Tue nodes like in the solutions, saves some nodes :)
    public static boolean canAllStudentsBeAssignedGroup(int n, int m, boolean[] student_availability_mon,
                                                        boolean[] student_availability_tues,
                                                        boolean[] supervisor_availability_mon,
                                                        boolean[] supervisor_availability_tues, boolean[][] selected) {

        List<Node> nodes = new ArrayList<>();
        Node source = new Node(-1, -n);
        Node sink = new Node(-2, n);
        nodes.add(source);
        nodes.add(sink);

        // Note: we assign students to GROUPS! Not supervisors, so we need group nodes.
        //  But since there is a constraint [3, 12] per supervisor, and students can select supervisors, we must include supervisor nodes too.
        Node[] students = new Node[n + 1];
        Node[] supervisors = new Node[m + 1];
        Node[] groupsMon_1 = new Node[m + 1];  // Mon group 1 slots for all m supervisors (idx corresponds to supervisors[])
        Node[] groupsTue_1 = new Node[m + 1];  // Tue group 1 slots for all m supervisors ...
        Node[] groupsMon_2 = new Node[m + 1];
        Node[] groupsTue_2 = new Node[m + 1];

        // Insert n student nodes, connected to source
        for (int i = 1; i <= n; i++) {
            Node student = new Node(i, 0);
            nodes.add(student);
            students[i] = student;
            source.addEdge(student, 0, 1);  // Note: each student only assigned 1 group!
        }

        // Insert m supervisor nodes, connected to sink
        for (int i = 1; i <= m; i++) {
            Node supervisor = new Node(i, 0);
            nodes.add(supervisor);
            supervisors[i] = supervisor;
            supervisor.addEdge(sink, 3, 12);  // Note: [3, 12] students per supervisor!

            // Per supervisor, 4 groups possible; 2 on Mon, 2 on Tue  --> 4 * m nodes for all groups
            // Note: supervisor may not necessarily be available that day, but in that case there wont be an edge to that supervisor
            groupsMon_1[i] = new Node(i, 0);
            groupsMon_2[i] = new Node(i, 0);
            groupsTue_1[i] = new Node(i, 0);
            groupsTue_2[i] = new Node(i, 0);
            nodes.add(groupsMon_1[i]);
            nodes.add(groupsMon_2[i]);
            nodes.add(groupsTue_1[i]);
            nodes.add(groupsTue_2[i]);
        }

        // For each student-supervisor pair,
        // Connect students to group on Mon/Tue for supervisor m, if selected and both available on that day
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (selected[i][j]) {
                    if (student_availability_mon[i]) {   // Note: if selected, connect to BOTH groups on Mon for supervisor j
                        students[i].addEdge(groupsMon_1[j], 0, 42);   // OR 1 but its already bounded by cap 1 on edge (s, student)
                        students[i].addEdge(groupsMon_2[j], 0, 42);
                    }
                    if (student_availability_tues[i]) {   // connect to BOTH groups on Tue for supervisor j
                        students[i].addEdge(groupsTue_1[j], 0, 42);
                        students[i].addEdge(groupsTue_2[j], 0, 42);
                    }
                }
            }
        }
        // Note: again connect BOTH groups on either Mon/Tue to supervisor, if available on that day
        for (int i = 1; i <= m; i++) {
            if (supervisor_availability_mon[i]) {
                groupsMon_1[i].addEdge(supervisors[i], 0, 5);  // Note: max 5 students in 1 group!
                groupsMon_2[i].addEdge(supervisors[i], 0, 5);
            }
            if (supervisor_availability_tues[i]) {
                groupsTue_1[i].addEdge(supervisors[i], 0, 5);
                groupsTue_2[i].addEdge(supervisors[i], 0, 5);
            }
        }

        sink.addEdge(source, 0, Integer.MAX_VALUE / 2);

        Graph g = new Graph(nodes);    // Note: this constructor does not take source, sink?
        return g.hasCirculation();
    }






    // Note: Uses both lower bound and supply/demand on s and t (no t-s edge, but you can add it to be sure)
    private static boolean solveWithSeparateArrays(int n, int m, boolean[] student_availability_mon, boolean[] student_availability_tues, boolean[] supervisor_availability_mon, boolean[] supervisor_availability_tues, boolean[][] selected) {

        // OR you set demand of s and t to 0, and give the t-s edge a lower bound of n!
        Node source = new Node(0, -n);    // s has supply of n students (total to be allocated to a supervisor group)
        Node sink = new Node(-1, n);     //  t has demand of n students
        Node[] students = new Node[n + 1];
        Node[] supervisors = new Node[m + 1];
        Node[] students_monday = new Node[n + 1];
        Node[] students_tuesday = new Node[n + 1];
        Node[] supervisors_monday_1 = new Node[m + 1];   // Each supervisor has 2 meeting slots per day (Mon/Tue)!
        Node[] supervisors_monday_2 = new Node[m + 1];
        Node[] supervisors_tuesday_1 = new Node[m + 1];
        Node[] supervisors_tuesday_2 = new Node[m + 1];
        for (int i = 1; i <= n; i++) {
            students[i] = new Node(i, 0);
            students_monday[i] = new Node(i, 0);
            students_tuesday[i] = new Node(i, 0);
        }
        for (int j = 1; j <= m; j++) {
            supervisors[j] = new Node(j, 0);
            supervisors_monday_1[j] = new Node(j, 0);
            supervisors_monday_2[j] = new Node(j, 0);
            supervisors_tuesday_1[j] = new Node(j, 0);
            supervisors_tuesday_2[j] = new Node(j, 0);
        }
        for (int i = 1; i <= n; i++) {

            source.addEdge(students[i], 0, 1);    // connect all n students to s, lets 1 student flow

            if (student_availability_mon[i]) {
                students[i].addEdge(students_monday[i], 0, 1);   // student avail on Mon
            }
            if (student_availability_tues[i]) {
                students[i].addEdge(students_tuesday[i], 0, 1);  // student avail on Tue
            }
        }
        for (int j = 1; j <= m; j++) {

            supervisors[j].addEdge(sink, 3, 12);    // connect all m supervisors to sink, min 3 students, max 12

            if (supervisor_availability_mon[j]) {       // if supervisor avail on Mon, connect to both slots!
                supervisors_monday_1[j].addEdge(supervisors[j], 0, 5);
                supervisors_monday_2[j].addEdge(supervisors[j], 0, 5);
            }
            if (supervisor_availability_tues[j]) {       // if supervisor avail on Tue, connect to both slots!
                supervisors_tuesday_1[j].addEdge(supervisors[j], 0, 5);
                supervisors_tuesday_2[j].addEdge(supervisors[j], 0, 5);
            }
        }
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (selected[i][j]) {           // Student i selected supervisor j
                    // Note: We connect both Mon and Tue slots of students (to both sup. slots on both days)
                    //       even tho that student/supervisor may not be avail on that day,
                    //       but that does not matter since there will be no flow possible anyways (since that supervisor would not be connected to that day)!
                    students_monday[i].addEdge(supervisors_monday_1[j], 0, 1);
                    students_monday[i].addEdge(supervisors_monday_2[j], 0, 1);
                    students_tuesday[i].addEdge(supervisors_tuesday_1[j], 0, 1);
                    students_tuesday[i].addEdge(supervisors_tuesday_2[j], 0, 1);
                }
            }
        }
        ArrayList<Node> allNodes = new ArrayList<>(2 + 3 * n + 5 * m);
        allNodes.add(source);
        allNodes.add(sink);
        for (int i = 1; i <= n; i++) {
            allNodes.add(students[i]);
            allNodes.add(students_monday[i]);
            allNodes.add(students_tuesday[i]);
        }
        for (int j = 1; j <= m; j++) {
            allNodes.add(supervisors[j]);
            allNodes.add(supervisors_monday_1[j]);
            allNodes.add(supervisors_monday_2[j]);
            allNodes.add(supervisors_tuesday_1[j]);
            allNodes.add(supervisors_tuesday_2[j]);
        }
        Graph g = new Graph(allNodes);
        return g.hasCirculation();
    }




    // TODO: with t-s edge here, but removing it does not make a difference in spec tests ???
    private static boolean solveProperArrayList(int n, int m, boolean[] student_availability_mon, boolean[] student_availability_tues, boolean[] supervisor_availability_mon, boolean[] supervisor_availability_tues, boolean[][] selected) {

        ArrayList<Node> allNodes = new ArrayList<>(2 + 3 * n + 5 * m);

        Node source = new Node(0, -n);    // source has supply of n students in total
        allNodes.add(source);

        // Student nodes are 1 through.
        for (int i = 1; i <= n; i++) {
            allNodes.add(new Node(i, 0));
        }
        // Staff nodes are n+1 through n+m
        for (int j = n + 1; j <= n + m; j++) {
            allNodes.add(new Node(j, 0));
        }
        // Student Monday are n+m+1 through n+m+n
        for (int i = n + m + 1; i <= n + m + n; i++) {
            allNodes.add(new Node(i, 0));
        }
        // Student Tuesday are n+m+n+1 through n+m+2n
        for (int i = n + m + n + 1; i <= n + m + n + n; i++) {
            allNodes.add(new Node(i, 0));
        }
        // Staff Monday are n+m+2n+1 through n+m+2n+2m
        for (int i = n + m + 2 * n + 1; i <= n + m + 2 * n + 2 * m; i++) {
            allNodes.add(new Node(i, 0));
        }
        // Staff Tuesday are n+m+2n+2m+1 through n+m+2n+4m
        for (int i = n + m + 2 * n + 2 * m + 1; i <= n + m + 2 * n + 4 * m; i++) {
            allNodes.add(new Node(i, 0));
        }

        Node sink = new Node(n + m + 2 * n + 4 * m + 1, n);  // sink has demand of n students!
        allNodes.add(sink);

        // Connect source to all n students
        for (int i = 1; i <= n; i++) {
            source.addEdge(allNodes.get(i), 0, 1);
        }

        // Connect staff to sink: [3, 12] students per supervisor!
        for (int j = n + 1; j <= n + m; j++) {
            allNodes.get(j).addEdge(sink, 3, 12);
        }

        // Connect student to days
        for (int i = 1; i <= n; i++) {
            if (student_availability_mon[i]) {
                allNodes.get(i).addEdge(allNodes.get(n + m + i), 0, 1);
            }
            if (student_availability_tues[i]) {
                allNodes.get(i).addEdge(allNodes.get(n + m + n + i), 0, 1);
            }
        }
        // Connect Staff days to staff.
        for (int j = 1; j <= m; j++) {
            if (supervisor_availability_mon[j]) {
                allNodes.get(n + m + 2 * n + j).addEdge(allNodes.get(n + j), 0, 5);
                allNodes.get(n + m + 2 * n + m + j).addEdge(allNodes.get(n + j), 0, 5);
            }
            if (supervisor_availability_tues[j]) {
                allNodes.get(n + m + 2 * n + 2 * m + j).addEdge(allNodes.get(n + j), 0, 5);
                allNodes.get(n + m + 2 * n + 3 * m + j).addEdge(allNodes.get(n + j), 0, 5);
            }
        }
        // Connect students to staff
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (selected[i][j]) {
                    // Connect monday from student to Tuesday of staff
                    allNodes.get(n + m + i).addEdge(allNodes.get(n + m + 2 * n + j), 0, 1);
                    allNodes.get(n + m + i).addEdge(allNodes.get(n + m + 2 * n + m + j), 0, 1);
                    // Connect Tuesday from student to Tuesday of staff
                    allNodes.get(n + m + n + i).addEdge(allNodes.get(n + m + 2 * n + 2 * m + j), 0, 1);
                    allNodes.get(n + m + n + i).addEdge(allNodes.get(n + m + 2 * n + 3 * m + j), 0, 1);
                }
            }
        }
        sink.addEdge(source, 0, Integer.MAX_VALUE / 2);   // "magic" t-s edge
        Graph g = new Graph(allNodes);
        return g.hasCirculation();
    }




}