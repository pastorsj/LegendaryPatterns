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
import legendary.Interfaces.IPattern;
import legendary.Interfaces.VisitorAdapter;

public class GraphVizOutputStream extends VisitorAdapter {
	private final StringBuilder builder;
	private final Map<Relations, String> relationRep;
	private Map<IClass, Set<IPattern>> patterns;

	public GraphVizOutputStream(StringBuilder builder,
			Map<Class<? extends IPattern>, Set<IClass>> map) {
		this.builder = builder;
		this.relationRep = new HashMap<>();
		this.initialize();
		this.patterns = invertPatternMap(map);
	}

	private Map<IClass, Set<IPattern>> invertPatternMap(
			Map<Class<? extends IPattern>, Set<IClass>> in) {
		Map<IClass, Set<IPattern>> res = new HashMap<>();
		for (Class<? extends IPattern> p : in.keySet()) {
			for (IClass c : in.get(p)) {
				if (!res.containsKey(c)) {
					res.put(c, new HashSet<IPattern>());
				}
				try {
					res.get(c).add((IPattern) p.newInstance());
				} catch (Exception e) {
					System.err.println("how on earth do you think that was supposed to work");
				}
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
					System.out.println("null relation for classes " + al.get(0)
							+ " and " + al.get(1));
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
		String line = String.format("%s [\n\tlabel = \"{%s%s",
				c.getClassName(), (c.isInterface() ? "\\<\\<interface\\>\\>\\n"
						: ""), c.getClassName());
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
		String s = "\\n\\<\\<";
		if (patterns.containsKey(c))
			for (IPattern p : patterns.get(c))
				s += p.tag();
		s = s.substring(0, s.lastIndexOf(","));
		s += "\\>\\>";
		this.write(s);
	}

	@Override
	public void postvisit(IClass c) {
		this.write(String.format("\t}\"\n\t%s]\n", patternColor(c)));
	}

	private String patternColor(IClass c) {
		if (patterns.containsKey(c))
			for (IPattern p : patterns.get(c)) {
				return p.color();
			}
		return "";
	}

	@Override
	public void visit(IField f) {
		boolean isStatic = f.getAccess().endsWith("_");
		String line = String.format("%s %s%s: %s%s\\l\n\t", f.getAccess()
				.replace("_", ""), isStatic ? "_" : "", f.getFieldName(), f
				.getType(), isStatic ? "_" : "");
		this.write(line);
	}

	@Override
	public void visit(IMethod m) {
		if (!(m.getMethodName().equals("<init>") || m.getMethodName().equals(
				"<clinit>"))) {
			boolean isStatic = m.getAccess().endsWith("_");
			String parameters = Arrays.toString(m.getParameters().toArray());
			String line = String.format("\t%s %s" + "%s(%s) : %s%s\\l\n", m
					.getAccess().replace("_", ""), isStatic ? "_" : "", m
					.getMethodName(), parameters.substring(1,
					parameters.length() - 1), m.getReturnType(), isStatic ? "_"
					: "");
			this.write(line);
		}
	}

	public void initialize() {
		this.relationRep.put(Relations.ASSOCIATES,
				"\tedge [style = \"solid\"] [arrowhead = \"open\"]\n\t");
		this.relationRep.put(Relations.EXTENDS,
				"\tedge [style = \"solid\"] [arrowhead = \"empty\"]\n\t");
		this.relationRep.put(Relations.IMPLEMENTS,
				"\tedge [style = \"dashed\"] [arrowhead = \"empty\"]\n\t");
		this.relationRep.put(Relations.USES,
				"\tedge [style = \"dashed\"] [arrowhead = \"open\"]\n\t");
	}
}
