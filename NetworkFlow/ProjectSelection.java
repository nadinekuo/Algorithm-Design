import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class ProjectSelection {


    /**
     *  Projects may have positive profit > 0 ("revenue") or negative profit < 0 ("cost")
     *  Each project has a list of requirements (prerequisites)!
     *      --> edge with capacity ∞ makes sure these must be met!
     *
     * @param projects List of projects, you can ignore list.get(0)
     * @return A set of feasible projects that yield the maximum possible profit.
     *
     *    min cut capacity = max flow
     *    min cut capacity = C - max profit(A)
     *    max net profit(A) = C - min cut capacity
     */
    public static int maximumProjects(List<Project> projects) {

        // 0-indexed!
        List<Node> nodes = new ArrayList<>();  // Each node is a project

        int n = projects.size();
        for (Project project : projects) {
            nodes.add(new Node(project.getId()));  // Note: 0-indexed nodes correspond to projects list, but ids are 1-indexed!
        }

        Node source = new Node(-1, 0);  // source side: all selected projects
        Node sink = new Node(-2, 0);
        nodes.add(source);
        nodes.add(sink);


        // C = sum of all profits > 0, aka all capacities leaving source
        int C = 0;

        // Go over all projects, check whether its profit is positive or negative
        // Source is connected to all projects having a net profit > 0
        // Sink is connected to all projects having a net profit < 0
        for (int i = 0; i < n; i++) {
            Project currProject = projects.get(i);

            int netProfit = currProject.getRevenue() - currProject.getCost();  // a project can have both revenue and cost!
            if (netProfit > 0) {
                source.addEdge(nodes.get(i), netProfit);  // OR connect all projects with both source and sink (absolute profit/cost)
//                OR source.addEdge(nodes.get(i), benefits[i]);
//                OR nodes.get(i).addEdge(sink, costs[i]);
                C += netProfit;
            } else {                                      // sink connected to all negative net profits < 0
                nodes.get(i).addEdge(sink, -netProfit);  // Note: make neg cost positive again!
            }

            // Add projects and their prerequisites to the NF graph (edges between project nodes)
            List<Integer> requirements = currProject.getRequirements();
            for (Integer reqId : requirements) {
                nodes.get(i).addEdge(nodes.get(reqId - 1), Integer.MAX_VALUE);
            }
        }

        Graph g = new Graph(nodes, source, sink);
        int minCutCapacity = MaxFlow.maximizeFlow(g);
        int maxProfit = C - minCutCapacity;   // Note: net profit = total positive profit possible - costs/missed profit

        // For recovering feasible solution: all selected projects (nodes in set A)
        Set<Integer> selectedProjects = recoverFeasibleSolution(g, maxProfit);
        for (Integer i : selectedProjects) {
            System.out.println(i);
        }

        return maxProfit;   // Total maximized net profit for all selected projects (in set A)
    }



    // Note: see TheGreedyProjectManager (exam 19/20) for a similar method!
    // Returns set of all projects selected
    // Aka all nodes in set A
    // BFS path finding in "residual graph"
    public static HashSet<Integer> recoverFeasibleSolution(Graph g, int maxProfit) {

        HashSet<Integer> selectedProjects = new HashSet<>();   // Note: serves as "visited" set

        if (maxProfit > 0) {

            Queue<Node> q = new LinkedList<>();

            // Note: Add all source neighbour nodes to queue (source itself is not a project)
            for (Edge e : g.getSource().getEdges()) {
                if (e.capacity - e.flow > 0) {
                    System.out.println("Adding to queue: " + e.to.getId());
                    q.add(e.to);
                }
            }
            while (!q.isEmpty()) {
                Node curr = q.poll();
                selectedProjects.add(curr.getId());

                // Note: iff max flow there CANNOT be any augmenting s-t paths anymore! So sink can never be reached
                for (Edge e : curr.getEdges()) {
                    if (e.getResidual() > 0
                                    && !e.to.equals(g.getSource())
                                                && !selectedProjects.contains(e.to.getId())) {    // Note: source may be neighbour of some node!!
                        q.add(e.to);
                        System.out.println("Adding to queue: " + e.to.getId());
                    }
                }
            }
        }
        return selectedProjects;
    }




    /**
     * You should implement this method
     *
     * @param projects List of projects, you can ignore list.get(0)
     * @return A set of feasible projects that yield the maximum possible profit.
     */
    public static int maximumProjectsSeparateArray(List<Project> projects) {

        List<Node> nodes = new ArrayList<>();
        int n = projects.size();

        Node[] projectNodes = new Node[n + 1];  // Note: 1-indexed array does not correspond to projects list!

        for (Project p : projects) {
            Node project = new Node(p.getId(), 0);
            nodes.add(project);
            projectNodes[p.getId()] = project;
        }

        Node source = new Node(-1, 0);
        Node sink = new Node(-2, 0);
        nodes.add(source);
        nodes.add(sink);

        int C = 0;

        // Add all project nodes
        for (int i = 0; i < n; i++) {

            Project p = projects.get(i);
            // Connect to source if net profit > 0, else sink
            int netProfit = p.getRevenue() - p.getCost();
            if (netProfit > 0) {
                source.addEdge(projectNodes[p.getId()], netProfit);  // profit
                C += netProfit;
            } else {
                projectNodes[p.getId()].addEdge(sink, -netProfit);  // cost
            }
            // Connect this node to all its requirements
            for (Integer req : p.getRequirements()) {
                projectNodes[p.getId()].addEdge(projectNodes[req], 0, Integer.MAX_VALUE);
            }
        }

        // max profit = C - min cut = all net profits - max flow
        Graph g = new Graph(nodes, source, sink);
        int minCut = MaxFlow.maximizeFlow(g);
        return C - minCut;
    }




    static class Project {

        private int id;
        private int cost;       // p_i < 0
        private int revenue;    // p_i > 0
        private List<Integer> requirements;   // prerequisites for this project (destination node of edge from this one!)

        public Project(int revenue, int cost) {
            this.revenue = revenue;
            this.cost = cost;
            this.requirements = new ArrayList<>();
        }

        public Project(int id, int revenue, int cost) {
            this(revenue, cost);
            this.id = id;
        }

        public void addRequirement(int requirement) {
            requirements.add(requirement);
        }

        public void addRequirements(List<Integer> requirements) {
            this.requirements.addAll(requirements);
        }

        public int getId() {
            return id;
        }

        public int getCost() {
            return cost;
        }

        public int getRevenue() {
            return revenue;
        }

        public List<Integer> getRequirements() {
            return requirements;
        }

        @Override
        public String toString() {
            return "Project{" + "id=" + id + ", cost=" + cost + ", revenue=" + revenue + ", requirements=" + requirements + '}' + "\n";
        }
    }

}
