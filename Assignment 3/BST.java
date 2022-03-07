package col106.assignment3.BST;
import java.util.LinkedList;
import java.util.Queue;

public class BST<T extends Comparable, E extends Comparable> implements BSTInterface<T, E>  {
	/*
	 * Do not touch the code inside the upcoming block
	 * If anything tempered your marks will be directly cut to zero
	*/
    @SuppressWarnings("unchecked")
	public static void main() {
		BSTDriverCode BDC = new BSTDriverCode();
		System.setOut(BDC.fileout());
	}

    public class BSTNode {
        T key;
        E value;
        BSTNode left;
        BSTNode right;

        public BSTNode (T key, E value) {
            this.key = key;
            this.value = value;
            this.left = null;
            this.right = null;
        }
    }
    public BSTNode root;

    public BST () {
        root = null;
    }
	/*
	 * end code
	 * start writing your code from here
	 */

	//write your code here
    @SuppressWarnings("unchecked")
    private BSTNode insertHelp (BSTNode root, T key, E value) {

        if (root == null) {
            root = new BSTNode (key, value);
            return root;
        }
        if (value.compareTo(root.value)==-1) {
            root.left = insertHelp (root.left, key, value);
            return root;
        }
         if (value.compareTo(root.value)==1) {
            root.right= insertHelp (root.right, key, value);
            return root;
        }
        return root;
    }

    public void insert(T key, E value) {
		//write your code here
        root = insertHelp (this.root, key, value);
    }

    public void update(T key, E value) {
		//write your code here
        delete(key);
        insert (key, value);
    }

    private BSTNode find (BSTNode root, T key) {
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

    @SuppressWarnings("unchecked")
    private BSTNode deleteHelp (BSTNode root, BSTNode temp) {

        if (root.value.compareTo(temp.value)==0) {
            if (root.left==null && root.right==null) {
                return null;
            }
            else if (root.left!=null && root.right==null) {
                BSTNode curr = root.left;
                return curr;
            }
            else if (root.left==null && root.right!=null) {
                BSTNode curr = root.right;
                return curr;
            }
            else {
                BSTNode temp1 = root.right;
                while (temp1.left!=null) temp1=temp1.left;
                root.value = temp1.value;
                root.key = temp1.key;
                root.right = deleteHelp (root.right, temp1);
                return root;
            }
        }
        if (root.value.compareTo(temp.value)==1) {
            root.left = deleteHelp (root.left, temp);

        }
        if (root.value.compareTo(temp.value)==-1) {
            root.right = deleteHelp (root.right, temp);
        }
        return root;
    }

    public void delete(T key) {
		//write your code here
        BSTNode temp = find (this.root, key);
        deleteHelp (this.root, temp);
    }

    private void printHelp (BSTNode root) {
        Queue<BSTNode> q = new LinkedList<>();
        q.add (root);
        while (q.size()!=0) {
            BSTNode temp = q.remove();
            if (temp.left!=null) q.add(temp.left);
            if (temp.right!=null) q.add(temp.right);
            System.out.println(temp.key + ", "+ temp.value);
        }
    }

    public void printBST () {
		//write your code here
        printHelp (this.root);

    }

}
