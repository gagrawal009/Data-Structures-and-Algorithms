package col106.assignment3.Election;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;




public class Election implements ElectionInterface {
	/*
	 * Do not touch the code inside the upcoming block
	 * If anything tempered your marks will be directly cut to zero
	*/

	public static void main() {
		ElectionDriverCode EDC = new ElectionDriverCode();
		System.setOut(EDC.fileout());
	}



	/*
	 * end code
	 */


	public BSTKey store;
	public ArrayList<cand> arr;

	public Election () {
		store = new BSTKey();
		arr = new ArrayList<>();
	}


	//write your code here
    public void insert(String name, String candID, String state, String district, String constituency, String party, String votes){
		//write your code here

		cand candidate = new cand (name, candID, state, district, constituency, party, votes);
		store.insert (votes, candidate);
		arr.add(candidate);

	}


	public void updateVote(String name, String candID, String votes){
		int i =0;
		String p  ="";
		for (i=0; i<arr.size(); i++) {
			cand candidate = arr.get(i);
			if (candidate.candID.compareTo(candID)==0) {
				p = candidate.votes;
				candidate.votes = votes;
				break;
			}
		}

		cand candidate = arr.get(i);
		store.delete (p);
		store.insert (votes, candidate);




		//update in BST

		//write your code here
	}

	public void topkInConstituency(String constituency, String k){
		//write your code here
		int i =0;
		HeapKey cons = new HeapKey();
		for (i=0; i<arr.size(); i++) {
			cand candidate = arr.get(i);
			if (candidate.constituency.compareTo(constituency)==0) {
				cons.insert (candidate.votes, candidate);
			}
		}
		int j = Integer.parseInt(k);
		for (i=0; i<j; i++) {
			cand temp = cons.extractMax();
			if (temp!=null) System.out.println(temp.name + ", " + temp.candID + ", " + temp.party);
			else break;
		}
	}

	private static ArrayList<String> sort_list (ArrayList<String> s) {
		//used bubble sort to sort
		for(int i = 0; i < s.size(); ++i) {
            for (int j = i + 1; j < s.size(); ++j) {
                if (s.get(i).compareTo(s.get(j)) > 0) {

                    String temp = s.get(i);
                    s.set(i, s.get(j));
                    s.set(j, temp);
                }
            }
        }
        return s;



	}

	public void leadingPartyInState(String state){

		HashMap<String, Integer> map = new HashMap<>();
		int i;
		int m;
		int n;
		for (i=0; i<arr.size(); i++) {
			cand candidate = arr.get(i);
			if (candidate.state.compareTo(state)==0) {
				if (map.containsKey (candidate.party)) {
					m = map.get(candidate.party);
					n = Integer.parseInt(candidate.votes);
					map.replace (candidate.party, m+n);
				}
				else {
					n = Integer.parseInt(candidate.votes);
					map.put (candidate.party, n);
				}
			}
		}
		ArrayList<String> s = new ArrayList<>();
		int max = 0;

		for (String key : map.keySet()) {
			m = map.get(key);
			if (max<m) {
				max =m;
			}
		}
		m = max;
		for (String key : map.keySet()) {
			n= map.get(key);
			if (m==n) {
				s.add(key);
			}
		}
		sort_list (s);
		for(i = 0; i < s.size(); ++i) {
        	System.out.println(s.get(i));
        }

		//write your code here
	}

	public void cancelVoteConstituency(String constituency){
		//write your code here
		ArrayList<String> s = new ArrayList<>();
		int i =0;
		int j=0;
		for (i=0; i<arr.size(); i++) {
			cand candidate = arr.get(i);

			if (candidate.constituency.compareTo(constituency)==0) {

				s.add(candidate.candID);

			}
		}

		sort_list(s);


		for (i=0; i<s.size(); i++) {

			String p = s.get(i);
			for (j=0; j<arr.size(); j++) {

				cand candidate = arr.get(j);
				if (candidate.candID.compareTo(p)==0) {
					store.delete(candidate.votes);

					arr.remove(j);
					break;
				}
			}
		}




	}

	public void leadingPartyOverall(){
		//write your code here
		HashMap<String, Integer> map = new HashMap<>();
		int i;
		int m;
		int n;
		for (i=0; i<arr.size(); i++) {
			cand candidate = arr.get(i);

			if (map.containsKey (candidate.party)) {
				m = map.get(candidate.party);
				n = Integer.parseInt(candidate.votes);
				map.replace (candidate.party, m+n);
			}
			else {
				n = Integer.parseInt(candidate.votes);
				map.put (candidate.party, n);
			}

		}

		ArrayList<String> s = new ArrayList<>();
		int max = 0;

		for (String key : map.keySet()) {
			m = map.get(key);
			if (max<m) {
				max =m;
			}
		}
		m=max;
		for (String key : map.keySet()) {
			n= map.get(key);
			if (m==n) {
				s.add(key);
			}
		}
		sort_list (s);
		for(i = 0; i < s.size(); ++i) {
        	System.out.println(s.get(i));
        }


	}

	public void voteShareInState(String party, String state){
		//write your code here
		int i=0;
		int m =0;
		int total = 0;
		for (i=0; i<arr.size(); i++) {
			cand candidate = arr.get(i);
			if (candidate.state.compareTo(state)==0) {
				total = total + Integer.parseInt(candidate.votes);
				if (candidate.party.compareTo(party)==0) {
					m = m + Integer.parseInt(candidate.votes);
				}
			}
		}


		int p = (int) (((float) m / (float) total)*100);
		System.out.println(p);
	}

	public void printElectionLevelOrder() {
		//write your code here
		store.printBST();

	}


}











