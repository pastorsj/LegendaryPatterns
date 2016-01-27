package legendary.patterns;

import legendary.Interfaces.IPattern;

public class AdapterPattern implements IPattern{

	@Override
	public String tag() {
		return "adapter, ";
	}

	@Override
	public String color() {
		return "style = \"filled\" fillcolor = red";
	}

}
