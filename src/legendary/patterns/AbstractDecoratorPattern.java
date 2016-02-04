package legendary.patterns;

import java.util.Set;

import legendary.Interfaces.IPattern;

/**
 * This class contains the similar aspects of the adapter pattern when
 * displaying in the uml diagram
 */
public abstract class AbstractDecoratorPattern implements IPattern {

	/*
	 * (non-Javadoc)
	 * 
	 * @see legendary.Interfaces.IPattern#color()
	 */
	@Override
	public String color() {
		return "style = \"filled\"\nfillcolor = \"green\"";
	}

	@Override
	public String tagArrow(Set<IPattern> cPatterns, Set<IPattern> c2Patterns) {
		for (IPattern p : cPatterns) {
			if (p instanceof DecoratorPattern) {
				for (IPattern p2 : c2Patterns) {
					if (p2 instanceof DecoratorComponentPattern) {
						return "decorates, ";
					}
				}
			}
		}
		return "";
	}
}
