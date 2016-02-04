package legendary.asm;

import legendary.Classes.Relations;
import legendary.Interfaces.IClass;
import legendary.Interfaces.IModel;

import java.io.IOException;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

/**
 * This class will be called when visiting the declaration of another class
 */
public class ClassDeclarationVisitor extends ClassVisitor {

	/** Stores the current class being visited */
	private IClass legendaryClass;
	
	/** Stores a representation of the project */
	private IModel legendaryModel;

	/**
	 * Instantiates a new class declaration visitor.
	 *
	 * @param api
	 * @param legendaryClass
	 * @param legendaryModel
	 */
	public ClassDeclarationVisitor(int api, IClass legendaryClass, IModel legendaryModel) {
		super(api);
		this.legendaryClass = legendaryClass;
		this.legendaryModel = legendaryModel;
	}

	/* (non-Javadoc)
	 * @see org.objectweb.asm.ClassVisitor#visit(int, int, java.lang.String, java.lang.String, java.lang.String, java.lang.String[])
	 */
	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		String n = name;
		if (n.contains("/")) {
			n = n.replace("/", ".");
			n = n.substring(0, n.lastIndexOf(".")) + "::" + n.substring(n.lastIndexOf(".") + 1, n.length());
		}
		this.legendaryClass.setClassName(n);
		if (superName != null) {
			String s = superName;
			if (s.contains("/")) {
				s = s.replace("/", ".");
				s = s.substring(0, s.lastIndexOf(".")) + "::" + s.substring(s.lastIndexOf(".") + 1, s.length());
			}
			if (!(this.legendaryModel.containsClass(s) || s.startsWith("java.lang::"))) {
				try {
					DesignParser.executeASM(superName, legendaryModel, false);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			this.legendaryModel.addRelation(n, s, Relations.EXTENDS);
		}
		for (String i : interfaces) {
			String s = i;
			if (s.contains("/")) {
				s = s.replace("/", ".");
				s = s.substring(0, s.lastIndexOf(".")) + "::" + s.substring(s.lastIndexOf(".") + 1, s.length());
			}
			if (!(this.legendaryModel.containsClass(i) || i.startsWith("java.lang::"))) {
				try {
					DesignParser.executeASM(i, legendaryModel, false);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			this.legendaryModel.addRelation(n, s, Relations.IMPLEMENTS);
		}

		if ((access & Opcodes.ACC_INTERFACE) != 0) {
			legendaryClass.setIsInterface(true);
		}

		super.visit(version, access, name, signature, superName, interfaces);
	}
}