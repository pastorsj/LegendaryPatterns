package legendary;

import legendaryClasses.Field;
import legendaryInterfaces.IClass;
import legendaryInterfaces.IField;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Opcodes;

import com.sun.xml.internal.ws.org.objectweb.asm.Type;

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
		//New Code
		IField field = new Field();
		addAccessLevel(access, field);
		field.setFieldName(name);
		field.setType((signature==null) ? Type.getType(desc).toString() : signature);
		legendaryClass.addField(field);
		for(String s: field.getBaseTypes())
		{
			legendaryClass.addAssociationClass(s);
		}
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