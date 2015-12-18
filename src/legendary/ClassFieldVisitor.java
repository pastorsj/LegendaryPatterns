package legendary;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import legendaryClasses.Field;
import legendaryInterfaces.IClass;
import legendaryInterfaces.IField;

/*
 * Modifications made by Sam Pastoriza and Jason Lane
 */
public class ClassFieldVisitor extends ClassVisitor {
	
	private IClass legendaryClass;
	
	public ClassFieldVisitor(int api) {
		super(api);
	}

	public ClassFieldVisitor(int api, ClassVisitor decorated, IClass legendaryClass) {
		super(api, decorated);
		this.legendaryClass = legendaryClass;
	}

	public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
		FieldVisitor toDecorate = super.visitField(access, name, desc, signature, value);
		String type = Type.getType(desc).getClassName();

		//New Code
		IField field = new Field();
		addAccessLevel(access, field);
		field.setMethodName(name);
		field.setReturnType(type);
		legendaryClass.addField(field);
		
		return toDecorate;
	};
	
	void addAccessLevel(int access, IField field) {
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
		field.setAccess(level);
	}
}