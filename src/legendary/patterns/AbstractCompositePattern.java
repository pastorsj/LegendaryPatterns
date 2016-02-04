package legendary.patterns;

import java.util.Set;

import legendary.Interfaces.IPattern;

/**
 * This class contains the similar aspects of the composite pattern when
 * displaying in the uml diagram
 */
public abstract class AbstractCompositePattern implements IPattern {

	/*
	 * (non-Javadoc)
	 * 
	 * @see legendary.Interfaces.IPattern#color()
	 */
	@Override
	public String color() {
		return "style = \"filled\"\nfillcolor = \"yellow\"";
	}

	@Override
	public String tagArrow(Set<IPattern> cPatterns, Set<IPattern> c2Patterns) {
		return "";
	}
}
