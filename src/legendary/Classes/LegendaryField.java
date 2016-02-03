package legendary.Classes;

import java.util.Set;

import legendary.Interfaces.IField;
import legendary.ParsingUtil.GeneralUtil;
import legendary.visitor.ITraverser;
import legendary.visitor.IVisitor;

// TODO: Auto-generated Javadoc
/**
 * The Class LegendaryField.
 */
/*
 * Authors: Jason Lane, Sam Pastoriza
 */
public class LegendaryField implements IField, ITraverser{

	/** The field access type. */
	private String fieldAccessType;
	
	/** The field name. */
	private String fieldName;
	
	/** The field type. */
	private String fieldType;
	
	/** The base fields. */
	private Set<String> baseFields;

	/**
	 * Instantiates a new legendary field.
	 */
	public LegendaryField() {
		this.fieldAccessType = "";
		this.fieldName = "";
		this.fieldType = "";
	}

	/* (non-Javadoc)
	 * @see legendary.Interfaces.IField#setAccess(java.lang.String)
	 */
	@Override
	public void setAccess(String fieldAccessType) {
		this.fieldAccessType = fieldAccessType;
	}

	/* (non-Javadoc)
	 * @see legendary.Interfaces.IField#setFieldName(java.lang.String)
	 */
	@Override
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
	/* (non-Javadoc)
	 * @see legendary.Interfaces.IField#setType(java.lang.String)
	 */
	@Override
	public void setType(String fieldType) {
		String s = fieldType;
		if (GeneralUtil.primCodes.containsKey(s)) {
			this.fieldType = GeneralUtil.primCodes.get(s);
		} else if(s.charAt(0) == '[') {
			if(GeneralUtil.primCodes.containsKey(String.valueOf(s.charAt(1)))) {
				this.fieldType = GeneralUtil.primCodes.get(String.valueOf(s.charAt(1))) + "[]";
			} else {
				this.fieldType = GeneralUtil.typeFieldCollections(s.substring(1)) + "[]";
			}
		} else {
			if (fieldType != null) {
				s = GeneralUtil.typeFieldCollections(fieldType);			
			}
			this.fieldType = s;
		}
		this.baseFields = GeneralUtil.getBaseFields(fieldType);
	}

	/* (non-Javadoc)
	 * @see legendary.Interfaces.IField#getAccess()
	 */
	@Override
	public String getAccess() {
		return this.fieldAccessType;
	}

	/* (non-Javadoc)
	 * @see legendary.Interfaces.IField#getFieldName()
	 */
	@Override
	public String getFieldName() {
		return this.fieldName;
	}

	/* (non-Javadoc)
	 * @see legendary.Interfaces.IField#getType()
	 */
	@Override
	public String getType() {
		return this.fieldType;
	}

	/* (non-Javadoc)
	 * @see legendary.Interfaces.IField#getBaseTypes()
	 */
	@Override
	public Set<String> getBaseTypes() {
		return this.baseFields;
	}

	/* (non-Javadoc)
	 * @see legendary.visitor.ITraverser#accept(legendary.visitor.IVisitor)
	 */
	@Override
	public void accept(IVisitor v) {
		v.preVisit(this);
		v.visit(this);
		v.postVisit(this);
	}
}
