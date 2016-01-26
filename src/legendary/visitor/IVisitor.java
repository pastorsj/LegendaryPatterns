package legendary.visitor;

import java.util.Map;

public interface IVisitor {
	public void preVisit(ITraverser t);
	public void visit(ITraverser t);
	public void postVisit(ITraverser t);
	
	public void addVisit(VisitType visitType, Class<?> clazz, IVisitMethod m);
	public void removeVisit(VisitType visitType, Class<?> clazz);
	public Map<LookupKey, IVisitMethod> getMap();
}
