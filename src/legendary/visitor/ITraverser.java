package legendary.visitor;

/**
 * This class allows for the any class using the visitor
 * to traverse through the class being analyzed
 */
public interface ITraverser {
	
	/**
	 * The accept method
	 *
	 * @param v the visitor
	 */
	public void accept(IVisitor v);
}
