import java.io.*;
import java.util.*;

/**
 * WARNING: The spec tests are not necessarily equal to your grade!
 * You can use them help you test for the correctness of your algorithm,
 * but the final grade is determined by a manual inspection of your implementation.
 */
class SurveyDesign {


    /**
     * Does G' have a valid circulation???
     *
     * @param n           the number of frequent users (customers)
     * @param k           the number of candy to be reviewed
     * @param connections a set of connections (x, y) representing that person x does _not_ like candy y.
     *
     *                    All arrays below are 1-indexed:
     * @param candyQuantities the amount qi of each candy provided for the event (1<=i<=k) (q)   <--- capacity (product, t)
     * @param  personMinCandy the amount of candy for a person such that the review is worthwhile (d)  <-- lower bound (s, person)
     * @param personMaxCandy the safe amount of candy without risking a sugar spike for a person (a)  <--- capacity (s, person)
     * @param candyMinApprovers the number of people needed to try a specific candy (c)  <--- lower bound (product, t)
     *
     * @return true, if the testing event is successful in reviewing all the candy, false otherwise.
     *
     *    Note: Instead of Range objects, we now have 4 int[] arrays
     */
    public static boolean isCandyProofingPossible(int n, int k, Set<Connection> connections, int[] personMinCandy, int[] personMaxCandy,
                                                  int[] candyMinApprovers, int[] candyQuantities) {

        // Create 1-indexed arrays for ease
        Node[] customers = new Node[n + 1];
        Node[] candies = new Node[k + 1];

        List<Node> nodes = new ArrayList<>();  // to create graph
        Node source = new Node(-1, 0);  // source and sink have demands 0!
        Node sink = new Node(-2, 0);
        nodes.add(source);
        nodes.add(sink);

        // Sum all the customers lower bounds + capacities for edge (t, s)
        int lowSumCustomers = 0;
        int capSumCustomers = 0;

        // Add all n customer nodes, connected to source
        for (int i = 1; i <= n; i++) {
            customers[i] = new Node(i);
            nodes.add(customers[i]);
            source.addEdge(customers[i], personMinCandy[i], personMaxCandy[i]);
            lowSumCustomers += personMinCandy[i];
            capSumCustomers += personMaxCandy[i];
        }

        // Add all k candy nodes, connected to sink
        for (int i = 1; i <= k; i++) {
            candies[i] = new Node(i);
            nodes.add(candies[i]);
            candies[i].addEdge(sink, candyMinApprovers[i], candyQuantities[i]);
        }

        // Add all edges between customers and candies, if NO connection! (Note: connection means person DOES NOT like candy)
        for (int p = 1; p <= n; p++) {
            for (int c = 1; c <= k; c++) {
                if (!connections.contains(new Connection(p, c))) {
                    customers[p].addEdge(candies[c], 0, 1);  // Note: could be any cap > 0
                }
            }
        }

        // Add edge (t, s) OR leave it out, since we have lower bounds already
        sink.addEdge(source, lowSumCustomers, capSumCustomers);


        // Check if circulation exists in graph
        Graph g = new Graph(nodes, source, sink);
        return g.hasCirculation();

    }



    /**
     *  Can we design a survey under constraints (lower bounds, capacities)??
     *   Circulation problem!
     *
     * @param n                 the number of frequent users
     * @param k                 the number of candy to be reviewed
     * @param connections       a set of connections (x, y) representing that person x does NOT like candy y. <-- no edge
     * @param customersRange    list of min-max range for customers
     *                          (minimum and maximum number of products that a customer i can review, 1<=i<=n)
     * @param productsRange     list of min-max range for products
     *                          (minimum and maximum number of customers that need to review product j , 1<=j<=k)
     * @return true, if the testing event is successfully in reviewing all the candy, false otherwise.
     */
    public static boolean isReviewPossible(int n, int k, Set<Connection> connections, Range[] customersRange, Range[] productsRange) {

        List<Node> nodes = new ArrayList<>();

        Node source = new Node(-1, 0);
        Node sink = new Node(-2, 0);
        nodes.add(source);
        nodes.add(sink);

        Node[] customers = new Node[n + 1];  // 1-indexed
        Node[] products = new Node[k + 1];
        // Create customer and product nodes
        for (int i = 1; i <= n; i++) customers[i] = new Node(i);
        for (int i = 1; i <= k; i++) products[i] = new Node(i);

        int capSum = 0, lowSum = 0;  // Will be lower bound and capacity for edge(t, s)
        for (int i = 1; i <= n; i++) {
            nodes.add(customers[i]);
            // Add one edge from source to customer i with max question amount
            source.addEdge(customers[i], customersRange[i].min, customersRange[i].max);
            lowSum += customersRange[i].min;  // update sum of all lower bounds for customers
            capSum += customersRange[i].max;  // update sum of all upper bounds for customers
        }
        for (int i = 1; i <= k; i++) {
            nodes.add(products[i]);
            // Add one edge from product j to sink with min question amount
            products[i].addEdge(sink, productsRange[i].min, productsRange[i].max);
        }
        // Note: For every NON-existing customer-product connection, add one edge with cap 1 <-- customer i can review candy j
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= k; j++) {
                if (!connections.contains(new Connection(i, j))) {  // contains relies on equals()
                    // Lower bound initialized as 0 if not passed
                    customers[i].addEdge(products[j], 1);   // 1 unit of flow means customer i gets 1 question about candy j
                    // OR any cap > 0
                }
            }
        }
        sink.addEdge(source, lowSum, capSum);  // overall no. of questions asked
        // OR no lower bound, but just inf as upper bound, since the lower bound is already ensured by edges (product, sink)

        Graph g = new Graph(nodes, source, sink);
        return g.hasCirculation();  // Will remove lower bounds, demand, supply, ...
    }



    // indicates lower bound and capacity
    static class Range {

        int min, max;

        public Range(int min, int max) {
            this.min = min;
            this.max = max;
        }
    }

    static class Connection {

        int x;
        int y;

        public Connection(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

}
