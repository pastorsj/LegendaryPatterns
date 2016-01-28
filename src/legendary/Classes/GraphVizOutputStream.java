package legendary.Classes;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import legendary.Interfaces.IClass;
import legendary.Interfaces.IField;
import legendary.Interfaces.IMethod;
import legendary.Interfaces.IModel;
import legendary.Interfaces.IPattern;
import legendary.ParsingUtil.GeneralUtil;
import legendary.asm.DesignParser;
import legendary.visitor.ITraverser;
import legendary.visitor.IVisitMethod;
import legendary.visitor.IVisitor;
import legendary.visitor.VisitType;
import legendary.visitor.VisitorAdapter;

public class GraphVizOutputStream {
	private final StringBuilder builder;
	private final IVisitor visitor;
	private final Map<Relations, String> relationRep;
	private static Map<IClass, Set<IPattern>> patterns;

	public GraphVizOutputStream(StringBuilder builder, Map<Class<? extends IPattern>, Set<IClass>> map, IModel m) {
		this.builder = builder;
		this.relationRep = new HashMap<>();
		this.initialize();
		patterns = invertPatternMap(map);
		this.visitor = new VisitorAdapter();
		this.initializeVisitors();
		ITraverser t = (ITraverser) m;
		t.accept(this.visitor);
	}

	private void initializeVisitors() {
		this.previsitModel();
		this.visitModel();
		this.postvisitModel();
		this.previsitClass();
		this.visitClass();
		this.postvisitClass();
		this.visitField();
		this.visitMethod();
	}

