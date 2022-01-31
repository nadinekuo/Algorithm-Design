public class MaxGradeKnapsack {

    public static void main(String[] args) {

        int n = 4;   // 4 assignments
        int h = 6;   // max 6 hours to spend
        int[][] f = { { 0, 0, 0, 0, 0, 0, 0 },
                { 1, 1, 2, 3, 4, 4, 4 },
                { 1, 6, 6, 6, 6, 6, 6 },
                { 1, 2, 2, 2, 2, 2, 2 },
                { 1, 1, 2, 3, 4, 7, 10 } };
        // Spend 1 hour on the second assignment and 5 hours on the last assignment
        // Including the base grade for the remaining two assignments
        // 6 + 7 + 2 = 15
        Assertions.assertEquals(15, MaxGradeKnapsack.maxGrade(n, h, f));
    }


    /**  Triple nested loop!!!       O(n^3) !!!
     *
     *  2 variables: <-- Knapsack
     *   - total grade (maximize)  <--- instead of 1 value v_i for doing this task, the value depends on how many hours spent!
     *                                  So we can take a part of an item now, giving grade g_i,h
     *
     *   - hours (limit!)
     *
     *  Hint: maximising the average is the same as maximising the sum of the grades!
     *
     *  You have to pick which tasks i to do, and for how many hours
     *                        // f is increasing function, so
     *                         // The more hours k spent on this task i, the higher grade for this task i, but...
     *                         // The less time t left for other (previous) tasks...
     *
     * @param n - the number of assignments
     * @param H - the number of hours you can spend
     * @param f - the function in the form of a (n + 1) x (h + 1) matrix. <-- each assignment has its own function f(h)
     *          In the normal KnapSack problem, this would be 1D array values[], but now the value depends on "how much we take"
     *
     *  Now there is no durations[] array, since must choose how long to spend!
     *    So the task will always fit within curr max time t
     *
     *      Row = assignment 1, 2, ..., n
     *      Col = grade f(h) you get when spending h hours, f(0) in col 0 is the minimum grade
     *          Index 0 of the number of assignments should be ignored. <-- row 0
     *          Index 0 of the number of hours spend is the minimum grade for this assignment.  <-- col 0
     *
     * @return the max total sum of grades achievable
     *
     *    Case 1: We do this task for h hours  <-- function is increasing, so
     *                  grade f[i][h] + Opt(i-1, t-f[i][h])
     *
     *    Case 2: We take Opt(i-1, t)
     */
    public static int maxGrade(int n, int H, int[][] f) {

        int[][] mem = new int[n + 1][H + 1];

        for (int i = 0; i <= n; i++) {  // For each task i,
            for (int t = 0; t <= H; t++) {   // for each total time t, find the (sum of) max grade possible

                if (i == 0) {        // base case: task 0 NOTE: uninitialized entries are 0 by default in Java!
                    mem[i][t] = 0;
                }
                else if (t == 0) {     // Note: minimum grade for task i will be achieved, even if 0 hours spent
                    mem[i][t] = f[i][0] + mem[i - 1][0];  // sum of min grades over all assignments!
                } else {
                    // Find out HOW MANY hours k (argmax) we spend on each task Note: in KnapSack we either took it or not, but now its about HOW MUCH we take
                    for (int k = 0; k <= t; k++) {
                        mem[i][t] = Math.max(mem[i][t], mem[i-1][t-k] + f[i][k]); // decrease t by k!
                    }
                    // the final k that updated max grade is how many hours we spend on this task (may be 0)

//
//                    int maxGradeOptNumHours = Integer.MIN_VALUE;
//                    for (int k = 0; k <= t; k++) {         // For this item, for this total time t, find the optimal no. of hours to spend on this task i
//                        int gradeWhenKHoursSpent = f[i][k] + mem[i - 1][t - k];    // This grade f[i][h] + Opt(i-1, t-f[i][h]), since we spent k hours on it
//                        maxGradeOptNumHours = Math.max(maxGradeOptNumHours, gradeWhenKHoursSpent);
//                    }
//                    mem[i][t] = maxGradeOptNumHours;
                }
            }
        }
        recoverSolution(n, H, f, mem);
        System.out.println("Max sum of grades possible = " + mem[n][H]);
        return mem[n][H];   // Return maximal total grade achievable having h hours in total, having n tasks
    }



    // Note: we include each task, even if we may have spent 0 hours on it (which still gives min grade)
    public static TimePerAssignment[] recoverSolution(int n, int H, int[][] f, int[][] mem) {

        // Initialize vars based on result call: Opt(n, H)
        int i = n;   // task (row)
        int h = H;   // curr constraint on time (col)

        TimePerAssignment[] result = new TimePerAssignment[n + 1];  // For all n tasks, how many hours k we chose to spend on it

        // Walk backwards to recover solution based on opt formula
        while (i > 0) {       // while-condition is based on base case (i == 0)
            // For task i, look for hours t spent on it, having constraint of time h (var)
            for (int t = 0; t <= h; t++) {
                if (mem[i][h] == f[i][t] + mem[i-1][h-t]) {
                    result[i] = new TimePerAssignment(i, t);   // we spent t time on task i
                    System.out.println("Spend " + t + " hours on task " + i + " --> grade " + f[i][t]);
                    i--;
                    h -= t;  // decrease items and time
                    break;   // NOTE: dont loop further to look for hours k spent on task i, we found t!
                }
            }
        }
        return result;
    }



    public static class TimePerAssignment {

        int task;
        int time;

        public TimePerAssignment(int task, int time) {
            this.task = task;
            this.time = time;
        }
    }








}
