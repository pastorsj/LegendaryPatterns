package legendary.Classes;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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
	public void visit(IModel m) {
		this.write(this.addArrows(m));
	}

	private String addArrows(IModel m) {
		StringBuilder sb = new StringBuilder();
		Map<List<String>, List<Relations>> relMap = m.getRelations();
		for (List<String> al : relMap.keySet()) {
			for (Relations r : relMap.get(al)) {
				switch (r) {
				case USES:
					sb.append("\tedge [style = \"dashed\"] [arrowhead = \"open\"]\n\t");
					break;
				case ASSOCIATES:
					sb.append("\tedge [style = \"solid\"] [arrowhead = \"open\"]\n\t");
					break;
				case EXTENDS:
					sb.append("\tedge [style = \"solid\"] [arrowhead = \"empty\"]\n");
					break;
				case IMPLEMENTS:
					sb.append("\tedge [style = \"dashed\"] [arrowhead = \"empty\"]\n\t");
					break;
				default:
					System.out.println("null relation for classes " + al.get(0)
							+ " and " + al.get(1));
					break;
				}
				sb.append(al.get(0) + "->" + al.get(1) + "\n");
			}
		}
		return sb.toString();
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
