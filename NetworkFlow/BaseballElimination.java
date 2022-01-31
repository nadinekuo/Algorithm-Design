import java.io.*;
import java.util.*;

public class BaseballElimination {


    /**   Nodes: all teams, except for team X!!!!
     *
     * Returns true if team x can still win the Cup, no other team can end up with > wins
     *   True iff max flow == g*
     *
     *    Input example:
     *    4   <--- numTeams
     *    4 10 2   <-- teamX
     *    2 3
     *    1 12 2  <-- team 1 ...
     *    2 3      <-- opponents team 1 are 2, 3 Note that the oppponent will also store 1!
     *    2 11 3
     *    1 3 4
     *    3 11 3
     *    1 2 4
     *
     *    List<Node> nodes order of nodes added:
     *    - Source (0)
     *    - Teams (right nodes) (id)
     *    - Sink (m + 1)
     *    - Game combos (left nodes)
     *
     */
    public static boolean parseInput(InputStream in) {

        Scanner sc = new Scanner(in);

        int numTeams = sc.nextInt();   // 2 * numTeams lines follow

        // Process first entry: team X
        int teamX = sc.nextInt();
        int winsX = sc.nextInt();
        int gamesLeftX = sc.nextInt();
        int maxWinsPossibleX = winsX + gamesLeftX;   // how many points team X can end with, assuming it wins all remaining games

        sc.nextLine();  // Note: The teams X will play against don't matter (this skips 1 line), since X is not included in graph
        sc.nextLine();

        // Add source, sink
        List<Node> nodes = new ArrayList<>();

        Node source = new Node(0);   // where wins emanate from
        nodes.add(source);

        // Add right nodes: all single teams that can earn wins still
        for (int i = 1; i <= numTeams; i++) {  // all other teams to add to network (Note: DON'T ADD team X!)
            nodes.add(new Node(i));    // idx corresponds to id :)
        }

        Node sink = new Node(numTeams + 1);  // absorbs wins
        nodes.add(sink);

        // Process all remaining m-1 teams: WE DON'T ADD TEAM X
        for (int i = 0; i < numTeams - 1; i++) {
            int teamIdx = sc.nextInt();
            int winsI = sc.nextInt();
            int gamesLeftI = sc.nextInt();  // How many team ids will follow in the line after
            if (winsI > maxWinsPossibleX) {
                return false;        // Note: There is already a team having more wins than the max no. of wins for X!
            }
            // Add edge capacities: Note: m - w_i ensures no team has more wins than team x, tie is allowed!
            nodes.get(i).addEdge(sink, maxWinsPossibleX - winsI);


            // Add left nodes: all combinations of teams to be played against each other still
            // Note: For THIS team i, find the number of games to play against each opponent j (K) --> g_ij (V)  (keep counter)
            HashMap<Integer, Integer> gamesLeftOpponents = new HashMap<>();
            for (int j = 0; j < gamesLeftI; j++) {
                int teamAgainst = sc.nextInt();
                // Note: Avoid DUPLICATES (by only consider lower ids) and exclude matches against team x
                if (teamAgainst < teamIdx && teamAgainst != teamX) {
                    int n = gamesLeftOpponents.getOrDefault(teamAgainst, 0);
                    gamesLeftOpponents.put(teamAgainst, n + 1);        // First time team encountered -> store 0 + 1 = 1
                    // Note: Each time we encounter this team again, we increment g_ij
                }
            }
            // Adds a (left) game node, edge from source and edges to teams
            for (Integer team : gamesLeftOpponents.keySet()) {   // Go over all team ids
                Node gameCombo = new Node(nodes.size());       // id is next idx
                nodes.add(gameCombo);
                source.addEdge(gameCombo, gamesLeftOpponents.get(team));    // Add edge capacities: g_i
                // Each game has 2 edges, to the 2 teams involved
                gameCombo.addEdge(nodes.get(i), gamesLeftOpponents.get(team));  // Note: to THIS team i (curr loop iteration)
                gameCombo.addEdge(nodes.get(team), gamesLeftOpponents.get(team)); // Note: to opponent team j
            }
        }
        // Create graph, compute max flow
        Graph g = new Graph(nodes, source, sink);
        g.maximizeFlow();
        // If max flow == g*, that means there was a way to ensure no team has > m wins, so team x can still win!
        // Instead of computing g*, this is faster:

        // If the flow is not maximal on the outgoing edges from the source, one of the games does not have a winner.
        // This means both teams have the same score as team x, so the flow was capped by the edges to the sink.
        // One of these teams will beat team x when the game is played, therefore team x cannot win.
        for (Edge e : source.getEdges()) {
            if (e.getFlow() != e.getCapacity()) {  // no max flow
                return false;
            }
        }
        return true;
    }





    }


