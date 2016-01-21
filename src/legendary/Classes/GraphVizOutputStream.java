package legendary.Classes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import legendary.Interfaces.IClass;
import legendary.Interfaces.IField;
import legendary.Interfaces.IMethod;
import legendary.Interfaces.IModel;
import legendary.Interfaces.VisitorAdapter;

public class GraphVizOutputStream extends VisitorAdapter {
	private final StringBuilder builder;
	private final Map<Relations, String> relationRep;
	private Map<IClass, Set<Pattern>> patterns;

	public GraphVizOutputStream(StringBuilder builder, Map<Pattern, Set<IClass>> patterns) {
		this.builder = builder;
		this.relationRep = new HashMap<>();
		this.initialize();
		this.patterns = invertPatternMap(patterns);
	}

	private Map<IClass, Set<Pattern>> invertPatternMap(Map<Pattern, Set<IClass>> in) {
		Map<IClass, Set<Pattern>> res = new HashMap<>();
		for (Pattern p : in.keySet()) {
			for (IClass c : in.get(p)) {
				if (!res.containsKey(c)) {
					res.put(c, new HashSet<Pattern>());
				}
				res.get(c).add(p);
			}
		}
		return res;
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
		String line = String.format("%s [\n\tlabel = \"{%s%s", c.getClassName(),
				(c.isInterface() ? "\\<\\<interface\\>\\>\\n" : ""), c.getClassName());
		this.write(line);
		if (this.patterns.containsKey(c))
			addPatternTags(c);
		this.write("|\n\t");
	}

	@Override
	public void visit(IClass c) {
		this.write("|\n");
	}

	private void addPatternTags(IClass c) {
		this.write("\\n");
		if (patterns.get(c).contains(Pattern.SINGLETON)) {
			this.write("\\<\\<Singleton\\>\\>");
		}
		// this.write("\\n");
	}

	@Override
	public void postvisit(IClass c) {
		this.write(String.format("\t}\"\n\t%s]\n", patternColor(c)));
	}

	private String patternColor(IClass c) {
		if (patterns.containsKey(c)) {
			if (patterns.get(c).contains(Pattern.SINGLETON)) {
				return ", color = blue";
			}
		}
		return "";
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
