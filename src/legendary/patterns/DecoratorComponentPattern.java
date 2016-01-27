package legendary.patterns;

import legendary.Interfaces.IPattern;

public class DecoratorComponentPattern implements IPattern {

	@Override
	public String tag() {
		return "component, ";
	}

	@Override
	public String color() {
		return "style = \"filled\"\nfillcolor = \"green\"";
	}

}
