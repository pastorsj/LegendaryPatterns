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

import legendary.DisplayScreen.ImageProxy;
import legendary.mainScreen.LegendaryProperties;

/**
 * This class contains most parsing code for asm syntax
 */
public class GeneralUtil {

	/** The prim codes. */
	public static Map<String, String> primCodes;
	public static String packageName;
	public static volatile boolean isGenning = false;

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
	 * Takes the types of the arguments of a method as a string and returns a
	 * list containing readable representations of each.
	 * 
	 * In this case, readable refers to how the type would be declared in
	 * standard code.
	 * 
	 * For example,
	 * "Ljava/util/List<Ljava/lang/String;>;Ljava/util/List<Ljava/lang/Integer;>;"
	 * would be returned as "[List<String>, List<Integer>]"
	 *
	 * @param in
	 *            A string containing the arguments
	 * @return the list of arguments
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
	 * Helper function for typeArgumentsCollection
	 * 
	 * Converts a list of input types from raw form to readable form
	 *
	 * @param argSet
	 *            A set of arguments
	 * @return a readable set of arguments
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
	 * Converts a raw string of types to a readable one, and modifies a given
	 * list to include the names of all classes used by the method
	 * 
	 * In this case, readable refers to how the type would be declared in
	 * standard code.
	 * 
	 * For example,
	 * "Ljava/util/List<Ljava/lang/String;>;Ljava/util/List<Ljava/lang/Integer;>;"
	 * is returned as "List<String>, List<Integer>"
	 *
	 * @param in
	 *            the raw string representation of the types of a method's args
	 * @param usesClasses
	 *            an arraylist in which to store the names of all classes used
	 *            by the method
	 * @return a readable string representation of the classes
	 */
	public static String typeMethodCollections(String in, List<String> usesClasses) {
		if (in.length() < 1)
			return in;
		String s = in;
		String res = "";
		if (primCodes.containsKey(s)) {
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
	 * Parses the primitives out of the argument set
	 *
	 * @param arg
	 *            the argument
	 * @param argSet
	 *            the argument set
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
	 * Converts a string of input types to a list of readable strings of the
	 * associated classes.
	 * 
	 * For example, input Ljava/util/List<Ljava/lang/String;>; becomes
	 * [java.util::List, java.lang::String]
	 *
	 * @param in
	 *            The string containing the field
	 * @return the base fields of the input string
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
	 * The same as type method collections, but does not require or modify a
	 * list. Intended to be used with the single type of a field.
	 *
	 * @param in
	 *            The field collection
	 * @return a readable string representation of the type of a field
	 */
	public static String typeFieldCollections(String in) {
		return typeMethodCollections(in, new ArrayList<>());
	}

	/**
	 * Gets the classes from given directory.
	 *
	 * @param dir
	 *            the directory
	 * @return paths to the classes in the directory
	 */
	public static List<String> getClassesFromDir(File dir, int dirlevels) {
		ArrayList<String> res = new ArrayList<String>();
		ArrayList<String> res2 = new ArrayList<String>();
		if (dir.isDirectory()) {
			File[] dirFiles = dir.listFiles();
			for (int i = 0; i < dirFiles.length; i++) {
				res2.addAll(getClassesFromDir(dirFiles[i], dirlevels));
			}
			for (String r : res2) {
				r = r.replace("\\", ".");
				int start = r.lastIndexOf(packageName);
				r = r.substring(start, (r.contains(".java") ? r.lastIndexOf(".java") : r.length())).replace("\\", ".");
				r = r.substring(r.lastIndexOf(packageName),
						(r.contains(".class") ? r.lastIndexOf(".class") : r.length())).replace("\\", ".");
				int levels = r.length() - r.replace(".", "").length();
				if (dirlevels >= 0 && levels >= dirlevels)
					continue;
				res.add(r);
			}

		} else if (dir.toString().endsWith(".java") || dir.toString().endsWith(".class")) {
			res.add(dir.toString());
		}
		return res;
	}

	/**
	 * Write and exec graph viz.
	 *
	 * @param builder
	 *            the builder
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void writeAndExecGraphViz(StringBuilder builder) throws IOException {
		LegendaryProperties properties = LegendaryProperties.getInstance();
		BufferedWriter writer = new BufferedWriter(new FileWriter(properties.getOutputDirectory() + "text.dot"));
		writer.write(builder.toString().replace("$", ""));
		writer.close();
		new Thread() {
			public void run() {
//				fileNum++;
				Runtime rt = Runtime.getRuntime();
				if (System.getProperty("os.name").contains("Mac")) {
					String cmd[] = { properties.getDotPath(), "-Tpng", properties.getOutputDirectory() + "text.dot",
							"-o", properties.getOutputDirectory() + "GraphVizOutput.png" };
					Process p;
					try {
						p = rt.exec(cmd);
						isGenning = true;
						p.waitFor();
						isGenning = false;
					} catch (InterruptedException | IOException e) {
					}
				} else {
					Process p;
					try {
						p = rt.exec(properties.getDotPath() + " -Tpng " + properties.getOutputDirectory()
								+ "text.dot -o " + properties.getOutputDirectory() + "GraphVizOutput.png");
						isGenning = true;
						p.waitFor();
						isGenning = false;
					} catch (InterruptedException | IOException e) {
					}
				}
				synchronized(ImageProxy.waitOnMe) {
					ImageProxy.waitOnMe.notify();
				}
			}
		}.start();
	}

	/**
	 * Write and exec sd edit.
	 *
	 * @param builder
	 *            the builder
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void writeAndExecSDEdit(StringBuilder builder) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter("./input_output/text.sd"));
		writer.write(builder.toString().replace("::", "."));
		writer.close();
		Runtime rt = Runtime.getRuntime();
		rt.exec("java -jar ./lib/sdedit-4.2-beta1.jar -o "
				+ "./input_output/SDEoutput.png -t png ./input_output/text.sd");
		// Desktop.getDesktop().open(new File("./input_output/SDEoutput.png"));
	}
}
