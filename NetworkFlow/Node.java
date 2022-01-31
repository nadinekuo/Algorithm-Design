import java.util.*;

class Node {

    protected int id;               // id for debugging
    protected int d;                // demand: default 0
    protected Collection<Edge> edges;  // outgoing edges of this node Note: contains backwards edges!

    /**
     * Create a new node (with demand 0)
     *
     * @param id: Id for the node.
     */
    public Node(int id) {
        this(id, 0);
    }

    /**
     * Create a new node
     *
     * @param id: Id for the node.
     * @param d: demand for the node. Remember that supply is represented as a negative demand.
     */
    public Node(int id, int d) {
        this.id = id;
        this.d = d;
        this.edges = new ArrayList<Edge>();
    }

    public void addEdge(Node destination, int capacity) {
        this.addEdge(destination, 0, capacity);   // default lower bound: 0
    }

    public void addEdge(Node to, int lower, int upper) {
        Edge e = new Edge(lower, upper, this, to);
        edges.add(e);
        to.getEdges().add(e.getBackwards());  // Note: add BACKwards edge as FORward edge of dest node!
    }

    public Collection<Edge> getEdges() {
        return edges;
    }

    public int getId() {
        return id;
    }

    public boolean equals(Object other) {
        if (other instanceof Node) {
            Node that = (Node) other;
            if (id == that.getId()) return edges.equals(that.getEdges());
        }
        return false;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getId());
        sb.append(" ");
        sb.append(this.getEdges().size());
        sb.append(":");
        for (Edge e : this.getEdges()) {
            sb.append("(");
            sb.append(e.from.getId());
            sb.append(" --[");
            sb.append(e.lower);
            sb.append(',');
            sb.append(e.capacity);
            sb.append("]-> ");
            sb.append(e.to.getId());
            sb.append(")");
        }
        return sb.toString();
    }


}
