package legendary.asm;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import legendary.Classes.Class;
import legendary.Classes.ClassParser;
import legendary.Classes.Model;
import legendary.Interfaces.IClass;
import legendary.Interfaces.IModel;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

/*
 * Modification made by Sam Pastoriza and Jason Lane
 */
public class DesignParser {

	public static final String packageName = "problem";
	public static final String[] directories = {
	"/Users/SamPastoriza/Documents/Programming/Java Development/Lab2-1/src/problem"};
//	"C:/Users/Administrator/Documents/GitHub/LegendaryPatterns/src/legendary" };

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
		ClassParser legendaryParser = ClassParser.getInstance();
		List<String> classes = new ArrayList<String>();
		IModel legendaryModel = new Model();
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
					legendaryClass, legendaryModel);
			// DECORATE declaration visitor with field visitor
			ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5,
					decVisitor, legendaryClass, legendaryModel);
			// DECORATE field visitor with method visitor
			ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5,
					fieldVisitor, legendaryClass, legendaryModel);

			reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
			legendaryModel.addClass(legendaryClass);
		}
		legendaryParser.parseModel(legendaryModel);
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