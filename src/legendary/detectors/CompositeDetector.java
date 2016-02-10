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

/**
 * This class allows for the detection of the composite pattern
 */
public class CompositeDetector extends AbstractPatternDetector {

	/**
	 * Instantiates a new composite detector.
	 */
	public CompositeDetector() {
	}

	/**
	 * Instantiates a new composite detector.
	 *
	 * @param detector
	 *            The pattern detector
	 */
	public CompositeDetector(IPatternDetector detector) {
		this.detector = detector;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * legendary.Interfaces.IPatternDetector#detect(legendary.Interfaces.IModel)
	 */
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
				if (compos != null)
					mid = temp3;
				else if (rels.get(temp).get(Relations.ASSOCIATES)
						.contains(temp3)) {
					compos = temp;
					compon = temp3;
					mid = temp2;
				} else if (rels.get(temp3).get(Relations.ASSOCIATES)
						.contains(temp)) {
					compos = temp3;
					compon = temp;
					mid = temp2;
				} else if (rels.get(temp2).get(Relations.ASSOCIATES)
						.contains(temp3)) {
					compos = temp2;
					compon = temp3;
					mid = temp;
				} else {
					compos = temp3;
					compon = temp2;
					mid = temp;
				}
			}
			System.out.println();
			if (!compos.isDrawable())
				continue outer;
			for (IField f : compos.getFields()) {
				Set<String> baseTypes = f.getBaseTypes();
				if (baseTypes.contains(compon.getClassName())
						|| baseTypes.contains(compon.getClassName() + "[]")) {
					for (String s : baseTypes) {
						if (collectionExt.contains(s)
								|| f.getType().endsWith("[]")) {
							composites.add(compos);
							components.add(compon);
							components.add(mid);
							leaves.addAll(mid.isInterface() ? rels.get(mid)
									.get(Relations.REV_IMPLEMENTS) : rels.get(
									mid).get(Relations.REV_EXTENDS));
							composites.addAll(mid.isInterface() ? rels.get(
									compos).get(Relations.REV_IMPLEMENTS)
									: rels.get(compos).get(
											Relations.REV_EXTENDS));
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * legendary.Interfaces.IPatternDetector#getCandidates(legendary.Interfaces
	 * .IModel)
	 */
	@Override
	public Set<Set<IClass>> getCandidates(IModel m) {
		Set<Set<IClass>> result = new HashSet<>();
		Map<IClass, Map<Relations, Set<IClass>>> rels = m.getRelGraph();
		for (IClass c : m.getClasses()) {
			if (!(rels.get(c).get(Relations.REV_IMPLEMENTS).isEmpty() && rels
					.get(c).get(Relations.REV_EXTENDS).isEmpty())) {
				Set<IClass> subs = new HashSet<>();
				subs.addAll(rels.get(c).get(Relations.REV_IMPLEMENTS));
				subs.addAll(rels.get(c).get(Relations.REV_EXTENDS));
				Set<IClass> allSupers = getAllSupers(m, c);
				allSupers.add(c);
				for (IClass sub : subs) {
					for (IClass superC : allSupers) {
						if (rels.get(sub).get(Relations.ASSOCIATES)
								.contains(superC)) {
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

	/**
	 * Gets the all classes that extend or implement the class that was passed
	 * in.
	 *
	 * @param m
	 *            The current model
	 * @param c
	 *            The current class
	 * @return all supers of the class
	 */
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

	@Override
	public String getPatternName() {
		// TODO Auto-generated method stub
		return "Composite Pattern";
	}
}
