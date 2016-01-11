package legendary.ParsingUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ParsingMethodUtil {
	
	private Map<String, String> returnPrimCheck;
	private List<String> usesClasses;

	
	public ParsingMethodUtil(List<String> usesClasses) {
		this.returnPrimCheck = new HashMap<>();
		this.usesClasses = usesClasses;
		this.initialize();
	}
	
	public List<String> typeArgumentCollections(String in) {
		String s = in;
		if(s.isEmpty()) {
			return new ArrayList<String>();
		}
		String args = s.contains(")") ? s.substring(1, s.lastIndexOf(")")) : s.substring(1);
		if(args.length() < 1) {
			return new ArrayList<String>();
		}
		String argSet[] = args.split(";");
		List<String> finalArgSet = new LinkedList<>();
		for(int i = 0; i <  argSet.length; i++) {
			String argVal = argSet[i];
			if(argVal.equals("java/lang/String")) {
				finalArgSet.add(argVal);
			} else if(argVal.startsWith("L")) {
				finalArgSet.add(argVal.substring(1));
			} else if(argVal.startsWith("[L")) {
				finalArgSet.add("[" + argVal.substring(2));
			} else {
				this.parsePrimOut(argVal, finalArgSet);
			}
		}
		return this.convert(finalArgSet);
	}
	
	private List<String> convert(List<String> argSet) {
		List<String> finalArgSet = new LinkedList<>();
		for(int i = 0; i < argSet.size(); i++) {
			String arg = argSet.get(i);
			if(arg.equals(">")) {
				String argGet = finalArgSet.get(i-1) + "\\>";
				argSet.remove(i);
				argSet.remove(i-1);
				argSet.add(i-1, argGet);
				finalArgSet = argSet;
				continue;
			}
			if(this.returnPrimCheck.containsKey(arg)) {
				finalArgSet.add(this.returnPrimCheck.get(arg));
			} else if(arg.startsWith("[")) {
				finalArgSet.add(this.typeCollections(arg.substring(1)) + "[]");
			} else {
				//Do type collections stuff
				//The last holdout
				finalArgSet.add(this.typeCollections(arg));
			}
		}
		return finalArgSet;
	}
	
	public String typeCollections(String in) {
		String s = in;
		if (in.contains("<")) {
			String split1 = s.substring(0, s.indexOf("<"));
			this.usesClasses.add(split1);
			String split2 = s.substring(s.indexOf("<") + 1);
			s = split1.substring(split1.lastIndexOf("/") + 1) + "<";
			String[] split = split2.split(";");
			for (int i = 0; i < split.length; i++) {
				String s2 = split[i];
				s += typeCollections(s2);
				if ((i < split.length - 1) && (!split[i + 1].equals(">")))
					s += ", ";
			}
		} else
			this.usesClasses.add(s.substring(s.lastIndexOf("/") + 1).replace(
					";", ""));
		s = s.substring(s.lastIndexOf("/") + 1).replace("<", "\\<")
				.replace(">", "\\>").replace("\\\\", "\\").replace(";", "");
		return s;
	}
	
	private void parsePrimOut(String arg, List<String> argSet) {
		argSet.add(String.valueOf(arg.charAt(0)));
		arg = arg.substring(1);
		if(arg.length() == 0) {
			return;
		}
		if(arg.startsWith("L")) {
			argSet.add(arg.substring(1));
			return;
		} else if(arg.startsWith("[L")) {
			argSet.add("[" + arg.substring(2));
		} else {
			parsePrimOut(arg, argSet);
		}
	}
	
	public Map<String, String> getPrimCheck() {
		return this.returnPrimCheck;
	}
	
	/*
	 * Primitive Representations for ASM 5
	 * Primitive representations:
	 * 'V' - void
	 * 'Z' - boolean
	 * 'C' - char
	 * 'B' - byte
	 * 'S' - short
	 * 'I' - int
	 * 'F' - float
	 * 'J' - long
	 * 'D' - double
	 */
	private void initialize() {
		this.returnPrimCheck.put("V", "void");
		this.returnPrimCheck.put("Z", "boolean");
		this.returnPrimCheck.put("C", "char");
		this.returnPrimCheck.put("B", "byte");
		this.returnPrimCheck.put("S", "short");
		this.returnPrimCheck.put("I", "int");
		this.returnPrimCheck.put("F", "float");
		this.returnPrimCheck.put("J", "long");
		this.returnPrimCheck.put("D", "double");
	}
}
