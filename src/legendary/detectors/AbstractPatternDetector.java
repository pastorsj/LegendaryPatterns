package legendary.detectors;

import java.util.Map;
import java.util.Set;

import legendary.Interfaces.IClass;
import legendary.Interfaces.IPatternDetector;

public abstract class AbstractPatternDetector implements IPatternDetector{
	
	protected IPatternDetector detector;
	protected Map<IClass, Set<IClass>> keyMap;
	
	@Override
	public void addDetector(IPatternDetector detector){
		this.detector = detector;
	}
	
	@Override
	public Map<IClass, Set<IClass>> getKeyMap() {
		return this.keyMap;
	}
	
	@Override
	public IPatternDetector getDecorated(){
		return this.detector;
	}

}
