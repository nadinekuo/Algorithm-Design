import java.util.*;

class Graph {

    private List<Node> nodes;
    private Node source;
    private Node sink;

    public Graph(List<Node> nodes) {
        this.nodes = nodes;
        this.source = null;
        this.sink = null;
    }

    public Graph(List<Node> nodes, Node source, Node sink) {
        this.nodes = nodes;
        this.source = source;
        this.sink = sink;
    }

    public Node getSink() {
        return sink;
    }

    public Node getSource() {
        return source;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public boolean equals(Object other) {
        if (other instanceof Graph) {
            Graph that = (Graph) other;
            return this.nodes.equals(that.nodes);
        }
        return false;
    }


    /**  Changes graph in order to be able to use Ford-Fulkerson
     *
     *  ----> We assume we only call this method on graph that has no flow yet!!!
     *
     * @return - true if graph has circulation, so all demands are fulfilled by supplies
     */
    public boolean hasCirculation() {
        this.removeLowerBounds();              // increases demand at src, increases supply at dest
        int D = this.removeSupplyDemand();     // Adds "super" source and sink,  D = sum of all demands (> 0)
        int maxFlow = MaxFlow.maximizeFlow(this);
        return maxFlow == D;                        // Note: all demands were supplied :)
    }


    /**  For edge(u, v) having lower bound k
     *  Add demand k to u
     *  Add supply k to v (decrease)
     *
     *   Note: We assume we only call this method on graph that has no flow yet!!!
     */
    private void removeLowerBounds() {
        for (Node n : this.getNodes()) {
            for (Edge e : n.edges) {       // check for ALL non-zero lower bounds
                if (e.lower > 0) {
                    e.capacity -= e.lower;   // decrease capacity
                    e.from.d += e.lower;    // src now demands more
                    e.to.d -= e.lower;      // dest now supplies more (more negative)

                    // Note: Update residuals (backward edges) too (which are part of the original graph here!)
                    e.backwards.capacity -= e.lower;
                    e.backwards.flow -= e.lower;       // Note: Initial flow of backwards edge (= initial residual) was capacity
                    e.lower = 0;      // no lower bound anymore
                }
            }
        }
    }


    /** Removes supply and demand from all nodes
     *   Adds "super" source and sink
     *
     *  @return - D = S = sum of all demands = sum of all supplies
     *
     */
    private int removeSupplyDemand() {

        // Dplus = sum of demands, Dmin = sum of supplies
        int Dplus = 0, Dmin = 0;
        int maxId = 0;                // to determine id for super source and sink

        for (Node n : this.getNodes()) {
            maxId = Math.max(n.id, maxId);
        }
        // Note: any original source/sink are treated as normal nodes now
        Node newSource = new Node(maxId + 1, 0);   // "Supplier" connected to all edges with d(v) < 0
        Node newSink = new Node(maxId + 2, 0);     // "absorbs demands" connected to all edges with d(v) > 0

        // For all nodes, remove d(v) and check if it has demand or supply
        for (Node n : this.getNodes()) {
            if (n.d < 0) {                              // SUPPLY  --> connect to super source
                newSource.addEdge(n, 0, -n.d);      // Note: supply is negative demand, so invert sign (we don't want cap < 0)
                Dmin -= n.d;                         // subtracting negative values gives positive sum!
            } else if (n.d > 0) {                   // DEMAND --> connect to super sink
                n.addEdge(newSink, 0, n.d);
                Dplus += n.d;
            }
            n.d = 0;   // Note: now the internal nodes don't store d(v) anymore, replaced by (capacities to/from) source/sink
        }
        if (Dmin != Dplus) {
            throw new IllegalArgumentException("Demand and supply are not equal! " +
                    "Circulation never possible...");
        }
        this.nodes.add(newSource);
        this.nodes.add(newSink);
        this.source = newSource;  // So MaxFlow() knows which source and sink to use!
        this.sink = newSink;
        return Dplus;    // must be equal to Dmin
    }



    public void maximizeFlow() {
        MaxFlow.maximizeFlow(this);
    }


}