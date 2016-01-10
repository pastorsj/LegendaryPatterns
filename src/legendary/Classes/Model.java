package legendary.Classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import legendary.Interfaces.IClass;
import legendary.Interfaces.IModel;
import legendary.Interfaces.ITraverser;
import legendary.Interfaces.IVisitor;

public class Model implements IModel, ITraverser{
	
	private Set<IClass> classList;
	private Map<List<String>, Relations> relations;
	
	@Override
	public Set<IClass> getClasses() {
		return classList;
	}

	@Override
	public void addClass(IClass c) {
		classList.add(c);
	}

	@Override
	public Map<List<String>, Relations> getRelations() {
		return relations;
	}

	@Override
	public void addRelation(String c1, String c2, Relations r) {
		List<String> al = new ArrayList<String>();
		al.add(c1);
		al.add(c2);
		relations.put(al, r);
	}

	@Override
	public void accept(IVisitor v) {
		v.previsit(this);
		v.visit(this);
		v.postvisit(this);
	}

}
