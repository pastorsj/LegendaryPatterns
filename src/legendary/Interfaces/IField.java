package legendary.Interfaces;

import java.util.Set;

/*
 * Author: Jason Lane
 */
public interface IField {
	public void setAccess(String methodAccessType);
	public void setFieldName(String methodName);
	public void setType(String methodReturnType);
	
	public String getAccess();
	public String getFieldName();
	public String getType();
	public String toString();
	Set<String> getBaseTypes();
}
