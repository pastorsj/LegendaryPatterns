package legendary.asm;

import legendary.Classes.Relations;
import legendary.Interfaces.IClass;
import legendary.Interfaces.IMethod;
import legendary.Interfaces.IModel;
import legendary.ParsingUtil.GeneralUtil;

import org.objectweb.asm.MethodVisitor;

/**
 * This class is called whenever a method is called inside of another 
 * method inside of the current class 
 */
public class LegendaryClassMethodVisitor extends MethodVisitor {

	/** The representation of the current class */
	IClass legendaryClass;
	
	/** The representation of the current project */
	IModel legendaryModel;
	
	/** The representation of the current method being analyzed */
	IMethod legendaryMethod;

	/**
	 * Instantiates a new legendary class method visitor.
	 *
	 * @param api the api
	 * @param decorated The current Method Visitor being decorated
	 * @param legendaryClass (see field)
	 * @param legendaryModel (see field)
	 * @param legendaryMethod (see field)
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
}
