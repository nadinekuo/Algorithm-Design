import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Huffman {


    /**
     * Left = 0, right = 1
     *
     * @param node A Node in the Huffman encoding tree  <--- assume it does not contain meta char
     * @return the encoded binary string representing the character in this node.
     *
     *  Repeatedly query parent until we reach root.
     */
    public static String encode(Node node) {

        String result = "";
        while (node.parent != null) {
            if (node.parent.getLeftChild() == node) {
                result = "0" + result;   // reverse order, since we walk upwards
            } else {
                result = "1" + result;
            }
            node = node.parent;  // continue from parent!
        }
        return result;
    }


    /**  Tree will be complete! (2 children), but not necessarily balanced.
     *   Build bottum-up: recurse upwards by passing meta chars
     *
     * Minimize ABL = freq * node depth
     *
     * @param n           the number of characters that need to be encoded.
     * @param characters  The characters c_1 through c_n. Note you should use only characters[1] up to and including characters[n]!
     * @param frequencies The frequencies f_1 through f_n. Note you should use only frequencies[1] up to and including frequencies[n]!
     * @return The root node of an optimal Huffman tree that represents the encoding of the characters given.
     */
    public static Node buildHuffman(int n, char[] characters, double[] frequencies) {

        return bottumUpTreeBuildingPQ(n, characters, frequencies);
    }




    // Using PQ is efficient, as we constantly remove and add meta chars!
    // O(n log n)
    public static Node bottumUpTreeBuildingPQ(int n, char[] characters, double[] frequencies) {

        PriorityQueue<Node> nodesPQ = new PriorityQueue<>(new NodeComparator());
        for (int i = 1; i <= n; i++) {
            nodesPQ.add(new Node(characters[i], frequencies[i]));
        }

        while (nodesPQ.size() > 1) {
            Node smallestFreq = nodesPQ.poll();      // Find 2 lowest frequency chars x, y in current set
            Node secondSmallest = nodesPQ.poll();
            Node metaCharNode = new Node(smallestFreq, secondSmallest);   // see constructor added
            // Adds up frequencies
            // Sets random char 0
            // Adds x, y as children of leaf z (meta char), sets parent as null
            smallestFreq.parent = metaCharNode;
            secondSmallest.parent = metaCharNode;

            // "Recurse" on new set to get T: remove x, y, add meta char z   --> basically adding meta char to P
            nodesPQ.add(metaCharNode);
        }
        // Single meta char left in set (size was 1), will be root of final tree to return
        return nodesPQ.poll();


//        // PQ must contain > 2 nodes
//        while (true) {     // Find 2 lowest frequency chars x, y in current set
//
//            Node smallestFreq = nodesPQ.poll();
//
//            if (nodesPQ.isEmpty()) {    // BASE CASE
//                return smallestFreq;  // Single meta char left in set (size was 1), will be root of final tree to return
//            } else {
//                Node secondSmallest = nodesPQ.poll();
//                Node metaCharNode = new Node(smallestFreq, secondSmallest);   // see constructor added
//                // Adds up frequencies
//                // Sets random char 0
//                // Adds x, y as children of leaf z (meta char), sets parent as null
//                smallestFreq.parent = metaCharNode;
//                secondSmallest.parent = metaCharNode;
//
//                // "Recurse" on new set to get T: remove x, y, add meta char z   --> basically adding meta char to P
//                nodesPQ.add(metaCharNode);
//            }
//        }
    }


    // As long as top is not reached yet, we add meta chars, created by the 2 lowest frequencies polled
//        while (entries.size() > 1) {
//            entries.add(HuffmanEncoding.Node.metaCharNode(entries.poll(), entries.poll()));
//        }
//
//        HuffmanEncoding.Node root = entries.poll();   // Root of final Huffman tree




    static class NodeComparator implements Comparator<Node> {

        @Override
        public int compare(Node node, Node node2) {
            return Double.compare(node.frequency, node2.frequency);
        }
    }


    // Base case: |S| = 2   -->  return tree with root and 2 leaves, we reached the top
    // These 2 leaves may contain any subtree (built recursively bottum-up)
//        if (nodesPQ.size() == 2) {
//            Node root = new Node((char) 0, 1);  // total frequency is 1, random symbol
//            Node xNode = nodesPQ.poll();
//            Node yNode = nodesPQ.poll();
//            root.leftChild =  new Node(xNode.symbol, xNode.frequency);
//            root.rightChild = new Node(yNode.symbol, yNode.frequency);
//            return root;
//        }



    /**
     * NOTE: You should ensure that if a Node is a part of a tree, then all nodes in the tree have their `parent`, `leftChild`, and `rightChild` set appropriately!
     * You may add methods to this class, provided you do not change the names of existing methods or fields!
     */
    static class Node {

        char symbol;
        double frequency;
        Node parent;
        Node leftChild;   // 2*i + 1
        Node rightChild;  // 2*i + 2

        public Node(char symbol, double frequency) {
            this.symbol = symbol;
            this.frequency = frequency;
        }

        public Node(char symbol, double frequency, Node parent) {
            this(symbol, frequency);
            this.parent = parent;
        }

        public Node(char symbol, double frequency, Node leftChild, Node rightChild) {
            this(symbol, frequency);
            this.leftChild = leftChild;
            this.rightChild = rightChild;
        }

        public Node(Node child1, Node child2) {    // Added to create meta char node
            this.symbol = 0;
            this.frequency = child1.frequency + child2.frequency;
            this.leftChild = child2;
            this.rightChild = child1;
            this.parent = null;
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
    }

}
