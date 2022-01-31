
class UnionFind {

    private int[] parent;

    private int[] rank;

    // Union Find structure implemented with two arrays for Union by Rank
    // Creates n (= size) different clusters, each containing 1 vertex/member
    public UnionFind(int size) {
        parent = new int[size];    // "representative"
        rank = new int[size];    // length of cluster (subtree rooted at leader)
        for (int i = 0; i < size; i++) parent[i] = i;
    }

    /**
     * Merge two subtrees if they have a different parent, input is array indices
     * @param i a node in the first subtree
     * @param j a node in the second subtree
     * @return true iff i and j had different parents.
     */
    boolean union(int i, int j) {

        int set1 = find(i); // leader of cluster of i
        int set2 = find(j); // leader of cluster of j

        if (set1 == set2) return false;   // No cycles!

        // If i and j different clusters, we merge into largest cluster
        if (rank[set1] > rank[set2]) {
            parent[set2] = set1;    // Now smaller cluster leader points to leader of larger cluster

        } else if (rank[set2] > rank[set1]) {
            parent[set1] = set2;    // If set 2 larger
        }
        else {      // only increase rank if EQUAL tree depths are merged
            rank[set1]++;
            parent[set2] = set1;  // just merge into set1
        }
        return true;

    }

    /**
     * NB: this function should also do path compression
     * @param i index of a node
     * @return the root of the subtree containg i.
     */
    int find(int i) {
        int leader = this.parent[i];  // current pointer of i

        if (leader == i) return i;   // base case: leader found

        // Path compression: we set parent[i] to the cluster leader! So next time, we can lookup in O(1)
        return this.parent[i] = find(leader);   // If i is not the leader, we find its cluster leader recursively
    }

    // Return the rank of the trees: length of longest path in tree
    public int[] getRank() {
        return rank;
    }

    // Return the parent/leader/representative of the trees
    public int[] getParent() {
        return parent;
    }
}

