package legendary.Interfaces;

import java.util.List;
import java.util.Map;
import java.util.Set;

import legendary.Classes.Relations;

public interface IModel {
	public Set<IClass> getClasses();
	public void addClass(IClass c);
	public Map<List<IClass>, Relations> getRelations();
	public void addRelation(IClass c1, IClass c2, Relations r);
}
