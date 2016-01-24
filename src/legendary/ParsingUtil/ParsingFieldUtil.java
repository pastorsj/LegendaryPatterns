package legendary.ParsingUtil;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ParsingFieldUtil {
	public static Map<String, String> primCodes;

	/*
	 * Primitive Representations for ASM 5 Primitive representations: 'V' - void
	 * 'Z' - boolean 'C' - char 'B' - byte 'S' - short 'I' - int 'F' - float 'J'
	 * - long 'D' - double
	 */
	static {
		primCodes = new HashMap<>();
		primCodes.put("V", "void");
		primCodes.put("Z", "boolean");
		primCodes.put("C", "char");
		primCodes.put("B", "byte");
		primCodes.put("S", "short");
		primCodes.put("I", "int");
		primCodes.put("F", "float");
		primCodes.put("J", "long");
		primCodes.put("D", "double");
	}

	public static Set<String> getBaseFields(String in) {
		if(in.equals("Ljava/util/Set<Llegendary/Interfaces/IClass;>;"))
			System.out.println();
		Set<String> res = new HashSet<>();
		if (in.contains("<")) {
			String split1, split2;
			split1 = in.substring(0, in.indexOf("<"));
			split2 = in.substring(in.indexOf("<") + 1, (in.contains(">") ? in.indexOf(">") : in.length()));
			res.add(split1.substring(split1.lastIndexOf("/")));
			for (String s : split2.split(";")) {
				res.addAll(getBaseFields(s));
			}
		} else {
			if (in.contains("/")) {
				res.add(in.substring(in.lastIndexOf("/") + 1).replace(";", ""));
			} else
				res.add(in);
		}
		return res;
	}

	public static String typeFieldCollections(String in) {
		String s = in;
		s = replacePrims(s);
		if (in.contains("<")) {
			String split1 = s.substring(0, s.indexOf("<"));
			String split2 = s.substring(s.indexOf("<") + 1);
			s = split1.substring(split1.lastIndexOf("/") + 1) + "<";
			String[] split = split2.split(";");
			for (int i = 0; i < split.length; i++) {
				String s2 = split[i];
				s += typeFieldCollections(s2);
				if ((i < split.length - 1) && (!split[i + 1].equals(">")))
					s += ", ";
			}
		}
		return s.substring(s.lastIndexOf("/") + 1).replace("<", "\\<").replace(">", "\\>").replace("\\\\", "\\")
				.replace(";", "");
	}

	// shouldn't be necessary
	private static String replacePrims(String s) {
		String res = "";
		boolean flag = false;
		for (String s2 : s.split(";")) {
			if (flag || !primCodes.containsKey(s2.charAt(0) + ""))
				res += s2 + ";";

			else {
				flag = true;
				res += primCodes.get(s2.charAt(0) + "") + ";" + s2.substring(1);
			}
		}
		if (flag)
			return replacePrims(res);
		return res;
	}
}
