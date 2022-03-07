package col106.assignment3.Election;

import java.util.LinkedList;
import java.util.Queue;

public class BSTKey  {

    public class BSTNode {
        String key;
        cand value;
        BSTNode left;
        BSTNode right;

        public BSTNode (String key, cand value) {
            this.key = key;
            this.value = value;
            this.left = null;
            this.right = null;
        }
    }

    public BSTNode root;

    public BSTKey () {
        root = null;
    }

    @SuppressWarnings("unchecked")
    public BSTNode insertHelp (BSTNode root, String key, cand value) {

        if (root == null) {
            root = new BSTNode (key, value);
            return root;
        }
        int m = Integer.parseInt(key);
        int n = Integer.parseInt(root.key);

        if (m<n) {
            root.left = insertHelp (root.left, key, value);
            return root;
        }
         if (m>n) {
            root.right= insertHelp (root.right, key, value);
            return root;
        }
        return root;
    }

    public void insert(String key, cand value) {
        //write your code here
        root = insertHelp (this.root, key, value);
    }

    public void update(String key, cand value) {
        //write your code here
        delete(key);
        insert (key, value);
    }

    private BSTNode findHelp (BSTNode root, String key) {
        Queue<BSTNode> q = new LinkedList<>();
        q.add (root);
        BSTNode temp = null;
        while (q.size()!=0) {
            temp = q.remove();
            if (temp.key == key) break;
            if (temp.left!=null) q.add(temp.left);
            if (temp.right!=null) q.add(temp.right);

        }
        return temp;
    }

    public BSTNode findCand (String key) {
        return findHelp (this.root, key);
    }

    @SuppressWarnings("unchecked")
    private BSTNode deleteHelp (BSTNode root, String key) {

        if (root==null) return null;
        if (Integer.parseInt(root.key)==Integer.parseInt(key)) {
            if (root.left==null && root.right==null) {
                //delete root;
                return null;
            }
            else if (root.left!=null && root.right==null) {
                BSTNode curr = root.left;
                //delete root;
                return curr;
            }
            else if (root.left==null && root.right!=null) {
                BSTNode curr = root.right;
                //delete root;
                return curr;
            }
            else {
                BSTNode temp1 = root.right;
                while (temp1.left!=null) temp1=temp1.left;
                root.value = temp1.value;
                root.key = temp1.key;
                root.right = deleteHelp (root.right, temp1.key);
                return root;
            }
        }
        if (Integer.parseInt(root.key)>Integer.parseInt(key)) {
            root.left = deleteHelp (root.left, key);

        }
        if (Integer.parseInt(root.key)<Integer.parseInt(key)) {
            root.right = deleteHelp (root.right, key);
        }
        return root;
    }

    public void delete(String key) {
        //write your code here
        //BSTNode temp = find (this.root, key);

        deleteHelp (this.root, key);
    }

    private void printHelp (BSTNode root) {

        Queue<BSTNode> q = new LinkedList<>();
        q.add (root);
        while (q.size()!=0) {
            BSTNode temp = q.remove();
            if (temp.left!=null) q.add(temp.left);
            if (temp.right!=null) q.add(temp.right);
            System.out.println(temp.value.name + ", " + temp.value.candID + ", " + temp.value.state + ", " + temp.value.district + ", " + temp.value.constituency + ", " + temp.value.party + ", " + temp.value.votes);
            }
    }


    public void printBST () {
        //write your code here
        printHelp (this.root);

    }

}
