package legendary.Interfaces;

import java.util.Set;

public interface IPatternDetector {
	public Set<IClass> detect(IModel m);
	public Set<IClass> getCandidates(IModel m);
}