	private Map<IClass, Set<IPattern>> invertPatternMap(Map<Class<? extends IPattern>, Set<IClass>> in) {
		Map<IClass, Set<IPattern>> res = new HashMap<>();
		for (Class<? extends IPattern> p : in.keySet()) {
			for (IClass c : in.get(p)) {
				if (!res.containsKey(c)) {
					res.put(c, new HashSet<IPattern>());
				}
				try {
					res.get(c).add((IPattern) p.newInstance());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return res;
	}

	private void write(String s) {
		this.builder.append(s);
	}

	private void previsitModel() {
		this.visitor.addVisit(VisitType.PreVisit, IModel.class, (ITraverser t) -> {
			this.write("digraph G{\n\tnode [shape = \"record\"]\n");
		});
	}

	private void visitModel() {
		this.visitor.addVisit(VisitType.Visit, IModel.class, (ITraverser t) -> {
			this.write(this.addArrows((IModel) t));
		});
	}

	private String addArrows(IModel m) {
		StringBuilder sb = new StringBuilder();
		Map<IClass, Map<Relations, Set<IClass>>> graph = m.getRelGraph();
		outer: for (IClass c : graph.keySet()) {
			if(c.getClassName().startsWith(DesignParser.packageName))
				c.setDrawable(true);
			if (c.isDrawable()) {
				for (Relations r : graph.get(c).keySet()) {
					for (IClass c2 : graph.get(c).get(r)) {
						if(c2.getClassName().startsWith(DesignParser.packageName))
							c2.setDrawable(true);
						if (c2.isDrawable()) {
							// System.out.println(c2.getClassName());
							if (!this.relationRep.containsKey(r)) {
								System.out.println(
										"null relation for classes " + c.getClassName() + " and " + c2.getClassName());
								break outer;
							}
							if (!this.relationRep.get(r).equals("")) {
								sb.append(this.relationRep.get(r));
								sb.append(c.getClassName().replace(".", "").replace(":", "") + "->"
										+ c2.getClassName().replace(".", "").replace(":", "") + "\n");
							}
						}
					}
				}

			}
		}
		return sb.toString();
	}

	private void postvisitModel() {
		this.visitor.addVisit(VisitType.PostVisit, IModel.class, (ITraverser t) -> {
			this.write("}");
		});
	}

	private void previsitClass() {

		IVisitMethod command = new IVisitMethod() {
			@Override
			public void execute(ITraverser t) {
				IClass c = (IClass) t;
				if (c.isDrawable()) {
					String line = String.format("%s [\n\tlabel = \"{%s%s",
							c.getClassName().replace(".", "").replace(":", ""),
							(c.isInterface() ? "\\<\\<interface\\>\\>\\n" : ""), c.getClassName());
					write(line);
					if (patterns.containsKey(c))
						addPatternTags(c);
					write("|\n\t");
				}
			}
		};
		this.visitor.addVisit(VisitType.PreVisit, IClass.class, command);
	}

	private void visitClass() {
		this.visitor.addVisit(VisitType.Visit, IClass.class, (ITraverser t) -> {
			if (((IClass) t).isDrawable())
				this.write("|\n");
		});
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

	private void postvisitClass() {
		this.visitor.addVisit(VisitType.PostVisit, IClass.class, (ITraverser t) -> {
			if (((IClass) t).isDrawable())
				this.write(String.format("\t}\"\n\t%s]\n", patternColor((IClass) t)));
		});
	}

	private String patternColor(IClass c) {
		if (patterns.containsKey(c))
			for (IPattern p : patterns.get(c)) {
				return p.color();
			}
		return "";
	}

	private void visitField() {
		IVisitMethod command = new IVisitMethod() {
			@Override
			public void execute(ITraverser t) {
				IField f = (IField) t;
				boolean isStatic = f.getAccess().endsWith("_");
				String ret = "";
				boolean include = false;
				for (char c : f.getType().toCharArray()) {
					if (GeneralUtil.primCodes.containsValue(f.getType().replace("[]", ""))) {
						ret = f.getType();
						break;
					}
					if (c == ':')
						include = true;
					if (c == '<' || c == '>' || c == ' ') {
						include = false;
						ret += c;
					}
					if (include)
						ret += c;
				}
				ret = ret.replace("<", "\\<").replace(">", "\\>").replace(":", "");
				String line = String.format("%s %s%s: %s%s\\l\n\t", f.getAccess().replace("_", ""), isStatic ? "_" : "",
						f.getFieldName(), ret, isStatic ? "_" : "");
				write(line);
			}
		};
		this.visitor.addVisit(VisitType.Visit, IField.class, command);
	}

	public void visit(IMethod m) {

	}

	private void visitMethod() {
		IVisitMethod command = new IVisitMethod() {
			@Override
			public void execute(ITraverser t) {
				IMethod m = (IMethod) t;
				if (!(m.getMethodName().equals("<init>") || m.getMethodName().equals("<clinit>"))) {
					boolean isStatic = m.getAccess().endsWith("_");
					String parameters = "";
					boolean include = false;
					int dimensions = 0;
					for (String s : m.getParameters()) {
						include = false;
						if (s.equals("[]")) {
							dimensions++;
							continue;
						}
						if (GeneralUtil.primCodes.containsValue(s.replace("[]", ""))) {
							include = true;
						}
						for (char c : s.toCharArray()) {
							if (c == ':')
								include = true;
							if (c == '<' || c == '>' || c == ' ' || c == ',') {
								include = false;
								parameters += c;
							}
							if (include)
								parameters += c;
						}
						for (int i = 0; i < dimensions; i++) {
							parameters += "[]";
						}
						dimensions = 0;
						parameters += ", ";
					}
					if (parameters.contains(", ")) {
						parameters = parameters.substring(0, parameters.lastIndexOf(", "));
					}
					parameters = parameters.replace(":", "").replace("<", "\\<").replace(">", "\\>");
					String ret = "";
					include = false;
					for (char c : m.getReturnType().toCharArray()) {
						if (GeneralUtil.primCodes.containsValue(m.getReturnType().replace("[]", ""))) {
							ret = m.getReturnType();
							break;
						}
						if (c == ':')
							include = true;
						if (c == '<' || c == '>' || c == ' ') {
							include = false;
							ret += c;
						}
						if (include)
							ret += c;
					}
					ret = ret.replace("<", "\\<").replace(">", "\\>");
					String line = String.format("\t%s %s" + "%s(%s) : %s%s\\l\n", m.getAccess().replace("_", ""),
							isStatic ? "_" : "", m.getMethodName(), parameters, ret.replace(":", ""),
							isStatic ? "_" : "");
					write(line);
				}
			}
		};
		this.visitor.addVisit(VisitType.Visit, IMethod.class, command);
	}

	public void initialize() {
		this.relationRep.put(Relations.ASSOCIATES, "\tedge [style = \"solid\"] [arrowhead = \"open\"]\n\t");
		this.relationRep.put(Relations.EXTENDS, "\tedge [style = \"solid\"] [arrowhead = \"empty\"]\n\t");
		this.relationRep.put(Relations.IMPLEMENTS, "\tedge [style = \"dashed\"] [arrowhead = \"empty\"]\n\t");
		this.relationRep.put(Relations.USES, "\tedge [style = \"dashed\"] [arrowhead = \"open\"]\n\t");
		this.relationRep.put(Relations.REV_ASSOCIATES, "");
		this.relationRep.put(Relations.REV_EXTENDS, "");
		this.relationRep.put(Relations.REV_IMPLEMENTS, "");
		this.relationRep.put(Relations.REV_USES, "");
	}
}
