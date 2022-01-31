import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class HuffmanEncoding {

    public static void main(String[] args) {
        String str = "katy perry is cool";
        char[] chars = str.toCharArray();
        double[] frequences = HuffmanEncoding.determineFrequencies(chars);
        HuffmanEncoding huffman = HuffmanEncoding.create(chars, frequences, '0', '1');
        System.out.println(huffman);

        String encoded = huffman.encode(str);

        DecimalFormat df = new DecimalFormat("#.#");
        int n = encoded.length();
        int o = 32 * chars.length;
        double diff = (n - o) / (double) o * 100;
        String decoded = huffman.decode(encoded);
        System.out.println(str + " -> " + encoded + " (" + df.format(diff) + "%)");
        System.out.println(encoded + " -> " + decoded);
    }

    private final Node root;
    private final Map<Character, Node> characterMap;
    private final char a;
    private final char b;

    private HuffmanEncoding(Node root, Map<Character, Node> characterMap, char a, char b) {
        this.root = root;
        this.characterMap = characterMap;
        this.a = a;   // left code symbol: 0
        this.b = b;   // right code symbol: 1
    }

    public static double[] determineFrequencies(char[] chars) {
        double[] frequencies = new double[chars.length];   // for each char, we store a frequency

        Map<Character, Double> cache = new HashMap<>();  // char, frequency
        for (int i = 0; i < chars.length; i++) {
            frequencies[i] = cache.computeIfAbsent(chars[i], key -> {
                int count = 0;
                for (char c : chars) {
                    if (key == c) count += 1;
                }
                return (double) count / chars.length;   // frequency percentage (relative to all chars)
            });
        }
        return frequencies;
    }


    public static HuffmanEncoding create(char[] chars, double[] frequencies, char a, char b) {
        PriorityQueue<Node> entries = new PriorityQueue<>();   // Poll lowest frequencies first
        Map<Character, Node> characterMap = new HashMap<>();
        for (int i = 0; i < chars.length; i++) {
            Node node = new Node(chars[i], frequencies[i]);
            entries.add(node);
            characterMap.put(node.value, node);
        }

        // As long as top is not reached yet, we add meta chars, created by the 2 lowest frequencies polled
        while (entries.size() > 1) {
            entries.add(Node.metaCharNode(entries.poll(), entries.poll()));
        }

        Node root = entries.poll();   // Root of final Huffman tree

        return new HuffmanEncoding(root, characterMap, a, b);
    }


    // Encodes a whole string --> converted to char array
    public String encode(String str) {
        StringBuilder sb = new StringBuilder();
        for (char c : str.toCharArray()) {
            Node node = characterMap.get(c);
            if (node == null) throw new IllegalArgumentException("Unsupported character '" + c + "'.");
            sb.append(encode(node));  // encode each char separately
        }
        return sb.toString();
    }

    // Encodes 1 char (leaf node)
    private String encode(Node node) {
        StringBuilder sb = new StringBuilder();
        Node current = node;
        while (current.hasParent()) {
            Node parent = current.parent;
            if (parent.left == current) {
                sb.append(a);
            } else {
                sb.append(b);
            }
            current = parent;
        }
        return sb.reverse().toString();  // We walked upwards, so reverse!
    }


    // Walk tree downwards to get to the final leaf that contains the char to be decoded
    // For each char in string!
    public String decode(String encoded) {
        StringBuilder sb = new StringBuilder();
        Node current = root;
        for (char c : encoded.toCharArray()) {
            if (c == a) {   // left code symbol: 0
                current = current.left;
            } else if (c == b) {  // right code symbol: 1
                current = current.right;
            } else {
                throw new IllegalArgumentException("Input string is not a huffman code.");
            }

            if (current.isLeaf()) {  // leaf contains char
                sb.append(current.value);
                current = root;   // Start at root again for next char to be decoded!
            }
        }
        return sb.toString();
    }


    @Override
    public String toString() {
        return root.toString(0, 2);
    }

    public static class Node implements Comparable<Node> {

        private final char value;
        private final double frequency;
        private Node parent;
        private final Node left;
        private final Node right;

        protected Node(char value, double frequency) {
            this(value, frequency, null, null);
        }

        public Node(char value, double frequency, Node left, Node right) {
            this.value = value;
            this.frequency = frequency;
            this.left = left;
            this.right = right;
        }

        public static Node metaCharNode(Node n1, Node n2) {
            Node parent = new Node((char) 0, n1.frequency + n2.frequency, n1, n2);
            n1.parent = parent;
            n2.parent = parent;
            return parent;
        }

        public char getValue() {
            return value;
        }

        public double getFrequency() {
            return frequency;
        }

        public boolean hasParent() {
            return parent != null;
        }

        public Node getParent() {
            return parent;
        }

        public Node getLeft() {
            return left;
        }

        public Node getRight() {
            return right;
        }

        public boolean isLeaf() {
            return left == null && right == null;
        }

        @Override
        public int compareTo(Node o) {
            return Double.compare(frequency, o.frequency);
        }

        public String toString(int level, int offset) {
            StringBuilder sb = new StringBuilder(" ".repeat(level * offset)).append(value == 0 ? "null" : ("'" + value + "'"));
            if (!isLeaf()) {
                sb
                        .append(System.lineSeparator())
                        .append("L (").append(level).append("): ").append(left.toString(level + 1, offset))
                        .append(System.lineSeparator())
                        .append("R (").append(level).append("): ").append(right.toString(level + 1, offset));
            }
            return sb.toString();
        }
    }
}