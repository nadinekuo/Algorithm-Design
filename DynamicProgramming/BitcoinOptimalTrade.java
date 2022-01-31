public class BitcoinOptimalTrade {


    public static void main(String[] args) {
        int t = 3;
        double[] r = { 0.0, 500, 100, 10000 };
        System.out.println(optimalTrade(t, r ));
    }



    /**  R(t) is how many euros/bitcoin, so the higher, the better time to trade.
     *
     *   B -> E : 0.95 * B * R(t) euros received
     *   E -> B: (E - 5) / R(t) bitcoins received
     *
     * @param t - the number of days you have to trade to euros
     * @param r - exchange rates of each day. Ignore index 0. I.e. the exchange rate of the first day can be found using r[1].
     *          The higher, the better time to trade B --> E  using B(t-1)
     *          The lower, the better time to buy B using E(t), so we can exchange it later for E.
     *
     * @return the maximum amount of euros after T days
     */
    public static double optimalTrade(int t, double[] r) {

        // 2 different memoization tables: they depend on each other!
        double[] optimalEuros = new double[t+1];
        double[] optimalBitcoins = new double[t+1];
        optimalBitcoins[0] = 0.1;       // Base case: On t = 0, we have 0.1 bitcoins
        optimalEuros[0] = 0;           // on day 0, we could not trade yet (day 1 is start)

        // Iteratively fill both memoization tables
        for (int i = 1; i <= t; i++) {
            optimalEuros[i] = Math.max(optimalEuros[i-1], optimalBitcoins[i-1] * r[i] * 0.95);  // either we keep euros, or exchange bitcoins of the PREV day
            optimalBitcoins[i]= Math.max(optimalBitcoins[i-1], (optimalEuros[i-1] - 5) / r[i]);  // either we keep bitcoins, or buy bitcoins using euros on PREV day
        }
        return optimalEuros[t];  // highest value is stored in final day of euro mem table
    }


}
