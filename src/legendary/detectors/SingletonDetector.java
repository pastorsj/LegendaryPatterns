package legendary.detectors;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import legendary.Classes.Relations;
import legendary.Interfaces.IClass;
import legendary.Interfaces.IField;
import legendary.Interfaces.IMethod;
import legendary.Interfaces.IModel;
import legendary.Interfaces.IPattern;
import legendary.Interfaces.IPatternDetector;
import legendary.patterns.SingletonPattern;

// TODO: Auto-generated Javadoc
/**
 * The Class SingletonDetector.
 */
public class SingletonDetector implements IPatternDetector {

	/** The next. */
	private IPatternDetector next;

	/**
	 * Instantiates a new singleton detector.
	 */
	public SingletonDetector() {
	}

	/**
	 * Instantiates a new singleton detector.
	 *
	 * @param next the next
	 */
	public SingletonDetector(IPatternDetector next) {
		this.next = next;
	}

	/* (non-Javadoc)
	 * @see legendary.Interfaces.IPatternDetector#detect(legendary.Interfaces.IModel)
	 */
	@Override
	public Map<Class<? extends IPattern>, Set<IClass>> detect(IModel m) {
		Set<IClass> singletons = new HashSet<>();
		Set<Set<IClass>> candidates = getCandidates(m);
		for (Set<IClass> candidateSet : candidates) {
			for (IClass candidate : candidateSet) {
				boolean privCtorsOnly = true;
				for (IMethod method : candidate.getMethods().get("<init>").values()) {
					privCtorsOnly = privCtorsOnly && method.getAccess().equals("-");
				}
				if (privCtorsOnly) {
					for (IField f : candidate.getFields()) {
						if (f.getAccess().equals("-_") && f.getType().equals(candidate.getClassName())) {
							for (IMethod method : candidate.getMethodObjects()) {
								// System.out.println(method.getReturnType());
								if (method.getReturnType().equals(candidate.getClassName())
										&& method.getAccess().equals("+_")) {
									singletons.add(candidate);
								}
							}
						}
					}
				}
			}
		}
		Map<Class<? extends IPattern>, Set<IClass>> res;
		if (next == null) {
			res = new HashMap<>();
		} else {
			res = next.detect(m);
		}
		res.put(SingletonPattern.class, singletons);
		return res;
	}

	/* (non-Javadoc)
	 * @see legendary.Interfaces.IPatternDetector#getCandidates(legendary.Interfaces.IModel)
	 */
	@Override
	public Set<Set<IClass>> getCandidates(IModel m) {
		Set<Set<IClass>> res = new HashSet<>();
		for (IClass c : m.getRelGraph().keySet()) {
			Set<IClass> candidates = new HashSet<>();
			if (m.getRelGraph().get(c).get(Relations.ASSOCIATES).contains(c)) {
				candidates.add(c);
				res.add(candidates);
			}
		}
		return res;
	}

}
