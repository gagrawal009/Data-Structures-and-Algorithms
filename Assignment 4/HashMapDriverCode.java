package col106.assignment4.HashMap;
import java.util.Vector;
import java.io.*;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;


public class HashMapDriverCode{
	static PrintStream out;
	public PrintStream fileout() {
		return out;
	}
	public static void main(String[] args) throws IOException {

		/*File file;
		file = new File(args[0]);
		out = new PrintStream(new FileOutputStream(args[1], false), true);
		System.setOut(out);*/

		File file;
        out = new PrintStream(new FileOutputStream("OUT_HEAP", false), true);
        //System.setOut(out);
        if (args.length == 0) {
            URL url = HashMapDriverCode.class.getResource("HashMap_INP3");
            file = new File(url.getPath());
        } else {
            file = new File(args[0]);
        }

		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
			String st = br.readLine().trim();
			if(!st.equals("TABLE_SIZE")){
				throw new Exception("Table size not specified");
			}
			st = br.readLine().trim();
			int tableSize = Integer.parseInt(st);

			HashMap<Integer> map = new HashMap<Integer>(tableSize);
			while ((st = br.readLine()) != null) {
				String[] cmd = st.split(" ");
				if (cmd.length == 0) {
					System.err.println("Error parsing: " + st);
					return;
				}
				String key_value;
				String key;
				int value;
				switch (st.strip()) {
					case "PUT":
						key_value = br.readLine();
						key = key_value.split(",")[0].trim();
						value = Integer.parseInt(key_value.split(",")[1].trim());
						Integer val = map.put(key, value);
						System.out.println("Put (" + key + "," + value + "):" + " " + val);
						break;
					case "GET":
						key_value = br.readLine();
						key = key_value.trim();
						System.out.println("Get " + key + ": " + map.get(key));
						break;
					case "REMOVE":
						key_value = br.readLine();
						key = key_value.trim();
						System.out.println("Remove "+key+": " + map.remove(key));
						try{
							map.remove(key);
						} catch (Exception e){
							System.out.println("Key Not Found");
						}
						break;
					case "CONTAINS":
						key_value = br.readLine();
						key = key_value.trim();
						System.out.println("Contains " + key + ": " + map.contains(key));
						break;
					case "KEY_SET":
						System.out.print("Key Set: ");
						Vector<String> keys = map.getKeysInOrder();
						for(String i : keys){
							System.out.print(i + " ");
						}
						System.out.println();
						break;
					default:
						System.err.println("Unknown command: " + st);
				}
			}
		} catch (FileNotFoundException e) {
				System.err.println("Input file Not found. " + file.getAbsolutePath());
		} catch (NullPointerException ne) {
				ne.printStackTrace();
		} catch (Exception e) {
				e.printStackTrace();
		}
	}
}
