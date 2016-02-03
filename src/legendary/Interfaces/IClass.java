package legendary.Interfaces;

import java.util.List;
import java.util.Map;

// TODO: Auto-generated Javadoc
/*
 * Author: Jason Lane
 */

/**
 * The Interface IClass.
 */
public interface IClass {
	
	/**
	 * Sets the class name.
	 *
	 * @param className the new class name
	 */
	public void setClassName(String className);
	
	/**
	 * Sets the super.
	 *
	 * @param superClassName the new super
	 */
	public void setSuper(String superClassName);
	
	/**
	 * Sets the interfaces.
	 *
	 * @param interfaces the new interfaces
	 */
	public void setInterfaces(List<String> interfaces);
	
	/**
	 * Adds the method.
	 *
	 * @param method the method
	 */
	public void addMethod(IMethod method);
	
	/**
	 * Adds the field.
	 *
	 * @param field the field
	 */
	public void addField(IField field);
	
	/**
	 * Gets the class name.
	 *
	 * @return the class name
	 */
	public String getClassName();
	
	/**
	 * Gets the super name.
	 *
	 * @return the super name
	 */
	public String getSuperName();
	
	/**
	 * Gets the interfaces.
	 *
	 * @return the interfaces
	 */
	public List<String> getInterfaces();
	
	/**
	 * Gets the methods.
	 *
	 * @return the methods
	 */
	public Map<String, Map<List<String>, IMethod>> getMethods();
	
	/**
	 * Gets the fields.
	 *
	 * @return the fields
	 */
	public List<IField> getFields();
	
	/**
	 * Sets the checks if is interface.
	 *
	 * @param isInterface the new checks if is interface
	 */
	public void setIsInterface(boolean isInterface);
	
	/**
	 * Checks if is interface.
	 *
	 * @return true, if is interface
	 */
	public boolean isInterface();
	
	/**
	 * Gets the method objects.
	 *
	 * @return the method objects
	 */
	public List<IMethod> getMethodObjects();
	
	/**
	 * Gets the creation order.
	 *
	 * @return the creation order
	 */
	public int getCreationOrder();
	
	/**
	 * Sets the drawable.
	 *
	 * @param drawable the new drawable
	 */
	public void setDrawable(boolean drawable);
	
	/**
	 * Checks if is drawable.
	 *
	 * @return true, if is drawable
	 */
	public boolean isDrawable();
};
