import java.io.*;
import java.util.*;

public class HospitalAssignment {


    /**   VALID ASSIGNMENT <--> value of max flow = n, so all patients can get assigned to hospital within dist x
     *
     * Checks if all n patients can be assigned to the hospital given the constraints.
     *
     * Distribute n patients over k hospitals, where:
     *   - Every patient travels a maximum provided distance to an assigned hospital.
     *   - Every hospital get at most n/k patients (rounded up).
     * Is there an assignment possible satisfying these constraints?
     *
     * @param n                 The number of patients
     * @param k                 The number of hospitals
     * @param patients          List of patient locations
     * @param hospitals         List of hospital locations
     * @param preferredDistance Preferred distance within which a patient can be assigned to a hospital.
     * @return True iff all patients can be assigned to at least one hospital within the preferred distance
     */
    public static boolean isAssignmentPossible(int n, int k, List<Location> patients, List<Location> hospitals, int preferredDistance) {
        List<Node> nodes = new ArrayList<>();
        Node source = new Node(-1, 0);
        Node sink = new Node(-2, 0);
        nodes.add(source);
        nodes.add(sink);

        // Create nodes for patients and hospitals
        Node[] patientNodes = new Node[n];  // Note 0-indexing is used here, since we need to access the ArrayLists in the nested loop later
        Node[] hospitalNodes = new Node[k];

        // Add hospital nodes
        for (int i = 0; i < k; i++) {
            hospitalNodes[i] = new Node(i);
            nodes.add(hospitalNodes[i]);
            source.addEdge(hospitalNodes[i], (int) Math.ceil((0.1) * n/k));  // make double!!
        }

        // YOU CAN ALSO SWAP PATIENTS (connected to source) AND HOSPITALS (connected to sink)

        // Add patient nodes
        for (int i = 0; i < n; i++) {
            patientNodes[i] = new Node(i);
            nodes.add(patientNodes[i]);
            patientNodes[i].addEdge(sink, 1);
        }

        // Add edges between patients and hospitals within preferredDistance
        for (int p = 0; p < patients.size(); p++) {
            for (int h = 0; h < hospitals.size(); h++) {
                if (patients.get(p).distance(hospitals.get(h)) <= preferredDistance) {
                    // OR you use 1-indexing but access nodes by h-1 and p-1
                    hospitalNodes[h].addEdge(patientNodes[p], 1);  // OR any cap > 0, such as Integer.MAX_VALUE
                }
            }
        }

        Graph g = new Graph(nodes, source, sink);

        // Valid flow iff value of max flow == n!
        // checks if all patients were assigned (alternatively can be done with hospitals)
        int maxFlow = MaxFlow.maximizeFlow(g);
        return maxFlow == n;
    }



    static class Location {

        private int x, y;

        public Location(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int distance(Location other) {
            return (int) Math.sqrt((this.x - other.x) * (this.x - other.x) + (this.y - other.y) * (this.y - other.y));
        }
    }





}
