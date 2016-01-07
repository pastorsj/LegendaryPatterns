package legendary.asm;

import java.util.Arrays;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import legendary.Interfaces.IClass;

/*
 * Modification made by Sam Pastoriza
 */
public class ClassDeclarationVisitor extends ClassVisitor {
	
	private IClass legendaryClass;
	
	public ClassDeclarationVisitor(int api, IClass legendaryClass) {
		super(api);
		this.legendaryClass = legendaryClass;
	}

	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		this.legendaryClass.setClassName(name);
		this.legendaryClass.setSuper(superName);
		this.legendaryClass.setInterfaces(Arrays.asList(interfaces));
		
		if((access & Opcodes.ACC_INTERFACE) != 0) {
			legendaryClass.setIsInterface(true);
		}

		super.visit(version, access, name, signature, superName, interfaces);
	}
}