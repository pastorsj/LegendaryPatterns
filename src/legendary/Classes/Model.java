package legendary.Classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import legendary.Interfaces.IClass;
import legendary.Interfaces.IModel;

public class Model implements IModel{
	
	private Set<IClass> classList;
	private Map<List<IClass>, Relations> relations;
	
	@Override
	public Set<IClass> getClasses() {
		return classList;
	}

	@Override
	public void addClass(IClass c) {
		classList.add(c);
	}

	@Override
	public Map<List<IClass>, Relations> getRelations() {
		return relations;
	}

	@Override
	public void addRelation(IClass c1, IClass c2, Relations r) {
		List<IClass> al = new ArrayList<IClass>();
		al.add(c1);
		al.add(c2);
		relations.put(al, r);
	}

}
