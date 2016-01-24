package legendary.Classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import legendary.Interfaces.IClass;
import legendary.Interfaces.IModel;
import legendary.Interfaces.ITraverser;
import legendary.Interfaces.IVisitor;
import legendary.Interfaces.IPattern;

public class LegendaryModel implements IModel, ITraverser {

	private Set<IClass> classList;
	private Map<List<String>, List<Relations>> relations;
	private Map<IPattern, Set<IClass>> patterns;


	public LegendaryModel() {
		this.classList = new TreeSet<IClass>();
		this.relations = new HashMap<>();
	}

	@Override
	public Set<IClass> getClasses() {
		return this.classList;
	}

	@Override
	public void addClass(IClass c) {
		classList.add(c);
	}

	@Override
	public Map<List<String>, List<Relations>> getRelations() {
		return relations;
	}
	
	public void addPattern(IPattern pat, Set<IClass> classes){
		if(!this.patterns.containsKey(pat)){
			patterns.put(pat, new HashSet<IClass>());
		}
		patterns.get(pat).addAll(classes);		
	}
	
	public Map<IPattern, Set<IClass>> getPatterns() {
		return patterns;
	}

	@Override
	public void addRelation(String c1, String c2, Relations r) {
		List<String> al = new ArrayList<String>();
		al.add(c1.substring(c1.lastIndexOf("/") + 1));
		al.add(c2.substring(c2.lastIndexOf("/") + 1));
		if (relations.containsKey(al)) {
			List<Relations> lr = relations.get(al);
			if(relations.get(al).contains(r))
				return;
			if (r.equals(Relations.ASSOCIATES)) {
				lr.remove(Relations.USES);
				lr.add(Relations.ASSOCIATES);
				return;
			}
			if (r.equals(Relations.USES)) {
				if (!lr.contains(Relations.ASSOCIATES))
					lr.add(Relations.USES);
				return;
			} else {
				lr.add(r);
				return;
			}
		}
		
		List<Relations> rl = new ArrayList<>();
		rl.add(r);
		relations.put(al, rl);
	}

	@Override
	public void accept(IVisitor v) {
		v.previsit(this);
		for(IClass c : this.classList) {
			ITraverser t = (ITraverser) c;
			t.accept(v);
		}
		v.visit(this);
		v.postvisit(this);
	}

}
