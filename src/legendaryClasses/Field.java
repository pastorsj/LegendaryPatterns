package legendaryClasses;

import legendaryInterfaces.IField;

/*
 * Author: Jason Lane
 */
public class Field implements IField{

	private String methodAccessType;
	private String methodName;
	private String methodReturnType;
	
	public Field() {
		this.methodAccessType = "";
		this.methodName = "";
		this.methodReturnType = "";
		
	}
	
	@Override
	public void setAccess(String methodAccessType) {
		this.methodAccessType = methodAccessType;
	}

	@Override
	public void setMethodName(String methodName) {
		// TODO Auto-generated method stub
		this.methodName = methodName;
	}

	@Override
	public void setReturnType(String methodReturnType) {
		// TODO Auto-generated method stub
		this.methodReturnType = methodReturnType.substring(methodReturnType.lastIndexOf(".")+1);
	}

	@Override
	public String getAccess() {
		// TODO Auto-generated method stub
		return this.methodAccessType;
	}

	@Override
	public String getMethodName() {
		// TODO Auto-generated method stub
		return this.methodName;
	}

	@Override
	public String getReturnType() {
		// TODO Auto-generated method stub
		return this.methodReturnType;
	}
	
	@Override
	public String toString() {
		return this.getAccess() + " " + this.getMethodName() + ": " + this.getReturnType() + "\n\t";
	}

}
