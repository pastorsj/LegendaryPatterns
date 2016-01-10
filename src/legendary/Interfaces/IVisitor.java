package legendary.Interfaces;


public interface IVisitor {
	public void previsit(IModel m);
	public void visit(IModel m);
	public void postvisit(IModel m);
	public void previsit(IClass c);
	public void visit(IClass c);
	public void postvisit(IClass c);
	public void previsit(IMethod m);
	public void visit(IMethod m);
	public void postvisit(IMethod m);
	public void previsit(IField f);
	public void visit(IField f);
	public void postvisit(IField f);
}
