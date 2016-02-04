package legendary.Interfaces;

import java.util.Set;


/*
 * This is the internal representation of a field in java
 * 
 * Author: Jason Lane
 */
public interface IField {
	
	/**
	 * Sets the access/visibility.
	 *
	 * @param fieldAccessType
	 */
	public void setAccess(String fieldAccessType);
	
	/**
	 * Sets the field name.
	 *
	 * @param fieldName
	 */
	public void setFieldName(String fieldName);
	
	/**
	 * Sets the type of the field (String, boolean...)
	 *
	 * @param fieldReturnType the new type
	 */
	public void setType(String fieldReturnType);
	
	/**
	 * Gets the access/visibility of the field.
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
	 * Gets the type of the field
	 *
	 * @return the type
	 */
	public String getType();
	
	//TODO: Jason
	/**
	 * Gets the base types.
	 *
	 * @return the base types
	 */
	Set<String> getBaseTypes();
}
