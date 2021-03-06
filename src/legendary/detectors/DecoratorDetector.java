package legendary.detectors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import legendary.Classes.Relations;
import legendary.Interfaces.IClass;
import legendary.Interfaces.IMethod;
import legendary.Interfaces.IModel;
import legendary.Interfaces.IPattern;
import legendary.Interfaces.IPatternDetector;
import legendary.asm.DesignParser;
import legendary.patterns.DecoratorComponentPattern;
import legendary.patterns.DecoratorPattern;

/**
 * This class allows for the detection of the decorator pattern with the given
 * model.
 */
public class DecoratorDetector extends AbstractPatternDetector {

	/**
	 * Instantiates a new decorator detector.
	 */
	public DecoratorDetector() {
		this.keyMap = new HashMap<>();
	}

	/**
	 * Instantiates a new decorator detector.
	 *
	 * @param detect
	 *            The pattern detector
	 */
	public DecoratorDetector(IPatternDetector detect) {
		this.detector = detect;
		this.keyMap = new HashMap<>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * legendary.Interfaces.IPatternDetector#detect(legendary.Interfaces.IModel)
	 */
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
				Set<IClass> all = new HashSet<>();
				all.addAll(m.getRelGraph().get(c).get(Relations.REV_IMPLEMENTS));
				all.addAll(m.getRelGraph().get(c).get(Relations.REV_EXTENDS));
				for (IClass c2 : all) {
					add = true;
					if (c2.isInterface()) {
						for (IClass c3 : m.getRelGraph().get(c2).get(Relations.REV_IMPLEMENTS)) {
							if (!m.getRelGraph().get(c3).get(Relations.ASSOCIATES).contains(c)) {
								add = false;
							}
						}
					} else if (!m.getRelGraph().get(c2).get(Relations.ASSOCIATES).contains(c)) {
						add = false;
					}
					int count = 0;
					for (IMethod method : c.getMethodObjects()) {
						for (IMethod method2 : c2.getMethodObjects()) {
							List<List<String>> call = new ArrayList<>();
							List<String> al = new ArrayList<>();
							al.add(c.getClassName());
							al.add(c2.getClassName());
							al.add(method2.getMethodName());
							call.add(al);
							call.add(method.getParameters());
							if (method.getCallStack().contains(call)) {
								count++;
								break;
							}
						}
						if (method.getMethodName().contains("<init>")) {
							if (method.getParameters().contains(c2.getClassName()))
								count++;
						}
					}
					if (count < DesignParser.DecoratorThreshold && count < c.getMethodObjects().size()) {
						add = false;
					}
					if (add) {
						tempSet.add(c2);
						tempSet.addAll(m.getRelGraph().get(c2)
								.get(c2.isInterface() ? Relations.REV_IMPLEMENTS : Relations.REV_EXTENDS));
						compSet.add(c);
						keyMap.put(c, tempSet);
						keyMap.get(c).add(c);
						decSet.addAll(tempSet);
					}
				}
			}
		}
		Map<Class<? extends IPattern>, Set<IClass>> res;
		if (this.detector == null) {
			res = new HashMap<>();
		} else {
			res = this.detector.detect(m);
		}
		boolean draw = false;
		for (IClass c : decSet) {
			if (c.getClassName().contains(DesignParser.packageName)) {
				draw = true;
				break;
			}
		}
		for (IClass c : compSet) {
			if (c.getClassName().contains(DesignParser.packageName)) {
				draw = true;
				break;
			}
		}
		if (draw) {
			for (IClass c : decSet) {
				c.setDrawable(true);
			}
			for (IClass c : compSet) {
				c.setDrawable(true);
			}
		}
		res.put(DecoratorPattern.class, decSet);
		res.put(DecoratorComponentPattern.class, compSet);
		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * legendary.Interfaces.IPatternDetector#getCandidates(legendary.Interfaces.
	 * IModel)
	 */
	@Override
	public Set<Set<IClass>> getCandidates(IModel m) {
		Set<Set<IClass>> candidates = new HashSet<>();
		Set<IClass> candSet = new HashSet<>();
		for (IClass c : m.getClasses()) {
			if (!m.getRelGraph().containsKey(c))
				continue;
			Map<Relations, Set<IClass>> temp = m.getRelGraph().get(c);
			if (temp.get(Relations.REV_IMPLEMENTS).size() + temp.get(Relations.REV_EXTENDS).size() > 0) {
				Set<IClass> all = new HashSet<>();
				all.addAll(temp.get(Relations.REV_IMPLEMENTS));
				all.addAll(temp.get(Relations.REV_EXTENDS));
				for (IClass c2 : all) {
					Map<Relations, Set<IClass>> temp2 = m.getRelGraph().get(c2);
					if (temp2.get(Relations.REV_IMPLEMENTS).size() + temp2.get(Relations.REV_EXTENDS).size() > 0) {
						candSet.add(c);
						candidates.add(candSet);
						candSet = new HashSet<>();
					}
				}
			}
		}
		return candidates;
	}

	@Override
	public String getPatternName() {
		// TODO Auto-generated method stub
		return "Decorator Pattern";
	}

}
