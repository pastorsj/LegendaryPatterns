package legendary.patterns;

/**
 * This class contains the unique aspects of the adaptee class within 
 * an adapter pattern
 */
public class AdapteePattern extends AbstractAdapterPattern{
	
	/* (non-Javadoc)
	 * @see legendary.Interfaces.IPattern#tag()
	 */
	@Override
	public String tag() {
		return "adaptee, ";
	}
}
