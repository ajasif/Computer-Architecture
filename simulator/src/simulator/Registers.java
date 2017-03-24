package simulator;

import java.util.Map;
import java.util.TreeMap;

// Represents 6 registers. $zero, $s0-s6
public class Registers {

	public static final int NUM_REGS = 7;
	
	public static final String $zero = "00000000000000000000000000000000";
	
	private Map<String, String> registers;
	
	public Registers() {
		registers = new TreeMap<String, String>();
		fill();
	}
	
	private void fill() {
		registers.put("$s0", $zero);
		registers.put("$s1", $zero);
		registers.put("$s2", $zero);
		registers.put("$s3", $zero);
		registers.put("$s4", $zero);
		registers.put("$s5", $zero);
		registers.put("$s6", $zero);
	}
	
	public void store(String register, String data) {
		registers.put(register,  data);
	}
	
	public String getRegister(String register) {
		if (register.equals("$zero")) {
			return $zero;
		} else {
			return registers.get(register);
		}
	}
	
	public void write() {
		System.out.println("Registers");
		for (int i = 0; i < NUM_REGS; i++) {
			System.out.println("$s" + i + ": " + registers.get("$s" + i));
		}
 		System.out.println("");
	}
	
}
