package legendary.patterns;

import legendary.Interfaces.IPattern;

public abstract class AbstractCompositePattern implements IPattern {

	@Override
	public String color() {
		return "style = \"filled\"\nfillcolor = \"yellow\"";
	}

}
