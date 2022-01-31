import java.util.ArrayList;
import java.util.List;

public class Aeronauts {



    /**
     * @param l          the number of flights Lee has already done  <--- " #wins already"
     * @param n          the number of competitors  <--- "teams", note: excluding Lee
     * @param m          the number of open slots left  <-- "#games left"
     * @param flights    the number of flights each of the competitors has done. You should use flights[1] to flights[n]  <-- "#wins of all teams"
     * @param compatible 2D boolean array such that slot i can be used by competitor j iff compatible[i][j] is true.  <--- "game combos left"
     *                Note that compatible[0][x] and compatible[x][0] should not be used. (1-indexed!)
     *
     *    Each slot is compatible with >= 1 slot!
     *
     * @return boolean - true if Lee can still win: there was a way to distribute all "points" such that no one has >= flights than him!
     */
    public static boolean solve(int l, int n, int m, int[] flights, boolean[][] compatible) {

        // Note: We don't include Lee ("team X") in our graph!

        Graph g = buildGraph(l, n, m, flights, compatible);
        return canLeeWin(g);
    }


    public static Graph buildGraph(int l, int n, int m, int[] flights, boolean[][] compatible) {

        List<Node> nodes = new ArrayList<>();
        Node[] slots = new Node[m + 1];  // 1-indexed
        Node[] flyers = new Node[n + 1];

        // 1. Create s and t
        Node source = new Node(-1, 0);
        Node sink = new Node(-2, 0);
        nodes.add(source);
        nodes.add(sink);

        // 2. Create left nodes for all time slots ("games left")
        for (int i = 1; i <= m; i++) {
            slots[i] = new Node(i, 0);
            nodes.add(slots[i]);
            // 4. Connect s to all time slots with cap 1 <-- Note: each slot counts as "1 win/score"
            source.addEdge(slots[i], 0, 1);
        }

        // 3. Create right nodes for all n flyer competitors ("teams") (check matrix)
        for (int j = 1; j <= n; j++) {
            flyers[j] = new Node(j, 0);
            nodes.add(flyers[j]);
            // 5. Connect all flyers to sink t with cap l - f[i] - 1   <-- Note: no ties! All scores must remain < l
            // Lee cannot fly anymore, so l is fixed now (he cannot get more points)
            // Lee is in the running to win, so l > any other flights made f[i]!

            // OR add check to see if no other has already more flights, but the description says that Lee has the most flights of all
//            if (flights[j] >= l) {
//                return null;
//            }

            flyers[j].addEdge(sink, 0, l - flights[j] - 1);
        }

        // 6. Connect edges from slot i to flyer j if compatible
        for (int slot = 1; slot <= m; slot++) {
            for (int j = 1; j <= n; j++) {
                if (compatible[slot][j]) {
                    slots[slot].addEdge(flyers[j], 0, Integer.MAX_VALUE); // Note: could be any cap > 0, since we already bottlenecked it
                }
            }
        }

        // TODO: how to retrieve how we distributed the slots (to prevent any competitor from having >= flies than Lee)
        // Iterate over all middle edges from slots to flyers and check which edges have flow = 1
        return new Graph(nodes, source, sink);
    }


    public static boolean canLeeWin(Graph g) {

        // Check if all edges going out of source have max flow!

        MaxFlow.maximizeFlow(g);

        // This solution was created when maxFlow() was void!
        // --------->  You could also have done: return maxFlow == m
        for (Edge e : g.getSource().getEdges()) {
            if (e.getFlow() != e.getCapacity()) {
                return false;
            }
        }
        return true;
    }



}
