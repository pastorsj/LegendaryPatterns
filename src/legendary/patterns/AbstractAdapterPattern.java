package legendary.patterns;

import legendary.Interfaces.IPattern;

public abstract class AbstractAdapterPattern implements IPattern{

	@Override
	public String color(){
		return "style = \"filled\"\nfillcolor = red";
		
	}
	
}
