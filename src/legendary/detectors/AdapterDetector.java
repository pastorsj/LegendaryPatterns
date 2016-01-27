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
import legendary.asm.DesignParser;
import legendary.patterns.AdapteePattern;
import legendary.patterns.AdapterPattern;
import legendary.patterns.AdapterTargetPattern;

public class AdapterDetector implements IPatternDetector {

	private IPatternDetector detector;

	public AdapterDetector() {
	}

	public AdapterDetector(IPatternDetector detector) {
		this.detector = detector;
	}

	@Override
	public Map<Class<? extends IPattern>, Set<IClass>> detect(IModel m) {
		Map<IClass, Map<Relations, Set<IClass>>> rels = m.getRelGraph();
		Set<Set<IClass>> candSets = getCandidates(m);
		Set<IClass> targetSet = new HashSet<>();
		Set<IClass> adapteeSet = new HashSet<>();
		Set<IClass> adapterSet = new HashSet<>();
		for (Set<IClass> candSet : candSets) {
			for (IClass c : candSet) {
				adapterSet.add(c);
				Set<IClass> supers = new HashSet<>();
				supers.addAll(rels.get(c).get(Relations.EXTENDS));
				supers.addAll(rels.get(c).get(Relations.IMPLEMENTS));
				for (IClass c2 : supers) {
					if (!rels.get(c2).get(Relations.REV_ASSOCIATES).isEmpty()) {
						targetSet.add(c2);
					}
				}
				for (IClass c2 : rels.get(c).get(Relations.ASSOCIATES)) {
					adapteeSet.add(c2);
				}
			}
		}
		Map<Class<? extends IPattern>, Set<IClass>> res;
		if (this.detector == null) {
			res = new HashMap<>();
		} else {
			res = detector.detect(m);
		}
		boolean draw = false;
		for (IClass c : adapteeSet) {
			if (c.getClassName().contains(DesignParser.packageName)) {
				draw = true;
				break;
			}
		}
		for (IClass c : adapterSet) {
			if (c.getClassName().contains(DesignParser.packageName)) {
				draw = true;
				break;
			}
		}
		for (IClass c : targetSet) {
			if (!c.getClassName().contains(DesignParser.packageName)) {
				draw = true;
				break;
			}
		}
		if (draw) {
			for (IClass c : adapteeSet) {
				c.setDrawable(true);
			}
			for (IClass c : adapterSet) {
				c.setDrawable(true);
			}
			for (IClass c : targetSet) {
				c.setDrawable(true);
			}
		}
		res.put(AdapterPattern.class, adapterSet);
		res.put(AdapteePattern.class, adapteeSet);
		res.put(AdapterTargetPattern.class, targetSet);
		return res;
	}

	@Override
	public Set<Set<IClass>> getCandidates(IModel m) {
		Map<IClass, Map<Relations, Set<IClass>>> rels = m.getRelGraph();
		Set<Set<IClass>> candSet = new HashSet<>();
		Set<IClass> cands = new HashSet<>();
		boolean in;
		for (IClass c : m.getClasses()) {
			in = false;
			Set<IClass> supers = new HashSet<>();
			supers.addAll(rels.get(c).get(Relations.EXTENDS));
			supers.addAll(rels.get(c).get(Relations.IMPLEMENTS));
			if (!supers.isEmpty()) {
				Set<IClass> assocs = rels.get(c).get(Relations.ASSOCIATES);
				if (!assocs.isEmpty()) {
					for (IClass c2 : supers) {
						if (!rels.get(c2).get(Relations.REV_ASSOCIATES).isEmpty()) {
							in = true;
						}
					}
				}
			}
			if (in) {
				cands.add(c);
				candSet.add(cands);
			}
		}
		return candSet;
	}

}
