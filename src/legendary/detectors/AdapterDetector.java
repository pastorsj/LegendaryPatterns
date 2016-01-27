package legendary.detectors;

import java.util.Map;
import java.util.Set;

import legendary.Interfaces.IClass;
import legendary.Interfaces.IModel;
import legendary.Interfaces.IPattern;
import legendary.Interfaces.IPatternDetector;

public class AdapterDetector implements IPatternDetector{

	@Override
	public Map<Class<? extends IPattern>, Set<IClass>> detect(IModel m) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Set<IClass>> getCandidates(IModel m) {
		// TODO Auto-generated method stub
		return null;
	}

}
