package legendary.asm;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import legendary.Classes.ClassParser;
import legendary.Classes.LegendaryClass;
import legendary.Classes.LegendaryModel;
import legendary.Interfaces.IClass;
import legendary.Interfaces.IModel;
import legendary.ParsingUtil.GeneralUtil;

/*
 * Modification made by Sam Pastoriza and Jason Lane
 */
public class DesignParser {

	public static final String packageName = "legendary";
	public static final String[] directories = {
//			"/Users/SamPastoriza/Documents/Programming/Java Development/LegendaryPatterns/src/legendary" };
	 "C:/Users/Administrator/Documents/GitHub/LegendaryPatterns/src/legendary"
	 };

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
		IModel legendaryModel = new LegendaryModel();
		for (String dir : directories) {
			classes.addAll(GeneralUtil.getClassesFromDir(new File(dir)));
		}
		for (String className : classes) {
			IClass legendaryClass = new LegendaryClass();
			// ASM's ClassReader does the heavy lifting of parsing the compiled
			// Java class
			ClassReader reader = new ClassReader(className);
			// make class declaration visitor to get superclass and interfaces
			ClassVisitor decVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, legendaryClass, legendaryModel);
			// DECORATE declaration visitor with field visitor
			ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, decVisitor, legendaryClass, legendaryModel);
			// DECORATE field visitor with method visitor
			ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, legendaryClass,
					legendaryModel);

			reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
			legendaryModel.addClass(legendaryClass);
		}
//		if (args.length > 0) {
//			if (args.length != 3) {
//				throw new IllegalArgumentException();
//			}
			legendaryParser.makeSDEdit("DesignParser", "main", 100, legendaryModel);
//		}
		legendaryParser.makeGraphViz(legendaryModel);
	}


}