package col106.assignment4.WeakAVLMap;
import java.util.Vector;
import java.util.LinkedList;
import java.util.Queue;

@SuppressWarnings("unchecked")
public class WeakAVLMap<K extends Comparable,V> implements WeakAVLMapInterface<K,V>{

	public class Node<K extends Comparable,V>  {
		K key;
		V value;
		Node<K,V> left;
		Node<K,V> right;
		Node<K,V> parent;
		int rank;

		Node(K key, V value, Node<K,V> parent) {
			this.key = key;
			this.value = value;
			this.parent = parent;
			this.left = null;
			this.right = null;
			this.rank = 0;
		}

		Node() {
			this.key = null;
			this.value = null;
			this.parent = null;
			this.left = null;
			this.right = null;
			this.rank = -1;
		}
	}

	Node<K,V> root;
	int size;
	int rotations;
	int leftR=0;
	int rightR=0;

	public WeakAVLMap(){

		this.root = null;
		this.size=0;
		this.rotations=0;
	}

	private void toRotateLeft (Node<K,V> x) {

		Node<K,V> y = x.right;
		Node<K,V> t2 = y.left;
		y.left=x;
		x.right=t2;

		Node<K,V> initial_parent = x.parent;

		y.parent = initial_parent;
		if (t2!=null) {
			t2.parent = x;
		}

		if (initial_parent==null) {
			root=y;
		}

		else if (initial_parent.left == x) {
			initial_parent.left = y;
		}

		else if (initial_parent.right == x) {
			initial_parent.right = y;
		}
		x.parent = y;
		leftR+=1;
		rotations+=1;
	}

	private void toRotateRight (Node<K,V> y) {

		Node<K,V> x = y.left;
		Node<K,V> t2 = x.right;
		x.right = y;
		y.left=t2;

		Node<K,V> initial_parent = y.parent;
		x.parent = initial_parent;

		if (t2!=null) {
			t2.parent = y;
		}

		if (initial_parent==null) {
			root=x;
		}

		else if (initial_parent.left == y) {
			initial_parent.left = x;
		}

		else if (initial_parent.right == y) {
			initial_parent.right = x;
		}
		y.parent = x;
		rightR+=1;
		rotations+=1;
	}


	public Node<K,V> insertHelp(Node<K,V> root,K key, V value) {

		if (root == null) {
			root = new Node(key,value,null);
			return root;
		}
		if (root.key.compareTo(key) < 0) {
			root.right = insertHelp(root.right,key,value);
			root.right.parent = root;
			return root;
		}
		if (root.key.compareTo(key) > 0) {
			root.left = insertHelp(root.left,key,value);
			root.left.parent = root;
			return root;
		}
		return root;
	}

	public V put(K key, V value){
		Node<K,V> present =  getNode(root, key);

		if (present == null) {
			root = insertHelp(root, key,value);
			Node<K,V> find =  getNode(root, key);
			fixAfterInsert(find);
			return null;
		}
		else if (present!=null) {
			V result = present.value;
			present.value = value;
			return result;
		}
		return null;
	}

	public void fixAfterInsert (Node<K,V> q) {

	    int rank_difference = 0;
	    if (q.parent!=null) rank_difference = q.parent.rank - q.rank;
	    int sibling_rd = 0;
	    int balance = 0;
	    Node<K,V> parent = q.parent;

	    while (parent!=null && rank_difference!=1) {

	        if (parent.right==q) {
	            if (parent.left!=null) sibling_rd = parent.rank - parent.left.rank;
	            else if ((parent.left==null && parent.rank==1)) sibling_rd = 2;

	            if (sibling_rd>=2) {
	                if (q.right!=null) balance = q.rank - q.right.rank;
	                if (q.right==null || balance>=2) {
	                    q.rank--;
	                    q.left.rank++;
	                    toRotateRight(q);
	                }
	                parent.rank--;
	                toRotateLeft (parent);
	                break;
	            }
	        }
	        else if (parent.left==q) {
	            if (parent.right!=null) sibling_rd = parent.rank - parent.right.rank;
	            else if ((parent.right==null && parent.rank==1)) sibling_rd = 2;

	            if (sibling_rd>=2) {
	                if (q.left!=null) balance = q.rank - q.left.rank;
	                if (q.left==null || balance>=2) {
	                    q.rank--;
	                    q.right.rank++;
	                    toRotateLeft(q);
	                }
	                parent.rank--;
	                toRotateRight (parent);
	                break;
	            }
	        }
	        q=q.parent;
	        q.rank++;
	        parent = q.parent;
	        if (parent!=null) rank_difference = parent.rank - q.rank;
	    }
	}


	public Node<K,V> getNode (Node<K,V> root, K key) {

		if (root==null) return null;
		if (root.key.compareTo(key)==0) {
			return root;
		}

		if (root.key.compareTo(key)<0) {
			return getNode (root.right, key);
		}

		if (root.key.compareTo(key)>0) {
			return getNode (root.left, key);
		}
		return root;
	}

	public Node<K,V> findReplace (Node<K,V> root) {
		Node<K,V> temp = root.right;
		while (temp.left!=null) {
			temp=temp.left;
		}
		return temp;
	}


