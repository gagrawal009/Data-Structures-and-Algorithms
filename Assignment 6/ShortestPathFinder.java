//package col106.assignment6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;
import java.util.LinkedList;
import java.util.*;

public class ShortestPathFinder implements ShortestPathInterface {

    ArrayList<ArrayList<Edge>> dual_adj;
    HashMap<String,DualVertex> dual_map;
    int[] s;
    int[] t;
    int W;
    Digraph G;
    ArrayList<int[]> hooks;
    HashMap<Integer,DualVertex> dual_map_2;
    /**
     * Computes shortest-path from the source vertex s to destination vertex t
     * in graph G.
     * DO NOT MODIFY THE ARGUMENTS TO THIS CONSTRUCTOR
     *
     * @param  G the graph
     * @param  s the source vertex
     * @param  t the destination vertex
     * @param left the cost of taking a left turn
     * @param right the cost of taking a right turn
     * @param forward the cost of going forward
     * @throws IllegalArgumentException unless 0 <= s < V
     * @throws IllegalArgumentException unless 0 <= t < V
     * where V is the number of vertices in the graph G.
     */

    public ShortestPathFinder (final Digraph G, final int[] s, final int[] t,
    final int left, final int right, final int forward) {
        // YOUR CODE GOES HERE
        this.G=G;
        this.W=G.W();
        this.s = new int[2];
        this.t = new int[2];
        for (int k=0; k<s.length; k++) {
            this.s[k] = s[k];
            this.t[k] = t[k];
        }
        this.hooks = new ArrayList<int[]>();
        this.dual_adj = new ArrayList<ArrayList<Edge>>();
        this.dual_map = new HashMap<String,DualVertex>();
        this.dual_map_2 = new HashMap<Integer,DualVertex>();

        Queue<Integer> q_vert= new LinkedList<>();
        Queue<DualVertex> q_dual= new LinkedList<>();

        q_vert.add(s[0]*G.W() + s[1]);

        int[] arr_i_t = {-1,s[0]};
        int[] arr_j_t = {-1,s[1]};
        DualVertex created = new DualVertex(arr_i_t, arr_j_t, 0);
        q_dual.add(created);
        dual_map.put("-1-1" + String.valueOf(s[0]) + String.valueOf(s[0]), created);
        dual_map_2.put(0,created);
        int count=0;
        double cost=0;
        int num =1;
        //int indicator =0;

        while(!q_dual.isEmpty()) {

            int q_temp1 = q_vert.poll();
            Vertex q_temp = G.nodemap(q_temp1);

            ArrayList<Edge> temp = G.adjacency()[q_temp1];
            DualVertex q_dual_temp = q_dual.poll();
            ArrayList<Edge> dual_adj_temp = new ArrayList<Edge>();

            for (int i=0; i<temp.size(); i++) {
                Edge e = temp.get(i);
                Vertex v = G.nodemap(e.to());
                int[] arr_i = {q_temp.i, v.i};
                int[] arr_j = {q_temp.j, v.j};

                int[] temp_key = {arr_i[0], arr_j[0], arr_i[1], arr_j[1]};
                String p = String.valueOf(arr_i[0]) + String.valueOf(arr_j[0]) + String.valueOf(arr_i[1]) + String.valueOf(arr_j[1]);

                if (!dual_map.containsKey(p)) {
                    created = new DualVertex(arr_i, arr_j, num);
                    dual_map.put(p, created);
                    dual_map_2.put(created.key,created);
                    num = num +1;
                    q_dual.add(created);
                    //indicator =1;
                }


               // System.out.println(arr_i[0] + " " + arr_j[0] + " " +  arr_i[1] + " " + arr_j[1]);
                int turn_cost = get_cost(q_dual_temp ,created, left, right, forward,s);
                cost = e.weight() + turn_cost;
                Edge dual_e = new Edge(q_dual_temp.key, created.key, cost);
                dual_adj_temp.add(dual_e);
                //if (v.flag==1) continue;
                if (q_temp.key != s[0]*G.W()+s[1]) {
                    int[] arr = {q_dual_temp.i[0], q_dual_temp.j[0], q_dual_temp.i[1], q_dual_temp.j[1], created.i[1], created.j[1], turn_cost};
                    hooks.add(arr);
                }
                q_vert.add(e.to());
                v.flag=1;
            }

            //DualVertex t = q_dual.poll();
            //if (indicator ==1) {
                dual_adj.add(dual_adj_temp);
             //   indicator=0;
           // }
            count= count+1;
            for(int i=0; i<G.V(); i++) {
                G.nodemap(i).flag=0;
            }

        }

    }

