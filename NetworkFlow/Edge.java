
class Edge {

    protected int lower;
    protected int capacity;
    protected int flow;     // Note: "backward edge" in theory: same flow pushed backwards
    protected Node from;
    protected Node to;
    protected Edge backwards;  // Note: backwards edge has flow = residual of THIS forward edge


    // Note: Constructor for residual ("backwards") edge (called in constructor below)
    private Edge(Edge e) {
        this.lower = 0;
        this.flow = e.getCapacity();  // Note: initial flow of backward edge = initial rsidual = capacity (updated in augmentFlow())
        this.capacity = e.getCapacity();
        this.from = e.getTo();      // Opposite directions
        this.to = e.getFrom();
        this.backwards = e;
    }

    protected Edge(int lower, int capacity, Node from, Node to) {
        this.lower = lower;
        this.capacity = capacity;
        this.from = from;
        this.to = to;
        this.flow = 0;      // Initially now flow yet
        this.backwards = new Edge(this);  // see constructor above
    }

    public void augmentFlow(int add) {
        assert (flow + add <= capacity); // bounded by capacity!
        flow += add;
        backwards.setFlow(getResidual());  // Note: update residual on backwards flow (since residual decreases after augmenting!)
    }

    public Edge getBackwards() {
        return backwards;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getFlow() {
        return flow;
    }

    public Node getFrom() {
        return from;
    }

    public int getResidual() {   // leftover capacity, Note this residual is flow on backward edge
        return capacity - flow;
    }

    public Node getTo() {
        return to;
    }

    private void setFlow(int f) {
        assert (f <= capacity);  // flow bounded by capacity!
        this.flow = f;
    }

    public boolean equals(Object other) {
        if (other instanceof Edge) {
            Edge that = (Edge) other;
            return this.capacity == that.capacity
                    && this.flow == that.flow
                    && this.from.getId() == that.getFrom().getId()
                    && this.to.getId() == that.getTo().getId();
        }
        return false;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "lower=" + lower +
                ", capacity=" + capacity +
                ", flow=" + flow +
                ", from=" + from +
                ", to=" + to;
        // Note: adding backward will lead to StackOverFlowError!!!
    }
}
