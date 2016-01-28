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
import legendary.detectors.AdapterDetector;
import legendary.detectors.DecoratorDetector;
import legendary.detectors.SingletonDetector;

/*
 * Modification made by Sam Pastoriza and Jason Lane
 */
public class DesignParser {

	public static String packageName = "problem";
	public static final String[] directories = {
//			"/Users/SamPastoriza/Documents/Programming/Java Development/LegendaryPatterns/src/legendary" };
	"C:/Users/Administrator/Documents/CSSE374/Lab2-1-Solution/src/problem" };
//	"C:/Users/Administrator/Documents/GitHub/LegendaryPatterns/src/legendary"};
	public static ArrayList<String> classesSeen = new ArrayList<String>();

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
		legendaryParser.addDetector(new SingletonDetector(new DecoratorDetector(new AdapterDetector())));
		List<String> classes = new ArrayList<String>();
		IModel legendaryModel = new LegendaryModel();
		for (String dir : directories) {
			classes.addAll(GeneralUtil.getClassesFromDir(new File(dir)));
		}
		
		// String[] classes = new String[] { "java/util/Collections",
		// "java/util/Random", "java/lang/System",
		// "java/util/concurrent/atomic/AtomicLong" };
		for (String className : classes) {
			executeASM(className, legendaryModel, true);
		}
		legendaryModel.convertToGraph();
		StringBuilder builder = new StringBuilder();
		legendaryParser.makeGraphViz(legendaryModel, builder);
		GeneralUtil.writeAndExecGraphViz(builder);
		if (args.length > 0) {
			if (args.length != 3 && args.length != 2) {
				// legendaryParser.makeSDEdit("DesignParser", "main", 5,
				// legendaryModel);
				throw new IllegalArgumentException(String.format("%s %s %s", args[0], args[1], args[2]));
			}
			String arg1 = args[0].substring(0, args[0].lastIndexOf("."));
			String arg2 = args[0].substring(args[0].lastIndexOf(".") + 1);
			builder = new StringBuilder();
			legendaryParser.makeSDEdit(arg1, arg2, (args.length == 2 ? Integer.parseInt(args[1]) : 5), legendaryModel,
					builder);
			GeneralUtil.writeAndExecSDEdit(builder);
		}
	}
	
	public static void executeASM(String className, IModel legendaryModel, boolean drawable) throws IOException {
		classesSeen.add(className);
		IClass legendaryClass = new LegendaryClass();
		legendaryClass.setDrawable(drawable);
		legendaryModel.addClass(legendaryClass);
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
	}

}