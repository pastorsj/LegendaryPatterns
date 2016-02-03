package legendary.patterns;

import legendary.Interfaces.IPattern;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractDecoratorPattern.
 */
public abstract class AbstractDecoratorPattern implements IPattern{
	
	/* (non-Javadoc)
	 * @see legendary.Interfaces.IPattern#color()
	 */
	@Override
	public String color() {
		return "style = \"filled\"\nfillcolor = \"green\"";
	}
}
