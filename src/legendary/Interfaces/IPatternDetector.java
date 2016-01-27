package legendary.Interfaces;

import java.util.Map;
import java.util.Set;

public interface IPatternDetector {
	public Map<Class<? extends IPattern>, Set<IClass>> detect(IModel m);
	public Set<Set<IClass>> getCandidates(IModel m);
}
