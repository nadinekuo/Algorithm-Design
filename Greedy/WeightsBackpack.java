import java.util.Arrays;
import java.util.Collections;

public class WeightsBackpack {


    /**
     * Return the minimum number of items we need to get to the weight we want to get to.
     *
     * @param n      the number of item categories
     * @param w      the weight we want to achieve with as few items as possible.
     * @param num    the number of items in each category c_1 through c_n stored in num[1] through num[n] (NOTE: you should ignore num[0]!)
     * @param weight the weight of items in each category c_1 through c_n stored in weight[1] through weight[n] (NOTE: you should ignore weight[0]!)
     * @return minimum number of items needed to get to the required weight
     */
    public static int minNumOfWeightsNeededForLoop(int n, int w, int[] num, int[] weight) {

        // Insert all Category objects (1-indexed!)
        Category[] cats = new Category[n+1];
        for (int i = 1; i <= n; i++) {
            cats[i] = new Category(num[i], weight[i]);
        }
        Arrays.sort(cats, 1, n + 1);     // Sort by weight descending

        // Get heaviest weights first, but limited by num!
        int numNeeded = 0;
        int weightSoFar = 0;
        for (int j = 1; j <= n; j++) {
            int numOfTimesWeUseWeight = Math.min((w - weightSoFar) / cats[j].weight, cats[j].num);
            numNeeded += numOfTimesWeUseWeight;
            weightSoFar += numOfTimesWeUseWeight * cats[j].weight;

            if (weightSoFar >= w) break;   // w reached (but this can be left out, since the division above will give 0)
        }
        // Until <= W reached (do not exceed!)
        return numNeeded;

    }



    /**
     * Return the minimum number of items we need to get to the weight we want to get to.
     *
     * @param n      the number of item categories
     * @param w      the weight we want to achieve with as few items as possible.
     * @param num    the number of items in each category c_1 through c_n stored in num[1] through num[n] (NOTE: you should ignore num[0]!)
     * @param weight the weight of items in each category c_1 through c_n stored in weight[1] through weight[n] (NOTE: you should ignore weight[0]!)
     * @return minimum number of items needed to get to the required weight
     */
    public static int minNumOfWeightsNeededWhileLoop(int n, int w, int[] num, int[] weight) {

        if (w == 0) return 0;

        int numNeeded = 0;

        // create n Category objects
        Category[] categories = new Category[n+1];

        for (int i = 0; i <= n; i++) {     // starts at 0, so first entry is just Category(0, 0)
            categories[i] = new Category(num[i], weight[i]);
        }
        // If idx 0 would be null, we'd have to pass index ranges to avoid NullPointerException
        // Arrays.sort(categories, 1, categories.length);
        Arrays.sort(categories);    // descending! --> pick the most heavy first until nothing left, until w reached

        int j = 0;

        // We need to keep track of how much weight still needed, so decrease w!
        while (w >= 0 && j < n) {            // integer division rounds down: we buy all we can until w reached

            int numWeWouldLikeOfThisItem = Math.min(w / categories[j].weight, categories[j].num);  // take all we can buy, but limited by num (in stock)

            numNeeded += numWeWouldLikeOfThisItem;
            w -= numWeWouldLikeOfThisItem * categories[j].weight;

            j++;
        }

        System.out.println("Weight left: " + w);
        return numNeeded;
    }


    static class Category implements Comparable<Category> {

        int num;   // how many items we have
        int weight;  // weight of each item in this category

        public Category(int num, int weight) {
            this.num = num;
            this.weight = weight;
        }

        @Override
        public int compareTo(Category category) {
            return Integer.compare(category.weight, this.weight);  // descending order!
        }
    }



}
