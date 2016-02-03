package legendary.patterns;

import legendary.Interfaces.IPattern;

// TODO: Auto-generated Javadoc
/**
 * The Class SingletonPattern.
 */
public class SingletonPattern implements IPattern {

	
	/* (non-Javadoc)
	 * @see legendary.Interfaces.IPattern#tag()
	 */
	@Override
	public String tag() {
		return "Singleton, ";
	}

	/* (non-Javadoc)
	 * @see legendary.Interfaces.IPattern#color()
	 */
	@Override
	public String color() {
		return "color = blue";
	}

}
