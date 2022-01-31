import java.io.*;
import java.util.*;

public class BipartiteMatching {

    /**
     * max size of matchings = n if perfect matching (each person assigned to a snail)
     *
     * @param n           the number of nodes in X and Y (i.e. n = |X| = |Y|)
     * @param connections set of connections between one object from X and one object from Y.  <--- possible matchings
     *                    Objects in X and Y are labelled 1 <= label <= n
     * @return the size of the maximum matching
     */
    public static int maximumMatching(int n, Set<Connection> connections) {
        List<Node> nodes = new ArrayList<>();
        Node source = new Node(-1, 0);
        Node sink = new Node(-2, 0);
        nodes.add(source);
        nodes.add(sink);

        // Create one node for every object in X, and every object in Y.
        // We recommend we put them in xs and ys for easy reference, but make sure to also put them in nodes!
        // 1-indexing!
        Node[] xs = new Node[n + 1];  // owners to match
        Node[] ys = new Node[n + 1];  // pets to be assigned

        for (int i = 1; i <= n; i++) {
            xs[i] = new Node(i);
            ys[i] = new Node(i);
            nodes.add(xs[i]);
            nodes.add(ys[i]);
            // Add edges between source and all xs[] nodes
            source.addEdge(xs[i], 1);  // cap = 1 means a person can only be assigned to 1 pet
            // Add edges between all ys[] nodes and sink
            ys[i].addEdge(sink, 1);    // cap = 1 means 1 pet could only be matched to 1 person
        }

        // Then create the edges between them based on `connections` parameter.
        for (Connection possiblePairing : connections) {
            xs[possiblePairing.x].addEdge(ys[possiblePairing.y], 1);   // OR: any cap > 0, like Integer.MAX!
        }

        Graph g = new Graph(nodes, source, sink);
        return MaxFlow.maximizeFlow(g);    // value of max flow in this graph = maximal matching size!
        // If its a perfect matching, max flow == n
    }





    /**
     * Recovers the solution for maximum matches.
     *
     * @param n           the number of nodes in X and Y (i.e. n = |X| = |Y|)
     * @param connections set of connections between one object from X and one object from Y. Objects in X and Y are labelled 1 <= label <= n
     * @return set of all pairs of matches that could be made
     */
    public static Set<Match> recoverSolution(int n, Set<Connection> connections) {
        Graph graph = maximumMatchingGraph(n, connections);   // creates graph of all possible pairings, without flow yet
        MaxFlow.maximizeFlow(graph);    // # pairings = max flow
        return recoverMatches(graph);
    }



    /**
     * Recovers the matches from a 1-1 bipartite matching problem
     *
     * @param graph the graph on which maximum matching algorithm was applied
     * @return a set of matches recovered
     */
    public static Set<Match> recoverMatches(Graph graph) {

        Set<Match> pairings = new HashSet<>();

        // Each outgoing edge of source having flow is another pairing, follow that path to t
        for (Edge outgoing : graph.getSource().getEdges()) {
            if (outgoing.flow == 1) {        // Note: if e(s,x) has no flow, there is no matching for owner x!
                Node person = outgoing.to;
                // Now look for the match made (neighbour having flow 1, Note: only 1 y is matched to this x)
                for (Edge matchToPet : person.getEdges()) {
                    if (matchToPet.flow == 1 && matchToPet.to != graph.getSource()) {  // Note: node.getEdges() contains backwards edges!!
                        Node pet = matchToPet.to;
                        pairings.add(new Match(person.getId(), pet.getId()));
                    }
                }
            }
        }
        return pairings;
    }




    /**
     * Construct network flow graph from the set of connections
     *
     * @param n           the number of nodes in X and Y (i.e. n = |X| = |Y|)
     * @param connections set of connections between one object from X and one object from Y. Objects in X and Y are labelled 1 <= label <= n
     * @return graph representing the connections
     */
    public static Graph maximumMatchingGraph(int n, Set<Connection> connections) {
        List<Node> nodes = new ArrayList<>();
        Node source = new Node(-1, 0);
        Node sink = new Node(-2, 0);
        nodes.add(source);
        nodes.add(sink);
        Node[] xs = new Node[n + 1];
        Node[] ys = new Node[n + 1];
        // Create one node for every object in X, and every object in Y.
        // We recommend we put them in xs and ys for easy reference, but make sure to also put them in nodes!
        for (int i = 1; i <= n; i++) {
            xs[i] = new Node(i);
            ys[i] = new Node(i);
            source.addEdge(xs[i], 1);  // source connected to people
            ys[i].addEdge(sink, 1);   // pets connected to sink
            nodes.add(xs[i]);
            nodes.add(ys[i]);
        }
        for (Connection con : connections) {
            xs[con.x].addEdge(ys[con.y], 1);  // edges between possible pairings
        }
        Graph g = new Graph(nodes, source, sink);
        return g;
    }




    // paired together (person and pet e.g.)
    static class Match {

        int x;
        int y;

        public Match(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return x + " - " + y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            Match match = (Match) o;
            return x == match.x && y == match.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }


}
