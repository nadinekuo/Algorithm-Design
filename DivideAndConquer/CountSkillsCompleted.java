public class CountSkillsCompleted {

    /**
     * You should implement this method in O(log n)!!
     *
     * @param n      the number of elements in skills.
     * @param skills the sorted array of `Skill`s (see Library for their implementation) to look through.
     *               Note that you should use entries skills[1] to skills[n]!
     * @return the number of completed skills in the sorted array = t <-- idx of first skill not completed
     */
    public static int numberOfCompletedSkills(int n, Skill[] skills) {

        if (n == 0) return 0;
        return numSkillsCompletedHelper(skills, 1, n);
    }

    public static int numSkillsCompletedHelper(Skill[] skills, int low, int high) {

        // Base case: if n == 1: return 1 if skill completed
        if (low == high) {
            if (skills[low].isCompleted()) {
                return 1;
            } else {
                return 0;
            }
        }

        if (high - low == 1) {
            if (skills[low].isCompleted())  {
                return 2;
            } else if (skills[high].isCompleted()) {
                return 1;
            } else {
                return 0;
            }
        }

        // DIVIDE: split skills list into 2
        int mid = (low + high) / 2;

        // CONQUER: recurse on EITHER left or right list
        if (!skills[mid].isCompleted()) {   // count completed skills on right (must be right side)
            return numSkillsCompletedHelper(skills, mid + 1, high);
        } else {     // If mid is completed, recurse on left: count completed skills and ADD to right size
            return numSkillsCompletedHelper(skills, low, mid - 1) + high - mid + 1;
        }
        // COMBINE: All skills after mid must be completed too!
    }




    // This seems like O(n), since there are 2 recursive calls
    // However, it is guaranteed that at least one of these recursive calls hits a base case, meaning it finishes immediately in O(1)

    /**  Similar to binary search
     * @param numbers - skills to check
     * @param left - lower idx
     * @param right - upper idx
     * @return - how many completed (right side)
     */
    public static int solveBinaryCall(Skill[] numbers, int left, int right) {

        // BASE CASES
        if (!numbers[right].isCompleted()) {    // nothing is completed
            return 0;
        }
        if (numbers[left].isCompleted()) {     // all skills completed
            return right - left + 1;
        }

        // DIVIDE
        int mid = (left + right) / 2;

        // CONQUER: recurse on left and right
        return solveBinaryCall(numbers, left, mid) + solveBinaryCall(numbers, mid + 1, right);
    }




    // Brute force: O(n)
    public static int solveSlow(Skill[] numbers) {
        int sum = 0;
        for (int i = 1; i < numbers.length; i++) {
            sum += numbers[i].isCompleted() ? 1 : 0;
        }
        return sum;
    }


    static class Skill {

        private String name;

        private boolean completed;

        public Skill(String name, boolean completed) {
            this.name = name;
            this.completed = completed;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isCompleted() {
            return completed;
        }

        public void setCompleted(boolean completed) {
            this.completed = completed;
        }
    }

}
