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
import legendary.ParsingUtil.ParsingMethodUtil;

/*
 * Modifications made by Sam Pastoriza and Jason Lane
 */
public class ClassMethodVisitor extends ClassVisitor {

	private List<String> usesClasses;
	private IClass legendaryClass;
	private IModel legendaryModel;
	private ParsingMethodUtil util;

	public ClassMethodVisitor(int api) {
		super(api);
	}

	public ClassMethodVisitor(int api, ClassVisitor decorated, IClass legendaryClass, IModel legendaryModel) {
		super(api, decorated);
		this.legendaryClass = legendaryClass;
		this.legendaryModel = legendaryModel;
		this.usesClasses = new ArrayList<>();
		this.util = new ParsingMethodUtil(this.usesClasses);
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		MethodVisitor toDecorate = super.visitMethod(access, name, desc, signature, exceptions);
		IMethod method = new LegendaryMethod();
		method.setMethodName(name);
		addAccessLevel(access, method);
		addArguments((signature == null) ? desc : signature, method);
		addReturnType((signature == null) ? desc : signature, method);
		this.legendaryClass.addMethod(method);
		for (String s : this.usesClasses) {
			this.legendaryModel.addRelation(this.legendaryClass.getClassName(), s, Relations.USES);
		}
		MethodVisitor toDecorateMore = new LegendaryClassMethodVisitor(Opcodes.ASM5, toDecorate, this.legendaryClass,
				this.legendaryModel, method);
		return toDecorateMore;
	}

	void addAccessLevel(int access, IMethod method) {
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

	void addReturnType(String desc, IMethod method) {
		String returnType = desc;
		if (desc != null) {
			String retSub = desc.substring(desc.length() - 2, desc.length());
			String val = String.valueOf(retSub.charAt(1));
			if (retSub.charAt(0) == ')' && ParsingMethodUtil.returnPrimCheck.containsKey(val)) {
				method.setReturnType(ParsingMethodUtil.returnPrimCheck.get(val));
			} else {
				returnType = this.util.typeMethodCollections(desc);
				method.setReturnType(returnType);
			}
		}
	}

	void addArguments(String desc, IMethod method) {
		String s = desc;
		List<String> arguments = new ArrayList<>();
		List<String> out = this.util.typeArgumentCollections(s);
		for (String arg : out)
			arguments.add(arg);
		method.setParameters(arguments);
	}

}