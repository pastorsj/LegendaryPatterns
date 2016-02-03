package legendary.patterns;

import legendary.Interfaces.IPattern;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractAdapterPattern.
 */
public abstract class AbstractAdapterPattern implements IPattern{

	/* (non-Javadoc)
	 * @see legendary.Interfaces.IPattern#color()
	 */
	@Override
	public String color(){
		return "style = \"filled\"\nfillcolor = \"red\"";
		
	}
	
}
