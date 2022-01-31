import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

public class TernaryHuffmanTest {

    @Test
    public void exampleDecoding() {
        TernaryHuffman.Node root = new TernaryHuffman.Node((char) 0, 1);
        TernaryHuffman.Node eNode = new TernaryHuffman.Node('e', 0.6, root);
        TernaryHuffman.Node xNode = new TernaryHuffman.Node('x', 0.1, root);
        TernaryHuffman.Node yNode = new TernaryHuffman.Node('y', 0.3, root);
        root.setLeftChild(eNode);
        root.setRightChild(xNode);
        root.setMiddleChild(yNode);
        Assertions.assertEquals("xyexy", TernaryHuffman.decode("21021", root));
    }

    @Test
    public void exampleDecoding2() {
        TernaryHuffman.Node root = new TernaryHuffman.Node((char) 0, 1);
        TernaryHuffman.Node eNode = new TernaryHuffman.Node('e', 0.6, root);
        TernaryHuffman.Node xNode = new TernaryHuffman.Node('x', 0.1, root);
        TernaryHuffman.Node yNode = new TernaryHuffman.Node('y', 0.3, root);
        root.setLeftChild(eNode);
        root.setRightChild(xNode);
        root.setMiddleChild(yNode);
        Assertions.assertEquals("y", TernaryHuffman.decode("1", root));
    }

    /** Tree looks like this: root combined k combined e 0 x f 0 g */
    @Test
    public void exampleEncoding2() {
        TernaryHuffman.Node root = new TernaryHuffman.Node((char) 0, 1);
        TernaryHuffman.Node leftRoot = new TernaryHuffman.Node((char) 0, 0.4, root);
        root.setLeftChild(leftRoot);
        TernaryHuffman.Node eNode = new TernaryHuffman.Node('e', 0.2, leftRoot);
        leftRoot.setLeftChild(eNode);
        TernaryHuffman.Node xNode = new TernaryHuffman.Node('x', 0.2, leftRoot);
        leftRoot.setRightChild(xNode);
        TernaryHuffman.Node nullNode = new TernaryHuffman.Node((char) 0, 0, leftRoot);
        leftRoot.setMiddleChild(nullNode);
        TernaryHuffman.Node middleRoot = new TernaryHuffman.Node('k', 0.2, root);
        root.setMiddleChild(middleRoot);
        TernaryHuffman.Node rightRoot = new TernaryHuffman.Node((char) 0, 0.4, root);
        root.setRightChild(rightRoot);
        TernaryHuffman.Node fNode = new TernaryHuffman.Node('f', 0.2, rightRoot);
        rightRoot.setLeftChild(fNode);
        TernaryHuffman.Node gNode = new TernaryHuffman.Node('g', 0.2, rightRoot);
        rightRoot.setRightChild(gNode);
        nullNode = new TernaryHuffman.Node((char) 0, 0, rightRoot);
        rightRoot.setMiddleChild(nullNode);
        Assertions.assertEquals("exkkfg", TernaryHuffman.decode("0002112022", root));
    }

    @Test
    public void exampleBuildTree() {
        int n = 3;
        char[] chars = {0, 'a', 'b', 'c'};
        double[] freq = {0, 0.5, 0.3, 0.2};
        TernaryHuffman.Node tree = TernaryHuffman.buildHuffman(n, chars, freq);
//        Assertions.assertNotNull(tree);
//        Assertions.assertNotNull(tree.leftChild);
//        Assertions.assertNotNull(tree.rightChild);
//        Assertions.assertNotNull(tree.middleChild);
        Set<Character> symbolsUsed = new HashSet<>();
        symbolsUsed.add(tree.leftChild.symbol);
        symbolsUsed.add(tree.rightChild.symbol);
        symbolsUsed.add(tree.middleChild.symbol);
        Assertions.assertEquals(3, symbolsUsed.size());
        Assertions.assertTrue(symbolsUsed.contains('a'));
        Assertions.assertTrue(symbolsUsed.contains('b'));
        Assertions.assertTrue(symbolsUsed.contains('c'));
    }

}
