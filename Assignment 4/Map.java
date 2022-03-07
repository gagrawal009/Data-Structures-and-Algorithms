package col106.assignment4.Map;
import col106.assignment4.HashMap.HashMap;
import col106.assignment4.WeakAVLMap.WeakAVLMap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;



public class Map<V> {

    static PrintStream out;
    public PrintStream fileout() {
        return out;
    }

	public Map() {
		// write your code here
	}

	public void eval(String inputFileName, String outputFileName) {
        try {
            out = new PrintStream(new FileOutputStream(outputFileName, false), true);
            System.setOut(out);
        }
        catch (FileNotFoundException e) {
            System.err.println("Input file Not found. ");
        }
        File file;
        URL url = Map.class.getResource(inputFileName);
        file = new File(url.getPath());

        BufferedReader br = null;
        try {
            long ht_insert = 0;
            long wt_insert = 0;
            long ht_delete = 0;
            long wt_delete = 0;
            long start = 0;
            long end = 0;

            br = new BufferedReader(new FileReader(file));
            String st = br.readLine().trim();

            int tableSize = Integer.parseInt(st);
            HashMap<String> hashmap = new HashMap<>(tableSize);
            WeakAVLMap<String,String> wavl = new WeakAVLMap<>();

            while ((st = br.readLine()) != null) {

                String op = st.substring(0,1);
                String key_value = st.substring(1,st.length());
                String key;
                String value;
                switch (op.strip()) {

                    case "I":
                        key = key_value.split(",")[0].trim();
                        value = key_value.split(",")[1].trim();

                        start = System.currentTimeMillis();
                        String val1 = hashmap.put(key, value);
                        end = System.currentTimeMillis();
                        ht_insert+=end-start;

                        start = System.currentTimeMillis();
                        String val2 = wavl.put(key,value);
                        end = System.currentTimeMillis();
                        wt_insert+=end-start;
                        break;

                    case "D":
                        key = key_value.trim();

                        start = System.currentTimeMillis();
                        Boolean res1 = hashmap.remove(key);
                        end = System.currentTimeMillis();
                        ht_delete+=end-start;

                        start = System.currentTimeMillis();
                        String res2 = wavl.remove(key);
                        end = System.currentTimeMillis();
                        wt_delete+=end-start;
                        break;

                    default:
                        System.err.println("Unknown command: " + st);
                }
            }

        System.out.println("Operations WAVL HashMap");
        System.out.println("Insertions " + wt_insert + " " + ht_insert);
        System.out.println("Deletions "+ " " + wt_delete + " " + ht_delete);

        } catch (FileNotFoundException e) {
                System.err.println("Input file Not found. " + file.getAbsolutePath());
        } catch (NullPointerException ne) {
                ne.printStackTrace();
        } catch (Exception e) {
                e.printStackTrace();
        }
    }


}
