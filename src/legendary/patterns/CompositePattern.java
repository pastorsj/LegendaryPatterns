package legendary.patterns;

/**
 * This class contains the unique aspects of the composite class within 
 * an composite pattern
 */
public class CompositePattern extends AbstractCompositePattern{

	/* (non-Javadoc)
	 * @see legendary.Interfaces.IPattern#tag()
	 */
	@Override
	public String tag() {
		return "Composite, ";
	}

}
