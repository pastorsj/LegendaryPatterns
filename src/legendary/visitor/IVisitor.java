package legendary.visitor;

import java.util.Map;

// TODO: Auto-generated Javadoc
/**
 * The Interface IVisitor.
 */
public interface IVisitor {
	
	/**
	 * Pre visit.
	 *
	 * @param t the t
	 */
	public void preVisit(ITraverser t);
	
	/**
	 * Visit.
	 *
	 * @param t the t
	 */
	public void visit(ITraverser t);
	
	/**
	 * Post visit.
	 *
	 * @param t the t
	 */
	public void postVisit(ITraverser t);
	
	/**
	 * Adds the visit.
	 *
	 * @param visitType the visit type
	 * @param clazz the clazz
	 * @param m the m
	 */
	public void addVisit(VisitType visitType, Class<?> clazz, IVisitMethod m);
	
	/**
	 * Removes the visit.
	 *
	 * @param visitType the visit type
	 * @param clazz the clazz
	 */
	public void removeVisit(VisitType visitType, Class<?> clazz);
	
	/**
	 * Gets the map.
	 *
	 * @return the map
	 */
	public Map<LookupKey, IVisitMethod> getMap();
}
