package simulator;

public class ALU {
	public ALU() {
		
	}
	
	public int getAddress(int base, int offset) {
		return base + offset;
	}
	
	public int getSum(int first, int second) {
		return first + second;
	}
}
