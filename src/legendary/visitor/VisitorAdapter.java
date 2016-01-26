package legendary.Interfaces;

public abstract class VisitorAdapter implements IVisitor {
	@Override
	public void previsit(IModel m) {
	};

	@Override
	public void visit(IModel m) {
	};

	@Override
	public void postvisit(IModel m) {
	};
	
	@Override
	public void previsit(IClass c) {
	};

	@Override
	public void visit(IClass c) {
	};

	@Override
	public void postvisit(IClass c) {
	};

	@Override
	public void previsit(IMethod m) {
	};

	@Override
	public void visit(IMethod m) {
	};

	@Override
	public void postvisit(IMethod m) {
	};

	@Override
	public void previsit(IField f) {
	};

	@Override
	public void visit(IField f) {
	};

	@Override
	public void postvisit(IField f) {
	};
}
