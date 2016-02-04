package legendary.Interfaces;

import java.util.Map;
import java.util.Set;

/**
 * This is the interface that defines how a pattern will
 * be analyzed
 */
public interface IPatternDetector {
	
	/**
	 * The main detection function that will return which classes are part
	 * of the pattern being analyzed
	 *
	 * @param m the model
	 * @return the map< class<? extends i pattern>, set< i class>>
	 */
	public Map<Class<? extends IPattern>, Set<IClass>> detect(IModel m);
	
	//TODO: Jason
	/**
	 * A helper method that will reduce the amount of work
	 * done in the detect method. 
	 *
	 * @param m the m
	 * @return the candidates
	 */
	public Set<Set<IClass>> getCandidates(IModel m);
}