    public int get_cost(DualVertex a, DualVertex b, int cleft, int cright, int cforward, int[] s) {


        int i1 = a.i[0];
        int j1 = a.j[0];
        int i2 = a.i[1];
        int j2 = a.j[1];
        int i3 = b.i[1];
        int j3 = b.j[1];

        if (i2==s[0] && j2==s[1]) return cforward;

        if ((i3-i1)*(i3-i1) + (j3-j1)*(j3-j1) ==4)
            return cforward;

        if ((i2-i1)==-1) {
            if ((j3-j2==1)) return cright;
            else return cleft;
        }

        if ((i2-i1)==1) {
            if ((j3-j2==-1)) return cright;
            else return cleft;
        }

        if ((j2-j1)==1) {
            if ((i3-i2==1)) return cright;
            else return cleft;
        }

        if ((j2-j1)==-1) {
            if ((i3-i2==1)) return cright;
            else return cleft;
        }

        return 0;


    }

    // Return number of nodes in dual graph
    public int numDualNodes() {
        // YOUR CODE GOES HERE
        return dual_adj.size();
    }

    // Return number of edges in dual graph
    public int numDualEdges() {
        // YOUR CODE GOES HERE
        int num =0;
        for (int i=0; i<dual_adj.size(); i++) {
            num = num + dual_adj.get(i).size();
        }
        return num;
    }

    // Return hooks in dual graph
    // A hook (0,0) - (1,0) - (1,2) with weight 8 should be represented as
    // the integer array {0, 0, 1, 0, 1, 2, 8}
    public ArrayList<int[]> dualGraph() {
        // YOUR CODE GOES HERE
        return hooks;
    }

    // Return true if there is a path from s to t.
    public boolean hasValidPath() {
        // YOUR CODE GOES HERE

        Queue<Integer> Q = new LinkedList<>();
        Q.add(s[0]*W + s[1]);
        G.nodemap(s[0]*W + s[1]).flag = 1;
        boolean result = false;

        while(!Q.isEmpty()){
            //System.out.println("while");
            int q_temp1 = Q.poll();

            Vertex q_temp = G.nodemap(q_temp1);
            //System.out.println(q_temp1 + " " + q_temp.i  + " " + q_temp.j);
            //q_temp.flag =1;

            ArrayList<Edge> temp = G.adjacency()[q_temp1];
            for (int i=0; i<temp.size(); i++) {
                //System.out.println("for");
                Edge e = temp.get(i);
                Vertex v = G.nodemap(e.to());
                if (v.flag==1) continue;
                Q.add(e.to());
                v.flag=1;

                if ((t[0]*W+t[1])==v.key) {
                    //System.out.println("hi_valid");
                    result = true;

                    Q = new LinkedList<>();
                    break;
                }

            }
        }


       // if (G.nodemap(G.V()-1).flag==1) result = true;

        for(int i=0; i<G.V(); i++) {
            G.nodemap(i).flag=0;
        }
        //System.out.println("in func :" + result);
        return result;

    }

    // Return the length of the shortest path from s to t.
    public int ShortestPathValue() {
        // YOUR CODE GOES HERE

        Queue<Integer> Q = new LinkedList<>();
        Q.add(s[0]*W + s[1]);
        G.nodemap(s[0]*W + s[1]).flag = 1;
        int result=0;

        while(!Q.isEmpty()){
            int q_temp1 = Q.poll();

            Vertex q_temp = G.nodemap(q_temp1);
            //System.out.println(q_temp1 + " " + q_temp.i  + " " + q_temp.j);
            //q_temp.flag =1;

            ArrayList<Edge> temp = G.adjacency()[q_temp1];
            for (int i=0; i<temp.size(); i++) {
                Edge e = temp.get(i);
                Vertex v = G.nodemap(e.to());
                if (v.flag==1) continue;

                Q.add(e.to());
                v.flag=1;
                v.level = q_temp.level+1;
                if ((t[0]*W+t[1])==v.key) {
                    //System.out.println("hi_value");
                    result = v.level;

                    Q = new LinkedList<>();
                    break;
                }
            }
        }

        for(int i=0; i<G.V(); i++) {
            G.nodemap(i).flag=0;
        }

        return result;
    }

