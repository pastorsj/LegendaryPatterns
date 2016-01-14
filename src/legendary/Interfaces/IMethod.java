package legendary.Interfaces;

import java.util.List;
import java.util.Queue;


/*
 * Author: Jason Lane
 */
public interface IMethod {
	public void setAccess(String accessType);
	public void setMethodName(String methodName);
	public void setParameters(List<String> parameters);
	public void setReturnType(String returnType);
	public void addMethodToCallStack(String methodOwner, String className, String methodName, String returnType, String params);
	
	public String getAccess();
	public String getMethodName();
	public List<String> getParameters();
	public String getReturnType();
	public Queue<List<String>> getCallStack();
}
