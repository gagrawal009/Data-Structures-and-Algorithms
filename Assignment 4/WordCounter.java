package col106.assignment4.HashMap;

public class WordCounter {

	public WordCounter(){
		// write your code here

	}

	public int count(String str, String word){
		// write your code here

        HashMap<Integer> my_map = new HashMap<>(1);

        for (int i=0; i<=str.length()-word.length(); i++) {


            if (word.equals(str.substring(i, i+word.length()))) {
                if (my_map.get(word)==null) {
                    my_map.put(word, 1);
                }
                else {
                    my_map.put(word, my_map.get(word) + 1);
                }
            }
        }

        if (my_map.get(word)==null) {
            return 0;
        }
        else {
            return my_map.get(word);
        }
	}

}

