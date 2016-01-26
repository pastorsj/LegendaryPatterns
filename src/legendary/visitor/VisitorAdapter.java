package legendary.visitor;

import java.util.HashMap;
import java.util.Map;

public class VisitorAdapter implements IVisitor {
	public Map<LookupKey, IVisitMethod> keyToVisitMethodMap;
	
	public VisitorAdapter() {
		this.keyToVisitMethodMap = new HashMap<>();
	}

	@Override
	public void preVisit(ITraverser t) {
		this.doVisit(VisitType.PreVisit, t);
	}

	@Override
	public void visit(ITraverser t) {
		this.doVisit(VisitType.Visit, t);
	}

	@Override
	public void postVisit(ITraverser t) {
		this.doVisit(VisitType.PostVisit, t);
	}
	
	private void doVisit(VisitType vType, ITraverser t) {
		LookupKey key = new LookupKey(vType, t.getClass());
		IVisitMethod m = this.keyToVisitMethodMap.get(key);
		if(m != null)
			m.execute(t);
	}

	@Override
	public void addVisit(VisitType visitType, Class<?> clazz, IVisitMethod m) {
		LookupKey key = new LookupKey(visitType, clazz);
		this.keyToVisitMethodMap.put(key, m);
	}
	
	public Map<LookupKey, IVisitMethod> getMap() {
		return this.keyToVisitMethodMap;
	}

	@Override
	public void removeVisit(VisitType visitType, Class<?> clazz) {
		LookupKey key = new LookupKey(visitType, clazz);
		this.keyToVisitMethodMap.remove(key);
	}
}
