package legendary.asm;

import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import legendary.Classes.Method;
import legendary.Classes.Relations;
import legendary.Interfaces.IClass;
import legendary.Interfaces.IMethod;
import legendary.Interfaces.IModel;

/*
 * Modifications made by Sam Pastoriza and Jason Lane
 */
public class ClassMethodVisitor extends ClassVisitor {

	private List<String> usesClasses;
	private IClass legendaryClass;
	private IModel legendaryModel;

	public ClassMethodVisitor(int api) {
		super(api);
	}

	public ClassMethodVisitor(int api, ClassVisitor decorated,
			IClass legendaryClass, IModel legendaryModel) {
		super(api, decorated);
		this.legendaryClass = legendaryClass;
		this.legendaryModel = legendaryModel;
		this.usesClasses = new ArrayList<String>();
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc,
			String signature, String[] exceptions) {
		MethodVisitor toDecorate = super.visitMethod(access, name, desc,
				signature, exceptions);
		MethodVisitor toDecorateMore = new LegendaryClassMethodVisitor(
				Opcodes.ASM5, toDecorate, this.legendaryClass,
				this.legendaryModel);
		IMethod method = new Method();
		method.setMethodName(name);
		addAccessLevel(access, method);
		addArguments((signature == null) ? desc : signature, method);
		addReturnType((signature == null) ? desc : signature, method);
		// System.out.println(name + " " + signature + " " + desc);
		this.legendaryClass.addMethod(method);
		for (String s : this.usesClasses) {
			this.legendaryModel.addRelation(this.legendaryClass.getClassName(),
					s, Relations.USES);
		}
		return toDecorateMore;
	}

	public String typeCollections(String in) {
		String s = in;
		if (s.endsWith(")V") || s.endsWith(")Z"))
			s = s.substring(0, s.lastIndexOf(")"));
		if (in.contains("<")) {
			String split1 = s.substring(0, s.indexOf("<"));
			this.usesClasses.add(split1);
			String split2 = s.substring(s.indexOf("<") + 1);
			s = split1.substring(split1.lastIndexOf("/") + 1) + "<";
			String[] split = split2.split(";");
			for (int i = 0; i < split.length; i++) {
				String s2 = split[i];
				s += typeCollections(s2);
				if ((i < split.length - 1) && (!split[i + 1].equals(">")))
					s += ", ";
			}
		} else
			this.usesClasses.add(s.substring(s.lastIndexOf("/") + 1).replace(
					";", ""));
		s = s.substring(s.lastIndexOf("/") + 1).replace("<", "\\<")
				.replace(">", "\\>").replace("\\\\", "\\").replace(";", "");
		return s;
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
		String returnType = desc;
		if (desc.endsWith(")Z")) {
			method.setReturnType("boolean");
			return;
		}
		if (desc.endsWith(")V")) {
			method.setReturnType("void");
			return;
		}
		if (desc != null) {
			returnType = typeCollections(desc);
			method.setReturnType(returnType);
		}

	}

	void addArguments(String desc, IMethod method) {
		String s = desc;
		s = s.substring(0, s.lastIndexOf(";") + 1);
		List<String> arguments = new ArrayList<String>();
		String out = (typeCollections(s));
		// System.out.println(out);
		for (String arg : out.split(" "))
			arguments.add(arg);
		method.setParameters(arguments);
	}

}