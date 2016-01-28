package legendary.Classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import legendary.Interfaces.IClass;
import legendary.Interfaces.IModel;
import legendary.Interfaces.IPattern;
import legendary.visitor.ITraverser;
import legendary.visitor.IVisitor;

public class LegendaryModel implements IModel, ITraverser {

	private Set<IClass> classList;
	private Map<IClass, Map<Relations, Set<IClass>>> relGraph;
	private Map<List<String>, List<Relations>> relations;
	private Map<IPattern, Set<IClass>> patterns;

	public LegendaryModel() {
		this.classList = new HashSet<IClass>();
		this.relGraph = new HashMap<>();
		this.relations = new HashMap<>();
	}

	@Override
	public Set<IClass> getClasses() {
		return this.classList;
	}

	@Override
	public void addClass(IClass c) {
		this.classList.add(c);
	}

	@Override
	public Map<List<String>, List<Relations>> getRelations() {
		return relations;
	}

	public void addPattern(IPattern pat, Set<IClass> classes) {
		if (!this.patterns.containsKey(pat)) {
			patterns.put(pat, new HashSet<IClass>());
		}
		patterns.get(pat).addAll(classes);
	}

	public Map<IPattern, Set<IClass>> getPatterns() {
		return patterns;
	}

	@Override
	public void convertToGraph() {
		for (List<String> al : this.relations.keySet()) {
			boolean first = false;
			IClass c1 = null;
			boolean second = false;
			IClass c2 = null;
			inner: for (IClass c : this.classList) {
				if (!first && c.getClassName().equals(al.get(0))) {
					first = true;
					c1 = c;
				}
				if (!second && c.getClassName().equals(al.get(1))) {
					second = true;
					c2 = c;
				}
				if (first && second) {
					boolean exists = this.relGraph.containsKey(c1);
					if (!exists) {
						Map<Relations, Set<IClass>> tempMap = new HashMap<>();
						for (Relations r : Relations.values()) {
							Set<IClass> cSet = new HashSet<>();
							cSet.add(c2);
							if (this.relations.get(al).contains(r)) {
								tempMap.put(r, cSet);
							} else {
								tempMap.put(r, new HashSet<>());
							}
						}
						this.relGraph.put(c1, tempMap);
					} else if (exists) {
						for (Relations r : this.relations.get(al)) {
							this.relGraph.get(c1).get(r).add(c2);
						}
					}
					break inner;
				}
			}
			// if (c1 != null && c2 != null)
			// System.out.println(c1.getClassName() + " " + c2.getClassName() +
			// " " + relations.get(al) + " "
			// + this.relGraph.get(c1));
		}
		Map<IClass, Map<Relations, Set<IClass>>> temp = new HashMap<>();
		for (IClass c : this.getClasses()) {
			Map<Relations, Set<IClass>> initTemp = new HashMap<>();
			for (Relations r : Relations.values()) {
				initTemp.put(r, new HashSet<>());
			}
			temp.put(c, initTemp);
		}
		removeDupArrows(temp);
	}

	@Override
	public void removeDupArrows(Map<IClass, Map<Relations, Set<IClass>>> temp) {
		for (IClass c : this.relGraph.keySet()) {
			for (IClass sup : this.relGraph.get(c).get(Relations.EXTENDS)) {
				// System.err.println(sup.getClassName());
				// if (!sup.isInterface())
				temp.get(c).get(Relations.EXTENDS).add(sup);
				temp.get(sup).get(Relations.REV_EXTENDS).add(c);
			}
			for (IClass sup : this.relGraph.get(c).get(Relations.IMPLEMENTS)) {
				// if (sup.isInterface())
				temp.get(c).get(Relations.IMPLEMENTS).add(sup);
				temp.get(sup).get(Relations.REV_IMPLEMENTS).add(c);
			}
			for (IClass c2 : this.relGraph.get(c).get(Relations.ASSOCIATES)) {
				boolean add = true;
				for (IClass sup : this.relGraph.get(c).get(Relations.EXTENDS)) {
					if (!this.relGraph.containsKey(sup))
						continue;
					if (this.relGraph.get(sup).get(Relations.ASSOCIATES).contains(c2)) {
						add = false;
						break;
					}
				}
				if (add) {
					temp.get(c).get(Relations.ASSOCIATES).add(c2);
					temp.get(c2).get(Relations.REV_ASSOCIATES).add(c);
				}
			}
			for (IClass c2 : this.relGraph.get(c).get(Relations.USES)) {
				boolean add = true;
				for (IClass sup : this.relGraph.get(c).get(Relations.EXTENDS)) {
					if (!this.relGraph.containsKey(sup))
						continue;
					if (this.relGraph.get(sup).get(Relations.USES).contains(c2)) {
						add = false;
						break;
					}
				}
				for (IClass sup : this.relGraph.get(c).get(Relations.IMPLEMENTS)) {
					if (!this.relGraph.containsKey(sup))
						continue;
					if (this.relGraph.get(sup).get(Relations.USES).contains(c2)) {
						// System.out.println(sup.getClassName());
						add = false;
						break;
					}
				}
				if (add && !temp.get(c).get(Relations.ASSOCIATES).contains(c2)) {
					temp.get(c).get(Relations.USES).add(c2);
					temp.get(c2).get(Relations.REV_USES).add(c);
				}
			}
		}
		// System.out.println(temp.equals(this.relGraph));
		this.relGraph = temp;
	}

	@Override
	public Map<IClass, Map<Relations, Set<IClass>>> getRelGraph() {
		return this.relGraph;
	}

	@Override
	public void addRelation(String c1, String c2, Relations r) {
		List<String> al = new ArrayList<String>();
		al.add(c1.substring(c1.lastIndexOf("/") + 1));
		al.add(c2.substring(c2.lastIndexOf("/") + 1));
		if (relations.containsKey(al)) {
			List<Relations> lr = relations.get(al);
			if (relations.get(al).contains(r))
				return;
			if (r.equals(Relations.ASSOCIATES)) {
				lr.remove(Relations.USES);
				lr.remove(Relations.REV_USES);
				lr.add(Relations.ASSOCIATES);
				lr.add(Relations.REV_ASSOCIATES);
				return;
			}
			if (r.equals(Relations.USES)) {
				if (!lr.contains(Relations.ASSOCIATES)) {
					lr.add(Relations.USES);
					lr.add(Relations.REV_USES);
				}
				return;
			} else {
				if (r.equals(Relations.EXTENDS)) {
					lr.add(r);
					lr.add(Relations.REV_EXTENDS);
					return;
				}
				if (r.equals(Relations.IMPLEMENTS)) {
					lr.add(r);
					lr.add(Relations.REV_IMPLEMENTS);
					return;
				} else
					System.out.println("wat");
			}
		}

		List<Relations> rl = new ArrayList<>();
		rl.add(r);
		relations.put(al, rl);
	}

	@Override
	public void accept(IVisitor v) {
		v.preVisit(this);
		for (IClass c : this.classList) {
			ITraverser t = (ITraverser) c;
			t.accept(v);
		}
		v.visit(this);
		v.postVisit(this);
	}

	@Override
	public boolean containsClass(String i) {
		for (IClass c : this.classList) {
			if (c.getClassName().equals(i)) {
				return true;
			}
		}
		return false;
	}

}
