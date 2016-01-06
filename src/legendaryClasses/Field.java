package legendaryClasses;

import legendaryInterfaces.IField;

/*
 * Author: Jason Lane
 */
public class Field implements IField {

	private String fieldAccessType;
	private String fieldName;
	private String fieldType;

	public Field() {
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
		// TODO Auto-generated method stub
		this.fieldName = fieldName;
	}

	@Override
	public void setType(String fieldType) {
		// TODO Auto-generated method stub
		String s = fieldType;
		if (fieldType.contains("<")) {
			String[] split = fieldType.split("<");
			s = split[0].substring(split[0].lastIndexOf("/") + 1) + "<";
			split = split[1].split(";");
			for (int i = 0; i < split.length; i ++) {
				String s2 = split[i];
				s += s2.substring(s2.lastIndexOf(";") + 1).substring(
						s2.lastIndexOf("/") + 1);

				if ((!s2.equals(">"))&&(!split[i+1].equals(">")))
					s += ", ";
			}
		}
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

}
