package legendary.patterns;

import legendary.Interfaces.IPattern;

public class DecoratorPattern implements IPattern{

	@Override
	public String tag() {
		return "Decorator, ";
	}

	@Override
	public String color() {
		return "";
	}

}
