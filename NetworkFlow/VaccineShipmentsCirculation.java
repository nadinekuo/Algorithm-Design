import java.util.ArrayList;
import java.util.List;

public class VaccineShipmentsCirculation {

    // Can all m shipments to validly assigned to a doctor??


    /**  VERSION 2: Supply / demand --> Circulation
     *
     * @param n - #doctors
     * @param m - #shipments
     * @param k - #types
     * @param a - how many shipments 1 doctor can use
     * @param types - what type shipment j belongs to (one of the k)
     * @param c - whether doctor i can use shipment j or not
     *
     * @return  true iff all m shipments to validly assigned to a doctor
     */
    public static boolean assignDoctorsToShipmentsNoSink(int n, int m, int k,
                                                   int[] a, int[] types, boolean[][] c) {

        List<Node> nodes = new ArrayList<>();

        Node source = new Node(-1, -m);  // Note: demand(s) = -m, makes sure all m shipments are assigned a doctor!
        // OR you set lower bounds to 1 for all shipment nodes, so demand/supply at source/sink it not needed
        nodes.add(source);

        // Create n doctor nodes connected to source OR connect shipments to source, and doctors to sink
        Node[] doctorNodes = new Node[n+1];
        for (int i = 1; i <= n; i++) {
            Node doctor = new Node(i, 0);
            doctorNodes[i] = doctor;
            nodes.add(doctor);
            source.addEdge(doctor, 0, a[i]);  // Note: 1 doctor can only use a_i shipments
        }

        // Note: Create all doctor (n) x type (k) nodes between doctors and shipments (Cartesian product)
        //  - ensures that PER doctor, PER type, only 2 shipments are made
        //  - allows us to still check whether doctor i can take shipment j (this was not possible if you would have Type nodes only)
        Node[][] doctorTypeNodes = new Node[n+1][k+1];
        for (int i = 1; i <= n; i++) {
            for (int t = 1; t <= k; t++) {
                Node doctorType = new Node(42, 0);
                doctorTypeNodes[i][t] = doctorType;
                nodes.add(doctorType);
            }
        }
        // Add edges from doctor i to doctor(i)-type(t) nodes (row holds all types)
        for (int i = 1; i <= n; i++) {
            for (int t = 1; t <= k; t++) {
                doctorNodes[i].addEdge(doctorTypeNodes[i][t], 0, 2);  // Note: Only 2 shipments PER type PER doctor
            }
        }

        // Create all m shipment nodes (connected to sink)
        Node[] shipmentNodes = new Node[m+1];
        for (int j = 1; j <= m; j++) {
            // NOTE: WE DONT EVEN NEED A SINK ANYMORE (nor edges to sink)!
            //  Instead we set d(sj) = 1, which is the same as d(sink) = m, since sum of all 1's = m <-- ensures all m shipments are done
            // OR If there would be a (shipment, sink) edge, the cap could be anything > 0
            Node shipment = new Node(j, 1);
            shipmentNodes[j] = shipment;
            nodes.add(shipment);
        }

        // Add edges from doctorType nodes to all m shipments, iff:
        // 1. doctor i can use shipment j
        // 2. shipment j is of type t
        for (int i = 1; i <= n; i++) {
            for (int t = 1; t <= k; t++) {    // OR shipments[j].addEdge(doctorTypes[i][types[j]], 0, 42) instead of 3-nested loop!
                for (int j = 1; j <= m; j++) {
                    if (c[i][j] && types[j] == t) {
                        doctorTypeNodes[i][t].addEdge(shipmentNodes[j], 0, 1);  // Note: each shipment handled by 1 doctor only!
                    }
                }
            }
        }
        Graph g = new Graph(nodes);
        return g.hasCirculation();
    }









}
