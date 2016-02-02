package legendary.patterns;

import legendary.Interfaces.IPattern;

public abstract class AbstractDecoratorPattern implements IPattern{
	@Override
	public String color() {
		return "style = \"filled\"\nfillcolor = \"green\"";
	}
}
