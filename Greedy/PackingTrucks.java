public class PackingTrucks {


    /**
     * @param n         the number of packages
     * @param weights   the weights of all packages 1 through n. Note that weights[0] should be ignored!
     * @param maxWeight the maximum weight a truck can carry
     * @return the minimal number of trucks required to ship the packages _in the given order_. !!!!!
     *
     *    WE DON'T SORT THE BOXES GIVEN, since we want to keep the initial order when shipping!
     */
    public static int minAmountOfTrucks(int n, int[] weights, int maxWeight) {

        int trucks = 0;
        int currentWeight = 0;
        if (n == 0) return 0;

        if (n > 0) {   // at least 1 box
            trucks++;    // first truck
            currentWeight = weights[1];  // Add weight of 1st box
        }

        // Or set currentWeight at Integer.MAX_VALUE and use absolute value of currentWeight
        for (int i = 2; i <= n; i++) {
            if (currentWeight + weights[i] > maxWeight) {   // new truck needed
                trucks++;
                currentWeight = weights[i];   // Start new truck at box i
            } else {
                currentWeight = currentWeight + weights[i];  // Current truck has capacity: we add the box
            }
        }
        return trucks;   // total number of trucks needed (minimum)
    }

}
