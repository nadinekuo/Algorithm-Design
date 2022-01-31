public class PrintSubsetSumsKnapsack {

    public static class Item {

        private final int value;
        private final int weight;

        public Item(int value, int weight) {
            this.value = value;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return "Item{" +
                    "v=" + value +
                    ", w=" + weight +
                    '}';
        }
    }

    public static void main(String[] args) {
        int maxKnapsackWeight = 11;   // W
        Item[] items = new Item[] {
                new Item(1, 1),
                new Item(6, 2),
                new Item(18,5),
                new Item(22, 6),
                new Item(28, 7)
        };

        // Rows (items i) are 0-indexed
        // Columns (weights w) are 1-indexed!
        int[][] mem = new int[items.length][];   // n x W
        System.out.println("Optimal Knapsack Value = " + optimalKnapsackFill(items, maxKnapsackWeight, mem));
        System.out.println();
        System.out.println("Items:");
        printOptimalItems(items, items.length - 1, maxKnapsackWeight, mem);
    }




    // Recursively retrieve items added
    public static void printOptimalItems(Item[] items, int i, int w, int[][] mem) {
        if (i <= 0 || w <= 0) return;

        if (mem[i][w] == mem[i - 1][w]) {
            printOptimalItems(items, i - 1, w, mem);
        } else {                                            // we took item i
            Item item = items[i];
            printOptimalItems(items, i - 1, w - item.weight, mem);
            System.out.println("i=" + i + " " + item);    // recursion gives ascending order!
        }
    }

    public static int optimalKnapsackFill(Item[] items, int W, int[][] mem) {

        for (int i = 0; i < items.length; i++) {
            mem[i] = new int[W + 1];   // Add all rows (max weights for each item i)
        }

        for (int i = 0; i < items.length; i++) {  // For each item i
            Item item = items[i];

            for (int w = 0; w <= W; w++) {    // for each max weight w
                int pv = i > 0 ? mem[i - 1][w] : 0;    // Opt(i-1, w), takes into account base case
                if (w < item.weight) {       // wi > w
                    mem[i][w] = pv;          // take Opt(i-1, w)
                } else {
                    int rv = i > 0 ? mem[i - 1][w - item.weight] : 0;  // Opt(i-1, w - w[i])
                    mem[i][w] = Math.max(pv, item.value + rv);
                }
            }
        }
        return mem[items.length - 1][W];
    }



}
