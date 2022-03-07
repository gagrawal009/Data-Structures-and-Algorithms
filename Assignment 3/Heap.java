package col106.assignment3.Heap;
import java.util.ArrayList;
public class Heap<T extends Comparable, E extends Comparable> implements HeapInterface <T, E> {
	/*
	 * Do not touch the code inside the upcoming block
	 * If anything tempered your marks will be directly cut to zero
	*/
	@SuppressWarnings("unchecked")
	public static void main() {
		HeapDriverCode HDC = new HeapDriverCode();
		System.setOut(HDC.fileout());
	}

	/*
	 * end code
	 */
	public class Node {
		T key;
		E value;

		public Node (T key, E value) {
			this.key = key;
			this.value = value;
		}
	}
	public ArrayList<Node> arr;

	public Heap() {
		arr = new ArrayList<>();
	}
	// write your code here
	@SuppressWarnings("unchecked")
	public void insert(T key, E value) {
		//write your code here
		Node curr = new Node (key, value);
		arr.add(curr);

		int ci = arr.size()-1;
		while (ci>0) {
			int pi = (ci-1)/2;

			if (arr.get(ci).value.compareTo(arr.get(pi).value)==1) {
				Node temp = arr.get(ci);
				arr.set (ci, arr.get(pi));
				arr.set (pi, temp);
			}
			ci = pi;
		}
	}

	@SuppressWarnings("unchecked")
	public E extractMax() {
		//write your code here
		E ans = arr.get(0).value;
		arr.set (0, arr.get(arr.size()-1));
		arr.remove (arr.size()-1);

		int pi = 0;
		int lci = 2*pi+1;
		int rci = 2*pi+2;
		int swapi =0;

		while (lci<arr.size()) {
			swapi = pi;
			if (arr.get(swapi).value.compareTo(arr.get(lci).value)==-1) {
				swapi = lci;
			}
			if (rci<arr.size() && arr.get(swapi).value.compareTo(arr.get(rci).value)==-1) {
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

	@SuppressWarnings("unchecked")
	public void delete(T key) {
		//write your code here

		int i =0;
		for (i=0; i<arr.size(); i++) {
			if (key.compareTo(arr.get(i).key)==0) {
				break;
			}
		}
		int ri =i;
		arr.set (ri, arr.get(arr.size()-1));
		arr.remove (arr.size()-1);

		int pi = ri;
		int lci = 2*pi+1;
		int rci = 2*pi+2;
		int swapi =pi;

		while (lci<arr.size()) {
			swapi = pi;
			if (arr.get(swapi).value.compareTo(arr.get(lci).value)==-1) {
				swapi = lci;
			}
			if (rci<arr.size() && arr.get(swapi).value.compareTo(arr.get(rci).value)==-1) {
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

	}

	@SuppressWarnings("unchecked")
	public void increaseKey(T key, E value) {
		//write your code here
		int i =0;
		for (i=0; i<arr.size(); i++) {
			if (key.compareTo(arr.get(i).key)==0) {
				break;
			}
		}
		int ui =i;
		E old = arr.get(ui).value;
		arr.get(ui).value = value;

		if (value.compareTo(old)==1) {
			int ci = ui;
			while (ci>0) {
				int pi = (ci-1)/2;

				if (arr.get(ci).value.compareTo(arr.get(pi).value)==1) {
					Node temp = arr.get(ci);
					arr.set (ci, arr.get(pi));
					arr.set (pi, temp);
				}
				ci = pi;
			}
		}
		else {
			int ri =i;

			int pi = ri;
			int lci = 2*pi+1;
			int rci = 2*pi+2;
			int swapi =pi;

			while (lci<arr.size()) {
				swapi = pi;
				if (arr.get(swapi).value.compareTo(arr.get(lci).value)==-1) {
					swapi = lci;
				}
				if (rci<arr.size() && arr.get(swapi).value.compareTo(arr.get(rci).value)==-1) {
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
		}


	}

	public void printHeap() {
		//write your code here
		int size = arr.size();

		for (int i = 0; i <size; i++) {
			Node temp = arr.get(i);
	        System.out.println(temp.key + ", " + temp.value);
	     }

	}

	public int size() {
		return arr.size();
	}

}
