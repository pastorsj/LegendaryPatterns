package legendary.patterns;

import legendary.Interfaces.IPattern;

/**
 * This class contains the similar aspects of the adapter pattern 
 * when displaying in the uml diagram
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
