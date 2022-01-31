import java.util.*;

public class DonutStoresClusters {


// Stop after n-k iterations of Kruskal!
// We cannot create MST first and then remove the k-1 most expensive, since UnionFind clusters cannot be broken anymore...


    /**
     * @param n the number of houses
     * @param k the number of stores to place
     * @param houses the houses, indexed 0 to n (with ids 0 to n)
     * @return the places to put the stores.
     *
     *   Version 1: Sorting list of distances / edges by passing comparator (O (n log n))
     *      WE DO NOT NEED PQ, since we don't continuously insert/remove items
     *
     */
    public static Set<Store> donutTimeSortList(int n, int k, List<House> houses) {

        int m = n * (n - 1) / 2;   // upper bound on edges / Distances

        List<Distance> distances = new ArrayList<>(m);

        // Insert "edge" between each node (House)
        for (int i = 0; i < n; i++) {    // For each house
            for (int j = i + 1; j < n; j++) {    // Store distance to ALL OTHER houses with higher id  (edge costs)  --> O(n^2)
                distances.add(new Distance(houses.get(i), houses.get(j)));
            }
        }

        UnionFind unionFind = new UnionFind(houses);   // initially creates n clusters, 1 per house

        // Sort by shortest distance = edge  ---> O(n log n)
        distances.sort(Comparator.comparingLong(d -> d.distance));

        int countEdges = 0;
        for (Distance d : distances) {
            if (countEdges == n - k)  // We add edges (build MST using Kruskal) but stop after n-k iterations! (don't add the k-1 most expensive edges)
                break;
            if (unionFind.join(d.a, d.b))   // "edge added to MST", aka 2 clusters merged
                countEdges++;
        }
        Set<Store> storesToBePlaced = new HashSet<>();
        for (List<House> cluster : unionFind.clusters()) {  // For each cluster, find the avg coordinates --> location of store
            long countNodes = 0;
            long sumX = 0;
            long sumY = 0;   // integer division rounds down!!!
            for (House house : cluster) {
                countNodes++;      // count of houses in 1 cluster (to divide by for avg)
                sumX += house.x;
                sumY += house.y;
            }
            // Adding 1e-6 to test if it's accepted
            storesToBePlaced.add(new Store(sumX * 1.0 / countNodes + 1e-6, sumY * 1.0 / countNodes));
        }
        return storesToBePlaced;
    }


//
//    /**
//     * @param n the number of houses
//     * @param k the number of stores to place
//     * @param houses the houses, indexed 0 to n (with ids 0 to n)
//     * @return the places to put the stores.
//     *
//     *   Version 2: Inserting all distances / edges into PQ (O (log n))
//     */
//    public static Set<Store> donutTimePQ(int n, int k, List<House> houses) {
//
//        int m = n * (n - 1) / 2;   // upper bound on edges / distances
//
//        List<Distance> distances = new ArrayList<>(m);
//
//        for (int i = 0; i < n; i++) {    // For each house
//            for (int j = i + 1; j < n; j++) {    // Store distance to ALL OTHER houses with higher id  (edge costs)
//                distances.add(new Distance(houses.get(i), houses.get(j)));
//            }
//        }
//
//        UnionFind unionFind = new UnionFind(houses);   // initially creates n clusters, 1 per house
//
//        // Create the clusters by removing the k-1 most expensive edges from MST
//
//        List<Distance> MST = buildMST(distances, houses, unionFind);  // The edges returned are in increasing cost!! So we remove from the back.
//
//        int numEdgesRemoved = 0;
//        while (numEdgesRemoved < k) {
//            MST.remove(MST.size()-1);  // keep on removing the most expensive edge (final idx)
//            numEdgesRemoved++;
//        }
//
//        // Get the avg of all houses in 1 cluster --> coordinates for store to be placed
//        Set<Store> storesToBePlaced = new HashSet<>();
//        Collection<List<House>> clusters = unionFind.clusters();
//
//        for (List<House> cluster : clusters) {
//            int numHouses = cluster.size();
//            double Xsum = 0;
//            double Ysum = 0;
//            for (House vertex : cluster) {  // Add x, y coords of each house
//                Xsum += vertex.x;
//                Ysum += vertex.y;
//            }
//            storesToBePlaced.add(new Store(Xsum / numHouses, Ysum / numHouses));
//        }
//        return storesToBePlaced;
//    }
//
//
//
//    private static List<Distance> buildMST(List<Distance> edges, List<House> houses, UnionFind unionFind) {
//
//        if (edges.isEmpty()) return new ArrayList<>();
//
//        List<Distance> MST = new ArrayList<>();  // stores all edges added to MST
//
//        // Insert all edges / distances into PQ to pick the lowest weight
//        PriorityQueue<Distance> edgesPQ = new PriorityQueue<>(edges);
//
//        while (!edgesPQ.isEmpty()) {
//
//            Distance smallestEdge = edgesPQ.poll();
//
//            if (unionFind.join(smallestEdge.a, smallestEdge.b)) {  // If 2 vertices in different clusters, we merge and grow MST
//                MST.add(smallestEdge);
//            }
//        }
//        return MST;
//    }




