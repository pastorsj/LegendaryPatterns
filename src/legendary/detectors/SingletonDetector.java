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

public class SingletonDetector implements IPatternDetector {

	private IPatternDetector next;

	public SingletonDetector() {
	}

	public SingletonDetector(IPatternDetector next) {
		this.next = next;
	}

	@Override
	public Map<Class<? extends IPattern>, Set<IClass>> detect(IModel m) {
		Set<IClass> singletons = new HashSet<>();
		Set<IClass> candidates = getCandidates(m);
		for (IClass candidate : candidates) {
			boolean privCtorsOnly = true;
			for (IMethod method : candidate.getMethods().get("<init>").values()) {
				privCtorsOnly = privCtorsOnly && method.getAccess().equals("-");
			}
			if (privCtorsOnly) {
				for (IField f : candidate.getFields()) {
					if (f.getAccess().equals("-_") && f.getType().equals(candidate.getClassName())) {
						for (IMethod method : candidate.getMethodObjects()) {
//							System.out.println(method.getReturnType());
							if (method.getReturnType().equals(candidate.getClassName())
									&& method.getAccess().equals("+_")) {
								singletons.add(candidate);
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

	@Override
	public Set<IClass> getCandidates(IModel m) {
		Set<IClass> candidates = new HashSet<>();
		for (IClass c : m.getRelGraph().keySet())
			if (m.getRelGraph().get(c).get(Relations.ASSOCIATES).contains(c))
				candidates.add(c);
		return candidates;
	}

}
