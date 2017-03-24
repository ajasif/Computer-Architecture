package simulator;

import java.util.List;

// Stores up to 10 lines of code in Instruction Memory.
public class IMEM {
	
	private final String[] storage;
	
	private final int lastInstruction;
	
	public IMEM(List<String> codes) {
		storage = new String[10];
		lastInstruction = codes.size();
		store(codes);
	}
	
	private void store(List<String> codes) {
		for (int i = 0; i < codes.size(); i++) {
			storage[i] = codes.get(i);
		}
		for (int j = codes.size(); j < storage.length; j++) {
			storage[j] = "00000000000000000000000000000000";
		}
	}
	
	public void write() {
		System.out.println("Instruction Memory:");
		for (int i = 0; i < storage.length; i++) {
			System.out.println(i + " " + storage[i]);
		}
		System.out.println("");
	}
	
	public String getInstruction(int PC) {
		return storage[PC];
	}
	
	public boolean containsInstruction(int PC) {
		return PC < lastInstruction;
	}
}
