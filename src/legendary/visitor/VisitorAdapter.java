package legendary.visitor;

import java.util.HashMap;
import java.util.Map;

/**
 * This class initializes a map of visit types with the classes being visited
 */
public class VisitorAdapter implements IVisitor {
	
	/** The key to visit method map. */
	public Map<LookupKey, IVisitMethod> keyToVisitMethodMap;
	
	/**
	 * Instantiates a new visitor adapter.
	 */
	public VisitorAdapter() {
		this.keyToVisitMethodMap = new HashMap<>();
	}

	/* (non-Javadoc)
	 * @see legendary.visitor.IVisitor#preVisit(legendary.visitor.ITraverser)
	 */
	@Override
	public void preVisit(ITraverser t) {
		this.doVisit(VisitType.PreVisit, t);
	}

	/* (non-Javadoc)
	 * @see legendary.visitor.IVisitor#visit(legendary.visitor.ITraverser)
	 */
	@Override
	public void visit(ITraverser t) {
		this.doVisit(VisitType.Visit, t);
	}

	/* (non-Javadoc)
	 * @see legendary.visitor.IVisitor#postVisit(legendary.visitor.ITraverser)
	 */
	@Override
	public void postVisit(ITraverser t) {
		this.doVisit(VisitType.PostVisit, t);
	}
	
	/**
	 * Execute a visit method
	 *
	 * @param vType the v type
	 * @param t the t
	 */
	private void doVisit(VisitType vType, ITraverser t) {
		LookupKey key = new LookupKey(vType, t.getClass());
		IVisitMethod m = this.keyToVisitMethodMap.get(key);
		if(m != null)
			m.execute(t);
	}

	/* (non-Javadoc)
	 * @see legendary.visitor.IVisitor#addVisit(legendary.visitor.VisitType, java.lang.Class, legendary.visitor.IVisitMethod)
	 */
	@Override
	public void addVisit(VisitType visitType, Class<?> clazz, IVisitMethod m) {
		LookupKey key = new LookupKey(visitType, clazz);
		this.keyToVisitMethodMap.put(key, m);
	}
	
	/* (non-Javadoc)
	 * @see legendary.visitor.IVisitor#getMap()
	 */
	public Map<LookupKey, IVisitMethod> getMap() {
		return this.keyToVisitMethodMap;
	}

	/* (non-Javadoc)
	 * @see legendary.visitor.IVisitor#removeVisit(legendary.visitor.VisitType, java.lang.Class)
	 */
	@Override
	public void removeVisit(VisitType visitType, Class<?> clazz) {
		LookupKey key = new LookupKey(visitType, clazz);
		this.keyToVisitMethodMap.remove(key);
	}
}
