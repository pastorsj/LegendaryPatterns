package legendary.Interfaces;

import java.util.List;
import java.util.Map;

/*
 * This interface is the internal representation
 * of a class
 * Author: Jason Lane
 */

public interface IClass {
	
	/**
	 * Sets the class name.
	 *
	 * @param className the new class name
	 */
	public void setClassName(String className);
	
	/**
	 * Sets the superclass name if it exists.
	 *
	 * @param superClassName the new super
	 */
	public void setSuper(String superClassName);
	
	/**
	 * Sets any interfaces.
	 *
	 * @param interfaces the new interfaces
	 */
	public void setInterfaces(List<String> interfaces);
	
	/**
	 * Adds a method to the class.
	 *
	 * @param method the method
	 */
	public void addMethod(IMethod method);
	
	/**
	 * Adds a field to the class.
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
	 * Gets the super class name if it exists.
	 *
	 * @return the super name
	 */
	public String getSuperName();
	
	/**
	 * Gets the interfaces if any exist.
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
	 * Sets the field that determines whether the class is an interface.
	 *
	 * @param isInterface
	 */
	public void setIsInterface(boolean isInterface);
	
	/**
	 * Checks if the class is interface.
	 *
	 * @return true, if the class is an interface
	 */
	public boolean isInterface();
	
	/**
	 * Gets all of the methods in the class.
	 *
	 * @return the methods
	 */
	public List<IMethod> getMethodObjects();
	
	/**
	 * Sets the drawable property of the class.
	 *
	 * @param drawable
	 */
	public void setDrawable(boolean drawable);
	
	/**
	 * Checks if the class is drawable.
	 *
	 * @return true, if the class is drawable
	 */
	public boolean isDrawable();
};
