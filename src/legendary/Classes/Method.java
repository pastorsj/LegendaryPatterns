package legendary.Classes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import legendary.Interfaces.IMethod;
import legendary.Interfaces.ITraverser;
import legendary.Interfaces.IVisitor;

/*
 * Author: Jason Lane
 */
public class Method implements IMethod, ITraverser{

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
		this.methodAccess = accessType;
	}

	@Override
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	@Override
	public void setParameters(List<String> parameters) {
		this.parameters = parameters;
	}

	@Override
	public void setReturnType(String returnType) {
		this.methodReturnType = returnType.substring(returnType.lastIndexOf(".")+1);		
	}

	@Override
	public String getAccess() {
		return this.methodAccess;
	}

	@Override
	public String getMethodName() {
		return this.methodName;
	}

	@Override
	public List<String> getParameters() {
		return this.parameters;
	}

	@Override
	public String getReturnType() {
		return this.methodReturnType;
	}
	
	@Override
	public void accept(IVisitor v){
		v.previsit(this);
		v.visit(this);
		v.postvisit(this);
	}

}