    static class House {

        int id, x, y;  // coordinates

        public House(int id, int x, int y) {
            this.id = id;
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            House house = (House) o;
            return id == house.id && x == house.x && y == house.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, x, y);
        }
    }



    // Edge between 2 vertices (houses) --> to be inserted into PQ to get the cheapest edge for MST
    static class Distance {   // Distance between 2 houses

        House a, b;  // vertices

        long distance;   // edge weight

        public Distance(House a, House b) {
            this.a = a;
            this.b = b;
            // Square Euclidean distance, to avoid floating-point errors
            this.distance = (long) (a.x - b.x) * (a.x - b.x) + (long) (a.y - b.y) * (a.y - b.y);
        }
    }


    static class UnionFind {

        private List<House> houses;  // "vertices"/nodes
        private int[] parent;       // leader of cluster
        private int[] rank;         // length of tree path

        UnionFind(List<House> houses) {
            this.houses = houses;
            int n = houses.size();   // how many nodes
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) parent[i] = i;  // initially, each cluster leader is the node itself
        }

        /**  Union: merges 2 clusters
         * Joins two disjoint sets together, if they are not already joined.
         *
         * @return false if x and y are in same set, true if the sets of x and y are now joined.
         */
        boolean join(House x, House y) {
            int xrt = find(x.id);  // setX
            int yrt = find(y.id);  // setY

            // If xrt == yrt --> return false
            // Instead of checking whether they are not in the same cluster, we just reset the parent no matter

            if (rank[xrt] > rank[yrt])  // merge into largest cluster (longest tree path)
                parent[yrt] = xrt;
            else if (rank[xrt] < rank[yrt])
                parent[xrt] = yrt;
            else if (xrt != yrt)      // no equal clusters!
                rank[parent[yrt] = xrt]++;   // only if equal ranks, the path gets increased by 1

            return xrt != yrt;     // true if different clusters!! Else, we create a cycle

        }


        /**  Find: maps node idx to House object
         * @return The house that is indicated as the "root" of the set of house h.
         */
        House find(House h) {
            return houses.get(find(h.id));   // leader node idx corresponds to idx into arraylist Houses
        }

        // Path compression: we recursively find the leader of the cluster and set parent[x] to that leader
        // so next lookup can be done in O(1)
        private int find(int x) {
            return parent[x] == x ? x : (parent[x] = find(parent[x]));
        }


        /**
         * @return All clusters of houses as Lists
         */
        Collection<List<House>> clusters() {
            Map<Integer, List<House>> map = new HashMap<>();
            for (int i = 0; i < parent.length; i++) {
                int root = find(i);
                if (!map.containsKey(root))
                    map.put(root, new ArrayList<>());  // K: leader idx, V: List<House>
                map.get(root).add(houses.get(i));
            }
            return map.values();
        }
    }




    static class Store {

        double x, y;

        public Store(double x, double y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            Store store = (Store) o;
            return Math.abs(store.x - x) <= 1e-4 && Math.abs(store.y - y) <= 1e-4;
        }

        @Override
        public int hashCode() {
            return Objects.hash(Math.floor(x), Math.floor(y));
        }

        @Override
        public String toString() {
            return "Store{" + "x=" + x + ", y=" + y + '}';
        }
    }




}
