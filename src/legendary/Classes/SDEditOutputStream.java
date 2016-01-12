package legendary.Classes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import legendary.Interfaces.IClass;
import legendary.Interfaces.IMethod;
import legendary.Interfaces.IModel;
import legendary.Interfaces.ITraverser;
import legendary.Interfaces.IVisitor;
import legendary.Interfaces.VisitorAdapter;

public class SDEditOutputStream extends VisitorAdapter {

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
	}

	public void write(String s) {
		builder.append(s);
	}

	@Override
	public void previsit(IMethod m) {
		this.depth--;
		if (this.depth == 0)
			return;
		Queue<List<String>> callStack = m.getCallStack();
		if (!callStack.isEmpty() && !callStack.peek().isEmpty())
			this.classes.add(callStack.peek().get(0));
	}

	@Override
	public void visit(IMethod m) {
		if (this.depth == 0)
			return;
		Queue<List<String>> callStack = m.getCallStack();
		for (List<String> mDetails : callStack) {
			String className = mDetails.get(1);
			for (IClass c : this.model.getClasses()) {
				if (c.getClassName().equals(className)) {
					this.classes.add(className);
					IMethod method = c.getMethods().get(mDetails.get(2));
					this.methodCalls.add(String.format("%s:%s.%s\n",
							mDetails.get(0), mDetails.get(1), mDetails.get(2)));
					ITraverser m2 = (ITraverser) method;
					m2.accept((IVisitor) this);
					break;
				}
			}
		}
	}

	@Override
	public void postvisit(IMethod m) {
		this.depth++;
		if (this.depth == this.origDepth) {
			for (String s : this.classes) {
				this.write(String.format("%s:%s\n", s, s));
			}
			this.write("\n");
			for (String s : this.methodCalls) {
				this.write(s);
			}
		}
	}
}
