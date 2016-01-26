package legendary.ParsingUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import legendary.asm.DesignParser;

public class GeneralUtil {

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

	public static List<String> typeArgumentCollections(String in) {
		String s = in;
		if (s.isEmpty()) {
			return new ArrayList<String>();
		}
		String args = s.contains(")") ? s.substring(1, s.lastIndexOf(")")) : s.substring(1);
		if (args.length() < 1) {
			return new ArrayList<String>();
		}
		String argSet[] = args.split(";");
		List<String> finalArgSet = new ArrayList<>();
		for (int i = 0; i < argSet.length; i++) {
			String argVal = argSet[i];
			if (argVal.equals("java/lang/String")) {
				finalArgSet.add(argVal);
			} else if (argVal.startsWith("L")) {
				finalArgSet.add(argVal.substring(1));
			} else if (argVal.startsWith("[L")) {
				finalArgSet.add("[" + argVal.substring(2));
			} else {
				parsePrimOut(argVal, finalArgSet);
			}
		}
		return convert(finalArgSet);
	}

	private static List<String> convert(List<String> argSet) {
		List<String> finalArgSet = new LinkedList<>();
		int argSize = argSet.size();
		for (int i = 0; i < argSize; i++) {
			String arg = argSet.get(i);
			if (arg.equals(">")) {
				String argGet = finalArgSet.get(i - 1) + ">";
				argSet.remove(i);
				argSet.remove(i - 1);
				argSet.add(i - 1, argGet);
				finalArgSet.set(i - 1, argGet);
				i--;
				argSize--;
				continue;
			}
			if (primCodes.containsKey(arg)) {
				finalArgSet.add(primCodes.get(arg));
			} else if (arg.startsWith("[")) {
				finalArgSet.add(typeMethodCollections(arg.substring(1) + "[]", new ArrayList<>()));
			} else {
				finalArgSet.add(typeMethodCollections(arg, new ArrayList<>()));
			}
		}
		return finalArgSet;
	}

	public static String typeMethodCollections(String in, List<String> usesClasses) {
		String s = in;
		String res = "";
		if (s.contains(")")) {
			s = s.substring(s.lastIndexOf(")") + 1);
		}
		if (s.charAt(0) == 'L') {
			s = s.substring(1);
		}
		String[] split = s.split(";");
		String split1 = split[0];
		if (split1.contains("<")) {
			res += split1.substring(0, split1.indexOf("<") + 1).replace("/", ".");
			res = res.substring(0, res.lastIndexOf(".")) + "::" + (res.substring(res.lastIndexOf(".") + 1));
			split[0] = split1.substring(split1.indexOf("<") + 1);
			for (String s2 : split) {
				if (!s2.equals(">"))
					res += typeMethodCollections(s2, usesClasses) + ", ";
				else {
					if(res.endsWith(", "))
						res = res.substring(0, res.lastIndexOf(", "));
					res += ">";
				}
			}
		} else {
			res = split1.replace("/", ".");
			res = res.substring(0, res.lastIndexOf(".")) + "::" + (res.substring(res.lastIndexOf(".") + 1));
		}
		if(res.endsWith(", "))
			res = res.substring(0, res.lastIndexOf(", "));
		return res.replace(", >", ">");
	}

	private static void parsePrimOut(String arg, List<String> argSet) {
		argSet.add(String.valueOf(arg.charAt(0)));
		arg = arg.substring(1);
		if (arg.length() == 0) {
			return;
		}
		if (arg.startsWith("L")) {
			argSet.add(arg.substring(1));
			return;
		} else if (arg.startsWith("[L")) {
			argSet.add("[" + arg.substring(2));
		} else {
			parsePrimOut(arg, argSet);
		}
	}

	public static Set<String> getBaseFields(String in) {
		Set<String> res = new HashSet<>();
		String s = in;
		if (primCodes.containsKey(s)) {
			res.add(primCodes.get(s));
			return res;
		}
		if (s.startsWith("L"))
			s = s.substring(1);
		if (!s.contains("::")) {
			s = s.replace("/", ".");
			s = s.substring(0, s.lastIndexOf(".")) + "::" + s.substring(s.lastIndexOf(".") + 1, s.length());
		}
		if (s.contains("<")) {
			String split1, split2;
			split1 = s.substring(0, s.indexOf("<"));
			split2 = s.substring(s.indexOf("<") + 1, (s.contains(">") ? s.indexOf(">") : s.length()));
			res.add(split1);
			for (String s2 : split2.split(";")) {
				res.addAll(getBaseFields(s2));
			}
		} else {
			if (s.contains("/")) {
				res.add(s.substring(s.lastIndexOf("/") + 1).replace(";", ""));
			} else
				res.add(s.replace(";", ""));
		}
		return res;
	}

	public static String typeFieldCollections(String in) {
//		String s = in;
//		if (s.contains(DesignParser.packageName)) {
//			s = s.substring(s.lastIndexOf(DesignParser.packageName)).replace("/", ".");
//			s = s.substring(0, s.lastIndexOf(".")) + "::" + s.substring(s.lastIndexOf(".") + 1, s.length());
//		}
//		if (s.contains("<")) {
//			String split1 = s.substring(0, s.indexOf("<"));
//			String split2 = s.substring(s.indexOf("<") + 1);
//			s = split1.substring(split1.lastIndexOf("/") + 1) + "<";
//			String[] split = split2.split(";");
//			for (int i = 0; i < split.length; i++) {
//				String s2 = split[i];
//				s += typeFieldCollections(s2);
//				if ((i < split.length - 1) && (!split[i + 1].equals(">")))
//					s += ", ";
//			}
//		}
//		return s.replace("<", "\\<").replace(">", "\\>").replace("\\\\", "\\").replace(";", "");
		return typeMethodCollections(in, new ArrayList<>());
	}

	public static List<String> getClassesFromDir(File dir) {
		ArrayList<String> res = new ArrayList<String>();
		ArrayList<String> res2 = new ArrayList<String>();
		if (dir.isDirectory()) {
			File[] dirFiles = dir.listFiles();
			for (int i = 0; i < dirFiles.length; i++) {
				res2.addAll(getClassesFromDir(dirFiles[i]));
			}
			for (String r : res2) {
				r = r.substring(r.lastIndexOf(DesignParser.packageName),
						(r.contains("java") ? r.lastIndexOf("java") - 1 : r.length())).replace("\\", ".");
				res.add(r);
			}

		} else if (dir.toString().endsWith(".java")) {
			res.add(dir.toString());
		}
		return res;
	}
}
