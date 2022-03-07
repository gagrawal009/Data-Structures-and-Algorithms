package col106.assignment3.Election;

import java.util.ArrayList;

public class HeapKey {

    public class Node {
        String key;
        cand value;

        public Node (String key, cand value) {
            this.key = key;
            this.value = value;
        }
    }
    public ArrayList<Node> arr;

    public HeapKey() {
        arr = new ArrayList<>();
    }

    // write your code here
    @SuppressWarnings("unchecked")

    public void insert(String key, cand value) {
        //write your code here
        Node curr = new Node (key, value);
        arr.add(curr);

        int ci = arr.size()-1;
        while (ci>0) {
            int pi = (ci-1)/2;

            int m = Integer.parseInt(arr.get(ci).key);
            int n = Integer.parseInt(arr.get(pi).key);

            if (m>n) {
                Node temp = arr.get(ci);
                arr.set (ci, arr.get(pi));
                arr.set (pi, temp);
            }
            ci = pi;
        }
    }

    @SuppressWarnings("unchecked")
    public cand extractMax() {
        //write your code here

        if (arr.isEmpty()) return null;
        cand ans = arr.get(0).value;
        arr.set (0, arr.get(arr.size()-1));
        arr.remove (arr.size()-1);

        int pi = 0;
        int lci = 2*pi+1;
        int rci = 2*pi+2;
        int swapi =0;
        int o = 0;

        while (lci<arr.size()) {

            swapi = pi;
            int m = Integer.parseInt(arr.get(swapi).key);
            int n = Integer.parseInt(arr.get(lci).key);

            if (rci<arr.size()) {
                o = Integer.parseInt(arr.get(rci).key);
            }


            if (m<n) {
                swapi = lci;
            }
            if (rci<arr.size() && m<o) {
                swapi = rci;
            }
            if (swapi == pi) {
                break;
            }

            Node temp = arr.get(swapi);
            arr.set (swapi, arr.get(pi));
            arr.set (pi, temp);


            pi = swapi;
            lci = 2*pi+1;
            rci = 2*pi+2;
        }

        return ans;
    }



}
