package legendary.Interfaces;

import java.util.List;
import java.util.Queue;


/*
 * This is the internal representation of a method in java
 * Author: Jason Lane
 */
public interface IMethod {
	
	/**
	 * Sets the access/visibility of the method.
	 *
	 * @param accessType
	 */
	public void setAccess(String accessType);
	
	/**
	 * Sets the method name.
	 *
	 * @param methodName
	 */
	public void setMethodName(String methodName);
	
	/**
	 * Sets the parameters.
	 *
	 * @param parameters
	 */
	public void setParameters(List<String> parameters);
	
	/**
	 * Sets the return type.
	 *
	 * @param returnType
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
	 * Gets the call stack of the method.
	 * This is all of the methods that are called inside of this method
	 *
	 * @return the call stack
	 */
	public Queue<List<List<String>>> getCallStack();
	
	/**
	 * Adds a method to call stack.
	 *
	 * @param methodOwner the owner of this method
	 * @param className the owner of the class of the called method
	 * @param methodName the name of the called method
	 * @param params the params of the called method
	 */
	public void addMethodToCallStack(String methodOwner, String className, String methodName, List<String> params);
}
