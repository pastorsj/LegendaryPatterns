package legendary.patterns;

/**
 * This class contains the unique aspects of the component class within 
 * an decorator pattern
 */
public class DecoratorComponentPattern extends AbstractDecoratorPattern {

	/* (non-Javadoc)
	 * @see legendary.Interfaces.IPattern#tag()
	 */
	@Override
	public String tag() {
		return "component, ";
	}
}
