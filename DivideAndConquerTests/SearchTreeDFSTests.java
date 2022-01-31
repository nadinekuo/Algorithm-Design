import org.junit.Test;
import static org.junit.Assert.*;


public class SearchTreeDFSTests {



    @Test
    public void example() {
        SearchTreeDFS s = new SearchTreeDFS();
        SearchTreeDFS.BinaryTree tree = new SearchTreeDFS.BinaryTree(42, new SearchTreeDFS.BinaryTree(1337), new SearchTreeDFS.BinaryTree(39));
        assertTrue(s.search(tree, 42));
        assertFalse(s.search(tree, 100));
    }



}
