import java.util.*;

public class FindMinCutBFS {


    // Perform DFS/BFS in residual graph (based on MAX FLOW!)
    //
    //	- All nodes reachable from s must be in the A side
    //	- So all nodes NOT reachable from s must be in B side!
    //	- The min cut is where the split is!

    public static void main(String[] args) {
        Set<Node> setA = minCutBFS(createGraph());

        // Check https://weblab.tudelft.nl/cse2310/2021-2022/assignment/92006/submission/38104/
        // Nodes 0 and 2 indeed belong to set A, since max flow = 7 = min cut capacity!
        for (Node n : setA) {
            System.out.println(n.getId());
        }

    }


    /**  Finds all vertices reachable from s in RESIDUAL graph using BFS  <-- set A
     *
     * @param g - graph to find min cut for (not max flow yet)
     * @return - all nodes in set A (contains source)
     */
    public static Set<Node> minCutBFS(Graph g) {

        System.out.println("Max flow value = " + MaxFlow.maximizeFlow(g));

        Queue<Node> q = new LinkedList<>();
        q.add(g.getSource());
        Set<Node> setA = new HashSet<>();
        setA.add(g.getSource());      // Note: includes source (which you should NOT do in project selection recovering e.g.!)

        while (!q.isEmpty()) {  // Visit unvisited neighbours, add to set A
            Node curr = q.poll();

            for (Edge e : curr.getEdges()) {
                Node neigh = e.to;

                // If loop or no residual flow, skip
                // Remember, all residual edges are added immediately when adding an edge, even tho value may be 0!
                if (neigh == curr || e.getResidual() == 0) continue;

                if (!setA.contains(neigh)) {   // OR add an extra check neigh != source
                    setA.add(neigh);            // Note that sink can never be reached, since max flow means no more augmenting s-t paths!
                    q.add(neigh);
                }
            }
        }
        return setA;
    }


    // Note: shorter version below combines all checks in 1 if, and returns node ids
    // Note: Example from Project Selection
    private static Set<Integer> bfsMinCut(Graph g) {

        Queue<Node> q = new LinkedList<>();
        q.add(g.getSource());
        Set<Integer> res = new HashSet<>();

        while (!q.isEmpty()) {
            Node cur = q.poll();
            for (Edge e : cur.getEdges()) {
                if (e.getFlow() < e.getCapacity()          // OR: e.getCapacity() > 0
                                    && !res.contains(e.to.getId())
                                             && e.to != g.getSource()) {  // Note: don't add source to set A, since its not a project
                    q.add(e.to);
                    res.add(e.to.getId());   // project reachable from s, so selected
                }
            }
        }
        return res;
    }



    // https://weblab.tudelft.nl/cse2310/2021-2022/assignment/92006/submission/38104/
//    public static Graph createGraph() {
//
//        List<Node> nodes = new ArrayList<>();
//
//        for (int i = 0; i <= 4; i++) {  // add inner nodes 1, 2, 3 and source, sink
//            nodes.add(new Node(i));
//        }
//        nodes.get(0).addEdge(nodes.get(2), 5);
//        nodes.get(0).addEdge(nodes.get(3), 3);
//        nodes.get(1).addEdge(nodes.get(4), 7);
//        nodes.get(2).addEdge(nodes.get(1), 2);
//        nodes.get(2).addEdge(nodes.get(4), 2);
//        nodes.get(3).addEdge(nodes.get(1), 5);
//
//        Graph g = new Graph(nodes, nodes.get(0), nodes.get(4));  // suorce = 0, sink = 4
//
//        return g;
//    }


    // 4 pixels (ids 1-4), source has id 0, sink has id 5
    public static Graph createGraph() {

        List<Node> nodes = new ArrayList<>();

        for (int i = 0; i <= 5; i++) {
            nodes.add(new Node(i));
        }
        nodes.get(0).addEdge(nodes.get(1), 5);
        nodes.get(0).addEdge(nodes.get(2), 4);
        nodes.get(0).addEdge(nodes.get(3), 3);
        nodes.get(0).addEdge(nodes.get(4), 7);

        nodes.get(1).addEdge(nodes.get(2), 10);
        nodes.get(2).addEdge(nodes.get(1), 10);
        nodes.get(1).addEdge(nodes.get(3), 10);
        nodes.get(3).addEdge(nodes.get(1), 10);
        nodes.get(2).addEdge(nodes.get(4), 10);
        nodes.get(4).addEdge(nodes.get(2), 10);
        nodes.get(4).addEdge(nodes.get(3), 10);
        nodes.get(3).addEdge(nodes.get(4), 10);

        nodes.get(1).addEdge(nodes.get(5), 5);
        nodes.get(2).addEdge(nodes.get(5), 7);
        nodes.get(3).addEdge(nodes.get(5), 6);
        nodes.get(4).addEdge(nodes.get(5), 3);

        Graph g = new Graph(nodes, nodes.get(0), nodes.get(5));  // suorce = 0, sink = 5
        return g;
    }

}
