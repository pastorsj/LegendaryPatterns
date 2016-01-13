package legendary.Classes;

import java.util.HashSet;
import java.util.Set;

import legendary.Interfaces.IField;
import legendary.Interfaces.ITraverser;
import legendary.Interfaces.IVisitor;
import legendary.ParsingUtil.ParsingFieldUtil;

/*
 * Authors: Jason Lane, Sam Pastoriza
 */
public class LegendaryField implements IField, ITraverser{

	private String fieldAccessType;
	private String fieldName;
	private String fieldType;
	private Set<String> baseFields;
	private ParsingFieldUtil util;

	public LegendaryField() {
		this.fieldAccessType = "";
		this.fieldName = "";
		this.fieldType = "";
		this.baseFields = new HashSet<String>();
		this.util = new ParsingFieldUtil(this.baseFields);
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
		if (this.util.getPrimCheck().containsKey(s)) {
			this.fieldType = this.util.getPrimCheck().get(s);
		} else if(s.charAt(0) == '[') {
			if(this.util.getPrimCheck().containsKey(String.valueOf(s.charAt(1)))) {
				this.fieldType = this.util.getPrimCheck().get(String.valueOf(s.charAt(1))) + "[]";
			} else {
				this.fieldType = this.util.typeCollections(s.substring(1)) + "[]";
			}
		} else {
			if (fieldType != null) {
				s = this.util.typeCollections(fieldType);			
			}
			this.fieldType = s;
		}
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
