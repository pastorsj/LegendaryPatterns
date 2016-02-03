package legendary.asm;

import legendary.Classes.Relations;
import legendary.Interfaces.IClass;
import legendary.Interfaces.IMethod;
import legendary.Interfaces.IModel;
import legendary.ParsingUtil.GeneralUtil;

import org.objectweb.asm.MethodVisitor;

// TODO: Auto-generated Javadoc
/**
 * The Class LegendaryClassMethodVisitor.
 */
public class LegendaryClassMethodVisitor extends MethodVisitor {

	/** The legendary class. */
	IClass legendaryClass;
	
	/** The legendary model. */
	IModel legendaryModel;
	
	/** The legendary method. */
	IMethod legendaryMethod;

	/**
	 * Instantiates a new legendary class method visitor.
	 *
	 * @param api the api
	 * @param decorated the decorated
	 * @param legendaryClass the legendary class
	 * @param legendaryModel the legendary model
	 * @param legendaryMethod the legendary method
	 */
	public LegendaryClassMethodVisitor(int api, MethodVisitor decorated, IClass legendaryClass, IModel legendaryModel,
			IMethod legendaryMethod) {
		super(api, decorated);
		this.legendaryClass = legendaryClass;
		this.legendaryModel = legendaryModel;
		this.legendaryMethod = legendaryMethod;
	}

	/* (non-Javadoc)
	 * @see org.objectweb.asm.MethodVisitor#visitMethodInsn(int, java.lang.String, java.lang.String, java.lang.String, boolean)
	 */
	@Override
	public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
		if (name.contains("<init>")) {
			String n = owner;
			if (n.contains("/")) {
				n = n.replace("/", ".");
				n = n.substring(0, n.lastIndexOf(".")) + "::" + n.substring(n.lastIndexOf(".") + 1, n.length());
			}
			this.legendaryModel.addRelation(this.legendaryClass.getClassName(), n, Relations.USES);
		}
		if (owner.startsWith(DesignParser.packageName)) {
			String ownerClass = owner.substring(owner.lastIndexOf("/") + 1);
			this.legendaryMethod.addMethodToCallStack(this.legendaryClass.getClassName(), ownerClass, name,
					GeneralUtil.typeArgumentCollections(desc));
		}
	}

	/* (non-Javadoc)
	 * @see org.objectweb.asm.MethodVisitor#visitFieldInsn(int, java.lang.String, java.lang.String, java.lang.String)
	 */
	// For Milestone 3
	@Override
	public void visitFieldInsn(int opcode, String owner, String name, String desc) {
		// Called whenever a field is accessed
		// System.out.println("\n=======Field Accessed Owner: " + owner);
		// System.out.println("=======Name of Field: " + name);
	}

	/* (non-Javadoc)
	 * @see org.objectweb.asm.MethodVisitor#visitTypeInsn(int, java.lang.String)
	 */
	@Override
	public void visitTypeInsn(int opcode, String type) {
		// Called whenever new is used
		// System.out.println("\n=======New keyword was used: " + type);
	}

	/* (non-Javadoc)
	 * @see org.objectweb.asm.MethodVisitor#visitVarInsn(int, int)
	 */
	@Override
	public void visitVarInsn(int opcode, int var) {
		// Called whenever a parameter or local variable is accessed
		// System.out.println("\n======Parameter accessed: " + var);
	}
}
