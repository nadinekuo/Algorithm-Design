import java.util.*;

public class MazePathFinding {


    public static class Node {

        List<Node> outgoingEdges;   // Adjacency list graph

        boolean marked;

        public Node() {
            this.outgoingEdges = new ArrayList<>();   // Adjacency list
            this.marked = false;
        }
    }


    // ----------------------------------------- DFS path finding: recursive .... WE PREFER ITERATIVE! --> BFS --------------------------------------------

    /**  IN O(n+m) TIME!!!!  <--- both DFS and BFS are O(n+m) for adjacency list/map
     *
     * @param nodes the nodes in the graph
     * @param s the starting node
     * @param t the final node
     * @return true iff there is a path from the start node to the final node
     */
    public static boolean solveDFS(Set<Node> nodes, Node s, Node t) {

        //if (nodes == null || !nodes.contains(s)) return false;

        // DFS to find a path between start and exit

        s.marked = true;  // We mark start node as visited   (we could also create a helper and pass a set<Node> known)

        if (s == t) return true;  // base case: path found

        // We visit each vertex node ----> O(n)
        // Sum of O(outDegree) ------>  O(m)
        // in total ------>   O(n+m)
        for (Node neighbour : s.outgoingEdges) {
            if (!neighbour.marked) {  // only visit neighbour if not visited yet!
                if (solveDFS(nodes, neighbour, t)) return true;   // Recursively continue finding path from neighbour to exit (will be marked visited in next recursive call)
            }
        }

        return false;
    }



    // -----------------------------------------------   BFS path finding: iterative!!  --------------------------------------------------


    // Implement the butWhoCanWinHowMany method to return the answer to the problem posed by the inputstream.

    public static boolean solveBFSIterative(Set<Node> nodes, Node s, Node t) {
        /* For BFS we use a queue to ensure the FIFO ordering */
        Queue<Node> q = new LinkedList<>();
        /* The first node is visited immediately */
        s.marked = true;
        q.add(s);
        /* Continue until there is nothing else to explore */
        while (!q.isEmpty()) {
            Node node = q.remove();
            if (node == t) {
                /* We found the node, so it must be reachable */
                return true;
            }
            for (Node to : node.outgoingEdges) {
                /* Add all the not yet marked nodes to the queue so that we explore them eventually */
                if (!to.marked) {
                    to.marked = true;
                    q.add(to);
                }
            }
        }
        /* We were unable to find it. */
        return false;
    }



}
