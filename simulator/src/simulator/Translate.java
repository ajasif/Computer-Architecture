package simulator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Translate {

	private final File assemblyCode;
	
	public Translate(final File assemblyCode) {
		this.assemblyCode = assemblyCode;
	}
	
	public List<String> getCode() throws FileNotFoundException {
		Scanner sc = new Scanner(assemblyCode);
		List<String> codes = new ArrayList<String>();
		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			String code = getObjectCode(line);
			codes.add(code);
		}
		sc.close();
		return codes;
	}
	
	private String getObjectCode(final String line) {
		StringBuffer code = new StringBuffer();
	    String[] parts = line.split("\\s+");
	    String instr = parts[0];
	    if (instr.equals("lw") || instr.equals("sw")) {
	    	String rt = parts[1].substring(0, parts[1].length()-1);
	    	String rtCode = registerCodes(rt);
	    	
	    	String[] memLocation = parts[2].split("\\(");
	    	
	    	String offsetCode = Integer.toBinaryString(Integer.parseInt(memLocation[0]));
	    	int zeroPadding = 16 - offsetCode.length();
	    	StringBuffer offset = new StringBuffer();
	    	for (int i = 0; i < zeroPadding; i++) {
	    		offset.append("0");
	    	}
	    	offset.append(offsetCode);
	    	
	    	String rs = memLocation[1].substring(0, memLocation[1].length()-1);
	    	String rsCode = registerCodes(rs);
	    	String opcode = opcodes(instr);
	    	code.append(opcode);
	    	code.append(rsCode);
	    	code.append(rtCode);
	    	code.append(offset);	    	
	    } else { // add instruction
	    	String opcode = opcodes("add");
	    	String rd = registerCodes(parts[1].substring(0,parts.length-1));
	    	String rs = registerCodes(parts[2].substring(0, parts.length-1));
	    	String rt = registerCodes(parts[3]);
	    	code.append(opcode);
	    	code.append(rs);
	    	code.append(rt);
	    	code.append(rd);
	    	code.append("00000100000"); //shamt + funct
	    }
		return code.toString();
	}
	
	private String registerCodes(String register) {
		switch (register) {
			case "$zero":
				return "00000";
			case "$s0":
				return "10000";
			case "$s1":
				return "10001";
			case "$s2":
				return "10010";
			case "$s3":
				return "10011";
			case "$s4":
				return "10100";
			case "$s5":
				return "10101";
			case "$s6":
				return "10110";
			default :
				return null;
		}
	}
	
	private String opcodes(String instruction) {
		if (instruction.equals("lw")) {
			return "100011";
		} else if (instruction.equals("sw")) {
			return "101011";
		} else if (instruction.equals("add")) {
			return "000000";
		} else {
			System.out.println("Instruction not supported");
			return null;
		}
	}
}
