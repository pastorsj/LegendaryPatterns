package legendary.Interfaces;

import java.util.List;
import java.util.Map;
import java.util.Set;

import legendary.Classes.Relations;

// TODO: Auto-generated Javadoc
/**
 * The Interface IModel.
 */
public interface IModel {
	
	/**
	 * Gets the classes.
	 *
	 * @return the classes
	 */
	public Set<IClass> getClasses();
	
	/**
	 * Adds the class.
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
	 * Adds the relation.
	 *
	 * @param c1 the c1
	 * @param c2 the c2
	 * @param r the r
	 */
	public void addRelation(String c1, String c2, Relations r);
	
	/**
	 * Convert to graph.
	 */
	public void convertToGraph();
	
	/**
	 * Gets the rel graph.
	 *
	 * @return the rel graph
	 */
	public Map<IClass, Map<Relations, Set<IClass>>> getRelGraph();
	
	/**
	 * Removes the dup arrows.
	 *
	 * @param tempMap the temp map
	 */
	public void removeDupArrows(Map<IClass, Map<Relations, Set<IClass>>> tempMap);
	
	/**
	 * Contains class.
	 *
	 * @param i the i
	 * @return true, if successful
	 */
	public boolean containsClass(String i);
}
