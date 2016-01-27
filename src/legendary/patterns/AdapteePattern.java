package legendary.patterns;

import legendary.Interfaces.IPattern;

public class AdapteePattern implements IPattern{
	@Override
	public String tag() {
		return "adaptee, ";
	}

	@Override
	public String color() {
		return "style = \"filled\" fillcolor = red";
	}
}
