package legendary.ParsingUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import legendary.Interfaces.IClass;
import legendary.Interfaces.IMethod;
import legendary.Interfaces.IModel;

public class ParsingMethodUtil {

	public static Map<String, String> returnPrimCheck;
	private List<String> usesClasses;

	static {
		returnPrimCheck = new HashMap<>();

		returnPrimCheck.put("V", "void");
		returnPrimCheck.put("Z", "boolean");
		returnPrimCheck.put("C", "char");
		returnPrimCheck.put("B", "byte");
		returnPrimCheck.put("S", "short");
		returnPrimCheck.put("I", "int");
		returnPrimCheck.put("F", "float");
		returnPrimCheck.put("J", "long");
		returnPrimCheck.put("D", "double");

	}

	public ParsingMethodUtil(List<String> usesClasses) {
		this.usesClasses = usesClasses;
	}

	public List<String> typeArgumentCollections(String in) {
		String s = in;
		if (s.isEmpty()) {
			return new ArrayList<String>();
		}
		String args = s.contains(")") ? s.substring(1, s.lastIndexOf(")")) : s.substring(1);
		if (args.length() < 1) {
			return new ArrayList<String>();
		}
		String argSet[] = args.split(";");
		List<String> finalArgSet = new LinkedList<>();
		for (int i = 0; i < argSet.length; i++) {
			String argVal = argSet[i];
			if (argVal.equals("java/lang/String")) {
				finalArgSet.add(argVal);
			} else if (argVal.startsWith("L")) {
				finalArgSet.add(argVal.substring(1));
			} else if (argVal.startsWith("[L")) {
				finalArgSet.add("[" + argVal.substring(2));
			} else {
				ParsingMethodUtil.parsePrimOut(argVal, finalArgSet);
			}
		}
		return this.convert(finalArgSet);
	}

	private List<String> convert(List<String> argSet) {
		List<String> finalArgSet = new LinkedList<>();
		int argSize = argSet.size();
		for (int i = 0; i < argSize; i++) {
			String arg = argSet.get(i);
			if (arg.equals(">")) {
				String argGet = finalArgSet.get(i - 1) + "\\>";
				argSet.remove(i);
				argSet.remove(i - 1);
				argSet.add(i - 1, argGet);
				finalArgSet.set(i - 1, argGet);
				i--;
				argSize--;
				continue;
			}
			if (returnPrimCheck.containsKey(arg)) {
				finalArgSet.add(returnPrimCheck.get(arg));
			} else if (arg.startsWith("[")) {
				finalArgSet.add(this.typeCollections(arg.substring(1)) + "[]");
			} else {
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
			this.usesClasses.add(s.substring(s.lastIndexOf("/") + 1).replace(";", ""));
		s = s.substring(s.lastIndexOf("/") + 1).replace("<", "\\<").replace(">", "\\>").replace("\\\\", "\\")
				.replace(";", "");
		return s;
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

	public Map<String, String> getPrimCheck() {
		return returnPrimCheck;
	}

	/*
	 * Primitive Representations for ASM 5 Primitive representations: 'V' - void
	 * 'Z' - boolean 'C' - char 'B' - byte 'S' - short 'I' - int 'F' - float 'J'
	 * - long 'D' - double
	 */

	@Deprecated
	public static void getCompleteCallStack(IModel model, IMethod method, int origDepth, int depth,
			StringBuilder sbMethods, Set<String> sbClasses) {
		if (depth == 0)
			return;
		if (!method.getCallStack().isEmpty())
			for (IClass c : model.getClasses())
				if (c.getClassName().equals(method.getCallStack().peek().get(0))) {
					sbClasses.add(SDEditFormatClass(c));
					// System.out.println(sbClasses.toString());
				}
		for (int i = 0; i < method.getCallStack().size(); i++) {
			List<String> methodDetails = method.getCallStack().poll();
			String className = methodDetails.get(1);
			innerloop: for (IClass c : model.getClasses()) {
				if (c.getClassName().equals(className)) {
					sbClasses.add(SDEditFormatClass(c));
					IMethod method2 = c.getMethods().get(methodDetails.get(2));
					methodDetails.add(method.getReturnType());
					sbMethods.append(SDEditFormatMethod(methodDetails));
					getCompleteCallStack(model, method2, origDepth, depth - 1, sbMethods, sbClasses);
					break innerloop;
				}
			}
		}
		if (depth == origDepth) {
			StringBuilder res = new StringBuilder();
			for (String s : sbClasses)
				res.append(s);
			res.append("\n");
			res.append(sbMethods);
			System.out.println(res);
		}
	}

	private static String SDEditFormatMethod(List<String> methodDetails) {
		String res = (String.format("%s:%s.%s\n", methodDetails.get(0), methodDetails.get(1), methodDetails.get(2)));
		return res;
	}

	private static String SDEditFormatClass(IClass c) {
		String res = (String.format("%s:%s\n", c.getClassName(), c.getClassName()));
		return res;
	}
}