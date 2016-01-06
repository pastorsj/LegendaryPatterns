package legendary;

import legendaryInterfaces.IClass;

import org.objectweb.asm.MethodVisitor;


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
//		System.out.println("owner: " + owner);
//		System.out.println("name: " + name);
//		System.out.println("desc: " + desc);
//		System.out.println("itf: " + itf);
		
		if(name.contains("<init>") && !owner.contains("java")) {
			this.legendaryClass.addUsesClass(owner);
		}
	}

}
