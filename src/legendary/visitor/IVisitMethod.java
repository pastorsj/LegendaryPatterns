package legendary.visitor;


/**
 * This class allows for methods to be executed within lambda blacks
 * during the visiting of the different class
 */
@FunctionalInterface
public interface IVisitMethod {
	
	/**
	 * The execute method.
	 *
	 * @param t the traverser
	 */
	public void execute(ITraverser t);
}
