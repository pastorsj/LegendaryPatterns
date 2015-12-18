package legendaryInterfaces;

/*
 * Author: Jason Lane
 */
public interface IField {
	public void setAccess(String methodAccessType);
	public void setMethodName(String methodName);
	public void setReturnType(String methodReturnType);
	
	public String getAccess();
	public String getMethodName();
	public String getReturnType();
}
