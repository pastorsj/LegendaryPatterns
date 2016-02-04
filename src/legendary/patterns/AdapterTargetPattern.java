package legendary.patterns;

/**
 * This class contains the unique aspects of the target class within 
 * an adapter pattern
 */
public class AdapterTargetPattern extends AbstractAdapterPattern{
	
	/* (non-Javadoc)
	 * @see legendary.Interfaces.IPattern#tag()
	 */
	@Override
	public String tag() {
		return "target, ";
	}
}
