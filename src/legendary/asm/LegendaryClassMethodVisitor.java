package legendary.asm;

import legendary.Classes.Relations;
import legendary.Interfaces.IClass;
import legendary.Interfaces.IModel;

import org.objectweb.asm.MethodVisitor;

public class LegendaryClassMethodVisitor extends MethodVisitor {

	IClass legendaryClass;
	IModel legendaryModel;

	public LegendaryClassMethodVisitor(int api, MethodVisitor decorated,
			IClass legendaryClass, IModel legendaryModel) {
		super(api, decorated);
		this.legendaryClass = legendaryClass;
		this.legendaryModel = legendaryModel;
	}

	@Override
	public void visitMethodInsn(int opcode, String owner, String name,
			String desc, boolean itf) {
		if (name.contains("<init>")) {
			this.legendaryModel.addRelation(this.legendaryClass.getClassName(), owner, Relations.USES);
		}
	}
	
	//For Milestone 3
	@Override
	public void visitFieldInsn(int opcode, String owner, String name, String desc) {
		//Called whenever a field is accessed
	}

	@Override
	public void visitTypeInsn(int opcode, String type) {
		//Called whenever new is used
	}
	
	@Override
	public void visitVarInsn(int opcode, int var) {
		//Called whenever a parameter or local variable is accessed
	}
}
