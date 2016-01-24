package legendary.Interfaces;

import java.util.List;
import java.util.Map;
import java.util.Set;

import legendary.Classes.Relations;

public interface IModel {
	public Set<IClass> getClasses();
	public void addClass(IClass c);
	public Map<List<String>, List<Relations>> getRelations();
	public void addRelation(String c1, String c2, Relations r);
	public void convertToGraph();
	public Map<IClass, Map<Relations, Set<IClass>>> getRelGraph();
	public void removeDupArrows(Map<IClass, Map<Relations, Set<IClass>>> tempMap);
}
