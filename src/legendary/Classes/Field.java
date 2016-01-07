package legendary.Classes;

import java.util.HashSet;
import java.util.Set;

import legendary.Interfaces.IField;

/*
 * Author: Jason Lane
 */
public class Field implements IField {

	private String fieldAccessType;
	private String fieldName;
	private String fieldType;
	private Set<String> baseFields;

	public Field() {
		this.fieldAccessType = "";
		this.fieldName = "";
		this.fieldType = "";
		this.baseFields = new HashSet<String>();
	}

	@Override
	public void setAccess(String fieldAccessType) {
		this.fieldAccessType = fieldAccessType;
	}

	@Override
	public void setFieldName(String fieldName) {
		// TODO Auto-generated method stub
		this.fieldName = fieldName;
	}

	public String typeCollections(String in) {
		String s = in;
		if (in.contains("<")) {
			String split1 = s.substring(0, s.indexOf("<"));
			this.baseFields.add(split1);
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
			this.baseFields.add(s.substring(s.lastIndexOf("/") + 1));
		return s.substring(s.lastIndexOf("/") + 1).replace("<", "\\<")
				.replace(">", "\\>").replace("\\\\", "\\").replace(";", "");
	}

	@Override
	public void setType(String fieldType) {
		// TODO Auto-generated method stub
		String s = fieldType;
		if (fieldType != null)
			s = typeCollections(fieldType);
		this.fieldType = s;
	}

	@Override
	public String getAccess() {
		// TODO Auto-generated method stub
		return this.fieldAccessType;
	}

	@Override
	public String getFieldName() {
		// TODO Auto-generated method stub
		return this.fieldName;
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return this.fieldType;
	}

	@Override
	public String toString() {
		return this.getAccess() + " " + this.getFieldName() + ": "
				+ this.getType() + "\\l\n\t";
	}

	@Override
	public Set<String> getBaseTypes() {
		return this.baseFields;
	}
}
