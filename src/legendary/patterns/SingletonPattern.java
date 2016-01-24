package legendary.patterns;

import legendary.Interfaces.IPattern;

public class SingletonPattern implements IPattern {

	
	@Override
	public String tag() {
		return "Singleton, ";
	}

	@Override
	public String color() {
		return "color = blue";
	}

}
