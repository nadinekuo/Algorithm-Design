import java.util.*;

public class CycleDetectionDFS {


    // A DIRECTED GRAPH HAS A CYCLE IFF DFS FINDS BACK EDGE

    static class Node {

        List<Node> outgoingEdges;
        int id;
        boolean marked;

        public Node(int id) {
            this.outgoingEdges = new ArrayList<>();
            this.marked = false;
            this.id = id;
        }

        public String toString() {
            return Integer.toString(id);
        }

        @Override
        public int hashCode() {
            return id;
        }
    }

    static class Edge {

        int from, to;

        public Edge(int from, int to) {
            this.from = from;
            this.to = to;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            Edge edge = (Edge) o;
            return from == edge.from && to == edge.to;
        }

        @Override
        public int hashCode() {
            return Objects.hash(from, to);
        }
    }


    // ITERATIVE APPROACH IS PREFERRED OVER RECURSION!  ---> DFS using stack!


    /**       O(n + m) time!!!!
     *
     * @param n the number of nodes
     * @param m the number of edges
     * @param edges the set of edges, with endpoints labelled between 1 and n inclusive.
     * @param s the starting point
     * @return true iff there is a cycle REACHABLE from s (so s itself does not necessarily have to be in the cycle!)
     */
    public static boolean isThereACycle(int n, int m, Set<Edge> edges, int s) {

        if (edges == null) return false;

        Map<Integer, Node> nodes = new HashMap<>();
                           // key: node id, value: status -1, 0 or 1
        Map<Integer, Integer> traversalStatus = new HashMap<>(); // To keep track of unvisited/visited/explored nodes
        // -1 = unvisited (initial state of all nodes)
        // 0 = being visited
        // 1 = explored, no further neighbours to be added

        for (int i = 1; i <= n; i++) {   // ids 1, 2, 3, ....., n
            nodes.put(i, new Node(i));
            traversalStatus.put(i, 0);   // initial status of all nodes: unvisited
        }
        for (Edge e : edges) {
            nodes.get(e.from).outgoingEdges.add(nodes.get(e.to));  // add all outgoing edges to each node
        }

        //return dfsFindCycleStackColors(nodes.get(s), traversalStatus);


        Set<Node> visited = new HashSet<>();
        return dfsFindCycleRecursive(nodes.get(s), visited);   // Call helper on start node

//        visited.add(nodes.get(s));         // Add start node
//        for (Node neighbour : nodes.get(s).outgoingEdges) {
//            if (dfsCycleUsingPrev(nodes.get(s), neighbour, visited)) return true;   // pass start as prev, neighbour as curr
//            visited.remove(nodes.get(s));           // remove vertex again for next search! (multiple components)
//        }
//        return false;
    }




    // ----------------------------------- NON-RECURSIVE DFS USING STACK ----------------------------------------------


    // https://stackoverflow.com/questions/46506077/how-to-detect-cycles-in-a-directed-graph-using-the-iterative-version-of-dfs
    // https://www.geeksforgeeks.org/detect-cycle-direct-graph-using-colors/

    // 0 = unvisited (initial state of all nodes)
    // -1 = being visited
    // 1 = explored, no further neighbours to be added
    public static boolean dfsFindCycleStackColors(Node node, Map<Integer, Integer> statuses) {

        Stack<Node> stack = new Stack<>();
        stack.push(node);   // push starting vertex

        while(!stack.isEmpty()) {
            Node curr = stack.peek();    // Don't pop yet!

            if (statuses.get(curr.id) == 0) {  // if unvisited, we explore its neighbours
                statuses.put(curr.id, -1);   // change status to being visited

                // Go over all neighbours
                for (Node neigh : curr.outgoingEdges) {
                    if (statuses.get(neigh.id) == -1) return true;   // if also being visited (-1), we found a back edge! --> cycle
                    if (statuses.get(neigh.id) == 0) {    // if unvisited (0), push to stack
                        stack.push(neigh);
                    }
                    // 1 is not possible, since we immediately pop after having marked explored (1) (see below)
                }
            } else if (statuses.get(curr.id) == -1 || statuses.get(curr.id) == 1) {
                statuses.put(curr.id, 1);     // This node did not add any neighbours, as its still on the top of the stack! (previous iteration set it from 0 to -1)
                stack.pop();                 // So mark as "explored" and pop
            }
        }
        return false;
    }



//    public static boolean dfsFindCycleStack(Node node, Set<Node> visited) {
//

//        Stack<Node> stack = new Stack<>();
//        stack.push(node);
//        LinkedList<Node> path = new LinkedList<>();
//        while (!stack.isEmpty()) {
//            Node curr = stack.pop();
//            if (visited.contains(curr)) {
//                return true;
//            }
//            visited.add(curr);
//            path.add(curr);
//            boolean flag = false;
//            for (Node n : curr.outgoingEdges) {
//                if (!stack.contains(n)) {
//                    stack.push(n);
//                    flag = true;
//                }
//            }
//            if (!flag && !stack.isEmpty()) {
//                Node next = stack.peek();
//                while (!path.getLast().outgoingEdges.contains(next)) {
//                    visited.remove(path.getLast());
//                    path.removeLast();
//                }
//            }
//        }
//        return false;
//    }


    // -------------------------------------- RECURSIVE DFS ----------------------------------

    public static boolean dfsFindCycleRecursive(Node node, Set<Node> visited) {
        if (visited.contains(node)) {   // Base case: visited before --> cycle!
            return true;
        }
        visited.add(node);
        for (Node n : node.outgoingEdges) {
            if (dfsFindCycleRecursive(n, visited)) {
                return true;
            }
        }
        // TODO: Isn't this only necessary if we do searches on multiple components?
        visited.remove(node);    // If multiple components, we wanna start a new search!
        return false;
    }



// TODO: WHY DO I GET 9/13 ON THIS VERSION? Even if I remove the condition, nothing changes ?

//    public static boolean dfsCycleUsingPrev(Node prev, Node curr, Set<Node> visited) {
//
//        visited.add(curr);
//
//        for (Node neigh : prev.outgoingEdges) {
//            if (!visited.contains(neigh)) {    // Neighbour not visited yet: check for cycle recursively
//                if (dfsCycleUsingPrev(curr, neigh, visited)) return true;
//            } else if (neigh != prev) {      // Neighbour visited already: check if cycle has > 2 vertices! Oly 2 vertices is not a cycle by definition.
//                return true;
//            }
//        }
//        return false;
//    }


}
