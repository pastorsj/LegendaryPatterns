package legendary.ParsingUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ParsingFieldUtil {
	private Map<String, String> returnPrimCheck;
	private Set<String> baseFields;
	
	public ParsingFieldUtil(Set<String> baseFields) {
		returnPrimCheck = new HashMap<>();
		this.baseFields = baseFields;
		this.initialize();
	}
	
	public Map<String, String> getPrimCheck() {
		return this.returnPrimCheck;
	}
	
	public String typeCollections(String in) {
		String s = in;
		if(s.equals("Z")) {
			System.out.println("Find me" + s);
		}
		if (in.contains("<")) {
			String split1 = s.substring(0, s.indexOf("<"));
			this.baseFields.add(split1);
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
			this.baseFields.add(s.substring(s.lastIndexOf("/") + 1));
		return s.substring(s.lastIndexOf("/") + 1).replace("<", "\\<")
				.replace(">", "\\>").replace("\\\\", "\\").replace(";", "");
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
