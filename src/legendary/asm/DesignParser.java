package legendary.asm;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import legendary.Classes.Class;
import legendary.Classes.ClassParser;
import legendary.Interfaces.IClass;

/*
 * Modification made by Sam Pastoriza and Jason Lane
 */
public class DesignParser {
	// public static final String[] classes = {
	// // "legendaryTests.ManualTestClass"
	// "legendaryInterfaces.IClass", "legendaryClasses.Class",
	// "legendary.ClassDeclarationVisitor",
	// "legendary.ClassFieldVisitor", "legendary.ClassMethodVisitor",
	// "legendaryClasses.ClassParser",
	// "legendaryClasses.Field", "legendaryClasses.Method",
	// "legendaryInterfaces.IField",
	// "legendaryInterfaces.IField", "legendaryInterfaces.IMethod"
	// };

	public static final String packageName = "headfirst";
	public static final String[] directories = {
	// "./src/legendary", "./src/legendaryClasses", "./src/legendaryInterfaces"
	"/Users/SamPastoriza/Documents/Programming/Java Development/Lab2-3/src/headfirst" };

	/**
	 * Reads in a list of Java Classes and reverse engineers their design.
	 *
	 * @param args
	 *            : the names of the classes, separated by spaces. For example:
	 *            java DesignParser java.lang.String
	 *            edu.rosehulman.csse374.ClassFieldVisitor java.lang.Math
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		ClassParser legendaryParser = new ClassParser();
		List<String> classes = new ArrayList<String>();
		for (String dir : directories) {
			classes.addAll(getClassesFromDir(new File(dir)));
		}

		for (String className : classes) {
			IClass legendaryClass = new Class();
			// ASM's ClassReader does the heavy lifting of parsing the compiled
			// Java class
			ClassReader reader = new ClassReader(className);
			// make class declaration visitor to get superclass and interfaces
			ClassVisitor decVisitor = new ClassDeclarationVisitor(Opcodes.ASM5,
					legendaryClass);
			// DECORATE declaration visitor with field visitor
			ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5,
					decVisitor, legendaryClass);
			// DECORATE field visitor with method visitor
			ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5,
					fieldVisitor, legendaryClass);

			reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
			legendaryParser.addClass(legendaryClass);
		}
		legendaryParser.parse();
	}

	public static List<String> getClassesFromDir(File dir) {
		ArrayList<String> res = new ArrayList<String>();
		ArrayList<String> res2 = new ArrayList<String>();
		if (dir.isDirectory()) {
			File[] dirFiles = dir.listFiles();
			for (int i = 0; i < dirFiles.length; i++) {
				res2.addAll(getClassesFromDir(dirFiles[i]));
				for (String r : res2) {
					r = r.substring(
							r.lastIndexOf(packageName),
							(r.contains("java") ? r.lastIndexOf("java") - 1 : r
									.length())).replace("\\", ".");
					res.add(r);
				}
			}
		} else
			res.add(dir.toString());
		return res;
	}
}