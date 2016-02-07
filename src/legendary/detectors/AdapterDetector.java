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
import legendary.patterns.AdapteePattern;
import legendary.patterns.AdapterPattern;
import legendary.patterns.AdapterTargetPattern;

/**
 * This class allows for the detection of the Adapter pattern
 */
public class AdapterDetector implements IPatternDetector {

	/** The next detector to run. */
	private IPatternDetector detector;

	/**
	 * Instantiates a new adapter detector.
	 */
	public AdapterDetector() {
	}

	/**
	 * Instantiates a new adapter detector.
	 *
	 * @param detector
	 *            The pattern detector
	 */
	public AdapterDetector(IPatternDetector detector) {
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
		Map<IClass, Map<Relations, Set<IClass>>> rels = m.getRelGraph();
		Set<Set<IClass>> candSets = getCandidates(m);
		Set<IClass> targetSet = new HashSet<>();
		Set<IClass> adapteeSet = new HashSet<>();
		Set<IClass> adapterSet = new HashSet<>();
		boolean draw = true;
		for (Set<IClass> candSet : candSets) {
			for (IClass c : candSet) {
				Set<IClass> tempSet1 = new HashSet<>();
				Set<IClass> tempSet2 = new HashSet<>();
				draw = c.isDrawable();
				if (draw) {
					Set<IClass> supers = new HashSet<>();
					supers.addAll(rels.get(c).get(Relations.EXTENDS));
					supers.addAll(rels.get(c).get(Relations.IMPLEMENTS));
					for (IClass c2 : supers) {
						Set<IClass> revassoc = rels.get(c2).get(
								Relations.REV_ASSOCIATES);
						boolean rc = revassoc.contains(c);
						boolean rc2 = revassoc.contains(c2);
						int rsize = revassoc.size();
						if ((rsize == 1 && !(rc || rc2))
								|| (rsize == 2 && !(rc && rc2)) || rsize > 2) {
							tempSet1.add(c2);
						} else
							draw = false;
					}
					for (IClass c2 : rels.get(c).get(Relations.ASSOCIATES)) {
						int count = 0;
						for (IMethod method : c.getMethodObjects()) {
							for (IMethod method2 : c2.getMethodObjects()) {
								List<List<String>> call = new ArrayList<>();
								List<String> al = new ArrayList<>();
								al.add(c.getClassName());
								al.add(c2.getClassName().substring(c2.getClassName().lastIndexOf(":")+1));
								al.add(method2.getMethodName());
								call.add(al);
								call.add(method.getParameters());
								if (method.getCallStack().contains(call)) {
									count++;
									break;
								}
							}
							if (method.getMethodName().contains("<init>")) {
								if (method.getParameters().contains(
										c2.getClassName()))
									count++;
							}
						}
						if (count >= DesignParser.AdapterThreshold)
							tempSet2.add(c2);
						if (tempSet2.isEmpty())
							draw = false;

					}
					if (draw) {
						adapterSet.add(c);
						adapteeSet.addAll(tempSet2);
						targetSet.addAll(tempSet1);
					}
				}
			}
		}

		for (IClass c : targetSet) {
			c.setDrawable(true);
		}

		for (IClass c : adapteeSet) {
			c.setDrawable(true);
		}

		Map<Class<? extends IPattern>, Set<IClass>> res;
		if (this.detector == null)

		{
			res = new HashMap<>();
		} else

		{
			res = detector.detect(m);
		}
		res.put(AdapterPattern.class, adapterSet);
		res.put(AdapteePattern.class, adapteeSet);
		res.put(AdapterTargetPattern.class, targetSet);
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
		Map<IClass, Map<Relations, Set<IClass>>> rels = m.getRelGraph();
		Set<Set<IClass>> candSet = new HashSet<>();
		Set<IClass> cands = new HashSet<>();
		boolean in;
		for (IClass c : m.getClasses()) {
			in = false;
			Set<IClass> supers = new HashSet<>();
			Set<IClass> supers2 = new HashSet<>();
			supers2.addAll(rels.get(c).get(Relations.EXTENDS));
			supers2.addAll(rels.get(c).get(Relations.IMPLEMENTS));
			supers.addAll(supers2);
			for (IClass c2 : supers2) {
				if (rels.get(c2).get(Relations.REV_ASSOCIATES).isEmpty()) {
					supers.remove(c2);
				}
			}
			if (!supers.isEmpty()) {
				Set<IClass> assocs = new HashSet<>();
				assocs.addAll(rels.get(c).get(Relations.ASSOCIATES));
				assocs.remove(c);
				if (!assocs.isEmpty() && !supers.containsAll(assocs)) {
					for (IClass c2 : supers) {
						Set<IClass> revs = rels.get(c2).get(
								Relations.REV_ASSOCIATES);
						if (!revs.isEmpty()
								&& (!(revs.size() == 1 && revs.contains(c)))) {
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
