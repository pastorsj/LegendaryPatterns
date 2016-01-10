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

	public ClassDeclarationVisitor(int api, IClass legendaryClass,
			IModel legendaryModel) {
		super(api);
		this.legendaryClass = legendaryClass;
		this.legendaryModel = legendaryModel;
	}

	@Override
	public void visit(int version, int access, String name, String signature,
			String superName, String[] interfaces) {
		this.legendaryClass.setClassName(name);
		if (superName != null)
			this.legendaryModel.addRelation(name, superName, Relations.EXTENDS);
		for(String i : interfaces){
			this.legendaryModel.addRelation(name, i, Relations.IMPLEMENTS);
		}

		if ((access & Opcodes.ACC_INTERFACE) != 0) {
			legendaryClass.setIsInterface(true);
		}

		super.visit(version, access, name, signature, superName, interfaces);
	}
}