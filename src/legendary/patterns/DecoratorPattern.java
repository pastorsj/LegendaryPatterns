package legendary.patterns;

import legendary.Interfaces.IPattern;

public class DecoratorPattern implements IPattern{

	@Override
	public String tag() {
		return "decorator, ";
	}

	@Override
	public String color() {
		return "style = \"filled\"\nfillcolor = \"green\"";
	}

}
