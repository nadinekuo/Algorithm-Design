import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import static org.junit.Assert.*;

public class HuffmanTest {



    @Test
    public void exampleEncoding() {
        Huffman.Node root = new Huffman.Node((char) 0, 1);
        Huffman.Node eNode = new Huffman.Node('e', 0.6, root);
        Huffman.Node xNode = new Huffman.Node('x', 0.4, root);
        root.setLeftChild(eNode);
        root.setRightChild(xNode);
        assertEquals("0", Huffman.encode(eNode));
        assertEquals("1", Huffman.encode(xNode));
    }

    /**
     * Tree looks like this:
     * root
     * combined         combined
     * e          x     f          g
     */
    @Test
    public void exampleEncoding2() {
        Huffman.Node root = new Huffman.Node((char) 0, 1);

        Huffman.Node leftRoot = new Huffman.Node((char) 0, 0.5, root);
        root.setLeftChild(leftRoot);
        Huffman.Node eNode = new Huffman.Node('e', 0.25, leftRoot);
        leftRoot.setLeftChild(eNode);
        Huffman.Node xNode = new Huffman.Node('x', 0.25, leftRoot);
        leftRoot.setRightChild(xNode);

        Huffman.Node rightRoot = new Huffman.Node((char) 0, 0.5, root);
        root.setRightChild(rightRoot);
        Huffman.Node fNode = new Huffman.Node('f', 0.25, rightRoot);
        rightRoot.setLeftChild(fNode);
        Huffman.Node gNode = new Huffman.Node('g', 0.25, rightRoot);
        rightRoot.setRightChild(gNode);

        Assertions.assertEquals("00", Huffman.encode(eNode));
        Assertions.assertEquals("01", Huffman.encode(xNode));
        Assertions.assertEquals("10", Huffman.encode(fNode));
        Assertions.assertEquals("11", Huffman.encode(gNode));
    }

    @Test
    public void exampleBuildTree() {
        int n = 2;
        char[] chars = { 0, 'a', 'b' };
        double[] freq = { 0, 0.7, 0.3 };
        Huffman.Node tree = Huffman.buildHuffman(n, chars, freq);  // root may contain any symbol like char (0)
        Assertions.assertNotNull(tree);
        Assertions.assertNotNull(tree.leftChild);
        Assertions.assertNotNull(tree.rightChild);
        if (tree.leftChild.symbol != 'a') {     // within 1 layer, it does not matter if char is left or right child
            Assertions.assertEquals('a', tree.rightChild.symbol);
            Assertions.assertEquals('b', tree.leftChild.symbol);
        } else {
            Assertions.assertEquals('a', tree.leftChild.symbol);
            Assertions.assertEquals('b', tree.rightChild.symbol);
        }
    }



}
