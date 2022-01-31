import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class MazeTest {


    @Test
    public void example() {
        int n = 7;
        MazePathFinding.Node[] nodesArr = new MazePathFinding.Node[n + 1];
        Set<MazePathFinding.Node> nodes = new HashSet<>();
        for (int i = 1; i <= n; i++) {
            nodesArr[i] = new MazePathFinding.Node();
            nodes.add(nodesArr[i]);
        }
        MazePathFinding.Node s = nodesArr[1];
        MazePathFinding.Node t = nodesArr[5];
        nodesArr[1].outgoingEdges.add(nodesArr[2]);
        nodesArr[2].outgoingEdges.add(nodesArr[3]);
        nodesArr[3].outgoingEdges.add(nodesArr[4]);
        nodesArr[4].outgoingEdges.add(nodesArr[5]);
        nodesArr[2].outgoingEdges.add(nodesArr[6]);
        nodesArr[6].outgoingEdges.add(nodesArr[7]);
        nodesArr[7].outgoingEdges.add(nodesArr[4]);
        assertTrue(MazePathFinding.solveBFSIterative(nodes, s, t));
    }





}
