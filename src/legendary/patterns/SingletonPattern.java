package legendary.patterns;

import java.util.Set;

import legendary.Interfaces.IPattern;

/**
 * This class contains the aspects of the singleton pattern
 */
public class SingletonPattern implements IPattern {

	/*
	 * (non-Javadoc)
	 * 
	 * @see legendary.Interfaces.IPattern#tag()
	 */
	@Override
	public String tag() {
		return "Singleton, ";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see legendary.Interfaces.IPattern#color()
	 */
	@Override
	public String color() {
		return "color = blue";
	}

	@Override
	public String tagArrow(Set<IPattern> cPatterns, Set<IPattern> c2Patterns) {
		return "";
	}
}
