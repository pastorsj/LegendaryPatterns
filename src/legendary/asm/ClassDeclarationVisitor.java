package legendary.asm;

import legendary.Classes.Relations;
import legendary.Interfaces.IClass;
import legendary.Interfaces.IModel;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

/*
 * Modification made by Sam Pastoriza
 */
public class ClassDeclarationVisitor extends ClassVisitor {

	private IClass legendaryClass;
	private IModel legendaryModel;

	public ClassDeclarationVisitor(int api, IClass legendaryClass, IModel legendaryModel) {
		super(api);
		this.legendaryClass = legendaryClass;
		this.legendaryModel = legendaryModel;
	}

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
			this.legendaryModel.addRelation(n, s, Relations.EXTENDS);
			System.out.println(n + " " + s);
		}
		for (String i : interfaces) {
			String s = i;
			if (s.contains("/")) {
				s = s.replace("/", ".");
				s = s.substring(0, s.lastIndexOf(".")) + "::" + s.substring(s.lastIndexOf(".") + 1, s.length());
			}
			this.legendaryModel.addRelation(n, s, Relations.IMPLEMENTS);
		}

		if ((access & Opcodes.ACC_INTERFACE) != 0) {
			legendaryClass.setIsInterface(true);
		}

		super.visit(version, access, name, signature, superName, interfaces);
	}
}