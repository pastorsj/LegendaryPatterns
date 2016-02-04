package legendary.patterns;

/**
 * This class contains the unique aspects of the component class within 
 * an composite pattern
 */
public class CompositeComponentPattern extends AbstractCompositePattern{

	/* (non-Javadoc)
	 * @see legendary.Interfaces.IPattern#tag()
	 */
	@Override
	public String tag() {
		return "Component, ";
	}

}
