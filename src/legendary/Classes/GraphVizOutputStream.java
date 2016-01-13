package legendary.Classes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import legendary.Interfaces.IClass;
import legendary.Interfaces.IField;
import legendary.Interfaces.IMethod;
import legendary.Interfaces.IModel;
import legendary.Interfaces.VisitorAdapter;

public class GraphVizOutputStream extends VisitorAdapter {
	private final StringBuilder builder;
	private final Map<Relations, String> relationRep;

	public GraphVizOutputStream(StringBuilder builder) {
		this.builder = builder;
		this.relationRep = new HashMap<>();
		this.initialize();
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
		List<String> classNames = new ArrayList<>();
		for (IClass c : m.getClasses()) {
			classNames.add(c.getClassName());
		}
		outer: for (List<String> al : relMap.keySet()) {
			if (!classNames.contains(al.get(1))) {
				continue;
			}
			for (Relations r : relMap.get(al)) {
				if (!this.relationRep.containsKey(r)) {
					System.out.println("null relation for classes " + al.get(0) + " and " + al.get(1));
					break outer;
				}
				sb.append(this.relationRep.get(r));
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
		String line = String.format("%s [\n\tlabel = \"{%s%s|\n\t", c.getClassName(),
				(c.isInterface() ? "\\<\\<interface\\>\\>\\n" : ""), c.getClassName());
		this.write(line);
	}

	@Override
	public void visit(IClass c) {
		this.write("|\n");
	}

	@Override
	public void postvisit(IClass c) {
		this.write("\t}\"\n\t]\n");
	}

	@Override
	public void visit(IField f) {
		String line = String.format("%s %s: %s\\l\n\t", f.getAccess(), f.getFieldName(), f.getType());
		this.write(line);
	}

	@Override
	public void visit(IMethod m) {
		if (!(m.getMethodName().equals("<init>") || m.getMethodName().equals("<clinit>"))) {
			String parameters = Arrays.toString(m.getParameters().toArray());
			String line = String.format("\t%s %s(%s) : %s\\l\n", m.getAccess(), m.getMethodName(),
					parameters.substring(1, parameters.length() - 1), m.getReturnType());
			this.write(line);
		}
	}

	public void initialize() {
		this.relationRep.put(Relations.ASSOCIATES, "\tedge [style = \"solid\"] [arrowhead = \"open\"]\n\t");
		this.relationRep.put(Relations.EXTENDS, "\tedge [style = \"solid\"] [arrowhead = \"empty\"]\n\t");
		this.relationRep.put(Relations.IMPLEMENTS, "\tedge [style = \"dashed\"] [arrowhead = \"empty\"]\n\t");
		this.relationRep.put(Relations.USES, "\tedge [style = \"dashed\"] [arrowhead = \"open\"]\n\t");
	}
}
