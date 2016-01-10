package legendary.Classes;

import java.io.IOException;

import legendary.Interfaces.IClass;
import legendary.Interfaces.IField;
import legendary.Interfaces.IMethod;
import legendary.Interfaces.IModel;
import legendary.Interfaces.VisitorAdapter;

public class GraphVizOutputStream extends VisitorAdapter {
	private final StringBuilder builder;

	public GraphVizOutputStream(StringBuilder builder) throws IOException {
		this.builder = builder;
	}

	private void write(String s) {
		this.builder.append(s);
	}

	@Override
	public void previsit(IModel m) {
		this.write("digraph G{\n\tnode [shape = \"record\"]\n");
	}

	@Override
	public void postvisit(IModel m) {
		this.write("}");
	}

	@Override
	public void previsit(IClass c) {
		this.write("\t" + c.getClassName() + " [\n");
	}

	@Override
	public void visit(IClass c) {
		// TODO: Implement
	}

	@Override
	public void postvisit(IClass c) {
		this.write("]");
	}

	@Override
	public void visit(IField f) {
		// TODO: Implement
	}

	@Override
	public void postvisit(IField f) {
		// TODO: Implement
	}

	@Override
	public void visit(IMethod m) {
		// TODO: Implement
	}

	@Override
	public void postvisit(IMethod m) {
		// TODO: Implement
	}
}
