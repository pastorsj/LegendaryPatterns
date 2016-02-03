package legendary.Interfaces;

import java.util.Map;
import java.util.Set;

// TODO: Auto-generated Javadoc
/**
 * The Interface IPatternDetector.
 */
public interface IPatternDetector {
	
	/**
	 * Detect.
	 *
	 * @param m the m
	 * @return the map< class<? extends i pattern>, set< i class>>
	 */
	public Map<Class<? extends IPattern>, Set<IClass>> detect(IModel m);
	
	/**
	 * Gets the candidates.
	 *
	 * @param m the m
	 * @return the candidates
	 */
	public Set<Set<IClass>> getCandidates(IModel m);
}
