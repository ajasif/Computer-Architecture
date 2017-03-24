package simulator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * TCSS 372B
 * @author Asif Jamal
 * Loads an assembly program. Translates the assembly to machine code. Stores
 * the machine code in IMEM. Stores data in DMEM. Displays IMEM, DMEM and 
 * registers. Implements a datapath/pipeline. Controlled by PC fetches and 
 * runs instructions through pipeline. Stores sum back in DMEM.
 * 
 * In this case: Takes 4 from location 5, and 7 from location 6 and stores in 
 * location 9. 
 */
public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		File assemblyCode = new File("input.txt"); //Load assembly
		List<String> machineCode = (new Translate(assemblyCode)).getCode(); //Translate to machine code
		IMEM imem = new IMEM(machineCode); //Store machine code in IMEM
		DMEM dmem = new DMEM(); //Data Memory
		Registers regs = new Registers(); //Initialize registers
		
		//Store initial values to add, store in D-MEM
		dmem.store(5, "00000000000000000000000000000100"); // 4
		dmem.store(6, "00000000000000000000000000000111"); // 7
		
		//Display I-MEM, D-MEM & Registers
		regs.write();
		dmem.write();
		imem.write();
				
		//Pipeline
		PC pc = new PC(0); // Set PC to start at zero.
		ALU alu = new ALU();
		while (imem.containsInstruction(pc.get())) {
			//Instruction Fetch
			String instruction = imem.getInstruction(pc.get());
			pc.set(pc.get() + 1); //Increment Program Counter
			
			String opcode = instruction.substring(0, 6);
			if (opcode.equals("100011")) { // LW Instruction
				// Instruction Decode
				String rs = instruction.substring(6, 11);
				String rt = instruction.substring(11, 16);
				String offset = instruction.substring(16);
				String rsValue = regs.getRegister(getRegSymbol(rs));
				
				// Execution
				int base = Integer.parseInt(rsValue, 2);
				int offsetValue = Integer.parseInt(offset, 2) / 4;
				int address = alu.getAddress(base, offsetValue);
				
				// Data Access 
				String value = dmem.get(address);
				//Write-Back
				regs.store(getRegSymbol(rt), value);
				
			} else if (opcode.equals("101011")) { // SW Instruction
				// Instruction Decode
				String rs = instruction.substring(6,11);
				String rt = instruction.substring(11, 16);
				String offset = instruction.substring(16);
				String rsValue = regs.getRegister(getRegSymbol(rs));

				//Execution
				int base = Integer.parseInt(rsValue, 2);
				int offsetValue = Integer.parseInt(offset, 2) / 4;
				int address = alu.getAddress(base, offsetValue);
				
				// Data Access
				dmem.store(address, regs.getRegister(getRegSymbol(rt)));
			} else { // 000000 Add Instruction
				// Instruction Decode
				String rs = instruction.substring(6, 11);
				String rt = instruction.substring(11, 16);
				String rd = instruction.substring(16, 21);
				String rsValue = regs.getRegister(getRegSymbol(rs));
				String rtValue = regs.getRegister(getRegSymbol(rt));
				String rdSymbol = getRegSymbol(rd);
				
				//Execution
				int first = Integer.parseInt(rsValue, 2);
				int second = Integer.parseInt(rtValue, 2);
				int sum = alu.getSum(first, second);

				//Write-Back
				String sumBinary = Integer.toBinaryString(sum);
				int zerosInFront = 32 - sumBinary.length();
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < zerosInFront; i++) {
					sb.append("0");
				}
				sb.append(sumBinary);
				regs.store(rdSymbol, sb.toString());
			}
		}
		System.out.println("After Execution");
		dmem.write();
	}
	
	public static String getRegSymbol(String registerCode) {
		switch (registerCode) {
			case "00000":
				return "$zero";
			case "10000":
				return "$s0";
			case "10001":
				return "$s1";
			case "10010":
				return "$s2";
			case "10011":
				return "$s3";
			case "10100":
				return "$s4";
			case "10101":
				return "$s5";
			case "10110":
				return "$s6";
			default :
			return null;
		}
	}

}
