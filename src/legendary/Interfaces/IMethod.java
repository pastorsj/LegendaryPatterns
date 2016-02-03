package legendary.Interfaces;

import java.util.List;
import java.util.Queue;


// TODO: Auto-generated Javadoc
/**
 * The Interface IMethod.
 */
/*
 * Author: Jason Lane
 */
public interface IMethod {
	
	/**
	 * Sets the access.
	 *
	 * @param accessType the new access
	 */
	public void setAccess(String accessType);
	
	/**
	 * Sets the method name.
	 *
	 * @param methodName the new method name
	 */
	public void setMethodName(String methodName);
	
	/**
	 * Sets the parameters.
	 *
	 * @param parameters the new parameters
	 */
	public void setParameters(List<String> parameters);
	
	/**
	 * Sets the return type.
	 *
	 * @param returnType the new return type
	 */
	public void setReturnType(String returnType);
	
	/**
	 * Gets the access.
	 *
	 * @return the access
	 */
	public String getAccess();
	
	/**
	 * Gets the method name.
	 *
	 * @return the method name
	 */
	public String getMethodName();
	
	/**
	 * Gets the parameters.
	 *
	 * @return the parameters
	 */
	public List<String> getParameters();
	
	/**
	 * Gets the return type.
	 *
	 * @return the return type
	 */
	public String getReturnType();
	
	/**
	 * Gets the call stack.
	 *
	 * @return the call stack
	 */
	public Queue<List<List<String>>> getCallStack();
	
	/**
	 * Adds the method to call stack.
	 *
	 * @param methodOwner the method owner
	 * @param className the class name
	 * @param methodName the method name
	 * @param params the params
	 */
	public void addMethodToCallStack(String methodOwner, String className, String methodName, List<String> params);
}
