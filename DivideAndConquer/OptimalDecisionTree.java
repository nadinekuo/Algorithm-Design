import java.util.ArrayList;
import java.util.List;

public class OptimalDecisionTree {



    /**
     * @param d - MAXIMAL NUMBER OF INTERNAL NODE LAYERS possible (constraint)
     * @param samples - All samples (array [ ] of feature values
     * @return - No. of correctly classified samples for the OPTIMAL DECISION TREE (minimal no. of mistakes)
     *   Classification of sample in leaves is based on MAJORITY VOTE of all samples that ended up in that leaf (final set)
     *
     *  To get the optimal decision tree, we must find the best feature to split on <-- gives most pure subsets
     *   - we can use the same feature multiple times
     *   - or not use any feature at all
     *
     *   EACH METHOD CALL corresponds to a root, for which we wanna find the best splitting value
     *
     *   Left path = 0, right path = 1
     *   Must be complete tree!!    ---->  2 children
     *
     *   But not necessarily balanced! We can stop splitting as soon as 1 sample left in subset.
     *   So we may have a leaf earlier than d levels reached
     *
     *   Pretty inefficient: you go over ALL features for each internal node  ---> O(2 ^ h+1) * O(numFeatures) ??
     *   In the end you check EACH possible combination of the whole tree.... numNodes^2  possible subsets
     *
     */
    public static int decisionTree(int d, List<Pair<Integer[], Integer>> samples) {

        //   if (samples.isEmpty()) return 0;   // nothing to classify, empty subset found...
        //  you can add this instead of keeping track of featIdx ( == -1 ), cause if no feature split the samples, the original list of samples will just be unmodified in the final recursive call

        // Base cases:
        //  d == 0: final layer (d levels reached)  <-- count correctly classified in final subset
        //  1 sample in (sub)set left <- pure subset obtained, so no further split needed! This will be the leaf. (even if d levels not reached) --> 1 correct
        if (d == 0 || samples.size() == 1) {     // must be correct if 1 sample (majority vote of itself)
            return leafNodeAmountCorrect(samples);
        }

        int numFeatures = samples.get(0).getL().length;
        int featIndex = -1;    // splitting feature for THIS root (of this method call)
        int maxCorrect = -1;   // corresponds to optimal decision tree

        // Go through every possible index/feature to split on and find the optimum split --> most correct
        // For THIS root
        // Each (recursive) method call corresponds to a root (like a recursion tree!)
        for (int f = 0; f < numFeatures; f++) {

            // Split the samples according to their assigned label
            List<Pair<Integer[], Integer>> subsetLeft0 = new ArrayList<>();
            List<Pair<Integer[], Integer>> subsetRight1 = new ArrayList<>();

            for (Pair<Integer[], Integer> s : samples) {  // For THIS feature f, split all samples on 0 (left) or 1 (right) values
                if (s.getL()[f].equals(0)) {
                    subsetLeft0.add(s);
                } else {
                    subsetRight1.add(s);
                }
            }

            // When you don't split the samples then you move to the next index/feature...
            // They all fall in the same subset, so no "information gain"! We want the samples to be split as much as possible to get pure subsets
            if (subsetLeft0.isEmpty() || subsetRight1.isEmpty())
                continue;

            // DIVIDE
            // Run decisionTree for the left and right side of the split: we go 1 layer down (so d--)
            int correctL = decisionTree(d - 1, subsetLeft0);    // For each layer, and for each internal node, we need to pick the best splitting feature
            int correctR = decisionTree(d - 1, subsetRight1);

            // CONQUER
            // COMBINE
            // When you get a better result (more optimal split feature), update your best result so far
            if (correctL + correctR > maxCorrect) {
                maxCorrect = correctL + correctR;
                featIndex = f;     // continuously update feature idx to split on in layer d
            }
        }

        // all features were skipped --> NOT ANY FEATURE splits the samples into 2 subsets ..... Thus for any tree, all samples would fall in 1 subset
        if (featIndex == -1)
            return leafNodeAmountCorrect(samples);   // Original samples are taken as final subset "in leaves", and we count the correctly classified ones

        return maxCorrect;   // corresponding to optimal tree found
    }




    // Counts no. of correctly assigned labels, assuming we assign each sample to MAJORITY VOTE (0, 1)
    //  Real label = Pair.R
    //  Assigned label = majority vote in samples[] <-- all samples eventually classified to this leaf label
    public static int leafNodeAmountCorrect(List<Pair<Integer[], Integer>> samples) {

        int count1s = 0;
        int count0s = 0;

        for (Pair<Integer[], Integer> sample : samples) {

            int realLabel = sample.getR();
            if (realLabel == 1) {
                count1s++;
            } else {
                count0s++;
            }
        }
        //int count0s = samples.size() - count1s;   // If not 1, it must be a 0

        int correct = Math.max(count1s, count0s);   // If majority is 0 --> all 0's are correct
        return correct;                               // If majority is 1 --> all 1's are correct
    }




    static class Pair<L, R> {

        private L l;   // Sample {f1, f2, ..., fn} containing all feature values

        private R r;   // Real label of sample

        /**
         * Constructor
         *
         * @param l left element
         * @param r right element
         */
        public Pair(L l, R r) {
            this.l = l;
            this.r = r;
        }

        /**
         * @return the left element
         */
        public L getL() {
            return l;
        }

        /**
         * @return the right element
         */
        public R getR() {
            return r;
        }

        /**
         * @param l left element
         */
        public void setL(L l) {
            this.l = l;
        }

        /**
         * @param r right element
         */
        public void setR(R r) {
            this.r = r;
        }
    }



}
