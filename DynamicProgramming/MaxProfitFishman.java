public class MaxProfitFishman {


    /**
     * @param n the number of days
     * @param P the profits that can be made on day 1 through n on location P are stored in P[1] through P[n].
     * @param Q the profits that can be made on day 1 through n on location Q are stored in Q[1] through Q[n].
     *
     * @return the maximum obtainable profit in n days.
     *
     *   M[0][i] (row 1) stores how much total profit there can be on day i, being at P on day i
     *   M[1][i] (row 2) stores how much total profit there can be on day i, being at Q on day i
     *
     */
    public static int maxProfit2Stands(int n, int[] P, int[] Q) {

        int[][] profits = new int[2][n + 1];

        // Base case, one should never pick this value and therefore min_value is assigned.
        // This would be picked if you decided to not set-up store on day 1, which is never a good choice.
        // No profit on day 0, we start on day 1
        profits[0][0] = Integer.MIN_VALUE;
        profits[1][0] = Integer.MIN_VALUE;

        // The max profit gained for setting up that shop on day 1. (being at that shop on day 1)
        profits[0][1] = P[1];
        profits[1][1] = Q[1];

        for (int i = 2; i <= n; i++) {
            // For each day, for either shop, either:
            //  1)  stay at the same location: add profit of THAT location to Opt(i-1)
            //  2)  move to the other location: take Opt(i-1) of the OTHER location, no profit for day i!
                      // assuming we moved from that OTHER location to THIS

            // NOTE: on which day did we switch from Q to P?
            if (profits[1][i-1] > profits[0][i-1] + P[i]) {
                System.out.println("Switch from Q --> P on day " + i);
            }

            profits[0][i] = Integer.max(P[i] + profits[0][i - 1],     // stay at P, add profit
                                                    profits[1][i - 1]);  // switched from Q to P
            // NOTE: on which day did we switch from P to Q?
            if (profits[0][i-1] > profits[1][i-1] + Q[i]) {
                System.out.println("Switch from P --> Q on day " + i);
            }

            profits[1][i] = Integer.max(Q[i] + profits[1][i - 1],
                                                        profits[0][i - 1]);
        }

        // Note: max 0 (P) or 1 (Q) gives the shop we end with on day n <-- max profit attainable on day n
        return Integer.max(profits[0][n], profits[1][n]);

    }


    // OR use 2 separate arrays for P and Q, instead of the 2D array above
    public static int maxProfitTwoArrays(int n, int[] P, int[] Q) {

        int[] optP = new int[n + 1];
        int[] optQ = new int[n + 1];

        optP[0] = Integer.MIN_VALUE;
        optQ[0] = Integer.MIN_VALUE;
        optP[1] = P[1];
        optQ[1] = Q[1];

        for (int i = 2; i <= n; i++) {
            optP[i] = Math.max(optP[i-1] + P[i], optQ[i-1]);
            optQ[i] = Math.max(optQ[i-1] + Q[i], optP[i-1]);
        }
        return Math.max(optP[n], optQ[n]);
    }


}
