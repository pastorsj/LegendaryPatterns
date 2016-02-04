package legendary.patterns;

/**
 * This class contains the unique aspects of the decorator class within 
 * an decorator pattern
 */
public class DecoratorPattern extends AbstractDecoratorPattern{

	/* (non-Javadoc)
	 * @see legendary.Interfaces.IPattern#tag()
	 */
	@Override
	public String tag() {
		return "decorator, ";
	}
}
