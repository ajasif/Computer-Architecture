package simulator;

public class PC {

	private int location;
	
	public PC(int start) {
		location = start;
	}
	
	public void set(int value) {
		location = value;
	}
	
	public int get() {
		return location;
	}
}