    // Return the shortest path computed from s to t as an ArrayList of nodes,
    // where each node is represented by its location on the grid.
    public ArrayList<int[]> getShortestPath() {
        // YOUR CODE GOES HERE

        PriorityQueue<DualVertex> pq = new PriorityQueue<DualVertex>(new DualComparator());

        Hashmap<DualVertex,Double> storage_map = new  Hashmap<DualVertex,Double> ();

        DualVertex source = dual_map.get("-1-1" + String.valueOf(s[0]) + String.valueOf(s[0]));
        //storage_map.put(source, 0);

        source.shortest_value=0;
        source.flag=1;
        pq.add(source);

        DualVertex temp;

        while (pq.size()!=0) {
            temp = pq.poll();
            storage_map.put(temp, temp.shortest_value);
            ArrayList<Edge> temp_adj = dual_adj.get(temp.key);
            //double min = 10000;
            for (int i=0; i<temp_adj.size(); i++) {
                //Edge inserted = temp_adj.get(i).weight();
                //if (min>cost) min = cost;
                DualVertex temp_vert = dual_map_2.get(temp_adj.get(i).to());
                if (temp_vert.shortest_value> temp.shortest_value) {
                    temp_vert.shortest_value = temp.shortest_value + temp_adj.get(i).weight();
                }
                if (temp_vert.flag==1) continue;
                pq.add(temp_vert);
                temp_vert.flag=1;
            }
        }

        return null;
    }

    public static void main(String[] args) {
        String file = "in1.csv";
        Digraph G = new Digraph(file);
        int[] s = new int[]{0, 0};
        int[] t = new int[]{1, 2};
        int forward = 0;
        int left = 8;
        int right = 1;

        // Construct dual graph
        ShortestPathFinder sp = new ShortestPathFinder(G, s, t, left, right, forward);
        int nodes = sp.numDualNodes();
        int edges = sp.numDualEdges();
        System.out.println("Number of nodes in dual graph = " + nodes);
        System.out.println("Number of edges in dual graph = " + edges);
        ArrayList<int[]> hooks = sp.dualGraph();
        System.out.println("Hooks in orignal graph corresponding to edges in dual graph:");
        for (int[] hook: hooks){
            System.out.println("(" + hook[0] + "," + hook[1] + ") "
                            + "(" + hook[2] + "," + hook[3] + ") "
                            + "(" + hook[4] + "," + hook[5] + ") "
                            + hook[6]);
        }

        // Compute shortest path
        boolean hasPath = sp.hasValidPath();
        int pathLength = sp.ShortestPathValue();
        // ArrayList<int[]> path = sp.getShortestPath();
        System.out.println("Shortest path length = " + pathLength);

        // Print the path obtained
        if (!hasPath) {
            System.out.println("No valid path found.");
        }
        // else {
        //     for (int[] node : path) {
        //         System.out.println("(" + node[0] + "," + node[1] + ")");
        //     }
        //     System.out.println();



        for (int i=0; i<sp.dual_adj.size(); i++) {
            ArrayList<Edge> temp1 = sp.dual_adj.get(i);
            for (int j=0; j<temp1.size(); j++) {
                Edge temp = temp1.get(j);
                System.out.print(temp.from());
                System.out.print(" " + temp.to());
                System.out.println(" " + temp.weight());
            }
            System.out.println("next");
        }
        }
    }

    class DualComparator implements Comparator<DualVertex> () {

        public int compare (DualVertex a, DualVertex b ) {

            if (a.shortest_value<b.shortest_value) {
                return -1;
            }
            else if (a.shortest_value>b.shortest_value) {
                return 1;
            }
            else return 0;
        }
    }

