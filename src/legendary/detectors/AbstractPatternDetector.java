package legendary.detectors;

import legendary.Interfaces.IPatternDetector;

public abstract class AbstractPatternDetector implements IPatternDetector{
	
	protected IPatternDetector detector;
	
	@Override
	public void addDetector(IPatternDetector detector){
		this.detector = detector;
	}

}
