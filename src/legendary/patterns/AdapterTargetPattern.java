package legendary.patterns;

import legendary.Interfaces.IPattern;

public class AdapterTargetPattern implements IPattern{
	@Override
	public String tag() {
		return "target, ";
	}

	@Override
	public String color() {
		return "style = \"filled\" fillcolor = red";
	}
}
