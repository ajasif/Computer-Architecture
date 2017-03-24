package simulator;

import java.util.Map;
import java.util.TreeMap;

// Data Memory. Stores up to 10 32bit(4word) units of data.
public class DMEM {
	
	public static final int SIZE = 10; // SIZE of memory
	
	private final Map<Integer, String> data; // Storage for data
	

	public DMEM() {
		data = new TreeMap<Integer, String>();
		fill();
	}
	
	// Fills memory with 10 address mapped to empty units of data
	private void fill() {
		for (int i = 0; i < 10; i++) {
			data.put(i, "00000000000000000000000000000000");
		}
	}
	
	// Stores data at given address
	public void store(int address, String info) {
		data.put(address, info);
	}
	
	// Loads data from memory
	public String get(int address) {
		return data.get(address);
	}
	
	public void write() {
		System.out.println("Data Memory:");
		System.out.println("Address    Data");
		for (int i : data.keySet()) {
			System.out.print(i + "    ");
			System.out.println(data.get(i));
		}
		System.out.println("");
	}
}
