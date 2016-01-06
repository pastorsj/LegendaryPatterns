package legendary;

import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import legendaryClasses.Method;
import legendaryInterfaces.IClass;
import legendaryInterfaces.IMethod;

/*
 * Modifications made by Sam Pastoriza and Jason Lane
 */
public class ClassMethodVisitor extends ClassVisitor {
	
	private IClass legendaryClass;
	
	public ClassMethodVisitor(int api) {
		super(api);
	}

	public ClassMethodVisitor(int api, ClassVisitor decorated, IClass legendaryClass) {
		super(api, decorated);
		this.legendaryClass = legendaryClass;
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		MethodVisitor toDecorate = super.visitMethod(access, name, desc, signature, exceptions);
		IMethod method = new Method();
		method.setMethodName(name);
		addAccessLevel(access, method); 
		addArguments(desc, method);
		addReturnType(desc, method);
		this.legendaryClass.addMethod(method);

		return toDecorate;
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
		method.setAccess(level);
	}

	void addReturnType(String desc, IMethod method) {
		String returnType = Type.getReturnType(desc).getClassName();
		method.setReturnType(returnType);
	}

	void addArguments(String desc, IMethod method) {
		Type[] args = Type.getArgumentTypes(desc);
		List<String> arguments = new ArrayList<String>();
		for (int i = 0; i < args.length; i++) {
			String arg = args[i].getClassName();
			arguments.add(arg.substring(arg.lastIndexOf(".") + 1, arg.length()));
		}
		method.setParameters(arguments);
	}
	
}