package legendary.detectors;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import legendary.Classes.Relations;
import legendary.Interfaces.IClass;
import legendary.Interfaces.IModel;
import legendary.Interfaces.IPattern;
import legendary.Interfaces.IPatternDetector;
import legendary.patterns.DecoratorComponentPattern;
import legendary.patterns.DecoratorPattern;

public class DecoratorDetector implements IPatternDetector {

	private IPatternDetector detect;

	public DecoratorDetector() {
	}

	public DecoratorDetector(IPatternDetector detect) {
		this.detect = detect;
	}

	@Override
	public Map<Class<? extends IPattern>, Set<IClass>> detect(IModel m) {
		Set<IClass> decSet = new HashSet<>();
		Set<IClass> compSet = new HashSet<>();
		Set<IClass> tempSet = new HashSet<>();
		Set<Set<IClass>> candidateSets = this.getCandidates(m);
		boolean add = false;
		for (Set<IClass> set : candidateSets) {
			tempSet = new HashSet<>();
			add = true;
			for (IClass c : set) {
				for (IClass c2 : m.getRelGraph().get(c)
						.get(c.isInterface() ? Relations.REV_IMPLEMENTS : Relations.REV_EXTENDS)) {
					add = true;
					if (c2.isInterface()) {
						for (IClass c3 : m.getRelGraph().get(c2).get(Relations.REV_IMPLEMENTS)) {
							if (!m.getRelGraph().get(c3).get(Relations.ASSOCIATES).contains(c)) {
								add = false;
							} else {
								System.out.println(c3.getClassName());
								System.out.println(m.getRelGraph().get(c3).get(Relations.ASSOCIATES));
							}
						}
					} else if (!m.getRelGraph().get(c2).get(Relations.ASSOCIATES).contains(c)) {
						add = false;
					}
					if (add) {
						tempSet.add(c2);
						tempSet.addAll(m.getRelGraph().get(c2)
								.get(c2.isInterface() ? Relations.REV_IMPLEMENTS : Relations.REV_EXTENDS));
					}
				}
				if (add) {
					compSet.add(c);
					decSet.addAll(tempSet);
				}
			}
		}
		Map<Class<? extends IPattern>, Set<IClass>> res;
		if (this.detect == null) {
			res = new HashMap<>();
		} else {
			res = this.detect.detect(m);
		}
		res.put(DecoratorPattern.class, decSet);
		res.put(DecoratorComponentPattern.class, compSet);
		return res;
	}

	@Override
	public Set<Set<IClass>> getCandidates(IModel m) {
		Set<Set<IClass>> candidates = new HashSet<>();
		Set<IClass> candSet = new HashSet<>();
		Set<IClass> supers = new HashSet<>();
		for (IClass c : m.getClasses()) {
			if (!m.getRelGraph().containsKey(c))
				continue;
			Map<Relations, Set<IClass>> temp = m.getRelGraph().get(c);
			if (temp.get(Relations.REV_IMPLEMENTS).size() + temp.get(Relations.REV_EXTENDS).size() > 0) {
				supers.add(c);
				for (IClass c2 : temp.get(c.isInterface() ? Relations.REV_IMPLEMENTS : Relations.REV_EXTENDS)) {
					Map<Relations, Set<IClass>> temp2 = m.getRelGraph().get(c2);
					if (temp2.get(Relations.REV_IMPLEMENTS).size() + temp2.get(Relations.REV_EXTENDS).size() > 0) {
						candSet.add(c2);
						candidates.add(candSet);
						candSet = new HashSet<>();
					}
				}
			}
		}
		return candidates;
	}

}
