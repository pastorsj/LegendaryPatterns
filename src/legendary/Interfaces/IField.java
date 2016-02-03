package legendary.Interfaces;

import java.util.Set;

// TODO: Auto-generated Javadoc
/**
 * The Interface IField.
 */
/*
 * Author: Jason Lane
 */
public interface IField {
	
	/**
	 * Sets the access.
	 *
	 * @param methodAccessType the new access
	 */
	public void setAccess(String methodAccessType);
	
	/**
	 * Sets the field name.
	 *
	 * @param methodName the new field name
	 */
	public void setFieldName(String methodName);
	
	/**
	 * Sets the type.
	 *
	 * @param methodReturnType the new type
	 */
	public void setType(String methodReturnType);
	
	/**
	 * Gets the access.
	 *
	 * @return the access
	 */
	public String getAccess();
	
	/**
	 * Gets the field name.
	 *
	 * @return the field name
	 */
	public String getFieldName();
	
	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType();
	
	/**
	 * Gets the base types.
	 *
	 * @return the base types
	 */
	Set<String> getBaseTypes();
}
