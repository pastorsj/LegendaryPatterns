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

/**
 * This class will traverse through the model and create a sequence diagram
 * representation of the model given a starting point of a class and a method
 */
public class SDEditOutputStream {

	/** The visitor. */
	private final IVisitor visitor;

	/** The model. */
	private IModel model;

	/** The builder containing the final representation of the sequence diagram. */
	private StringBuilder builder;

	/** The classes using during the generation of the diagram. */
	private Set<String> classes;

	/** The method calls on the method stack. */
	private List<String> methodCalls;

	/** The depth. */
	private int depth;

	/** The orig depth, since depth can be modified. */
	private final int origDepth;

	/**
	 * Instantiates a new SD edit output stream.
	 *
	 * @param model
	 *            The current representation of the project
	 * @param depth
	 *            The depth at which the user wants to traverse
	 * @param builder
	 *            The final representation of the sequence diagram as a string
	 */
	public SDEditOutputStream(IMethod method, IModel model, int depth,
			StringBuilder builder) {
		this.model = model;
		this.builder = builder;
		this.classes = new HashSet<>();
		this.methodCalls = new ArrayList<>();
		this.depth = depth;
		this.origDepth = depth;
		this.visitor = new VisitorAdapter();
		this.initializeVisitors();
		ITraverser t = (ITraverser) method;
		t.accept(this.visitor);
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
	 * Write to the string builder
	 *
	 * @param s
	 *            a string
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
				if (!callStack.isEmpty()
						&& !callStack.peek().isEmpty()
						&& !classes.contains("/"
								+ callStack.peek().get(0).get(0))) {
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
							if (mDetails.get(2).contains("<init>")
									&& !classes.contains(className)) {
								if (classes.contains("/" + className))
									continue;
								classes.add("/" + className);
							} else if (!classes.contains("/" + className)) {
								classes.add(className);
							}
							IMethod method = c.getMethods()
									.get(mDetails.get(2)).get(params);
							if (method == null)
								continue;
							String s;
							String paramString = Arrays.toString(params
									.toArray());
							s = String.format("%s:%s=%s.%s(%s)\n", mDetails
									.get(0).replace(".", "").replace(":", ""),
									method.getReturnType(), mDetails.get(1)
											.replace(".", "").replace(":", ""),
									(mDetails.get(2).contains("<init>") ? "new"
											: mDetails.get(2)), paramString
											.substring(1,
													paramString.length() - 1));
							if (mDetails.get(0).equals(mDetails.get(1))) {
								if (mDetails.get(2).contains("<init>"))
									continue;
								if (!methodCalls.get(methodCalls.size() - 1)
										.equals(s)) {
									methodCalls.add(s);
								}
							} else if (methodCalls.size() < 1
									|| !methodCalls.get(methodCalls.size() - 1)
											.equals(s))
								methodCalls.add(s);
							ITraverser m2 = (ITraverser) method;
							m2.accept((IVisitor) that.visitor);
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
						write(String.format("%s:%s\n", s.replace(".", "")
								.replace(":", ""),
								(s.startsWith("/") ? s.substring(1) : s)
										.replace(".", "").replace(":", "")));
					}
					write("\n");
					for (String s : methodCalls) {
						write(s);
					}
				}
			}
		};
		this.visitor.addVisit(VisitType.PostVisit, IMethod.class, command);
	}
}
