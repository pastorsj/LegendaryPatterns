package legendary.patterns;

import legendary.Interfaces.IPattern;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractCompositePattern.
 */
public abstract class AbstractCompositePattern implements IPattern {

	/* (non-Javadoc)
	 * @see legendary.Interfaces.IPattern#color()
	 */
	@Override
	public String color() {
		return "style = \"filled\"\nfillcolor = \"yellow\"";
	}

}
