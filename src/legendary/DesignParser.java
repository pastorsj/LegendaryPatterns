package legendary;

import java.io.IOException;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import legendaryClasses.ClassParser;
import legendaryClasses.Class;
import legendaryInterfaces.IClass;
/*
 * Modification made by Sam Pastoriza and Jason Lane
 */
public class DesignParser {
	public static final String[] classes = {
		"legendaryInterfaces.IClass"
	};
	/**
	 * Reads in a list of Java Classes and reverse engineers their design.
	 *
	 * @param args:
	 *            the names of the classes, separated by spaces. For example:
	 *            java DesignParser java.lang.String
	 *            edu.rosehulman.csse374.ClassFieldVisitor java.lang.Math
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		ClassParser legendaryParser = new ClassParser();

		for (String className : classes) {
			IClass legendaryClass = new Class();
			// ASM's ClassReader does the heavy lifting of parsing the compiled
			// Java class
			ClassReader reader = new ClassReader(className);
			// make class declaration visitor to get superclass and interfaces
			ClassVisitor decVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, legendaryClass);
			// DECORATE declaration visitor with field visitor
			ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, decVisitor, legendaryClass);
			// DECORATE field visitor with method visitor
			ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, legendaryClass);
			
			reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
			legendaryParser.addClass(legendaryClass);
		}
		legendaryParser.parse();
	}
}