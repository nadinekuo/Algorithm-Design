import java.util.Comparator;
import java.util.PriorityQueue;

public class TernaryHuffman {


    /**
     * You should implement this method.
     *
     * @param encrypted the encrypted message to decipher (a string of '0's, '1's, and '2's)
     * @param root      the root of the Ternary Huffman tree
     * @return the unencrypted message
     */
    public static String decode(String encrypted, Node root) {

        Node curr = root;
        String message = "";

        if (curr.getSymbol() != (char) 0) {  // single node in tree (root is leaf)
            return message + root.getSymbol();
        }

        // Go down the tree, taking the curr path (0, 1, 2) until leaf reached
        for (char c : encrypted.toCharArray()) {
            if (c == '0') {
                curr = curr.leftChild;
            } else if (c == '1') {
                curr = curr.middleChild;
            } else {
                curr = curr.rightChild;
            }
            // or: if (n.leftChild == null && n.middleChild == null && n.rightChild == null) {
            if (curr.getSymbol() != (char) 0) {  // leaf reached, add symbol in leaf to res
                message += curr.getSymbol();
                curr = root;  // start at root again, to decode the next prefix code
            }
        }
        return message;
    }

    /**
     * You should implement this method. Remember to look at the even/oddness of the number
     * characters!
     *
     * @param n           the number of characters that need to be encoded.
     * @param characters  The characters c_1 through c_n. Note you should use only characters[1] up
     *                    to and including characters[n]!
     * @param frequencies The frequencies f_1 through f_n. Note you should use only frequencies[1]
     *                    up to and including frequencies[n]!
     * @return The rootnode of an optimal Ternary Huffman tree that represents the encoding of the
     * characters given.
     */
    public static Node buildHuffman(int n, char[] characters, double[] frequencies) {

        if (n == 1) {  // single char forms root and leaf of tree
            return new Node(characters[1], 1);
        }


        PriorityQueue<Node> nodes = new PriorityQueue<>();

        // insert all chars and frequencies in PQ
        for (int i = 1; i <= n; i++) {
            nodes.add(new Node(characters[i], frequencies[i]));
        }
        if (n % 2 == 0) {    // If even no. of characters, add a node with (char) 0 and freq 0 (will be at bottom) to keep tree balanced
            nodes.add(new Node((char) 0, 0));
        }

        // If > 1 nodes in PQ, poll the 3 lowest frequency nodes
        while (nodes.size() > 1) {

            Node node1 = nodes.poll();
            Node node2 = nodes.poll();
            Node node3 = nodes.poll();

            // Add meta char (built from the 3 polled nodes), so size decreases by 2 (stays odd)  <-- size can never be 2
            double sumFrequencies = node1.frequency + node2.frequency + node3.frequency;
            Node meta = new Node((char) 0, sumFrequencies, node1, node2, node3);
            node1.parent = meta;  // Set pointers to parents
            node2.parent = meta;
            node3.parent = meta;
            nodes.add(meta);      // Add meta char to PQ
        }
        // If 1 node left, return that meta char as root
        Node root = nodes.poll();
        return root;
    }


//        PriorityQueue<Node> nodes = new PriorityQueue<>(new NodeComparator());
//        for (int i = 1; i <= n; i++) {
//            nodes.add(new Node(characters[i], frequencies[i]));
//        }
//        if (n % 2 == 0) {
//            nodes.add(new Node((char) 0, 0));
//        }
//        while (true) {
//            Node smallestFrequency = nodes.poll();
//            if (nodes.isEmpty()) {
//                return smallestFrequency;
//            } else {
//                Node secondSmallest = nodes.poll();
//                Node thirdSmallest = nodes.poll();
//                Node newNode = new Node(smallestFrequency, secondSmallest, thirdSmallest);
//                thirdSmallest.setParent(newNode);
//                smallestFrequency.setParent(newNode);
//                secondSmallest.setParent(newNode);
//                nodes.offer(newNode);
//            }
//        }
//   }




    static class NodeComparator implements Comparator<Node> {

        @Override
        public int compare(Node node, Node node2) {
            return Double.compare(node.frequency, node2.frequency);
        }
    }

    // no need to implement Comparable if NodeComparator used.
    static class Node implements Comparable<Node> {

        char symbol;
        double frequency;

        Node parent;

        Node leftChild;
        Node rightChild;
        Node middleChild;

        public Node(char symbol, double frequency) {
            this.symbol = symbol;
            this.frequency = frequency;
        }

        public Node(char symbol, double frequency, Node parent) {
            this(symbol, frequency);
            this.parent = parent;
        }

        public Node(char symbol, double frequency, Node leftChild, Node middleChild, Node rightChild) {
            this(symbol, frequency);
            this.leftChild = leftChild;
            this.middleChild = middleChild;
            this.rightChild = rightChild;
        }

        public char getSymbol() {
            return symbol;
        }

        public double getFrequency() {
            return frequency;
        }

        public Node getParent() {
            return parent;
        }

        public void setParent(Node parent) {
            this.parent = parent;
        }

        public Node getLeftChild() {
            return leftChild;
        }

        public void setLeftChild(Node leftChild) {
            this.leftChild = leftChild;
        }

        public Node getRightChild() {
            return rightChild;
        }

        public void setRightChild(Node rightChild) {
            this.rightChild = rightChild;
        }

        public Node getMiddleChild() {
            return middleChild;
        }

        public void setMiddleChild(Node middleChild) {
            this.middleChild = middleChild;
        }

        @Override
        public int compareTo(Node o) {
            return Double.compare(this.frequency, o.frequency);  // increasing freq
        }
    }

}
