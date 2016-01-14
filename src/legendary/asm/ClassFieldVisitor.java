package legendary.asm;

import javax.swing.plaf.synth.SynthSeparatorUI;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Opcodes;

import com.sun.xml.internal.ws.org.objectweb.asm.Type;

import legendary.Classes.LegendaryField;
import legendary.Classes.Relations;
import legendary.Interfaces.IClass;
import legendary.Interfaces.IField;
import legendary.Interfaces.IModel;

/*
 * Modifications made by Sam Pastoriza and Jason Lane
 */
public class ClassFieldVisitor extends ClassVisitor {

	private IClass legendaryClass;
	private IModel legendaryModel;

	public ClassFieldVisitor(int api) {
		super(api);
	}

	public ClassFieldVisitor(int api, ClassVisitor decorated,
			IClass legendaryClass, IModel legendaryModel) {
		super(api, decorated);
		this.legendaryClass = legendaryClass;
		this.legendaryModel = legendaryModel;
	}

	public FieldVisitor visitField(int access, String name, String desc,
			String signature, Object value) {
		FieldVisitor toDecorate = super.visitField(access, name, desc,
				signature, value);
		IField field = new LegendaryField();
		addAccessLevel(access, field);
		field.setFieldName(name);
		field.setType((signature == null) ? Type.getType(desc).toString()
				: signature);
		legendaryClass.addField(field);
		for (String s : field.getBaseTypes()) {
			if(legendaryClass.getClassName().equals("LegendaryModel"))
				System.out.println(s);
			legendaryModel.addRelation(legendaryClass.getClassName(), s,
					Relations.ASSOCIATES);
//			System.out.println(s);
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