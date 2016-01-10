package legendary.Interfaces;

import java.util.List;


/*
 * Author: Jason Lane
 */
public interface IMethod {
	public void setAccess(String accessType);
	public void setMethodName(String methodName);
	public void setParameters(List<String> parameters);
	public void setReturnType(String returnType);
	
	public String getAccess();
	public String getMethodName();
	public List<String> getParameters();
	public String getReturnType();
}
