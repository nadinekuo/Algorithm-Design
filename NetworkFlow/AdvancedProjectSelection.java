import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class AdvancedProjectSelection {

    final int PARSING_SCANNER = 0;
    final int PARSING_BUFFEREDREADER = 1;
    int numMembers;   // n
    int numJobs;     // m
    int[] memberTimes;
    int[] jobTimes;
    Set[] memberSkills;  // Array of sets: each set contains skills of 1 member
    Set[] jobSkills;     // Array of sets: each set contains skills required for 1 job




    public static void main(String[] args) {
        String str = "2 3\n" +
                "Oliver 5 2 investigation interviewing\n" +
                "Caleb 8 2 interviewing lit\n" +
                "hire_member 1 1 interviewing\n" +
                "interview_author 3 2 interviewing lit\n" +
                "solve_crime 4 1 investigation";
        //assertEquals(true, solve(str));

        String str2 = "1 1\n" +
                "a 71 3 b c d\n" +
                "e 2 3 f c g";
        //assertEquals(false, solve(str2));
    }

    public static boolean solve(String str) {
        return new AdvancedProjectSelection().solve(new ByteArrayInputStream(str.getBytes(StandardCharsets.UTF_8)));
    }



    /**
     * @param in - text to be parsed
     * @return - true if all jobs can be assigned
     *
     *  Each member can do multiple jobs!
     *  Each job can be done by multiple members (split up hours)
     *  Every member of the club has a certain set of skills and every job requires a certain set of skills.
     *
     *   2 3
     *   Oliver 5 2 investigation interviewing   <---- n members: how many hours available, skills
     *   Caleb 8 2 interviewing lit
     *   hire_member 1 1 interviewing            <--- m jobs: how many hours, which skills required
     *   interview_author 3 2 interviewing lit
     *   solve_crime 4 1 investigation
     *
     *    Gives true: Oliver can take solve_crime, Caleb can take the other 2
     *
     */
    public boolean solve(InputStream in) {
        // Used for testing scanner vs bufferedReader
        int parseMethod = PARSING_SCANNER;
        try {
            return solve(in, parseMethod);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean solve(InputStream in, int parseMethod) throws IOException {
        parse(in, parseMethod);   // Note: Data to be stored in global variables
        Graph g = buildGraph();   // Note: creates network flow model to solve problem
        return solveLoop(g);      // Note: checks if max flow == total hours required for all jobs
    }

    // Allows 2 types of input parsing
    private void parse(InputStream in, int parseMethod) throws IOException {
        switch(parseMethod) {
            case PARSING_SCANNER:
                Scanner sc = new Scanner(in);
                parse(sc);
                break;
            case PARSING_BUFFEREDREADER:
                BufferedReader bf = new BufferedReader(new InputStreamReader(in));
                parse(bf);
                break;
        }
    }

    public void parse(Scanner sc) {
        this.numMembers = sc.nextInt();
        this.numJobs = sc.nextInt();
        this.memberSkills = new Set[this.numMembers];  // Note: array of sets per member, containing all skills!
        this.memberTimes = new int[this.numMembers];

        // Parse all n members (n lines)
        for (int member = 0; member < numMembers; member++) {
            this.memberSkills[member] = new HashSet();     // initialize Set of skills for this member (id)
            sc.next();                                    // skip name
            this.memberTimes[member] = sc.nextInt();      // how many hours this member is available
            int numSkills = sc.nextInt();                 // how many words (skills) follow after this
            // Parse the skills for this member
            for (int skill = 0; skill < numSkills; skill++) {
                this.memberSkills[member].add(sc.next());   // Add all skills to Set for this member
            }
        }
        this.jobSkills = new Set[this.numJobs];       // Note: Set of skills required for this job
        this.jobTimes = new int[this.numJobs];          // how many hours each job requires

        // Parse all m jobs (m lines)
        for (int job = 0; job < numJobs; job++) {
            this.jobSkills[job] = new HashSet();    // initialize set of required skills for this job
            sc.next();                              // skip job name
            this.jobTimes[job] = sc.nextInt();     // how many hours this job takes
            int numSkills = sc.nextInt();          // how many words (skills) follow after this
            // Parse all skills for this job
            for (int skill = 0; skill < numSkills; skill++) {
                this.jobSkills[job].add(sc.next());   // Add all required skills to set for this job
            }
        }
    }

    public void parse(BufferedReader bf) throws IOException {
        String line = bf.readLine();
        String[] split = line.split(" ");
        this.numMembers = Integer.parseInt(split[0]);
        this.numJobs = Integer.parseInt(split[1]);
        // Parse each member
        this.memberSkills = new Set[this.numMembers];
        for (int member = 0; member < numMembers; member++) {
            this.memberSkills[member] = new HashSet();
            split = bf.readLine().split(" ");
            this.memberTimes[member] = Integer.parseInt(split[1]);
            // Parse the skills for this member
            int numSkills = Integer.parseInt(split[2]);
            for (int skill = 0; skill < numSkills; skill++) {
                this.memberSkills[member].add(split[skill + 3]);
            }
        }
        // Parse all jobs
        this.jobSkills = new Set[this.numJobs];
        for (int job = 0; job < numJobs; job++) {
            this.jobSkills[job] = new HashSet();
            split = bf.readLine().split(" ");
            this.jobTimes[job] = Integer.parseInt(split[1]);
            int numSkills = Integer.parseInt(split[2]);
            // Parse all skills for this job
            for (int skill = 0; skill < numSkills; skill++) {
                this.jobSkills[job].add(split[skill + 3]);
            }
        }
    }


    private Graph buildGraph() {
        Node source = new Node("s");
        Node sink = new Node("t");

        // Create nodes for each member and connect to source with the max time as capacity
        Node[] memberNodes = new Node[this.numMembers];
        for (int m = 0; m < this.numMembers; m++) {
            memberNodes[m] = new Node("m" + m);
            source.addEdge(memberNodes[m], memberTimes[m]);
        }
        // Create nodes for each job and connect to sink with the time per job
        Node[] jobNodes = new Node[this.numJobs];
        for (int j = 0; j < this.numJobs; j++) {
            jobNodes[j] = new Node("j" + j);
            jobNodes[j].addEdge(sink, jobTimes[j]);
        }
        // Connect each member to each compatible job with a very large capacity
        for (int i = 0; i < this.numMembers; i++) {
            for (int j = 0; j < this.numJobs; j++) {
                if (compatible(this.memberSkills[i], this.jobSkills[j])) {
                    memberNodes[i].addEdge(jobNodes[j], Integer.MAX_VALUE / 10); // avoids overflow
                }
            }
        }
        // Collect all nodes for the graph and build the graph
        Collection<Node> nodes = new ArrayList<>(2 + this.numMembers + this.numJobs);
        nodes.add(source);
        nodes.add(sink);
        for (int i = 0; i < this.numMembers; i++) {
            nodes.add(memberNodes[i]);
        }
        for (int i = 0; i < this.numJobs; i++) {
            nodes.add(jobNodes[i]);
        }
        Graph g = new Graph(nodes, source, sink);
        return g;
    }

    // Checks if this member can perform this job
    // aka if ALL skills required are part of the members set of skills too
    // Returns true when the member and job are compatible, false otherwise.
    private boolean compatible(Set memberSkill, Set jobSkill) {
        for (Object skill : jobSkill) {
            if (!memberSkill.contains(skill)) {
                return false;
            }
        }
        return true;
    }

    public boolean solveLoop(Graph g) {
        g.maximizeFlow();
        // Compute flow
        int flow = 0;
        // Value of flow = sum of all flows leaving source OR: just return maxFlow == sumJobHoursNeeded (but in the old library code MaxFlow was void...)
        for (Edge e : g.getSource().getEdges()) {
            flow += e.getFlow();
        }
        // Compute total job times needed <-- can we fulfill all hours??
        int sumJobHoursNeeded = 0;
        for (int i = 0; i < this.numJobs; i++) {
            sumJobHoursNeeded += this.jobTimes[i];
        }
        // Total flow should be equal to total job times, otherwise not all jobs are (fully) completed
        return flow == sumJobHoursNeeded;
    }



    class Node {

        protected String id;    // String representing skill or job
        protected Collection<Edge> edges;

        public Node(String id) {
            this.id = id;
            this.edges = new ArrayList<Edge>();
        }

        public void addEdge(Node to, int capacity) {
            Edge e = new Edge(capacity, this, to);
            edges.add(e);
            to.getEdges().add(e.getBackwards());
        }

        public Collection<Edge> getEdges() {
            return edges;
        }

        public String getId() {
            return id;
        }

        public boolean equals(Object other) {
            if (other instanceof Node) {
                Node that = (Node) other;
                if (id.equals(that.getId()))
                    return edges.equals(that.getEdges());
            }
            return false;
        }
    }


    class Edge {

        protected int capacity;
        protected int flow;
        protected Node from;
        protected Node to;
        protected Edge backwards;

        private Edge(Edge e) {
            this.flow = e.getCapacity();
            this.capacity = e.getCapacity();
            this.from = e.getTo();
            this.to = e.getFrom();
            this.backwards = e;
        }

        protected Edge(int capacity, Node from, Node to) {
            this.capacity = capacity;
            this.from = from;
            this.to = to;
            this.flow = 0;
            this.backwards = new Edge(this);
        }

        public void augmentFlow(int add) {
            assert (flow + add <= capacity);
            flow += add;
            backwards.setFlow(getResidual());
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

        public int getResidual() {
            return capacity - flow;
        }

        public Node getTo() {
            return to;
        }

        private void setFlow(int f) {
            assert (f <= capacity);
            this.flow = f;
        }

        public boolean equals(Object other) {
            if (other instanceof Edge) {
                Edge that = (Edge) other;
                return this.capacity == that.capacity && this.flow == that.flow && this.from.getId().equals(that.getFrom().getId()) && this.to.getId().equals(that.getTo().getId());
            }
            return false;
        }
    }

    static class MaxFlow {

        private static List<Edge> findPath(Graph g, Node start, Node end) {
            Map<Node, Edge> mapPath = new HashMap<Node, Edge>();
            Queue<Node> sQueue = new LinkedList<Node>();
            Node currentNode = start;
            sQueue.add(currentNode);
            while (!sQueue.isEmpty() && currentNode != end) {
                currentNode = sQueue.remove();
                for (Edge e : currentNode.getEdges()) {
                    Node to = e.getTo();
                    if (to != start && mapPath.get(to) == null && e.getResidual() > 0) {
                        sQueue.add(e.getTo());
                        mapPath.put(to, e);
                    }
                }
            }
            if (sQueue.isEmpty() && currentNode != end)
                return null;
            LinkedList<Edge> path = new LinkedList<Edge>();
            Node current = end;
            while (mapPath.get(current) != null) {
                Edge e = mapPath.get(current);
                path.addFirst(e);
                current = e.getFrom();
            }
            return path;
        }

        static void maximizeFlow(Graph g) {
            Node sink = g.getSink();
            Node source = g.getSource();
            List<Edge> path;
            while ((path = findPath(g, source, sink)) != null) {
                int r = Integer.MAX_VALUE;
                for (Edge e : path) {
                    r = Math.min(r, e.getResidual());
                }
                for (Edge e : path) {
                    e.augmentFlow(r);
                }
            }
        }
    }

    class Graph {

        private Collection<Node> nodes;

        private Node source;

        private Node sink;

        public Graph(Collection<Node> nodes, Node source, Node sink) {
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

        public Collection<Node> getNodes() {
            return nodes;
        }

        public void maximizeFlow() {
            MaxFlow.maximizeFlow(this);
        }

        public boolean equals(Object other) {
            if (other instanceof Graph) {
                Graph that = (Graph) other;
                return this.nodes.equals(that.nodes);
            }
            return false;
        }
    }
}
