package legendary.asm;

import org.objectweb.asm.MethodVisitor;

import legendary.Interfaces.IClass;


public class LegendaryClassMethodVisitor extends MethodVisitor {

	IClass legendaryClass;
	
	public LegendaryClassMethodVisitor(int api, MethodVisitor decorated, IClass legendaryClass) {
		super(api, decorated);
		this.legendaryClass = legendaryClass;
	}
	
	@Override
	public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
//		System.out.println("--------------Visited Method--------------");
//		System.out.println("opcode: " + opcode);
//		System.out.println("desc: " + desc);
//		System.out.println("itf: " + itf);
		
		if(name.contains("<init>")) {
			this.legendaryClass.addUsesClass(owner);
//			System.out.println("owner: " + owner);
//			System.out.println("name: " + name);
		}
	}

}
