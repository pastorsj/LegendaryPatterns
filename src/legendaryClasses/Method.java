package legendaryClasses;

import java.util.ArrayList;
import java.util.List;

import legendaryInterfaces.IMethod;

/*
 * Author: Jason Lane
 */
public class Method implements IMethod{

	private String methodAccess;
	private String methodName;
	private List<String> parameters;
	private String methodReturnType;
	
	public Method() {
		this.methodAccess = "";
		this.methodName = "";
		this.parameters = new ArrayList<String>();
		this.methodReturnType = "";
	}
	
	@Override
	public void setAccess(String accessType) {
		// TODO Auto-generated method stub
		this.methodAccess = accessType;
	}

	@Override
	public void setMethodName(String methodName) {
		// TODO Auto-generated method stub
		this.methodName = methodName;
	}

	@Override
	public void setParameters(List<String> parameters) {
		// TODO Auto-generated method stub
		this.parameters = parameters;
	}

	@Override
	public void setReturnType(String returnType) {
		// TODO Auto-generated method stub
		this.methodReturnType = returnType.substring(returnType.lastIndexOf(".")+1);
	}

	@Override
	public String getAccess() {
		// TODO Auto-generated method stub
		return this.methodAccess;
	}

	@Override
	public String getMethodName() {
		// TODO Auto-generated method stub
		return this.methodName;
	}

	@Override
	public List<String> getParameters() {
		// TODO Auto-generated method stub
		return this.parameters;
	}

	@Override
	public String getReturnType() {
		// TODO Auto-generated method stub
		return this.methodReturnType;
	}

}
