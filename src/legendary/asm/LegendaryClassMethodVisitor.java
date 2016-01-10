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
		// System.out.println("--------------Visited Method--------------");
		// System.out.println("opcode: " + opcode);
		// System.out.println("desc: " + desc);
		// System.out.println("itf: " + itf);

		if (name.contains("<init>")) {
			this.legendaryModel.addRelation(this.legendaryClass.getClassName(), owner, Relations.USES);
			// System.out.println("owner: " + owner);
			// System.out.println("name: " + name);
		}
	}

}
