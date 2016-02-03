package legendary.ParsingUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import legendary.asm.DesignParser;

// TODO: Auto-generated Javadoc
/**
 * The Class GeneralUtil.
 */
public class GeneralUtil {

	/** The prim codes. */
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

	/**
	 * Type argument collections.
	 *
	 * @param in the in
	 * @return the list
	 */
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

	/**
	 * Convert.
	 *
	 * @param argSet the arg set
	 * @return the list
	 */
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

	/**
	 * Type method collections.
	 *
	 * @param in the in
	 * @param usesClasses the uses classes
	 * @return the string
	 */
	public static String typeMethodCollections(String in, List<String> usesClasses) {
		if(in.length()<1)
			return in;
		String s = in;
		String res = "";
		if(primCodes.containsKey(s)){
			return primCodes.get(s);
		}
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
					if (res.endsWith(", "))
						res = res.substring(0, res.lastIndexOf(", "));
					res += ">";
				}
			}
		} else {
			res = split1.replace("/", ".");
			if (res.contains("."))
				res = res.substring(0, res.lastIndexOf(".")) + "::" + (res.substring(res.lastIndexOf(".") + 1));
		}
		if (res.endsWith(", "))
			res = res.substring(0, res.lastIndexOf(", "));
		return res.replace(", >", ">");
	}

	/**
	 * Parses the prim out.
	 *
	 * @param arg the arg
	 * @param argSet the arg set
	 */
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

	/**
	 * Gets the base fields.
	 *
	 * @param in the in
	 * @return the base fields
	 */
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
			if (s.contains("."))
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

	/**
	 * Type field collections.
	 *
	 * @param in the in
	 * @return the string
	 */
	public static String typeFieldCollections(String in) {
		return typeMethodCollections(in, new ArrayList<>());
	}

	/**
	 * Gets the classes from dir.
	 *
	 * @param dir the dir
	 * @return the classes from dir
	 */
	public static List<String> getClassesFromDir(File dir) {
		ArrayList<String> res = new ArrayList<String>();
		ArrayList<String> res2 = new ArrayList<String>();
		if (dir.isDirectory()) {
			File[] dirFiles = dir.listFiles();
			for (int i = 0; i < dirFiles.length; i++) {
				System.out.println(dirFiles[i].toString());
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
	
	/**
	 * Write and exec graph viz.
	 *
	 * @param builder the builder
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void writeAndExecGraphViz(StringBuilder builder) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter("./input_output/text.dot"));
		writer.write(builder.toString().replace("$", ""));
		writer.close();
//		Runtime rt = Runtime.getRuntime();
//		rt.exec("./lib/Graphviz2.38/bin/dot -Tpng ./input_output/text.dot -o ./input_output/GraphVizoutput.png");
		// Desktop.getDesktop().open(new
		// File("./input_output/GraphVizoutput.png"));
	}
	
	/**
	 * Write and exec sd edit.
	 *
	 * @param builder the builder
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void writeAndExecSDEdit(StringBuilder builder) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter("./input_output/text.sd"));
		writer.write(builder.toString());
		writer.close();
//		Runtime rt = Runtime.getRuntime();
//		rt.exec("java -jar ./lib/sdedit-4.2-beta1.jar -o"
//				+ "./input_output/SDEoutput.png -t png ./input_output/text.sd");
		// Desktop.getDesktop().open(new File("./input_output/SDEoutput.png"));
		// System.out.println(builder.toString());
	}
}
