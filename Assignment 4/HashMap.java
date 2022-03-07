package col106.assignment4.HashMap;
import java.util.Vector;
import java.util.ArrayList;

public class HashMap<V> implements HashMapInterface<V> {
	public class Node {
		String key;
		V value;

		Node (String key, V value) {
			this.key = key;
			this.value = value;
		}
	}

	public ArrayList<Node> arr;
	static int size =0;


	public HashMap(int size) {
		// write your code here
		this.arr = new ArrayList<Node>(size);
		this.size = size;
		for (int i=0; i<size; i++) {
			arr.add(null);
		}
	}

	public static int hashFunction (String key) {
		int n = key.length();
		int factor = 1;
		int ans=0;
		for (int i=n-1; i>=0; i--) {
			ans += key.charAt(n-i-1) * factor;
			ans = ans % size;
			factor *= 41;
			factor = factor % size;
		}
		return ans % size;
	}




	public V put(String key, V value){
		// write your code here
		int index = hashFunction(key);

		while (index < arr.size() && arr.get(index)!=null) {
			Node temp = arr.get(index);
			if (temp.key.equals(key)) {
				V result = temp.value;
				temp.value = value;
				arr.set(index, temp);
				return result;
			}
			index= (index + 1)%size;
		}

		Node temp = new Node (key, value);
		arr.set (index, temp);
		return null;
	}


	public V get(String key){
		// write your code here
		int index = hashFunction(key);
		int noOfLoop = 0;
		while (index < arr.size() && arr.get(index)!=null && noOfLoop<size) {

			Node temp = arr.get(index);
			if (temp.key.equals(key)) {
				V result = temp.value;
				return result;
			}
			noOfLoop +=1;
			index= (index + 1)%size;
		}
		return null;
	}

	public boolean remove(String key){
		int index = hashFunction(key);
		int noOfLoop = 0;
		int deleted = 0;
		int current = 0;
		int count = 0;
		int curr_hash=0;

		while (index<arr.size() && arr.get(index)!=null  && noOfLoop<size) {

			Node temp = arr.get(index);
			if (temp.key.equals(key)) {
				deleted = index;
				current = (index+1)%size;
				count = 0;
				Node temp2 = arr.get(current);
				while(count<size && temp2!=null) {

					if (temp2!=null) {
						curr_hash = hashFunction(temp2.key);
						if (current!=curr_hash) {
							if ((deleted<curr_hash && current > deleted && current < curr_hash) || (deleted>=curr_hash)) {
								Node curr_node =  arr.get(current);
								arr.set(deleted, curr_node);
								deleted = current;
							}
						}
					}
					current = (current+1)%size;
					count+=1;
					temp2 = arr.get(current);
				}
				arr.set(deleted, null);
				return true;
			}
			noOfLoop+=1;
			index = (index+1)%size;
		}
		return false;
	}

	public boolean contains(String key){
		// write your code here
		int index = hashFunction(key);
		int noOfLoop = 0;
		while (index < arr.size() && arr.get(index)!=null  && noOfLoop<size) {

			Node temp = arr.get(index);
			if (temp.key.equals(key)) {
				return true;
			}
			noOfLoop +=1;
			index= (index + 1)%size;
		}
		return false;
	}

	public Vector<String> getKeysInOrder(){
		// write your code here
		Vector<String> result = new Vector<>();
		int index = 0;
		while (index < arr.size()) {
			if (arr.get(index)!=null) {
				Node temp = arr.get(index);
				result.add(temp.key);
			}
			index+=1;
		}
		return result;
}
}