	public void fixAfterDelete (Node<K,V> p, Node<K,V> s, Node<K,V> q) {

		int deltaRank = 0;
		int left_rank=0;
		int right_rank=0;
		int balance=0;
		int left_balance=0;
		int right_balance=0;
		deltaRank = p.rank - q.rank;

		boolean decide;
		if (p.right==null && p.left==null) {
			if (p.rank==1) {
				decide = false;
			}
			else decide = true;
		}
		else decide = true;

		while (deltaRank > 2 || decide==false) {

			if (s==null) balance = p.rank+1;
			else balance = p.rank - s.rank;

		    if (balance != 2) {
		    	if (s.left==null) left_rank=-1;
		    	else left_rank = s.left.rank;
				left_balance = s.rank - left_rank;

				if (s.right==null) right_rank=-1;
		    	else right_rank = s.right.rank;
				right_balance = s.rank - right_rank;

				if (left_balance == 2 && right_balance == 2) {
			    	s.rank--;
			    	p.rank--;
				}

				else if (p.right == s) {
			    	if (right_balance == 1) {
			    		if (s.left == null) p.rank-=2;
			    		else p.rank--;
						s.rank++;
						toRotateLeft(p);
						break;
			    	}
				    if (right_balance!=1) {
						p.rank -= 2;
						s.rank--;
						s.left.rank += 2;
						toRotateRight(s);
						toRotateLeft(p);
						break;
				    }
				}

				else if (p.left == s) {
				    if (left_balance == 1) {
				    	if (s.right == null) p.rank-=2;
				    	else p.rank--;
						s.rank++;
						toRotateRight(p);
						break;
				    }
				    if (left_balance!=1) {
						p.rank -= 2;
						s.rank--;
						s.right.rank += 2;
						toRotateLeft(s);
						toRotateRight(p);
						break;
				    }
				}
		    }
		    else if (balance == 2) p.rank--;

			if (p.parent!=null) {
			    q = p;
			    p = p.parent;
			    deltaRank = p.rank - q.rank;

			    if (p.right == q)
			    	s = p.left;
			    else if (p.left == q)
			    	s = p.right;

			    if (p.right==null && p.left==null) {
					if (p.rank==1) {
						decide = false;
					}
					else decide = true;
					}
				else decide = true;
				}

			else return;
			}
	}


	public void deleteNode (Node<K,V> target) {

		if (target.left!=null && target.right!=null) {
			Node<K,V> replace = findReplace(target);
			target.key = replace.key;
			target.value = replace.value;
			target = replace;
		}

		if (target.left==null && target.right==null) {

			if (target.parent==null) {
				root = null;
				return;
			}
			Node<K,V> replace = target.parent;
			Node<K,V> sibling = null;

			if(replace.left==target) {
				replace.left = null;
				sibling = replace.right;
			}
			else if (replace.right==target) {
				replace.right = null;
				sibling = replace.left;
			}
			target.rank--;
			target.parent=null;
			fixAfterDelete (replace, sibling, target);
		}

		else if (target.left!=null && target.right==null) {
			Node<K,V> replace = target.left;
			Node<K,V> sibling = null;
			replace.parent = target.parent;

			if (target.parent==null) {
				root = replace;
				return;
			}
			else if (target.parent.right ==target) {
				target.parent.right= replace;
				sibling = target.parent.left;
			}

			else if (target.parent.left ==target) {
				target.parent.left= replace;
				sibling = target.parent.right;
			}
			target.left=null;
			target.right=null;
			target.parent=null;

			fixAfterDelete (replace.parent, sibling, replace);
		}

		else if (target.left==null && target.right!=null) {
			Node<K,V> replace = target.right;
			Node<K,V> sibling = null;
			replace.parent = target.parent;

			if (target.parent==null) {
				root = replace;
				return;
			}
			else if (target.parent.right ==target) {
				target.parent.right= replace;
				sibling = target.parent.left;
			}

			else if (target.parent.left ==target) {
				target.parent.left= replace;
				sibling = target.parent.right;
			}
			target.left=null;
			target.right=null;
			target.parent=null;

			fixAfterDelete (replace.parent, sibling, replace);
		}
	}


	public V remove(K key){
		// write your code her

		Node<K,V> target = getNode (root, key);
		if (target==null) return null;
		V result = target.value;
		size--;
		deleteNode (target);
		return result;

	}

	public V getHelp (Node<K,V> root, K key) {

		if (root==null) return null;
		if (key.compareTo(root.key)<0) {
	            return getHelp(root.left, key);
	    }

	    else if (key.compareTo(root.key)>0) {
	        return getHelp (root.right, key);
	    }
        else {
        	V result = root.value;
        	return result;
        }
	}

	public V get(K key){
		// write your code her
		return getHelp(root, key);
	}

	public void searchRangeHelp(Node<K, V> root, Vector<V> vec, K key1, K key2) {

		if (root==null) return;

		if (root.key.compareTo(key1)>=0 && root.key.compareTo(key2)<=0) {
			searchRangeHelp (root.left, vec, key1, key2);
			vec.add (root.value);
			searchRangeHelp (root.right, vec, key1, key2);
		}
		if (root.key.compareTo(key1)<0) {
			searchRangeHelp (root.right, vec, key1, key2);
		}
		if (root.key.compareTo(key2)>0) {
			searchRangeHelp (root.left, vec, key1, key2);
		}
		return;
	}

	public Vector<V> searchRange(K key1, K key2){

		Vector<V> vec = new Vector<V>();

		searchRangeHelp(root, vec, key1, key2);
		return vec;
	}

	public int rotateCount(){

		return rotations;
	}

	public int max (int a, int b) {
		if (a>=b) return a;
		else return b;
	}

	public int height (Node<K, V> root) {
		if (root==null) return 0;
		else return (1+max (height(root.left), height (root.right)));
	}

	public int getHeight(){
		// write your code her
		return height(root);
	}

	public Vector<K> BFS(){
		// write your code her

		Vector<K> result = new Vector<K>();
		Queue<Node<K,V>> q = new LinkedList<>();

        if (root!=null) q.add (root);
        while (q.size()!=0) {
            Node<K,V> temp = q.remove();
            if (temp.left!=null) q.add(temp.left);
            if (temp.right!=null) q.add(temp.right);
            result.add (temp.key);
        }

		return result;
	}

}
