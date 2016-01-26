package legendary.Classes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import legendary.Interfaces.IClass;
import legendary.Interfaces.IMethod;
import legendary.Interfaces.IModel;
import legendary.visitor.ITraverser;
import legendary.visitor.IVisitMethod;
import legendary.visitor.IVisitor;
import legendary.visitor.VisitType;
import legendary.visitor.VisitorAdapter;

public class SDEditOutputStream {

	private final IVisitor visitor;
	private IModel model;
	private StringBuilder builder;
	private Set<String> classes;
	private List<String> methodCalls;
	private int depth;
	private final int origDepth;

	public SDEditOutputStream(IModel model, int depth, StringBuilder builder) {
		this.model = model;
		this.builder = builder;
		this.classes = new HashSet<>();
		this.methodCalls = new ArrayList<>();
		this.depth = depth;
		this.origDepth = depth;
		this.visitor = new VisitorAdapter();
		this.initializeVisitors();
		ITraverser t = (ITraverser) model;
		t.accept(visitor);
	}

	private void initializeVisitors() {
		this.previsitMethod();
		this.visitMethod();
		this.postvisitMethod();
	}

	public void write(String s) {
		builder.append(s);
	}

	public void previsitMethod() {
		IVisitMethod command = new IVisitMethod() {
			@Override
			public void execute(ITraverser t) {
				IMethod m = (IMethod) t;
				depth--;
				if (depth <= 0) {
					return;
				}
				Queue<List<List<String>>> callStack = m.getCallStack();
				if (!callStack.isEmpty() && !callStack.peek().isEmpty()
						&& !classes.contains("/" + callStack.peek().get(0).get(0))) {
					classes.add(callStack.peek().get(0).get(0));
				}
			}
		};
		this.visitor.addVisit(VisitType.Visit, IMethod.class, command);
	}

	public void visitMethod() {
		SDEditOutputStream that = this;
		IVisitMethod command = new IVisitMethod() {
			@Override
			public void execute(ITraverser t) {
				IMethod m = (IMethod) t;
				if (depth <= 0)
					return;
				Queue<List<List<String>>> callStack = m.getCallStack();
				for (List<List<String>> methodDetails : callStack) {
					List<String> mDetails = methodDetails.get(0);
					List<String> params = methodDetails.get(1);
					String className = mDetails.get(1);
					for (IClass c : model.getClasses()) {
						if (c.getClassName().equals(className)) {
							if (mDetails.get(2).contains("<init>") && !classes.contains(className)) {
								if (classes.contains("/" + className))
									continue;
								classes.add("/" + className);
							} else if (!classes.contains("/" + className)) {
								classes.add(className);
							}
							IMethod method = c.getMethods().get(mDetails.get(2)).get(params);
							if (method == null)
								continue;
							String s;
							String paramString = Arrays.toString(params.toArray());
							s = String.format("%s:%s=%s.%s(%s)\n", mDetails.get(0), method.getReturnType(),
									mDetails.get(1), (mDetails.get(2).contains("<init>") ? "new" : mDetails.get(2)),
									paramString.substring(1, paramString.length() - 1));

							if (mDetails.get(0).equals(mDetails.get(1))) {
								if (mDetails.get(2).contains("<init>"))
									continue;
								if (!methodCalls.get(methodCalls.size() - 1).equals(s))
									methodCalls.add(s);
							} else
								methodCalls.add(s);

							ITraverser m2 = (ITraverser) method;
							m2.accept((IVisitor) that);
							break;
						}
					}
				}
			}
		};
		this.visitor.addVisit(VisitType.Visit, IMethod.class, command);
	}

	public void postvisitMethod() {
		IVisitMethod command = new IVisitMethod() {
			@Override
			public void execute(ITraverser t) {
				depth++;
				if (depth == origDepth) {
					for (String s : classes) {
						write(String.format("%s:%s\n", s, (s.startsWith("/") ? s.substring(1) : s)));
					}
					write("\n");
					for (String s : methodCalls) {
						write(s);
					}
				}
			}
		};
		this.visitor.addVisit(VisitType.Visit, IMethod.class, command);
	}
}
