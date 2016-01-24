package legendary.Classes;

import java.util.Set;

import legendary.Interfaces.IField;
import legendary.Interfaces.ITraverser;
import legendary.Interfaces.IVisitor;
import legendary.ParsingUtil.GeneralUtil;

/*
 * Authors: Jason Lane, Sam Pastoriza
 */
public class LegendaryField implements IField, ITraverser{

	private String fieldAccessType;
	private String fieldName;
	private String fieldType;
	private Set<String> baseFields;

	public LegendaryField() {
		this.fieldAccessType = "";
		this.fieldName = "";
		this.fieldType = "";
	}

	@Override
	public void setAccess(String fieldAccessType) {
		this.fieldAccessType = fieldAccessType;
	}

	@Override
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
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

	@Override
	public String getAccess() {
		return this.fieldAccessType;
	}

	@Override
	public String getFieldName() {
		return this.fieldName;
	}

	@Override
	public String getType() {
		return this.fieldType;
	}

	@Override
	public Set<String> getBaseTypes() {
		return this.baseFields;
	}

	@Override
	public void accept(IVisitor v) {
		v.previsit(this);
		v.visit(this);
		v.postvisit(this);
	}
}
