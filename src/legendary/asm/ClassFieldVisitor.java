package legendary.asm;

import java.io.IOException;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Opcodes;

import com.sun.xml.internal.ws.org.objectweb.asm.Type;

import legendary.Classes.LegendaryField;
import legendary.Classes.Relations;
import legendary.Interfaces.IClass;
import legendary.Interfaces.IField;
import legendary.Interfaces.IModel;
import legendary.ParsingUtil.GeneralUtil;

/*
 * This class will be called when visiting the declaration of a field
 * inside of another class
 * 
 * Modifications made by Sam Pastoriza and Jason Lane
 */
public class ClassFieldVisitor extends ClassVisitor {

	/** Stores the current class being visited */
	private IClass legendaryClass;
	
	/** Stores a representation of the project */
	private IModel legendaryModel;

	/**
	 * Instantiates a new class field visitor.
	 *
	 * @param api the api
	 */
	public ClassFieldVisitor(int api) {
		super(api);
	}

	/**
	 * Instantiates a new class field visitor.
	 *
	 * @param api
	 * @param decorated
	 * @param legendaryClass
	 * @param legendaryModel
	 */
	public ClassFieldVisitor(int api, ClassVisitor decorated, IClass legendaryClass, IModel legendaryModel) {
		super(api, decorated);
		this.legendaryClass = legendaryClass;
		this.legendaryModel = legendaryModel;
	}

	/* (non-Javadoc)
	 * @see org.objectweb.asm.ClassVisitor#visitField(int, java.lang.String, java.lang.String, java.lang.String, java.lang.Object)
	 */
	public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
		FieldVisitor toDecorate = super.visitField(access, name, desc, signature, value);
		IField field = new LegendaryField();
		addAccessLevel(access, field);
		field.setFieldName(name);
		field.setType((signature == null) ? Type.getType(desc).toString() : signature);
		legendaryClass.addField(field);
		for (String s : field.getBaseTypes()) {
			legendaryModel.addRelation(legendaryClass.getClassName(), s, Relations.ASSOCIATES);
		}
		String s = GeneralUtil.typeFieldCollections(desc.replace("[", ""));
		if (!GeneralUtil.primCodes.containsValue(s)) {
			String sClass = s.replace(".", "/").replace("::", "/");
			if (!(this.legendaryModel.containsClass(s) || s.startsWith("java.lang::"))) {
				try {
					DesignParser.executeASM(sClass, legendaryModel, false);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return toDecorate;
	};

	/**
	 * Adds the visibility level of the field.
	 *
	 * @param access
	 * @param field
	 */
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
		if ((access & Opcodes.ACC_STATIC) != 0)
			level += "_";
		field.setAccess(level);
	}
}