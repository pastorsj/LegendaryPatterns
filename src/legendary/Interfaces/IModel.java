package legendary.Interfaces;

import java.util.List;
import java.util.Map;
import java.util.Set;

import legendary.Classes.Relations;

/**
 * This is the internal representation of the project
 * being analyzed. It has relations, patterns, and a set of classes that
 * compose it
 */
public interface IModel {
	
	/**
	 * Gets the classes.
	 *
	 * @return the classes
	 */
	public Set<IClass> getClasses();
	
	/**
	 * Adds a class.
	 *
	 * @param c the c
	 */
	public void addClass(IClass c);
	
	/**
	 * Gets the relations.
	 *
	 * @return the relations
	 */
	public Map<List<String>, List<Relations>> getRelations();
	
	/**
	 * Adds a relation.
	 *
	 * @param c1 The first class in the relation
	 * @param c2 The second class in the relation
	 * @param r The relation type
	 */
	public void addRelation(String c1, String c2, Relations r);
	
	/**
	 * Convert the model to a graph. This allows use to get back edges 
	 * which will make it much easier for pattern detection
	 */
	public void convertToGraph();
	
	//TODO: Jason
	/**
	 * Gets the relations graph.
	 *
	 * @return the relations graph
	 */
	public Map<IClass, Map<Relations, Set<IClass>>> getRelGraph();
	
	/**
	 * Removes any arrows that are unnecessary 
	 *
	 * @param tempMap the current map of the model
	 */
	public void removeDupArrows(Map<IClass, Map<Relations, Set<IClass>>> tempMap);
	
	/**
	 * Checks to see is the model contains a class
	 *
	 * @param clazz The class
	 * @return true, if the class is in the model
	 */
	public boolean containsClass(String clazz);
}
