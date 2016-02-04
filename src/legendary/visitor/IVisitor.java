package legendary.visitor;

import java.util.Map;

// TODO: Auto-generated Javadoc
/**
 * The Interface IVisitor.
 */
public interface IVisitor {
	
	/**
	 * The pre visit method
	 *
	 * @param t the traverser
	 */
	public void preVisit(ITraverser t);
	
	/**
	 * The visit method
	 *
	 * @param t the traverser
	 */
	public void visit(ITraverser t);
	
	/**
	 * The post visit method
	 *
	 * @param t the traverser
	 */
	public void postVisit(ITraverser t);
	
	/**
	 * Adds the visit.
	 *
	 * @param visitType the visit type, defined in VisitType
	 * @param clazz the class
	 * @param m the model
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
