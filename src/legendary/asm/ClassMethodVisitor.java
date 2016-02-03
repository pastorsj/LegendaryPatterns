package legendary.asm;

import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import legendary.Classes.LegendaryMethod;
import legendary.Classes.Relations;
import legendary.Interfaces.IClass;
import legendary.Interfaces.IMethod;
import legendary.Interfaces.IModel;
import legendary.ParsingUtil.GeneralUtil;


/*
 * This class will be called when visiting the declaration of a method
 * inside of another class
 * 
 * Modifications made by Sam Pastoriza and Jason Lane
 */
public class ClassMethodVisitor extends ClassVisitor {

	/** Stores the list of classes that this class uses. */
	private List<String> usesClasses;
	
	/** Stores the current class being visited */
	private IClass legendaryClass;
	
	/** Stores a representation of the project */
	private IModel legendaryModel;

	/**
	 * Instantiates a new class method visitor.
	 *
	 * @param api the api
	 */
	public ClassMethodVisitor(int api) {
		super(api);
	}

	/**
	 * Instantiates a new class method visitor.
	 *
	 * @param api
	 * @param decorated
	 * @param legendaryClass
	 * @param legendaryModel
	 */
	public ClassMethodVisitor(int api, ClassVisitor decorated,
			IClass legendaryClass, IModel legendaryModel) {
		super(api, decorated);
		this.legendaryClass = legendaryClass;
		this.legendaryModel = legendaryModel;
		this.usesClasses = new ArrayList<>();
	}

	/* (non-Javadoc)
	 * @see org.objectweb.asm.ClassVisitor#visitMethod(int, java.lang.String, java.lang.String, java.lang.String, java.lang.String[])
	 */
	@Override
	public MethodVisitor visitMethod(int access, String name, String desc,
			String signature, String[] exceptions) {
		MethodVisitor toDecorate = super.visitMethod(access, name, desc,
				signature, exceptions);
		IMethod method = new LegendaryMethod();
		method.setMethodName(name);
		addAccessLevel(access, method);
		addArguments((signature == null) ? desc : signature, method);
		addReturnType((signature == null) ? desc : signature, method);
		this.legendaryClass.addMethod(method);
		for (String s : this.usesClasses) {
			this.legendaryModel.addRelation(this.legendaryClass.getClassName(),
					s, Relations.USES);
		}
		MethodVisitor toDecorateMore = new LegendaryClassMethodVisitor(
				Opcodes.ASM5, toDecorate, this.legendaryClass,
				this.legendaryModel, method);
		return toDecorateMore;
	}

	/**
	 * Adds the visibility level of the method
	 *
	 * @param access
	 * @param method
	 */
	private void addAccessLevel(int access, IMethod method) {
		String level = "";
		if ((access & Opcodes.ACC_PUBLIC) != 0) {
			level = "+";
		} else if ((access & Opcodes.ACC_PROTECTED) != 0) {
			level = "#";
		} else if ((access & Opcodes.ACC_PRIVATE) != 0) {
			level = "-";
		} else {
			level = "";
		}
		if ((access & Opcodes.ACC_STATIC) != 0)
			level += "_";
		method.setAccess(level);
	}

	/**
	 * Adds the return type of the method.
	 *
	 * @param desc
	 * @param method
	 */
	private void addReturnType(String desc, IMethod method) {
		String returnType = desc;
		if (desc != null) {
			String retSub = desc.substring(desc.length() - 2, desc.length());
			String val = String.valueOf(retSub.charAt(1));
			if (retSub.charAt(0) == ')'
					&& GeneralUtil.primCodes.containsKey(val)) {
				method.setReturnType(GeneralUtil.primCodes.get(val));
			} else {
				returnType = GeneralUtil.typeMethodCollections(desc, this.usesClasses);
				method.setReturnType(returnType);
			}
		}
	}

	/**
	 * Adds the arguments.
	 *
	 * @param desc the desc
	 * @param method the method
	 */
	private void addArguments(String desc, IMethod method) {
		String s = desc;
		List<String> arguments = new ArrayList<>();
		List<String> out = GeneralUtil.typeArgumentCollections(s);
		for (String arg : out) {
			arguments.add(arg);
			this.usesClasses.add(arg);
		}
		method.setParameters(arguments);
	}

}