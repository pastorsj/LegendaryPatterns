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

// TODO: Auto-generated Javadoc
/**
 * The Class SDEditOutputStream.
 */
public class SDEditOutputStream {

	/** The visitor. */
	private final IVisitor visitor;
	
	/** The model. */
	private IModel model;
	
	/** The builder. */
	private StringBuilder builder;
	
	/** The classes. */
	private Set<String> classes;
	
	/** The method calls. */
	private List<String> methodCalls;
	
	/** The depth. */
	private int depth;
	
	/** The orig depth. */
	private final int origDepth;

	/**
	 * Instantiates a new SD edit output stream.
	 *
	 * @param model the model
	 * @param depth the depth
	 * @param builder the builder
	 */
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

	/**
	 * Initialize visitors.
	 */
	private void initializeVisitors() {
		this.previsitMethod();
		this.visitMethod();
		this.postvisitMethod();
	}

	/**
	 * Write.
	 *
	 * @param s the s
	 */
	public void write(String s) {
		builder.append(s);
	}

	/**
	 * Previsit method.
	 */
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
		this.visitor.addVisit(VisitType.PreVisit, IMethod.class, command);
	}

	/**
	 * Visit method.
	 */
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

	/**
	 * Postvisit method.
	 */
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
