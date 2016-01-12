package legendary.asm;

import legendary.Classes.Relations;
import legendary.Interfaces.IClass;
import legendary.Interfaces.IMethod;
import legendary.Interfaces.IModel;

import org.objectweb.asm.MethodVisitor;

public class LegendaryClassMethodVisitor extends MethodVisitor {

	IClass legendaryClass;
	IModel legendaryModel;
	IMethod legendaryMethod;

	public LegendaryClassMethodVisitor(int api, MethodVisitor decorated,
			IClass legendaryClass, IModel legendaryModel, IMethod legendaryMethod) {
		super(api, decorated);
		this.legendaryClass = legendaryClass;
		this.legendaryModel = legendaryModel;
		this.legendaryMethod = legendaryMethod;
	}

	@Override
	public void visitMethodInsn(int opcode, String owner, String name,
			String desc, boolean itf) {
		if (name.contains("<init>")) {
			this.legendaryModel.addRelation(this.legendaryClass.getClassName(), owner, Relations.USES);
		} else if(owner.startsWith("legendary")) {
//			System.out.println("\n======Class Name: "+ this.legendaryClass.getClassName());
//			System.out.println("======Method Insn Owner: " + owner);
//			System.out.println("======Method description: " + desc);
			String ownerClass = owner.substring(owner.lastIndexOf("/")+1);
//			System.out.println("======Owner Class: " + ownerClass);
//			System.out.println("======Method Name: " + name);
			this.legendaryMethod.addMethodToCallStack(this.legendaryClass.getClassName(), ownerClass, name);				
		}
	}
	
	//For Milestone 3
	@Override
	public void visitFieldInsn(int opcode, String owner, String name, String desc) {
		//Called whenever a field is accessed
//		System.out.println("\n=======Field Accessed Owner: " + owner);
//		System.out.println("=======Name of Field: " + name);
	}

	@Override
	public void visitTypeInsn(int opcode, String type) {
		//Called whenever new is used
//		System.out.println("\n=======New keyword was used: " + type);
	}
	
	@Override
	public void visitVarInsn(int opcode, int var) {
		//Called whenever a parameter or local variable is accessed
//		System.out.println("\n======Parameter accessed: " + var);
	}
}
