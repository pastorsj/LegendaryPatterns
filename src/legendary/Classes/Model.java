package legendary.Classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import legendary.Interfaces.IClass;
import legendary.Interfaces.IModel;
import legendary.Interfaces.ITraverser;
import legendary.Interfaces.IVisitor;

public class Model implements IModel, ITraverser {

	private Set<IClass> classList;
	private Map<List<String>, List<Relations>> relations;

	public Model() {
		this.classList = new HashSet<IClass>();
		this.relations = new HashMap<>();
	}

	@Override
	public Set<IClass> getClasses() {
		return classList;
	}

	@Override
	public void addClass(IClass c) {
		classList.add(c);
	}

	@Override
	public Map<List<String>, List<Relations>> getRelations() {
		return relations;
	}

	@Override
	public void addRelation(String c1, String c2, Relations r) {
		List<String> al = new ArrayList<String>();
		al.add(c1);
		al.add(c2);
		if (relations.containsKey(al)) {
			if (r.equals(Relations.ASSOCIATES)) {
				List<Relations> lr = relations.get(al);
				lr.remove(Relations.USES);
				lr.add(Relations.ASSOCIATES);
				return;
			}
			if (r.equals(Relations.USES)) {
				List<Relations> lr = relations.get(al);
				if (lr.contains(Relations.ASSOCIATES))
					lr.add(Relations.USES);
			}
		}
		List<Relations> rl = new ArrayList<>();
		rl.add(r);
		relations.put(al, rl);
	}

	@Override
	public void accept(IVisitor v) {
		v.previsit(this);
		v.visit(this);
		v.postvisit(this);
	}

}
