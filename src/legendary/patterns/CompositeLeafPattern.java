package legendary.patterns;

/**
 * This class contains the unique aspects of the leaf class within 
 * an composite pattern
 */
public class CompositeLeafPattern extends AbstractCompositePattern{

	/* (non-Javadoc)
	 * @see legendary.Interfaces.IPattern#tag()
	 */
	@Override
	public String tag() {
		return "Leaf, ";
	}

}
