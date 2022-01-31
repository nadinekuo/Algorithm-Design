import java.io.*;
import java.util.*;


/**
 * WARNING: The spec tests are not equal to your grade!
 * You can use them help you test for the correctness of your algorithm,
 * but the final grade is determined solely by a manual inspection of your implementation.
 */
class RecoverProjectsSelected {

    /**
     *  Return all projects in cut partition A!
     *
     * @param n            The number of projects
     * @param benefits     An array of dimension n+1 containing the benefits of all the projects for 1 <= i <= n
     * @param costs        An array of dimension n+1 containing the costs of all the projects for 1 <= i <= n
     * @param dependencies is an array of dimension (n+1)*(n+1) that contains value 1 iff project i depends on j and 0 otherwise,
     *                         for all 1 <= i,j <= n.
     * @return the set of project ids that are selected.
     *              Note that the ids of `Node`s in the graph correspond to the ids of the projects they represent.
     */
    public static Set<Integer> outputSelection(int n, int[] benefits, int[] costs, int[][] dependencies) {

        Graph g = buildGraph(n, benefits, costs, dependencies);
        MaxFlow.maximizeFlow(g);

        //return bfs(g);

        // BFS: find all nodes still reachable from s after MAX FLOW reached! (in "residual graph")
        Queue<Node> q = new LinkedList<>();
        Set<Integer> selectedProjects = new HashSet<>();    // Note: serves as "visited" set

        // Note: Add all source neighbour nodes to queue (source itself is not a project)
        for (Edge e : g.getSource().getEdges()) {
            if (e.getResidual() > 0) {
                q.add(e.to);
            }
        }

        while(!q.isEmpty()) {            // Poll node/project, mark "visited" by adding to result
            Node curr = q.poll();
            selectedProjects.add(curr.getId());

            // Note: iff max flow there CANNOT be any augmenting s-t paths anymore! So sink can never be reached
            for (Edge e : curr.getEdges()) {
                if (!selectedProjects.contains(e.to.getId())                  // Add unvisited neighbours
                                    && e.getResidual() > 0                    // if augmenting path, node is reachable!
                                                && e.to != g.getSource()) {   // Note: source may be neighbour of some node!!
                    q.add(e.to);
                }
            }
        }
        System.out.println("Selected projects: " + selectedProjects.toString());
        return selectedProjects;

    }



    // Note: shorter version below combines all checks in 1 if, and returns node ids
    // Example from Project Selection
    private static Set<Integer> bfsMinCut(Graph g) {

        Queue<Node> q = new LinkedList<>();
        q.add(g.getSource());                   // Note: adds source to queue, above we added source neighbours!
        Set<Integer> res = new HashSet<>();

        while (!q.isEmpty()) {
            Node cur = q.poll();
            for (Edge e : cur.getEdges()) {
                if (e.getFlow() < e.getCapacity()
                                    && !res.contains(e.to.getId())
                                               && e.to != g.getSource()) {  // don't add source to set A (has id < 0)
                    q.add(e.to);
                    res.add(e.to.getId());
                }
            }
        }
        return res;
    }




    /**
     * @return - network created for Project Selection problem, no max flow yet!
     */
    private static Graph buildGraph(int n, int[] benefits, int[] costs, int[][] dependencies) {
        Node source = new Node(0);
        Node sink = new Node(n + 1);
        ArrayList<Node> nodes = new ArrayList<>(n + 2);   // idx corresponds to id
        nodes.add(source);
        for (int i = 1; i <= n; i++) {   // all n projects
            Node newNode = new Node(i);
            nodes.add(newNode);
        }
        nodes.add(sink);

        // The projects can have both profit and cost!! So we connect all projects to both s and t
        // You can also check net profit = revenue - cost, to reduce #edges
        for (int i = 1; i <= n; i++) {
            source.addEdge(nodes.get(i), benefits[i]);
            nodes.get(i).addEdge(sink, costs[i]);

            for (int j = 1; j <= n; j++) {
                if (dependencies[i][j] > 0) {    // edge(i, j) means i requires j
                    nodes.get(i).addEdge(nodes.get(j), Integer.MAX_VALUE / 2);  // prevents selection of i WITHOUT prereq j!
                }
            }
        }
        return new Graph(nodes, source, sink);
    }


}
