package legendary.detectors;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import legendary.Classes.Relations;
import legendary.Interfaces.IClass;
import legendary.Interfaces.IField;
import legendary.Interfaces.IModel;
import legendary.Interfaces.IPattern;
import legendary.Interfaces.IPatternDetector;
import legendary.patterns.CompositeComponentPattern;
import legendary.patterns.CompositeLeafPattern;
import legendary.patterns.CompositePattern;

public class CompositeDetector implements IPatternDetector {

	private IPatternDetector detector;

	public CompositeDetector() {
	}

	public CompositeDetector(IPatternDetector detector) {
		this.detector = detector;
	}

	@Override
	public Map<Class<? extends IPattern>, Set<IClass>> detect(IModel m) {
		Map<Class<? extends IPattern>, Set<IClass>> res;
		Map<IClass, Map<Relations, Set<IClass>>> rels = m.getRelGraph();
		Set<IClass> leaves = new HashSet<>();
		Set<IClass> composites = new HashSet<>();
		Set<IClass> components = new HashSet<>();
		Set<String> collectionExt = new HashSet<>();
		for (IClass c : m.getClasses()) {
			for (IClass c2 : (getAllSupers(m, c))) {
				if (c2.getClassName().equals("java.util::Collection")) {
					collectionExt.add(c.getClassName().replace("::", "."));
					break;
				}
			}
		}
		outer: for (Set<IClass> candSet : getCandidates(m)) {
			IClass compon = null;
			IClass compos = null;
			IClass mid = null;
			IClass temp;
			IClass temp2;
			IClass temp3;
			boolean three = false;
			Iterator<IClass> iter = candSet.iterator();
			temp = iter.next();
			temp2 = iter.next();
			if (iter.hasNext()) {
				three = true;
			}
			// I really, really, really hate this particular block
			if (rels.get(temp).get(Relations.ASSOCIATES).contains(temp2)) {
				compos = temp;
				compon = temp2;
				mid = compon;
			} else if (rels.get(temp2).get(Relations.ASSOCIATES).contains(temp)) {
				compos = temp2;
				compon = temp;
				mid = compon;
			}
			if (three) {
				temp3 = iter.next();
				if (rels.get(temp).get(Relations.ASSOCIATES).contains(temp3)) {
					compos = temp;
					compon = temp3;
					mid = temp2;
				} else if (rels.get(temp3).get(Relations.ASSOCIATES).contains(temp)) {
					compos = temp3;
					compon = temp;
					mid = temp2;
				} else if (rels.get(temp2).get(Relations.ASSOCIATES).contains(temp3)) {
					compos = temp2;
					compon = temp3;
					mid = temp;
				} else {
					compos = temp3;
					compon = temp2;
					mid = temp;
				}
			}
			for (IField f : compos.getFields()) {
				System.out.println(f.getBaseTypes());
				if (f.getBaseTypes().contains(compon.getClassName()) || f.getBaseTypes().contains(compon.getClassName()+"[]")) {
					for (String s : f.getBaseTypes()) {
						if (collectionExt.contains(s) || f.getType().endsWith("[]")) {
							if (!compos.isDrawable())
								continue outer;
							composites.add(compos);
							components.add(compon);
							components.add(mid);
							leaves.addAll(mid.isInterface() ? rels.get(mid).get(Relations.REV_IMPLEMENTS)
									: rels.get(mid).get(Relations.REV_EXTENDS));
							composites.addAll(mid.isInterface() ? rels.get(compos).get(Relations.REV_IMPLEMENTS)
									: rels.get(compos).get(Relations.REV_EXTENDS));
							leaves.removeAll(composites);
							leaves.removeAll(components);
						}
					}
				}
			}
		}
		Set<IClass> allRes = new HashSet<>();
		allRes.addAll(components);
		allRes.addAll(composites);
		allRes.addAll(leaves);
		for (IClass c : allRes) {
			c.setDrawable(true);
		}
		if (this.detector != null) {
			res = detector.detect(m);
		} else {
			res = new HashMap<>();
		}
		res.put(CompositeComponentPattern.class, components);
		res.put(CompositeLeafPattern.class, leaves);
		res.put(CompositePattern.class, composites);
		return res;
	}

	@Override
	public Set<Set<IClass>> getCandidates(IModel m) {
		Set<Set<IClass>> result = new HashSet<>();
		Map<IClass, Map<Relations, Set<IClass>>> rels = m.getRelGraph();
		for (IClass c : m.getClasses()) {
			if (!(rels.get(c).get(Relations.REV_IMPLEMENTS).isEmpty()
					&& rels.get(c).get(Relations.REV_EXTENDS).isEmpty())) {
				Set<IClass> subs = new HashSet<>();
				subs.addAll(rels.get(c).get(Relations.REV_IMPLEMENTS));
				subs.addAll(rels.get(c).get(Relations.REV_EXTENDS));
				Set<IClass> allSupers = getAllSupers(m, c);
				allSupers.add(c);
				for (IClass sub : subs) {
					for (IClass superC : allSupers) {
						if (rels.get(sub).get(Relations.ASSOCIATES).contains(superC)) {
							Set<IClass> cand = new HashSet<>();
							cand.add(superC);
							cand.add(sub);
							cand.add(c);
							result.add(cand);
						}
					}
				}
			}
		}
		return result;
	}

	private Set<IClass> getAllSupers(IModel m, IClass c) {
		Set<IClass> result = new HashSet<>();
		Set<IClass> supers = new HashSet<>();
		supers.addAll(m.getRelGraph().get(c).get(Relations.EXTENDS));
		supers.addAll(m.getRelGraph().get(c).get(Relations.IMPLEMENTS));
		result.addAll(supers);
		for (IClass c2 : supers) {
			result.addAll(getAllSupers(m, c2));
		}
		return result;
	}
}
