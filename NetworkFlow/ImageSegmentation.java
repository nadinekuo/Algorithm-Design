import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ImageSegmentation {

//    public static void main(String[] args) {

//        Graph g = createGraph();
//        MaxFlow.maximizeFlow(g);
//        int sumMaxFlow = 0;
//        for (Edge e : g.getSource().getEdges()) {
//            sumMaxFlow += e.getFlow();   // value of max flow = min cut
//        }
//        System.out.println(sumMaxFlow);

//    }


    public static void main(String[] args) {
        String str = "2 1\n" +
                "1 9 1\n" +
                "2 8 2\n" +
                "1 2";
        System.out.println(solve(str));
    }


    public static int solve(String str) {
        return new ImageSegmentation().solve(new ByteArrayInputStream(str.getBytes(StandardCharsets.UTF_8)));
    }




    /**  Min cut (= max flow) gives the minimized cost for this segmentation! ("maximized score")
     *
     * @param in  - input stream: all pixels, probabilities f_i and b_i, edges
     * @return - minimized cost ("opposite maximized score")
     */
    public int solve(InputStream in) {
        Graph g = parseIntoGraph(in);
        MaxFlow.maximizeFlow(g);
        int sumMaxFlow = 0;
        for (Edge e : g.getSource().getEdges()) {   // OR just use max flow value
            sumMaxFlow += e.getFlow();   // value of max flow = min cut!
        }
        return sumMaxFlow;
    }



    /**
     * @param in - input stream
     *
     *          2 1    <-- n, m
     *          1 9 1  <--- node/pixel id, f_i, b_i
     *          2 8 2
     *          1 2   <--- edge from, to
     *
     * @return - Graph built as network model
     */
    public Graph parseIntoGraph(InputStream in) {
        Scanner sc = new Scanner(in);        // parse input stream
        ArrayList<Node> nodes = new ArrayList<>();

        int n = sc.nextInt();                    // no. of nodes / pixels with ids in [1, n]
        int m = sc.nextInt();                    // no. of undirected edges (Note: between each pair of pixels, in both directions!)
        Node source = new Node(0);        // source on "foreground side",  id = 0
        Node sink = new Node(n + 1);      // sink on "background side", id = n+1
        nodes.add(source);                       // Create nodes list and add source

        // n lines / iterations for all n pixel nodes
        for (int x = 0; x < n; x++) {
            int id = sc.nextInt();
            int f_i = sc.nextInt();
            int b_i = sc.nextInt();
            Node pixel = new Node(id);
            nodes.add(pixel);                    // Note: idx in Arraylist corresponds to id!
            source.addEdge(pixel, f_i);         // from source to pixel, capacity is f_i
            pixel.addEdge(sink, b_i);           // from pixel to sink, capacity is b_i
        }
        nodes.add(sink);     // Note: n + 1 th index in nodes List! So add sink after all n nodes have been added!

        // Create all edges between ALL PAIRS of pixels, having capacity of penalty 10
        // m lines / iterations for all m edges Note: that we don't even need m
        for (int x = 0; x < m; x++) {
            int from = sc.nextInt();
            int to = sc.nextInt();
            // Note: Initialize capacity with penalty, 2 edges for both directions, since cost is paid for 2 combinations possible (Each pixel can only be part of 1 side (foreground/background))
            nodes.get(from).addEdge(nodes.get(to), 10);
            nodes.get(to).addEdge(nodes.get(from), 10);
        }
        return new Graph(nodes, source, sink);
    }


    private static Graph parseIntoGraphExtraArray(InputStream in) {

        Scanner sc = new Scanner(in);        // parse input stream
        int n = sc.nextInt();                    // no. of nodes / pixels with ids in [1, n]
        int m = sc.nextInt();                    // no. of nodes, between each pair of pixels

        ArrayList<Node> nodes = new ArrayList<>();
        Node[] pixelNodes = new Node[n + 1];

        Node source = new Node(-1);
        Node sink = new Node(-2);
        nodes.add(source);
        nodes.add(sink);

        // n lines / iterations for all n pixel nodes
        for (int i = 1; i <= n; i++) {
            int id = sc.nextInt();
            int f_i = sc.nextInt();
            int b_i = sc.nextInt();
            Node pixel = new Node(id);
            pixelNodes[i] = pixel;
            nodes.add(pixel);                    // Note: idx in Arraylist corresponds to id!
            source.addEdge(pixel, f_i);         // from source to pixel, capacity is f_i
            pixel.addEdge(sink, b_i);           // from pixel to sink, capacity is b_i
        }

        for (int j = 1; j <= m; j++) {
            int from = sc.nextInt();
            int to = sc.nextInt();
            pixelNodes[from].addEdge(pixelNodes[to], 10);
            pixelNodes[to].addEdge(pixelNodes[from], 10);
        }

        Graph g = new Graph(nodes, source, sink);
        return g;
    }



    // 4 pixels (ids 1-4), source has id 0, sink has id 5
    public static Graph createGraph() {

        List<Node> nodes = new ArrayList<>();

        for (int i = 0; i <= 5; i++) {
            nodes.add(new Node(i));
        }
        nodes.get(0).addEdge(nodes.get(1), 5);
        nodes.get(0).addEdge(nodes.get(2), 3);
        nodes.get(0).addEdge(nodes.get(3), 4);
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
